package com.cursojava.id.app2.springboot_id.models;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo que representa un producto")
public class Productos {
    
    @Schema(description = "Identificador único del producto", example = "1")
    private Long idProducto;
    
    @Schema(description = "Nombre del producto", example = "Disco duro 1TB")
    private String nombre;
    
    @Schema(description = "Precio del producto en pesos", example = "500")
    private int precio;

    public Productos(Long idProducto, String nombre, int precio) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
