package com.example.swaggerdemo.model;

public class Producto {
    private Long id;
    private String nombre;

    public Producto() {}

    public Producto(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }

    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
