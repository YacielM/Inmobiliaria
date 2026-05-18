package com.example.inmobiliariaapp.modelo;

import java.io.Serializable;

public class Propietario implements Serializable {

    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private String clave;
    private String avatar;

    private CambioClaveView camPass;

    public Propietario() { }

    public Propietario(int id, String nombre, String apellido, String dni, String telefono, String email, String clave, String avatar) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.clave = clave;
        this.avatar = avatar;
    }

    public Propietario(int id, String nombre, String apellido, String dni, String telefono, String email, String clave) {
        this(id, nombre, apellido, dni, telefono, email, clave, null);
    }

    public Propietario(String nombre, String apellido, String dni, String telefono, String email, String clave) {
        this(0, nombre, apellido, dni, telefono, email, clave, null);
    }

    public Propietario(int id, String nombre, String apellido, String dni, String telefono) {
        this(id, nombre, apellido, dni, telefono, null, null, null);
    }

    // Getters / Setters estándar
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Alias para compatibilidad con código que espera getIdPropietario()
    public int getIdPropietario() {
        return id;
    }

    public void setIdPropietario(int id) {
        this.id = id;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public CambioClaveView getCamPass() {
        return camPass;
    }

    public void setCamPass(CambioClaveView camPass) {
        this.camPass = camPass;
    }

    @Override
    public String toString() {
        return "Propietario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
