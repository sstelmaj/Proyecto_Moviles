package com.example.proyecto_moviles.rest.dto;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.Serializable;

public class RequestWithDataObject implements Serializable {
    private int codigo;
    private String mensaje;
    private JsonObject data;

    public RequestWithDataObject() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }
}
