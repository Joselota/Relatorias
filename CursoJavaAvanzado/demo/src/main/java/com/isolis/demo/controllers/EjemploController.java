package com.isolis.demo.controllers;

import com.isolis.demo.models.Empleado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empleados")
public class EjemploController {
    
    // Simulamos una base de datos en memoria para el ejemplo
    private List<Empleado> empleados = new ArrayList<>();
    private Long nextId = 1L;
    
    // Constructor para inicializar datos de ejemplo
    public EjemploController() {
        // Agregar algunos empleados de ejemplo
        empleados.add(new Empleado(nextId++, "Juan", "Pérez", "juan.perez@empresa.com", "555-1234",
                "Desarrollador", "IT", 50000.0, LocalDate.of(2020, 1, 15), true));
        empleados.add(new Empleado(nextId++, "María", "González", "maria.gonzalez@empresa.com", "555-5678",
                "Analista", "IT", 45000.0, LocalDate.of(2021, 3, 10), true));
        empleados.add(new Empleado(nextId++, "Carlos", "Rodríguez", "carlos.rodriguez@empresa.com", "555-9012",
                "Gerente", "Ventas", 70000.0, LocalDate.of(2019, 6, 1), true));
    }
    
    // GET - Obtener todos los empleados
    @GetMapping
    public ResponseEntity<List<Empleado>> obtenerTodosLosEmpleados() {
        return ResponseEntity.ok(empleados);
    }
    
    // GET - Obtener empleado por ID
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Long id) {
        Optional<Empleado> empleado = empleados.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst();
        
        if (empleado.isPresent()) {
            return ResponseEntity.ok(empleado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // POST - Crear nuevo empleado
    @PostMapping
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody Empleado empleado) {
        empleado.setId(nextId++);
        empleado.setFechaContratacion(LocalDate.now());
        empleado.setActivo(true);
        empleados.add(empleado);
        return ResponseEntity.ok(empleado);
    }
    
    // PUT - Actualizar empleado existente
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleadoActualizado) {
        for (int i = 0; i < empleados.size(); i++) {
            Empleado empleado = empleados.get(i);
            if (empleado.getId().equals(id)) {
                empleadoActualizado.setId(id);
                empleados.set(i, empleadoActualizado);
                return ResponseEntity.ok(empleadoActualizado);
            }
        }
        return ResponseEntity.notFound().build();
    }
    
    // DELETE - Eliminar empleado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        boolean removed = empleados.removeIf(empleado -> empleado.getId().equals(id));
        if (removed) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // PATCH - Dar de baja empleado (marcar como inactivo)
    @PatchMapping("/{id}/baja")
    public ResponseEntity<Empleado> darDeBajaEmpleado(@PathVariable Long id) {
        Optional<Empleado> empleadoOpt = empleados.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst();
        
        if (empleadoOpt.isPresent()) {
            Empleado empleado = empleadoOpt.get();
            empleado.darDeBaja();
            return ResponseEntity.ok(empleado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // PATCH - Reactivar empleado
    @PatchMapping("/{id}/reactivar")
    public ResponseEntity<Empleado> reactivarEmpleado(@PathVariable Long id) {
        Optional<Empleado> empleadoOpt = empleados.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst();
        
        if (empleadoOpt.isPresent()) {
            Empleado empleado = empleadoOpt.get();
            empleado.reactivar();
            return ResponseEntity.ok(empleado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // GET - Buscar empleados por departamento
    @GetMapping("/departamento/{departamento}")
    public ResponseEntity<List<Empleado>> obtenerEmpleadosPorDepartamento(@PathVariable String departamento) {
        List<Empleado> empleadosDepartamento = empleados.stream()
                .filter(emp -> emp.getDepartamento() != null && 
                        emp.getDepartamento().equalsIgnoreCase(departamento))
                .toList();
        return ResponseEntity.ok(empleadosDepartamento);
    }
    
    // GET - Obtener solo empleados activos
    @GetMapping("/activos")
    public ResponseEntity<List<Empleado>> obtenerEmpleadosActivos() {
        List<Empleado> empleadosActivos = empleados.stream()
                .filter(Empleado::isActivo)
                .toList();
        return ResponseEntity.ok(empleadosActivos);
    }
}
