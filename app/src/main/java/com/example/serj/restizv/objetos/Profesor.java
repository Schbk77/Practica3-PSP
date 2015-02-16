package com.example.serj.restizv.objetos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Profesor implements Serializable{

    private String id,
            nombre,
            apellidos,
            departamento;

    public Profesor(){}

    public Profesor(JSONObject object){
        try {
            this.id = object.getString("id");
            this.nombre = object.getString("nombre");
            this.apellidos = object.getString("apellidos");
            this.departamento = object.getString("departamento");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJSON(){
        JSONObject objetoJSON = new JSONObject();
        try {
            objetoJSON.put("id", this.id);
            objetoJSON.put("nombre", this.nombre);
            objetoJSON.put("apellidos", this.apellidos);
            objetoJSON.put("departamento", this.departamento);
            return objetoJSON;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Profesor(String id, String nombre, String apellidos, String departamento) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.departamento = departamento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
