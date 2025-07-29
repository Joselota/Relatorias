package com.example.wildfly;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class RestApplication extends Application {
    // La clase puede estar vacía, la anotación es suficiente
}
