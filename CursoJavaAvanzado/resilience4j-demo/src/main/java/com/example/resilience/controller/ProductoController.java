package com.example.resilience.controller;

import com.example.resilience.model.Producto;
import com.example.resilience.service.ProductoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/{id}")
    public Producto getProducto(@PathVariable Long id) {
        return productoService.obtenerProducto(id);
    }
}
