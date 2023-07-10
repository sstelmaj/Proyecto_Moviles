package com.example.proyecto_moviles.domain;

import com.google.gson.annotations.SerializedName;

public class Comentario {
    @SerializedName("comet_id")
    private int id;

    @SerializedName("comet_fecha_hora")
    private String fecha_hora;

    @SerializedName("comet_usu_id")
    private int usu_id;

    @SerializedName("comet_lib_id")
    private int lib_id;

    @SerializedName("comet_comentario")
    private String comentario;

    @SerializedName("comet_referencia_id")
    private int referencia_id;

    @SerializedName("comet_padre_id")
    private int padre_id;

    @SerializedName("comet_vigente")
    private String vigente;

    @SerializedName("usu_nombre")
    private String nombreUsuario;

    @SerializedName("usu_documento")
    private String documentoUsuario;

    public Comentario() {
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public int getUsu_id() {
        return usu_id;
    }

    public void setUsu_id(int usu_id) {
        this.usu_id = usu_id;
    }

    public int getLib_id() {
        return lib_id;
    }

    public void setLib_id(int lib_id) {
        this.lib_id = lib_id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getReferencia_id() {
        return referencia_id;
    }

    public void setReferencia_id(int referencia_id) {
        this.referencia_id = referencia_id;
    }

    public int getPadre_id() {
        return padre_id;
    }

    public void setPadre_id(int padre_id) {
        this.padre_id = padre_id;
    }

    public String getVigente() {
        return vigente;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }
}
