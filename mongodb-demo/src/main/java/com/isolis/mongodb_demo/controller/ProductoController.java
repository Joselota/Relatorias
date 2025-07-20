package com.isolis.mongodb_demo.controller;

import com.isolis.mongodb_demo.model.Producto;
import com.isolis.mongodb_demo.repository.ProductoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoRepository productoRepo;

    public ProductoController(ProductoRepository productoRepo) {
        this.productoRepo = productoRepo;
    }

    @GetMapping
    public List<Producto> getAll() {
        return productoRepo.findAll();
    }

    @PostMapping
    public Producto create(@RequestBody Producto producto) {
        return productoRepo.save(producto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        productoRepo.deleteById(id);
    }

    // 🆕 Buscar por nombre
    @GetMapping("/buscar")
    public List<Producto> buscarPorNombre(@RequestParam String nombre) {
        return productoRepo.findByNombre(nombre);
    }
    
}
