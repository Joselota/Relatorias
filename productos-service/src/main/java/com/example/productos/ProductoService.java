
package com.example.productos;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {
    private List<Producto> productos = new ArrayList<>();

    public List<Producto> listarProductos() {
        return productos;
    }

    public Producto guardar(Producto producto) {
        productos.add(producto);
        return producto;
    }
}
