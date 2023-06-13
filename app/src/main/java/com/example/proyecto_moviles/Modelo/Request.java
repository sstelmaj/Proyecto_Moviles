package com.example.proyecto_moviles.Modelo;

import java.io.Serializable;

public class Request implements Serializable {
    private int codigo;
    private String mensaje;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
