package ejemplo19;

public class Main {
    public static void validarEdad(int edad) throws IllegalArgumentException {
        if (edad < 18) {
            throw new IllegalArgumentException("Debes ser mayor de edad.");
        }
        System.out.println("Edad validada correctamente: " + edad);
    }

    public static void main(String[] args) {
        System.out.println("--- Ejercicio: Propagación de Excepciones (throws) ---");

        // --- Escenario 1: Llamada al método que propaga y lo manejamos (try-catch) ---
        System.out.println("\n--- Escenario 1: Manejo de la excepción en main ---");
        try {
            // Intentamos validar una edad que causará una excepción
            System.out.println("Intentando validar edad: 16");
            validarEdad(16); // Esta llamada lanzará una IllegalArgumentException
            System.out.println("Este mensaje no se imprimirá si la excepción se lanza.");
        } catch (IllegalArgumentException e) {
            // Capturamos la excepción y la manejamos
            System.out.println("Error de validación capturado: " + e.getMessage());
        } finally {
            // El bloque finally siempre se ejecuta, haya o no excepción.
            System.out.println("Bloque finally ejecutado.");
        }

        System.out.println("\n--- Escenario 2: Llamada al método con edad válida ---");
        try {
            // Intentamos validar una edad válida
            System.out.println("Intentando validar edad: 25");
            validarEdad(25); // Esta llamada no lanzará una excepción
            System.out.println("Edad validada y procesada.");
        } catch (IllegalArgumentException e) {
            System.out.println("Este mensaje no se imprimirá si no hay excepción.");
        } finally {
            System.out.println("Bloque finally ejecutado.");
        }

    }
}
