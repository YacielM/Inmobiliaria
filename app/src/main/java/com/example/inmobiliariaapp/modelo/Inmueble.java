package com.example.inmobiliariaapp.modelo;

public class Inmueble {
    private int id;
    private String direccion;
    private String tipo; // ej. "Departamento", "Casa"
    private double precio;
    private boolean disponible;
    // getters y setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}

