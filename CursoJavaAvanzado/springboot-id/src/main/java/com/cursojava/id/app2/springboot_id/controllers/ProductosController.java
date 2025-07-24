package com.cursojava.id.app2.springboot_id.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursojava.id.app2.springboot_id.services.Productos_Services;
import com.cursojava.id.app2.springboot_id.models.Productos;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController // Indica que esta clase es un controlador REST y los métodos devolverán datos directamente (ej. JSON)
@RequestMapping("/api") // Mapea todas las URLs de esta clase para que comiencen con /api
@Tag(name = "Productos", description = "API para gestión de productos")
public class ProductosController {

    // Inyecta el servicio de productos. Spring Boot creará e inyectará una instancia.
    // Esto se llama Inyección de Dependencias.
    private final Productos_Services productosServices;

    // Constructor para la inyección de dependencias del servicio.
    // Spring Boot lo detectará automáticamente.
    public ProductosController() {
        this.productosServices = new Productos_Services();
    }


    // Endpoint para obtener todos los productos (ej. GET /api/productos)
    @GetMapping("/productos")
    @Operation(summary = "Obtener todos los productos", 
               description = "Devuelve una lista con todos los productos disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                    description = "Lista de productos obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = Productos.class)))
    })
    public List<Productos> obtenerTodosLosProductos() {
        return productosServices.findAll();
    }

    // Endpoint para obtener un producto por su ID (ej. GET /api/productos/1)
    @GetMapping("/productos/{id}")
    @Operation(summary = "Obtener producto por ID", 
               description = "Devuelve un producto específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                    description = "Producto encontrado",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = Productos.class))),
        @ApiResponse(responseCode = "404", 
                    description = "Producto no encontrado",
                    content = @Content)
    })
    public Productos obtenerProductoPorId(
            @Parameter(description = "ID del producto a buscar", required = true)
            @PathVariable Long id) {
        Productos producto = productosServices.buscaId(id);
        // Podrías añadir lógica aquí para manejar si el producto es null (no encontrado),
        // por ejemplo, lanzar una excepción HttpNotFound o devolver un ResponseEntity.
        return producto;
    }
}
