package com.example.securitydemo.controller;

import com.example.securitydemo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        if ("ingrid".equals(username) && "1234".equals(password)) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok().body("Bearer " + token);
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello public!";
    }
}