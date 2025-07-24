package com.example.patrones.dao;

import com.example.patrones.model.Producto;

public interface ProductoDao {
    Producto findById(Long id);
    void save(Producto p);
}
