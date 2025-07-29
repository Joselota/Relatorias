package com.isolis.demo.controllers;

import java.util.HashMap;
import java.util.Map;

//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;

import com.isolis.demo.controllers.models.Empleados;


@RestController
public class EjemploRestController {

    @GetMapping("/detalle_info2")
    public Map<String, Object> detalle_info2() {
        Empleados empleado1 = new Empleados("Juan", "Perez", "Calle Falsa 123", "Desarrollador", 30, "123456789", 1);
        Empleados empleado2 = new Empleados("Maria", "Lopez", "Avenida Siempre Viva 742", "Diseñadora", 28, "987654321", 2);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Empleado 1", empleado1);
        respuesta.put("Empleado 2", empleado2);

        return respuesta;
    }

}
