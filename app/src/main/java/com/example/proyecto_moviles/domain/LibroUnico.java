package com.example.proyecto_moviles.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
@JsonIgnoreProperties(ignoreUnknown = true)
public class LibroUnico {
    @SerializedName("lib_id")
    private int lib_id;
    @SerializedName("lib_stock")
    private int lib_stock;
    @SerializedName("lib_isbn")
    private String lib_isbn;

    @SerializedName("lib_titulo")
    private String lib_titulo;
    @SerializedName("lib_imagen")
    private String lib_imagen;
    @SerializedName("lib_descripcion")
    private String lib_descripcion;
    @SerializedName("lib_autores")
    private String lib_autores;
    @SerializedName("lib_edicion")
    private String lib_edicion;

    @SerializedName("lib_novedades")
    private String lib_novedades;
    @SerializedName("lib_fecha_creado")
    private String lib_fecha_creado;
    @SerializedName("lib_idioma")
    private String lib_idioma;
    @SerializedName("lib_puntuacion")
    private Double lib_puntuacion;
    @SerializedName("lib_vigencia")
    private String lib_vigencia;

    public int getLib_id() {
        return lib_id;
    }

    public void setLib_id(int lib_id) {
        this.lib_id = lib_id;
    }

    public int getLib_stock() {
        return lib_stock;
    }

    public void setLib_stock(int lib_stock) {
        this.lib_stock = lib_stock;
    }

    public String getLib_isbn() {
        return lib_isbn;
    }

    public void setLib_isbn(String lib_isbn) {
        this.lib_isbn = lib_isbn;
    }

    public String getLib_titulo() {
        return lib_titulo;
    }

    public void setLib_titulo(String lib_titulo) {
        this.lib_titulo = lib_titulo;
    }

    public String getLib_imagen() {
        return lib_imagen;
    }

    public void setLib_imagen(String lib_imagen) {
        this.lib_imagen = lib_imagen;
    }

    public String getLib_descripcion() {
        return lib_descripcion;
    }

    public void setLib_descripcion(String lib_descripcion) {
        this.lib_descripcion = lib_descripcion;
    }

    public String getLib_autores() {
        return lib_autores;
    }

    public void setLib_autores(String lib_autores) {
        this.lib_autores = lib_autores;
    }

    public String getLib_edicion() {
        return lib_edicion;
    }

    public void setLib_edicion(String lib_edicion) {
        this.lib_edicion = lib_edicion;
    }

    public String getLib_novedades() {
        return lib_novedades;
    }

    public void setLib_novedades(String lib_novedades) {
        this.lib_novedades = lib_novedades;
    }

    public String getLib_fecha_creado() {
        return lib_fecha_creado;
    }

    public void setLib_fecha_creado(String lib_fecha_creado) {
        this.lib_fecha_creado = lib_fecha_creado;
    }

    public String getLib_idioma() {
        return lib_idioma;
    }

    public void setLib_idioma(String lib_idioma) {
        this.lib_idioma = lib_idioma;
    }

    public Double getLib_puntuacion() {
        return lib_puntuacion;
    }

    public void setLib_puntuacion(Double lib_puntuacion) {
        this.lib_puntuacion = lib_puntuacion;
    }

    public String getLib_vigencia() {
        return lib_vigencia;
    }

    public void setLib_vigencia(String lib_vigencia) {
        this.lib_vigencia = lib_vigencia;
    }
}
