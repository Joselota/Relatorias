package com.isolis.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class EjemploController {

    @GetMapping("/detalle_info")
    public String info(Model model) {
        model.addAttribute("Titulo", "Sevidor en linea"); // Nota: "Sevidor" parece un error tipográfico, debería ser "Servidor"
        model.addAttribute("Servidor", "Este es un ejemplo de controlador en Spring Boot");
        model.addAttribute("Autor", "Ingrid");
        model.addAttribute("Fecha", "2023-10-01");
        return "detalle_info";
    }

}
