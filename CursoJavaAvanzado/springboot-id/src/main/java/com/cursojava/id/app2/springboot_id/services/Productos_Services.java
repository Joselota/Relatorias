package com.cursojava.id.app2.springboot_id.services;

import java.util.List;
import java.util.stream.Collectors;

import com.cursojava.id.app2.springboot_id.repositorios.Repo_Productos;
import com.cursojava.id.app2.springboot_id.models.Productos;

public class Productos_Services {

    // Instancia del repositorio para acceder a los datos de los productos
    private Repo_Productos repositorio = new Repo_Productos();

    // Método para obtener todos los productos, aplicando un aumento de precio
    public List<Productos> findAll() {
        // Obtiene todos los productos del repositorio
        return repositorio.findAll()
            .stream() // Convierte la lista a un Stream para operaciones funcionales
            .map(p -> { // Mapea cada producto a una nueva versión
                double precioTotal = p.getPrecio(); 
                p.setPrecio((int) precioTotal); // Actualiza el precio del producto (se hace un cast a int)
                return p; // Devuelve el producto modificado
            })
            .collect(Collectors.toList()); // Recolecta los productos modificados de nuevo en una lista
    }

    // Método para buscar un producto por su ID
    public Productos buscaId(Long idProducto) {
        // Delega la búsqueda al repositorio
        return repositorio.buscaById(idProducto);
    }
}
