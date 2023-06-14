package com.example.proyecto_moviles.Modelo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.Serializable;

public class Request implements Serializable {
    private int codigo;
    private String mensaje;
    private JsonArray data;

    public Request() {
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
