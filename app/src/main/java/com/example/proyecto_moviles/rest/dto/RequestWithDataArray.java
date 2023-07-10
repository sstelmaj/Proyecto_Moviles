package com.example.proyecto_moviles.rest.dto;

import com.google.gson.JsonArray;

import java.io.Serializable;

public class RequestWithDataArray implements Serializable {
    private int codigo;
    private String mensaje;
    private JsonArray data;

    public RequestWithDataArray() {
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

    public JsonArray getData() {
        return data;
    }

    public void setData(JsonArray data) {
        this.data = data;
    }
}
