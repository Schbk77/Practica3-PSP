package com.example.serj.restizv.fragmentos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serj.restizv.ClienteRestful;
import com.example.serj.restizv.R;
import com.example.serj.restizv.adaptador.AdaptadorActividades;
import com.example.serj.restizv.objetos.Actividad;
import com.example.serj.restizv.objetos.ActividadGrupo;
import com.example.serj.restizv.objetos.Grupo;
import com.example.serj.restizv.objetos.Profesor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

public class Lista extends Fragment {

    private static final String URL_BASE = "http://ieszv.x10.bz/restful/api/";

    private ListView lv;
    private AdaptadorActividades ad;
    private Callbacks escuchador;

    private ArrayList<Actividad> actividades;
    private ArrayList<Grupo> grupos;
    private ArrayList<Profesor> profesores;
    private ArrayList<ObjetoLista> listaObjetos;
    private ArrayList<ActividadGrupo> actividadgrupo;
    private String idProfesor, idGrupo, fechaI, fechaF, lugarI, lugarF, descripcion;
    boolean inicial;
    private String diaI, horaI, diaF, horaF;

    final Calendar calendar = Calendar.getInstance();

    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";

    static class ParametrosPost {
        String url;
        JSONObject json;
        String idGrupo;
    }

    public static class ObjetoLista {
        public Actividad actividad;
        public String nombre,
                apellido;
    }

