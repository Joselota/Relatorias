package ejemplo19;

public class Main {

    // Método validarEdad que PROPAGA (throws) una IllegalArgumentException
    public static void validarEdad(int edad) throws IllegalArgumentException {
        // Si la edad es menor de 18, lanza una nueva IllegalArgumentException
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
        
        // --- Escenario 3 (Discusión): Propagando la excepción en main (¡Esto podría crashear el programa!) ---
        // Descomenta el siguiente bloque para ver cómo main podría propagar la excepción.
        // Si main propaga una excepción que no es RuntimeException, la JVM la capturará
        // y el programa terminará abruptamente.
        /*
        System.out.println("\n--- Escenario 3: Propagando la excepción en main (¡Cuidado!) ---");
        System.out.println("Intentando validar edad: 10 (esta llamada hará que main propague la excepción)");
        validarEdad(10); // main AHORA NECESITARÍA declarar 'throws IllegalArgumentException'
                         // o envolverlo en un try-catch. Si no lo hace, y main no lo maneja,
                         // el programa terminará.
        System.out.println("Este mensaje nunca se imprimirá.");
        */
        
        // Si quisieras que main también propague (¡generalmente no recomendado para main!):
        // public static void main(String[] args) throws IllegalArgumentException { ... }
        // Pero esto terminaría el programa si la excepción se lanza y no hay nadie más arriba para atraparla.
    }
}
