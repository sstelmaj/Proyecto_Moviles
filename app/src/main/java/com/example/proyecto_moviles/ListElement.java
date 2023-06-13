package com.example.proyecto_moviles;

public class ListElement {
    public String color;
    public String titulo;
    public String autor;
    public String fecha;


    public ListElement(String color, String titulo, String autor, String fecha) {
        this.color = color;
        this.titulo = titulo;
        this.autor = autor;
        this.fecha = fecha;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}