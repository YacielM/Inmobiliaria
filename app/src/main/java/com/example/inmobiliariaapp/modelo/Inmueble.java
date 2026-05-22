package com.example.inmobiliariaapp.modelo;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Inmueble implements Serializable {
    @SerializedName("idInmueble")
    private int idInmueble;
    private String direccion;
    private String uso;
    private String tipo;
    private int ambientes;
    private int superficie;
    private double latitud;
    private double longitud;
    @SerializedName("valor")
    private double precio; // La API lo llama 'valor', yo lo uso como 'precio'
    private String imagen;
    private boolean disponible;
    private int idPropietario;

    public Inmueble() {}


    public int getIdInmueble() { return idInmueble; }
    public void setIdInmueble(int idInmueble) { this.idInmueble = idInmueble; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getUso() { return uso; }
    public void setUso(String uso) { this.uso = uso; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getAmbientes() { return ambientes; }
    public void setAmbientes(int ambientes) { this.ambientes = ambientes; }

    public int getSuperficie() { return superficie; }
    public void setSuperficie(int superficie) { this.superficie = superficie; }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public void setValor(double valor) { this.precio = valor; }
    public double getValor() { return precio; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public int getIdPropietario() { return idPropietario; }
    public void setIdPropietario(int idPropietario) { this.idPropietario = idPropietario; }
}