package com.example.patrones.service;

import com.example.patrones.dao.ProductoDao;
import com.example.patrones.model.Producto;
import com.example.patrones.singleton.Logger;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    private final ProductoDao productoDao;

    public ProductoService(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }

    public Producto obtenerProducto(Long id) {
        Logger.getInstance().log("Buscando producto con ID " + id);
        return productoDao.findById(id);
    }

    public void guardarProducto(Producto producto) {
        Logger.getInstance().log("Guardando producto " + producto.getNombre());
        productoDao.save(producto);
    }
}
