package com.example.organizingchristmas.ui_organizador.cronograma;

import java.time.LocalTime;

public class Evento {

    String hora;
    String descripcion;
    String organizador;

    public Evento(String hora, String descripcion, String organizador) {
        this.hora = hora;
        this.descripcion = descripcion;
        this.organizador = organizador;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }
}
