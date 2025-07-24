package com.example.securitydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private")
public class SecureController {

    @GetMapping("/data")
    public String getSecureData() {
        return "Este contenido está protegido con JWT 🔒";
    }
}