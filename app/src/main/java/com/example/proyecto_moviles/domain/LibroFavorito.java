package com.example.proyecto_moviles.domain;

import com.example.proyecto_moviles.domain.Categoria;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LibroFavorito implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("stock")
    private int stock;
    @SerializedName("isbn")
    private String isbn;
    @SerializedName("titulo")
    private String titulo;
    @SerializedName("imagen")
    private String imagen;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("autores")
    private String autores;
    @SerializedName("edicion")
    private String edicion;

    @SerializedName("fav_id")
    private int fav_id;
    @SerializedName("novedades")
    private String novedades;
    @SerializedName("fechaLanzamiento")
    private String fechaLanzamiento;
    @SerializedName("idioma")
    private String idioma;
    @SerializedName("puntuacion")
    private Double puntuacion;
    @SerializedName("vigencia")
    private String vigencia;
    @SerializedName("categorias")
    private List<Categoria> categorias;



    public LibroFavorito() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getNovedades() {
        return novedades;
    }

    public void setNovedades(String novedades) {
        this.novedades = novedades;
    }

    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(String fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public int getFav_id() {
        return fav_id;
    }

    public void setFav_id(int fav_id) {
        this.fav_id = fav_id;
    }
}
