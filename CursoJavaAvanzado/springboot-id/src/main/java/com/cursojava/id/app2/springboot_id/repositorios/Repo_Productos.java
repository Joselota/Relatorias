package com.cursojava.id.app2.springboot_id.repositorios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.cursojava.id.app2.springboot_id.models.Productos;

public class Repo_Productos {

    // Lista que almacenará los objetos Productos
    List<Productos> datos;

    // Constructor de la clase Repo_Productos
    public Repo_Productos() {
        // Inicializa la lista 'datos' con una serie de objetos Productos
        // Usa Arrays.asList para crear una lista inicial y luego la convierte a ArrayList
        this.datos = new ArrayList<>(Arrays.asList(
            new Productos(1L, "Disco duro 1TB", 500),
            new Productos(2L, "USB 20GB", 400),
            new Productos(3L, "Mouse", 50),
            new Productos(4L, "Teclado", 60),
            new Productos(5L, "Monitor 20\"", 2000),
            new Productos(6L, "Monitor 24\"", 2500),
            new Productos(7L, "Monitor 27\"", 3000),
            new Productos(8L, "Laptop 15\"", 5000),
            new Productos(9L, "Laptop 17\"", 6000),
            new Productos(10L, "Laptop 19\"", 7000)
        ));
    }

    // Método para obtener todos los productos
    public List<Productos> findAll() {
        return datos;
    }

    // Método para buscar un producto por su ID
    public Productos buscaById(Long id) {
        // Utiliza Streams de Java 8 para filtrar la lista
        // .filter(): Filtra los productos cuyo idProducto coincida con el id dado
        // .findFirst(): Devuelve el primer elemento que coincide (encapsulado en un Optional)
        // .orElse(null): Si el Optional está vacío (no se encontró ningún producto), devuelve null
        return datos.stream()
            .filter(producto -> producto.getIdProducto().equals(id))
            .findFirst()
            .orElse(null); // Podrías usar .orElseThrow() o retornar Optional<Productos> para un manejo más robusto
    }
}
