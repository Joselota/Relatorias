package com.example.patrones.controller;

import com.example.patrones.model.Producto;
import com.example.patrones.service.ProductoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/{id}")
    public Producto obtener(@PathVariable Long id) {
        return productoService.obtenerProducto(id);
    }

    @PostMapping
    public void guardar(@RequestBody Producto producto) {
        productoService.guardarProducto(producto);
    }
}
