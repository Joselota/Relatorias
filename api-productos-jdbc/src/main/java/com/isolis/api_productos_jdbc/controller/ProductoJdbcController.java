package com.isolis.api_productos_jdbc.controller;

import com.isolis.api_productos_jdbc.dao.ProductoJdbcDAO;
import com.isolis.api_productos_jdbc.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/jdbc/productos")
public class ProductoJdbcController {
    @Autowired
    private ProductoJdbcDAO productoDAO;

    @GetMapping
    public List<Producto> listar() {
        return productoDAO.listar();
    }

    @GetMapping("/{id}")
    public Producto obtener(@PathVariable Long id) {
        return productoDAO.obtener(id);
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody Producto p) {
        productoDAO.crear(p);
        return ResponseEntity.ok("Producto creado");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody Producto p) {
        productoDAO.actualizar(id, p);
        return ResponseEntity.ok("Producto actualizado");
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        productoDAO.eliminar(id);
    }
}