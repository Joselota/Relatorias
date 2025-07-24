package com.example.resilience.service;

import com.example.resilience.model.Producto;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProductoService {

    private final WebClient client = WebClient.create("https://api.externo-que-falla.com");

    @Retry(name = "producto-api", fallbackMethod = "fallbackProducto")
    @CircuitBreaker(name = "producto-api", fallbackMethod = "fallbackProducto")
    public Producto obtenerProducto(Long id) {
        return client.get()
                .uri("/productos/" + id)
                .retrieve()
                .bodyToMono(Producto.class)
                .block();
    }

    public Producto fallbackProducto(Long id, Throwable e) {
        return new Producto(id, "Producto por defecto");
    }
}
