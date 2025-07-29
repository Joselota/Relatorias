package com.demo.orm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private EmpleadoRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Empleado e = new Empleado();
        e.setNombre("Ingrid");
        e.setSalario(new BigDecimal("2500.00"));
        repository.save(e);

        repository.findAll().forEach(emp -> System.out.println(emp.getNombre()));
    }
}
