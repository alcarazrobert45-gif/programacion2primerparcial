package com.example.comercioesq.model;

public class Cliente {
    private long id;
    private String nombre;
    private String email;
    private String direccion; // <-- ¡NUEVA PROPIEDAD!
    private String telefono;  // <-- ¡NUEVA PROPIEDAD!


    // Constructor ACTUALIZADO
    public Cliente(long id, String nombre, String email, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    // Getters y Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // GETTERS Y SETTERS NUEVOS
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}