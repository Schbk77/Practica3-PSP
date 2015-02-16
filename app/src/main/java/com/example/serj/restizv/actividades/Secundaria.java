package com.example.serj.restizv.actividades;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.serj.restizv.R;
import com.example.serj.restizv.fragmentos.Detalle;
import com.example.serj.restizv.fragmentos.Lista;
import com.example.serj.restizv.objetos.Actividad;
import com.example.serj.restizv.objetos.Grupo;
import com.example.serj.restizv.objetos.Profesor;

import java.util.ArrayList;


public class Secundaria extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundaria);
        Detalle fragmentDetalle = (Detalle)getSupportFragmentManager().findFragmentById(R.id.fragment_detalle);
        if(fragmentDetalle != null && fragmentDetalle.isInLayout()){
            Actividad actividad = (Actividad) getIntent().getSerializableExtra("actividad");
            Profesor profesor = (Profesor) getIntent().getSerializableExtra("profesor");
            Grupo grupo = (Grupo) getIntent().getSerializableExtra("grupo");
            fragmentDetalle.setDetalle(actividad, profesor, grupo);
        }
    }
}
