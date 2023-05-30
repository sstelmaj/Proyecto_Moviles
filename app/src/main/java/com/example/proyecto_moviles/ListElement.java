package com.example.proyecto_moviles;

public class ListElement {
    public String color;
    public String titulo;
    public String autor;
    public String categoria;


    public ListElement(String color, String titulo, String autor, String categoria) {
        this.color = color;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}