    public Lista() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lista, container, false);
        lv = (ListView) v.findViewById(R.id.lvActividades);
        actividades = new ArrayList<Actividad>();
        grupos = new ArrayList<Grupo>();
        profesores = new ArrayList<Profesor>();
        listaObjetos = new ArrayList<ObjetoLista>();
        actividadgrupo = new ArrayList<ActividadGrupo>();
        getArrayLists();
        getActividadGrupo();
        ad = new AdaptadorActividades(this.getActivity(), R.layout.detalle_lista, listaObjetos);
        return v;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //Infla el menu que se visualiza al hacer longClick en un elemento del ListView
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_opciones, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //Realiza la acción que se elija del menu contextual
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int posicion = info.position;
        if (id == R.id.opBorrar) {
            return borrar(Integer.parseInt(actividades.get(posicion).getId()));
        }
        return super.onContextItemSelected(item);
    }

    private void initListView() {
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Profesor p = new Profesor();
                for(Profesor pr : profesores){
                    if(pr.getId().equals(actividades.get(position).getIdProfesor())){
                        p = pr;
                    }
                }
                // get grupo from actividadgrupo a where a.idactividad = idactividad;
                Grupo g = new Grupo();
                for(ActividadGrupo ag : actividadgrupo){
                    if(ag.getIdActividad().equals(actividades.get(position).getId())){
                        for(Grupo gr : grupos){
                            if(ag.getIdGrupo().equals(gr.getId())){
                                g = gr;
                            }
                        }
                    }
                }
                escuchador.onItemSelected(actividades.get(position), p, g);
            }
        });
        registerForContextMenu(lv);
    }

    private void getArrayLists() {
        String[] peticiones = new String[3];
        peticiones[0] = "actividad/serj";
        peticiones[1] = "grupo";
        peticiones[2] = "profesor";
        GetRESTful get = new GetRESTful(false);
        get.execute(peticiones);
    }

    private void getActividadGrupo() {
        String[] peticiones = new String[1];
        peticiones[0] = "actividadgrupo";// + idActividad;
        GetRESTful get = new GetRESTful(true);
        get.execute(peticiones);
    }

    private void actualizarListView() {
        actividades = new ArrayList<Actividad>();
        listaObjetos = new ArrayList<ObjetoLista>();
        actividadgrupo = new ArrayList<ActividadGrupo>();
        getActividades();
        getActividadGrupo();
        ad = new AdaptadorActividades(this.getActivity(), R.layout.detalle_lista, listaObjetos);
        initListView();
    }

    private void getActividades() {
        String[] peticiones = new String[1];
        peticiones[0] = "actividad/serj";
        GetRESTful get = new GetRESTful(false);
        get.execute(peticiones);
    }

    private void setObjetoLista(){
        for(Actividad a : actividades){
            ObjetoLista o = new ObjetoLista();
            o.actividad = a;
            for(Profesor p : profesores){
                if(p.getId().equals(a.getIdProfesor())){
                    o.nombre = p.getNombre();
                    o.apellido = p.getApellidos();
                }
            }
            listaObjetos.add(o);
        }
    }

    public void anadir(Boolean complementaria) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        if (complementaria) {
            // DIÁLOGO ACTIVIDAD COMPLEMENTARIA
            final View nuevaComplementaria = inflater.inflate(R.layout.dialog_anadir_complementaria, null);
            builder.setTitle("Nueva actividad complementaria");
            builder.setView(nuevaComplementaria);
            final TextView tvDiaI = (TextView) nuevaComplementaria.findViewById(R.id.tvDiaI);
            final TextView tvHoraI = (TextView) nuevaComplementaria.findViewById(R.id.tvHoraI);
            final TextView tvDiaF = (TextView) nuevaComplementaria.findViewById(R.id.tvDiaF);
            final TextView tvHoraF = (TextView) nuevaComplementaria.findViewById(R.id.tvHoraF);
            // DATEPICKER
            final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                    if (inicial) {
                        diaI = year + "-" + month + "-" + day;
                        tvDiaI.setText(diaI);
                    } else {
                        diaF = year + "-" + month + "-" + day;
                        tvDiaF.setText(diaF);
                    }
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);
            // TIMEPICKER
            final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
                    if (inicial) {
                        horaI = hourOfDay + ":" + minute;
                        tvHoraI.setText(horaI);
                    } else {
                        horaF = hourOfDay + ":" + minute;
                        tvHoraF.setText(horaF);
                    }
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, true);
            // SPINNERS
            final Spinner spProfesor = (Spinner) nuevaComplementaria.findViewById(R.id.spProfesor);
            ArrayAdapter<Profesor> adaptadorProfesor = new ArrayAdapter<Profesor>(getActivity(), android.R.layout.simple_spinner_item, profesores);
            adaptadorProfesor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spProfesor.setAdapter(adaptadorProfesor);
            spProfesor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    idProfesor = profesores.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            final Spinner spGrupo = (Spinner) nuevaComplementaria.findViewById(R.id.spGrupo);
            ArrayAdapter<Grupo> adaptadorGrupo = new ArrayAdapter<Grupo>(getActivity(), android.R.layout.simple_spinner_item, grupos);
            adaptadorGrupo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spGrupo.setAdapter(adaptadorGrupo);
            spGrupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    idGrupo = grupos.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            // EDITTEXT
            final EditText etDescripcion = (EditText) nuevaComplementaria.findViewById(R.id.etDescripcion);
            final EditText etLugarF = (EditText) nuevaComplementaria.findViewById(R.id.etLugarF);
            // ESCUCHADORES TEXTVIEW

            tvDiaI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // DatePicker
                    inicial = true;
                    datePickerDialog.setVibrate(true);
                    datePickerDialog.setYearRange(1985, 2028);
                    datePickerDialog.setCloseOnSingleTapDay(false);
                    datePickerDialog.show(getFragmentManager(), DATEPICKER_TAG);
                }
            });

            tvHoraI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TimePicker
                    inicial = true;
                    timePickerDialog.setVibrate(true);
                    timePickerDialog.setCloseOnSingleTapMinute(false);
                    timePickerDialog.show(getFragmentManager(), TIMEPICKER_TAG);
                }
            });

            tvDiaF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // DatePicker
                    inicial = false;
                    datePickerDialog.setVibrate(true);
                    datePickerDialog.setYearRange(1985, 2028);
                    datePickerDialog.setCloseOnSingleTapDay(false);
                    datePickerDialog.show(getFragmentManager(), DATEPICKER_TAG);
                }
            });

            tvHoraF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TimePicker
                    inicial = false;
                    timePickerDialog.setVibrate(true);
                    timePickerDialog.setCloseOnSingleTapMinute(false);
                    timePickerDialog.show(getFragmentManager(), TIMEPICKER_TAG);
                }
            });
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    fechaI = diaI + " " + horaI;
                    fechaF = diaF + " " + horaF;
                    lugarF = etLugarF.getText().toString();
                    descripcion = etDescripcion.getText().toString();

                    JSONObject actividad = new JSONObject();
                    try {
                        actividad.put("idprofesor", idProfesor);
                        actividad.put("tipo", "complementaria");
                        actividad.put("fechai", fechaI);
                        actividad.put("fechaf", fechaF);
                        actividad.put("lugari", "");
                        actividad.put("lugarf", lugarF);
                        actividad.put("descripcion", descripcion);
                        actividad.put("alumno", "serj");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ParametrosPost parametros = new ParametrosPost();
                    parametros.url = URL_BASE + "actividad";
                    parametros.json = actividad;
                    parametros.idGrupo = idGrupo;
                    PostRESTful post = new PostRESTful();
                    post.execute(parametros);
                }
            });
            builder.setNegativeButton("Cancelar", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            // DIÁLOGO ACTIVIDAD EXTRAESCOLAR
            final View nuevaExtraescolar = inflater.inflate(R.layout.dialog_anadir_extraescolar, null);
            builder.setTitle("Nueva actividad extraescolar");
            builder.setView(nuevaExtraescolar);
            final TextView tvDiaI = (TextView) nuevaExtraescolar.findViewById(R.id.tvDiaI);
            final TextView tvHoraI = (TextView) nuevaExtraescolar.findViewById(R.id.tvHoraI);
            final TextView tvDiaF = (TextView) nuevaExtraescolar.findViewById(R.id.tvDiaF);
            final TextView tvHoraF = (TextView) nuevaExtraescolar.findViewById(R.id.tvHoraF);
            // DATEPICKER
            final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                    if (inicial) {
                        diaI = year + "-" + month + "-" + day;
                        tvDiaI.setText(diaI);
                    } else {
                        diaF = year + "-" + month + "-" + day;
                        tvDiaF.setText(diaF);
                    }
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);
            // TIMEPICKER
            final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
                    if (inicial) {
                        horaI = hourOfDay + ":" + minute;
                        tvHoraI.setText(horaI);
                    } else {
                        horaF = hourOfDay + ":" + minute;
                        tvHoraF.setText(horaF);
                    }
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, true);
            // SPINNERS
            final Spinner spProfesor = (Spinner) nuevaExtraescolar.findViewById(R.id.spProfesor);
            ArrayAdapter<Profesor> adaptadorProfesor = new ArrayAdapter<Profesor>(getActivity(), android.R.layout.simple_spinner_item, profesores);
            adaptadorProfesor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spProfesor.setAdapter(adaptadorProfesor);
            spProfesor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    idProfesor = profesores.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            final Spinner spGrupo = (Spinner) nuevaExtraescolar.findViewById(R.id.spGrupo);
            ArrayAdapter<Grupo> adaptadorGrupo = new ArrayAdapter<Grupo>(getActivity(), android.R.layout.simple_spinner_item, grupos);
            adaptadorGrupo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spGrupo.setAdapter(adaptadorGrupo);
            spGrupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    idGrupo = grupos.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            // EDITTEXT
            final EditText etDescripcion = (EditText) nuevaExtraescolar.findViewById(R.id.etDescripcion);
            final EditText etLugarI = (EditText) nuevaExtraescolar.findViewById(R.id.etLugarI);
            final EditText etLugarF = (EditText) nuevaExtraescolar.findViewById(R.id.etLugarF);
            // ESCUCHADORES TEXTVIEW

            tvDiaI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // DatePicker
                    inicial = true;
                    datePickerDialog.setVibrate(true);
                    datePickerDialog.setYearRange(1985, 2028);
                    datePickerDialog.setCloseOnSingleTapDay(false);
                    datePickerDialog.show(getFragmentManager(), DATEPICKER_TAG);
                }
            });

            tvHoraI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TimePicker
                    inicial = true;
                    timePickerDialog.setVibrate(true);
                    timePickerDialog.setCloseOnSingleTapMinute(false);
                    timePickerDialog.show(getFragmentManager(), TIMEPICKER_TAG);
                }
            });

            tvDiaF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // DatePicker
                    inicial = false;
                    datePickerDialog.setVibrate(true);
                    datePickerDialog.setYearRange(1985, 2028);
                    datePickerDialog.setCloseOnSingleTapDay(false);
                    datePickerDialog.show(getFragmentManager(), DATEPICKER_TAG);
                }
            });

            tvHoraF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TimePicker
                    inicial = false;
                    timePickerDialog.setVibrate(true);
                    timePickerDialog.setCloseOnSingleTapMinute(false);
                    timePickerDialog.show(getFragmentManager(), TIMEPICKER_TAG);
                }
            });
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    fechaI = diaI + " " + horaI;
                    fechaF = diaF + " " + horaF;
                    lugarI = etLugarI.getText().toString();
                    lugarF = etLugarF.getText().toString();
                    descripcion = etDescripcion.getText().toString();

                    JSONObject actividad = new JSONObject();
                    try {
                        actividad.put("idprofesor", idProfesor);
                        actividad.put("tipo", "extraescolar");
                        actividad.put("fechai", fechaI);
                        actividad.put("fechaf", fechaF);
                        actividad.put("lugari", lugarI);
                        actividad.put("lugarf", lugarF);
                        actividad.put("descripcion", descripcion);
                        actividad.put("alumno", "serj");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ParametrosPost parametros = new ParametrosPost();
                    parametros.url = URL_BASE + "actividad";
                    parametros.json = actividad;
                    parametros.idGrupo = idGrupo;
                    PostRESTful post = new PostRESTful();
                    post.execute(parametros);
                }
            });
            builder.setNegativeButton("Cancelar", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private boolean borrar(final int pos) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Borrar actividad");
        alert.setMessage("¿Está seguro de borrar la actividad?");
        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DeleteRESTful delete = new DeleteRESTful();
                delete.execute(pos);
            }
        });
        alert.setNegativeButton("No", null);
        AlertDialog dialog = alert.create();
        dialog.show();
        return true;
    }

    private class DeleteRESTful extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            Log.v("PARAMS[0]: ", params[0] + "");
            Log.v("DELETE: ", ClienteRestful.delete(URL_BASE + "actividad/" + params[0]));
            ClienteRestful.delete(URL_BASE + "actividad/" + params[0]);
            ClienteRestful.delete(URL_BASE + "actividadprofesor/" + params[0]);
            ClienteRestful.delete(URL_BASE + "actividadgrupo/" + params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            actualizarListView();
            ad.notifyDataSetChanged();
        }
    }

    private class PostRESTful extends AsyncTask<ParametrosPost, Void, Void> {

        @Override
        protected Void doInBackground(ParametrosPost... params) {
            // Insertar actividad
            String idActividad = ClienteRestful.post(params[0].url, params[0].json);
            try {
                JSONObject objeto = new JSONObject(idActividad);
                idActividad = objeto.getString("r");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.v("json", params[0].json.toString());
            // Insertar actividadprofesor
            JSONObject actividadProfesor = new JSONObject();
            try {
                actividadProfesor.put("idactividad", idActividad);
                actividadProfesor.put("idprofesor", params[0].json.getString("idprofesor"));
                Log.v("json actividad profesor", actividadProfesor.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String idActividadProfesor = ClienteRestful.post(URL_BASE + "actividadprofesor", actividadProfesor);
            Log.v("actividadprofesor", idActividadProfesor);
            // Insertar actividadgrupo
            JSONObject actividadGrupo = new JSONObject();
            try {
                actividadGrupo.put("idactividad", idActividad);
                actividadGrupo.put("idgrupo", params[0].idGrupo);
                Log.v("json actividad grupo", actividadGrupo.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String idActividadGrupo = ClienteRestful.post(URL_BASE + "actividadgrupo", actividadGrupo);
            Log.v("actividadgrupo", idActividadGrupo);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            actualizarListView();
            ad.notifyDataSetChanged();
        }
    }

    private class GetRESTful extends AsyncTask<String, Void, String[]> {

        boolean isActividadGrupo=false;

        public GetRESTful(boolean isactividadgrupo){
            this.isActividadGrupo = isactividadgrupo;
        }

        @Override
        protected String[] doInBackground(String... params) {
            String[] r = new String[params.length];
            int i = 0;
            for (String s : params) {
                r[i] = ClienteRestful.get(URL_BASE + params[i]);
                i++;
            }
            return r;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            if(isActividadGrupo){
                JSONTokener tokener = new JSONTokener(strings[0]);
                try {
                    JSONArray array = new JSONArray(tokener);

                    for (int i = 0; i< array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        ActividadGrupo ag = new ActividadGrupo(object);
                        actividadgrupo.add(ag);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // GET ACTIVIDADES
                JSONTokener tokener = new JSONTokener(strings[0]);
                try {
                    JSONArray array = new JSONArray(tokener);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject objeto = array.getJSONObject(i);
                        Actividad a = new Actividad(objeto);
                        if (!actividades.contains(a)) {
                            actividades.add(a);
                        }
                    }
                    ad.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (strings.length > 1) {
                    // GET GRUPOS
                    tokener = new JSONTokener(strings[1]);
                    try {
                        JSONArray array = new JSONArray(tokener);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject objeto = array.getJSONObject(i);
                            Grupo g = new Grupo(objeto);
                            grupos.add(g);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // GET PROFESORES
                    tokener = new JSONTokener(strings[2]);
                    try {
                        JSONArray array = new JSONArray(tokener);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject objeto = array.getJSONObject(i);
                            Profesor p = new Profesor(objeto);
                            profesores.add(p);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setObjetoLista();
            }
        }
    }

    // CALLBACKS
    public interface Callbacks {
        public void onItemSelected(Actividad actividad, Profesor profesor, Grupo grupo);
    }

    public void setEscuchador(Callbacks escuchador) {
        this.escuchador = escuchador;
    }

}
