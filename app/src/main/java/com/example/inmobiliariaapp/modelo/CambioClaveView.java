package com.example.inmobiliariaapp.modelo;

import java.io.Serializable;

public class CambioClaveView implements Serializable {
    private String claveVieja;
    private String claveNueva;
    private String claveRepeticion;

    public CambioClaveView() {}

    public String getClaveVieja() {
        return claveVieja;
    }

    public void setClaveVieja(String claveVieja) {
        this.claveVieja = claveVieja;
    }

    public String getClaveNueva() {
        return claveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }

    public String getClaveRepeticion() {
        return claveRepeticion;
    }

    public void setClaveRepeticion(String claveRepeticion) {
        this.claveRepeticion = claveRepeticion;
    }
}

