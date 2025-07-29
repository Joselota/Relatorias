package com.example.swaggerdemo.controller;

import com.example.swaggerdemo.model.Producto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/productos")
@Tag(name = "Productos", description = "API para gestión de productos")
public class ProductoController {

    // Almacén en memoria de productos
    private final Map<Long, Producto> productos = new HashMap<>();
    private final AtomicLong contador = new AtomicLong(0);

    // Constructor para inicializar algunos productos de ejemplo
    public ProductoController() {
        // Agregar algunos productos de ejemplo
        guardarProducto(new Producto(null, "Laptop Dell"));
        guardarProducto(new Producto(null, "Mouse Logitech"));
        guardarProducto(new Producto(null, "Teclado Mecánico"));
        guardarProducto(new Producto(null, "Monitor 24 pulgadas"));
    }

    @Operation(summary = "Listar todos los productos", description = "Devuelve la lista completa de productos")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    @GetMapping
    public List<Producto> listarProductos() {
        return new ArrayList<>(productos.values());
    }

    @Operation(summary = "Obtener producto por ID", description = "Devuelve un producto específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        Producto producto = productos.get(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear nuevo producto", description = "Crea un nuevo producto y lo almacena en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos del producto inválidos")
    })
    @PostMapping
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        Long nuevoId = contador.incrementAndGet();
        producto.setId(nuevoId);
        productos.put(nuevoId, producto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos del producto inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        if (!productos.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        producto.setId(id);
        productos.put(id, producto);
        
        return ResponseEntity.ok(producto);
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (productos.containsKey(id)) {
            productos.remove(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
