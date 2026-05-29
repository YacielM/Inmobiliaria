package com.example.inmobiliariaapp.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pago  implements Serializable {
    @SerializedName("idPago")
    private int id;
    private int nroPago;
    private String fechaPago;
    private Double monto;
    private boolean estado;
    private  String detalle;

    private int idContrato;
    private Contrato contrato;

    public Pago(int id, int nroPago, String fechaPago, Double monto, boolean estado, String detalle, int idContrato, Contrato contrato) {
        this.id = id;
        this.nroPago = nroPago;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.estado = estado;
        this.detalle = detalle;
        this.idContrato = idContrato;
        this.contrato = contrato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNroPago() {
        return nroPago;
    }

    public void setNroPago(int nroPago) {
        this.nroPago = nroPago;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
