package com.example.serj.restizv.actividades;

import android.support.v4.app.FragmentActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.serj.restizv.R;
import com.example.serj.restizv.fragmentos.Detalle;
import com.example.serj.restizv.fragmentos.Lista;
import com.example.serj.restizv.objetos.Actividad;
import com.example.serj.restizv.objetos.Grupo;
import com.example.serj.restizv.objetos.Profesor;

import java.util.ArrayList;


public class Principal extends FragmentActivity implements Lista.Callbacks{

    private boolean horizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        getActionBar().setIcon(R.drawable.izv_icon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_nueva) {
            return tipoActividad();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Lista fragmentLista = (Lista)getSupportFragmentManager().findFragmentById(R.id.fragment_lista);
        fragmentLista.setEscuchador(this);
        Detalle fragmentDetalle = (Detalle)getSupportFragmentManager().findFragmentById(R.id.fragment_detalle);
        horizontal = fragmentDetalle != null && fragmentDetalle.isInLayout();
    }

    private boolean tipoActividad(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Lista f = (Lista)getSupportFragmentManager().findFragmentById(R.id.fragment_lista);
        builder.setTitle("Tipo de actividad");
        builder.setMessage("Escoja el tipo de actividad");
        builder.setPositiveButton("Complementaria", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                f.anadir(true);
            }
        });
        builder.setNegativeButton("Extraescolar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                f.anadir(false);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return true;
    }

    @Override
    public void onItemSelected(Actividad actividad, Profesor profesor, Grupo grupo) {
        if(horizontal){
            // Mostrar detalle en fragmento Detalle
            ((Detalle)getSupportFragmentManager().findFragmentById(R.id.fragment_detalle)).setDetalle(actividad, profesor, grupo);
        } else {
            // Mostrar detalle en actividad Secundaria
            Intent intent = new Intent(Principal.this, Secundaria.class);
            intent.putExtra("actividad", actividad);
            intent.putExtra("profesor", profesor);
            intent.putExtra("grupo", grupo);
            startActivity(intent);
        }
    }
}
