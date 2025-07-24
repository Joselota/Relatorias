package com.example.patrones.singleton;

public class Logger {
    private static final Logger instance = new Logger();
    private Logger() {}

    public static Logger getInstance() {
        return instance;
    }

    public void log(String mensaje) {
        System.out.println("LOG: " + mensaje);
    }
}
