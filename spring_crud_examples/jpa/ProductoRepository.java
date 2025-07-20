package com.isolis.api_productos.repository;

// TODO: Update the import below to the correct package where Producto is defined
// Example: import com.isolis.api_productos.model.Producto;
import com.isolis.api_productos.model.Producto;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombre(String nombre);
}