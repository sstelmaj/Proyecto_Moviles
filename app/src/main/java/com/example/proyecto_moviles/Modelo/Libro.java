package com.example.proyecto_moviles.Modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Libro implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("lib_isbn")
    private String isbn;
    @SerializedName("lib_titulo")
    private String lib_titulo;
    @SerializedName("lib_descripcion")
    private String lib_descripcion;
    @SerializedName("lib_imagen")
    private String imagen;
    @SerializedName("lib_categoria")
    private int categoria;
    @SerializedName("lib_sub_categoria")
    private int subCategoria;
    @SerializedName("lib_url")
    private String url;
    @SerializedName("lib_stock")
    private int stock;
    @SerializedName("lib_autores")
    private String autores;
    @SerializedName("lib_edicion")
    private String edicion;
    @SerializedName("lib_fecha_lanzamiento")
    private Date fechaLanzamiento;
    @SerializedName("lib_novedades")
    private char novedades;
    @SerializedName("lib_idioma")
    private String idioma;
    @SerializedName("lib_disponible")
    private char disponible;
    @SerializedName("lib_vigente")
    private char vigente;
    @SerializedName("lib_puntuacion")
    private Double puntuacion;

    public Libro() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return lib_titulo;
    }

    public void setTitulo(String titulo) {
        this.lib_titulo = titulo;
    }

    public String getDescripcion() {
        return lib_descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.lib_descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(int subCategoria) {
        this.subCategoria = subCategoria;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public char getNovedades() {
        return novedades;
    }

    public void setNovedades(char novedades) {
        this.novedades = novedades;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public char getDisponible() {
        return disponible;
    }

    public void setDisponible(char disponible) {
        this.disponible = disponible;
    }

    public char getVigente() {
        return vigente;
    }

    public void setVigente(char vigente) {
        this.vigente = vigente;
    }

    public Double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Double puntuacion) {
        this.puntuacion = puntuacion;
    }
}
