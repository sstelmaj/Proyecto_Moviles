package com.example.proyecto_moviles.rest.dto;

public class InputObtenerComentarios {
    private int lib_id;

    public InputObtenerComentarios(int lib_id) {
        this.lib_id = lib_id;
    }

    public int getLib_id() {
        return lib_id;
    }

    public void setLib_id(int lib_id) {
        this.lib_id = lib_id;
    }
}
