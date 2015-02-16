package com.example.serj.restizv.objetos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Actividad implements Serializable{

    private String id,
            idProfesor,
            tipo,
            fechaI,
            fechaF,
            lugarI,
            lugarF,
            descripcion,
            alumno;

    public Actividad(){}

    public Actividad(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.idProfesor = object.getString("idprofesor");
            this.tipo = object.getString("tipo");
            this.fechaI = object.getString("fechai");
            this.fechaF = object.getString("fechaf");
            this.lugarI = object.getString("lugari");
            this.lugarF = object.getString("lugarf");
            this.descripcion = object.getString("descripcion");
            this.alumno = object.getString("alumno");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJSON() {
        JSONObject objetoJSON = new JSONObject();
        try {
            objetoJSON.put("idprofesor", this.idProfesor);
            objetoJSON.put("tipo", this.tipo);
            objetoJSON.put("fechai", this.fechaI);
            objetoJSON.put("fechaf", this.fechaF);
            objetoJSON.put("lugari", this.lugarI);
            objetoJSON.put("lugarf", this.lugarF);
            objetoJSON.put("descripcion", this.descripcion);
            objetoJSON.put("alumno", this.alumno);
            return objetoJSON;
        } catch (Exception e) {
            return null;
        }
    }

    public Actividad(String idProfesor, String tipo, String fechaI, String fechaF, String lugarI, String lugarF, String descripcion, String alumno) {
        this.idProfesor = idProfesor;
        this.tipo = tipo;
        this.fechaI = fechaI;
        this.fechaF = fechaF;
        this.lugarI = lugarI;
        this.lugarF = lugarF;
        this.descripcion = descripcion;
        this.alumno = alumno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaI() {
        return fechaI;
    }

    public void setFechaI(String fechaI) {
        this.fechaI = fechaI;
    }

    public String getFechaF() {
        return fechaF;
    }

    public void setFechaF(String fechaF) {
        this.fechaF = fechaF;
    }

    public String getLugarI() {
        return lugarI;
    }

    public void setLugarI(String lugarI) {
        this.lugarI = lugarI;
    }

    public String getLugarF() {
        return lugarF;
    }

    public void setLugarF(String lugarF) {
        this.lugarF = lugarF;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "id='" + id + '\'' +
                ", idProfesor='" + idProfesor + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fechaI='" + fechaI + '\'' +
                ", fechaF='" + fechaF + '\'' +
                ", lugarI='" + lugarI + '\'' +
                ", lugarF='" + lugarF + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
