package com.example.proyecto_moviles.rest.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class LoginDataDto {
    private String token;
    private String datosUsuario;

    public LoginDataDto() {
    }

    public LoginDataDto(String token, String datosUsuario) {
        this.token = token;
        this.datosUsuario = datosUsuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDatosUsuario() {
        return datosUsuario;
    }

    public void setDatosUsuario(String datosUsuario) {
        this.datosUsuario = datosUsuario;
    }
}
