package com.example.proyecto_moviles.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
public class Reserva {
    @SerializedName("resv_id")
    int resv_id;
    @SerializedName("resv_usu_id")
    int resv_usu_id;
    @SerializedName("usu_documento")
    int usu_documento;
    @SerializedName("resv_fecha_hora")
    String resv_fecha_hora;
    @SerializedName("resv_lib_id")
    int resv_lib_id;
    @SerializedName("resv_fecha_desde")
    String resv_fecha_desde;
    @SerializedName("resv_fecha_hasta")
    String resv_fecha_hasta;
    @SerializedName("resv_estado")
    char resv_estado;
    @SerializedName("isbn_libro")
    String isbn_libro;

    public int getResv_id() {
        return resv_id;
    }

    public void setResv_id(int resv_id) {
        this.resv_id = resv_id;
    }

    public int getResv_usu_id() {
        return resv_usu_id;
    }

    public void setResv_usu_id(int resv_usu_id) {
        this.resv_usu_id = resv_usu_id;
    }

    public int getUsu_documento() {
        return usu_documento;
    }

    public void setUsu_documento(int usu_documento) {
        this.usu_documento = usu_documento;
    }

    public int getResv_lib_id() {
        return resv_lib_id;
    }

    public void setResv_lib_id(int resv_lib_id) {
        this.resv_lib_id = resv_lib_id;
    }

    public String getResv_fecha_hora() {
        return resv_fecha_hora;
    }

    public void setResv_fecha_hora(String resv_fecha_hora) {
        this.resv_fecha_hora = resv_fecha_hora;
    }

    public String getResv_fecha_desde() {
        return resv_fecha_desde;
    }

    public void setResv_fecha_desde(String resv_fecha_desde) {
        this.resv_fecha_desde = resv_fecha_desde;
    }

    public String getResv_fecha_hasta() {
        return resv_fecha_hasta;
    }

    public void setResv_fecha_hasta(String resv_fecha_hasta) {
        this.resv_fecha_hasta = resv_fecha_hasta;
    }

    public char getResv_estado() {
        return resv_estado;
    }

    public void setResv_estado(char resv_estado) {
        this.resv_estado = resv_estado;
    }

    public String getIsbn_libro() {
        return isbn_libro;
    }

    public void setIsbn_libro(String isbn_libro) {
        this.isbn_libro = isbn_libro;
    }
}
