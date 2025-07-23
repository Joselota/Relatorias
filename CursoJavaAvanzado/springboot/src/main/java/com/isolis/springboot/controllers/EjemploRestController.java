package com.isolis.springboot.controllers;

import java.util.HashMap;
import java.util.Map;

//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isolis.springboot.models.Empleados;


@RestController
public class EjemploRestController {

    @GetMapping(path = "/detalle_info2")
    public Map<String, Object> detalles_info2() {
        Empleados empleado1 = new Empleados(
            "Juan", 
            "Perez", 
            "Calle Falsa 123", 
            "Desarrollador", 
            30, 
            123456789, 
            1
        );
        Empleados empleado2 = new Empleados(
            "Maria", 
            "Lopez", 
            "Calle Verdadera 456", 
            "Diseñadora", 
            28, 
            123456789, 
            1
        );
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Empleado1", empleado1);
        respuesta.put("Empleado2", empleado2);

        // Patrón de inyección de dependencias
        return respuesta;
    }
}
