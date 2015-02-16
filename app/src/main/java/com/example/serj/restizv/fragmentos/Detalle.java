package com.example.serj.restizv.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.serj.restizv.R;
import com.example.serj.restizv.adaptador.AdaptadorActividades;
import com.example.serj.restizv.objetos.Actividad;
import com.example.serj.restizv.objetos.Grupo;
import com.example.serj.restizv.objetos.Profesor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Detalle extends Fragment {

    private View v;
    private TextView tvProfesor, tvLugarI, tvLugarF, tvFechaI, tvFechaF, tvNombreProfesor, tvDepartamento, tvDescripcion;
    private FrameLayout frame_detalle;

    public Detalle() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_detalle, container, false);
        initComponents();
        return v;
    }

    public void setDetalle(Actividad actividad, Profesor profesor, Grupo grupo){
        // SET DETALLE
        frame_detalle.setVisibility(View.VISIBLE);
        /*String nombre = profesor.getNombre().substring(0, 1).toUpperCase() +
                profesor.getApellidos().substring(0, 1).toUpperCase();*/
        tvProfesor.setText(grupo.getGrupo());
        tvLugarI.setText(actividad.getLugarI());
        tvLugarF.setText(actividad.getLugarF());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = simpleDateFormat.parse(actividad.getFechaI());
            Calendar myCal = new GregorianCalendar();
            myCal.setTime(date);
            tvFechaI.setText(myCal.get(GregorianCalendar.DAY_OF_MONTH)+" de " + AdaptadorActividades.getNombreDelMes(myCal.get(GregorianCalendar.MONTH)));
            date = simpleDateFormat.parse(actividad.getFechaF());
            myCal = new GregorianCalendar();
            myCal.setTime(date);
            tvFechaF.setText(myCal.get(GregorianCalendar.DAY_OF_MONTH)+" de " + AdaptadorActividades.getNombreDelMes(myCal.get(GregorianCalendar.MONTH)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvNombreProfesor.setText(profesor.getNombre() + " " + profesor.getApellidos());
        tvDepartamento.setText(profesor.getDepartamento());
        tvDescripcion.setText(actividad.getDescripcion());
    }

    private void initComponents(){
        tvProfesor = (TextView)v.findViewById(R.id.tvProfesor);
        tvLugarI = (TextView)v.findViewById(R.id.tvLugarI);
        tvLugarF = (TextView)v.findViewById(R.id.tvLugarF);
        tvFechaI = (TextView)v.findViewById(R.id.tvFechaI);
        tvFechaF = (TextView)v.findViewById(R.id.tvFechaF);
        tvNombreProfesor = (TextView)v.findViewById(R.id.tvNombre);
        tvDepartamento = (TextView)v.findViewById(R.id.tvDepartamento);
        tvDescripcion = (TextView)v.findViewById(R.id.tvDescripcion);
        frame_detalle = (FrameLayout)v.findViewById(R.id.frame_detalle);
        frame_detalle.setVisibility(View.INVISIBLE);
    }
}
