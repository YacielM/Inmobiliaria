package com.example.inmobiliariaapp.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Contrato implements Serializable {
    @SerializedName("idContrato")
    private int id;
    private String fechaInicio;
    private String fechaFinalizacion;
    private double montoAlquiler;
    private boolean estado;
    @SerializedName("idInquilino")
    private  int idInquilino;
    private Inquilino inquilino;

    @SerializedName("idInmueble")
    private  int inmuebleId;
    private Inmueble inmueble;


    public Contrato(int id, String fechaInicio, String fechaFinalizacion, double montoAlquiler, boolean estado, int idInquilino, Inquilino inquilino, int inmuebleId, Inmueble inmueble) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFinalizacion = fechaFinalizacion;
        this.montoAlquiler = montoAlquiler;
        this.estado = estado;
        this.inquilino = inquilino;
        this.idInquilino = idInquilino;
        this.inmuebleId = inmuebleId;
        this.inmueble = inmueble;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public double getMontoAlquiler() {
        return montoAlquiler;
    }

    public void setMontoAlquiler(double montoAlquiler) {
        this.montoAlquiler = montoAlquiler;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }
    public int getInmuebleId() {
        return inmuebleId;
    }

    public void setInmuebleId(int inmuebleId) {
        this.inmuebleId = inmuebleId;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }
}