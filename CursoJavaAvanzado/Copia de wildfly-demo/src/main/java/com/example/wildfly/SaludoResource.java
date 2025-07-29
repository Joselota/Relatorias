
package com.example.wildfly;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/saludo")
public class SaludoResource {
    @GET
    @Produces("text/plain")
    public String saludar() {
        return "Hola desde WildFly";
    }
}
