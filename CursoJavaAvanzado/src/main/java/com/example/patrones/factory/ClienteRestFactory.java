package com.example.patrones.factory;

public class ClienteRestFactory {
    public static ClienteRest crearCliente(String tipo) {
        if ("externo".equalsIgnoreCase(tipo)) {
            return new ClienteRESTExterno();
        } else {
            return new ClienteRESTInterno();
        }
    }
}
