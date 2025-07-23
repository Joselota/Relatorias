package com.isolis.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class EjemploController {
    @GetMapping("/detalle_info")
    public String info(Model model) {
        model.addAttribute("Titulo", "Servidor en línea");
        model.addAttribute("Servidor", "Este es un ejemplo de controlador en Spring Boot");
        model.addAttribute("Autor", "Ingrid");
        
        // Patrón de inyección de dependencias
        return "detalle_info";
    }

}
