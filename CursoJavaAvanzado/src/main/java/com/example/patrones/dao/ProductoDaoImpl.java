package com.example.patrones.dao;

import com.example.patrones.model.Producto;
import org.springframework.stereotype.Repository;

@Repository
public class ProductoDaoImpl implements ProductoDao {
    @Override
    public Producto findById(Long id) {
        return new Producto(id, "Producto demo");
    }

    @Override
    public void save(Producto p) {
        System.out.println("Producto guardado: " + p.getNombre());
    }
}
