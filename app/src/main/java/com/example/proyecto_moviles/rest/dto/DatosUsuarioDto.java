package com.example.proyecto_moviles.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatosUsuarioDto {
    @JsonProperty("usu_id")
    private int id;
    @JsonProperty("usu_documento")
    private String documento;
    @JsonProperty("usu_nombre")
    private String nombre;
    @JsonProperty("usu_apellido")
    private String apellido;
    @JsonProperty("usu_mail")
    private String mail;
    @JsonProperty("usu_clave")
    private String clave;
    @JsonProperty("usu_telefono")
    private String telefono;
    @JsonProperty("usu_activo")
    private String activo;
    @JsonProperty("usu_tipo_usuario")
    private int tipo;
    @JsonProperty("usu_habilitado")
    private String habilitado;

    public DatosUsuarioDto() {
    }

    public DatosUsuarioDto(int id, String documento, String nombre, String apellido, String mail, String clave, String telefono, String activo, int tipo, String habilitado) {
        this.id = id;
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.clave = clave;
        this.telefono = telefono;
        this.activo = activo;
        this.tipo = tipo;
        this.habilitado = habilitado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }
}
