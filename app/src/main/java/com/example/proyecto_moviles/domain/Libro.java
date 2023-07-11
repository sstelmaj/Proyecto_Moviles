package com.example.proyecto_moviles.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Libro implements Serializable {

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



    /*
    @SerializedName("lib_fecha_creado")
    private String fecha_creado;

    @SerializedName("lib_libposicion")
    private String posicion;

    @SerializedName("lib_disponible")

    private char disponible;

    //@SerializedName("subcategoria")
    //private int subCategoria;

    //@SerializedName("url")
    //private String url;
    */

    public Libro() {
    }

    public Libro(String titulo, String autor) {
        this.titulo = titulo;
        this.autores = autor;
    }

    public Libro(int id, int stock, String isbn, String titulo, String imagen, String descripcion, String autores, String edicion, String novedades, String fechaLanzamiento, String idioma, Double puntuacion, String vigencia) {
        this.id = id;
        this.stock = stock;
        this.isbn = isbn;
        this.titulo = titulo;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.autores = autores;
        this.edicion = edicion;
        this.novedades = novedades;
        this.fechaLanzamiento = fechaLanzamiento;
        this.idioma = idioma;
        this.puntuacion = puntuacion;
        this.vigencia = vigencia;
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

}
