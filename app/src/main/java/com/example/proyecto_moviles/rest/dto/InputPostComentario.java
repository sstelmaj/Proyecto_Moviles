package com.example.proyecto_moviles.rest.dto;

public class InputPostComentario {

    private String comet_fecha_hora;
    private String comet_comentario;
    private int comet_usu_id;
    private int comet_lib_id;

    public InputPostComentario(String comet_fecha_hora, String comet_comentario, int comet_usu_id, int comet_lib_id) {
        this.comet_fecha_hora = comet_fecha_hora;
        this.comet_comentario = comet_comentario;
        this.comet_usu_id = comet_usu_id;
        this.comet_lib_id = comet_lib_id;
    }

    public String getComet_fecha_hora() {
        return comet_fecha_hora;
    }

    public void setComet_fecha_hora(String comet_fecha_hora) {
        this.comet_fecha_hora = comet_fecha_hora;
    }

    public String getComet_comentario() {
        return comet_comentario;
    }

    public void setComet_comentario(String comet_comentario) {
        this.comet_comentario = comet_comentario;
    }

    public int getComet_usu_id() {
        return comet_usu_id;
    }

    public void setComet_usu_id(int comet_usu_id) {
        this.comet_usu_id = comet_usu_id;
    }

    public int getComet_lib_id() {
        return comet_lib_id;
    }

    public void setComet_lib_id(int comet_lib_id) {
        this.comet_lib_id = comet_lib_id;
    }
}
