package com.example.serj.restizv.objetos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Marco on 16/2/15.
 */
public class ActividadGrupo implements Serializable{

    private String id,
            idActividad,
            idGrupo;

    public ActividadGrupo(){}

    public ActividadGrupo(JSONObject object){
        try {
            this.id = object.getString("id");
            this.idActividad = object.getString("idactividad");
            this.idGrupo = object.getString("idgrupo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    @Override
    public String toString() {
        return "ActividadGrupo{" +
                "id='" + id + '\'' +
                ", idActividad='" + idActividad + '\'' +
                ", idGrupo='" + idGrupo + '\'' +
                '}';
    }
}
