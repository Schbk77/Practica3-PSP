package com.example.serj.restizv.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.serj.restizv.R;
import com.example.serj.restizv.fragmentos.Lista;
import com.example.serj.restizv.objetos.Actividad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AdaptadorActividades extends ArrayAdapter<Lista.ObjetoLista>{

    private Context contexto;
    private int recurso;
    private ArrayList<Lista.ObjetoLista> lista;
    private static LayoutInflater i;

    static class ViewHolder {
        public TextView tvProfesor, tvDescripcion, tvLugarF, tvFechaI;
        public int posicion;
    }

    public AdaptadorActividades(Context context, int resource, ArrayList<Lista.ObjetoLista> objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.recurso = resource;
        this.lista = objects;
        this.i = (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh =  null;
        if(convertView == null) {
            convertView = i.inflate(recurso, null);
            vh = new ViewHolder();
            vh.tvProfesor = (TextView)convertView.findViewById(R.id.tvProfesor);
            vh.tvDescripcion = (TextView)convertView.findViewById(R.id.tvDescripcion);
            vh.tvLugarF = (TextView)convertView.findViewById(R.id.tvLugarF);
            vh.tvFechaI = (TextView)convertView.findViewById(R.id.tvFechaI);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }
        vh.posicion = position;
        String profesor = lista.get(vh.posicion).nombre.substring(0, 1).toUpperCase() +
                lista.get(vh.posicion).apellido.substring(0, 1).toUpperCase();
        vh.tvProfesor.setText(profesor);
        String descripcion = lista.get(vh.posicion).actividad.getDescripcion();
        if(descripcion.length() < 35){
            vh.tvDescripcion.setText(descripcion);
        }else{
            vh.tvDescripcion.setText(descripcion.substring(0, 35) + "...");
        }
        vh.tvLugarF.setText(lista.get(vh.posicion).actividad.getLugarF());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = simpleDateFormat.parse(lista.get(vh.posicion).actividad.getFechaI());
            Calendar myCal = new GregorianCalendar();
            myCal.setTime(date);
            vh.tvFechaI.setText(myCal.get(GregorianCalendar.DAY_OF_MONTH)+" de " + getNombreDelMes(myCal.get(GregorianCalendar.MONTH)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public static String getNombreDelMes(int mes){
        String nombreMes = "";
        switch (mes){
            case GregorianCalendar.JANUARY:
                nombreMes = "Enero";
                break;
            case GregorianCalendar.FEBRUARY:
                nombreMes = "Febrero";
                break;
            case GregorianCalendar.MARCH:
                nombreMes = "Marzo";
                break;
            case GregorianCalendar.APRIL:
                nombreMes = "Abril";
                break;
            case GregorianCalendar.MAY:
                nombreMes = "Mayo";
                break;
            case GregorianCalendar.JUNE:
                nombreMes = "Junio";
                break;
            case GregorianCalendar.JULY:
                nombreMes = "Julio";
                break;
            case GregorianCalendar.AUGUST:
                nombreMes = "Agosto";
                break;
            case GregorianCalendar.SEPTEMBER:
                nombreMes = "Septiembre";
                break;
            case GregorianCalendar.OCTOBER:
                nombreMes = "Octubre";
                break;
            case GregorianCalendar.NOVEMBER:
                nombreMes = "Noviembre";
                break;
            case GregorianCalendar.DECEMBER:
                nombreMes = "Diciembre";
                break;
        }
        return nombreMes;
    }
}
