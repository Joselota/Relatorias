package com.isolis.mongodb_demo.repository;

import com.isolis.mongodb_demo.model.Producto;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductoRepository extends MongoRepository<Producto, String> {
    // Puedes agregar métodos como: List<Producto> findByNombre(String nombre);
    List<Producto> findByNombre(String nombre);
    List<Producto> findByPrecioLessThan(double precio);
    List<Producto> findByStockGreaterThan(int stock);
}
