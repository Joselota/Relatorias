package com.isolis.api_productos.controller;

// Make sure this import matches the actual package of Producto
import com.isolis.api_productos.model.Producto;
import com.isolis.api_productos.repository.ProductoRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/jpa/productos")
public class ProductoJpaController {

    @Autowired
    private ProductoRepository productoRepository;

    // GET: Listar todos los productos
    @GetMapping
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    // GET: Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Crear producto nuevo
    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto p) {
        Producto creado = productoRepository.save(p);
        return ResponseEntity.ok(creado);
    }

    // PUT: Actualizar producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @Valid @RequestBody Producto p) {
        return productoRepository.findById(id)
                .map(productoExistente -> {
                    productoExistente.setNombre(p.getNombre());
                    productoExistente.setPrecio(p.getPrecio());
                    return ResponseEntity.ok(productoRepository.save(productoExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Eliminar producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // HTTP 204
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404
        }
    }
}
