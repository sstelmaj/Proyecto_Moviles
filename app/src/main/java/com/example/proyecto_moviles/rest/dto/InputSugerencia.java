package com.example.proyecto_moviles.rest.dto;

public class InputSugerencia {
    private String sug_sugerencia;
    private String sug_nombre_libro;
    private String sug_usu_id;

    public InputSugerencia(String sug_sugerencia, String sug_nombre_libro, String sug_usu_id) {
        this.sug_sugerencia = sug_sugerencia;
        this.sug_nombre_libro = sug_nombre_libro;
        this.sug_usu_id = sug_usu_id;
    }

    public String getSugerencia() {
        return sug_sugerencia;
    }

    public void setSugerencia(String sug_sugerencia) {
        this.sug_sugerencia = sug_sugerencia;
    }

    public String getNombre() {
        return sug_nombre_libro;
    }

    public void setNombreLibro(String sug_nombre_libro) {
        this.sug_nombre_libro = sug_nombre_libro;
    }

    public String getUsusuarioId() {
        return sug_usu_id;
    }

    public void setUsuarioId(String sug_usu_id) {
        this.sug_usu_id = sug_usu_id;
    }
}
