
package com.example.securitydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/public")
public class AuthController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello world - public endpoint";
    }
}
