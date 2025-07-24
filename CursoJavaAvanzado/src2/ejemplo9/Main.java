package ejemplo9;
import java.util.Optional; // Necesitas importar Optional

public class Main { 
    public static Optional<String> obtenerNombreUsuario(boolean existe) {
        return existe ? Optional.of("Ingrid") : Optional.empty();
    }
    public static void main(String[] args) {

        System.out.println("\n--- Escenario : Usuario NO EXISTE ---");
        Optional<String> usuarioNoExistente = obtenerNombreUsuario(false);

        // 1. Usando isPresent()
        if (usuarioNoExistente.isPresent()) {
            System.out.println("1. (isPresent) El nombre de usuario es: " + usuarioNoExistente.get());
        } else {
            System.out.println("1. (isPresent) El usuario no existe.");
        }

        // 2. Usando ifPresent() (no se ejecutará el bloque)
        usuarioNoExistente.ifPresent(nombre -> {
            System.out.println("2. (ifPresent) Ejecutando acción para el usuario: " + nombre); // Esto NO se imprimirá
        });

        // 3. Usando orElse()
        String nombrePorDefectoNoExistente = usuarioNoExistente.orElse("UsuarioInvitado");
        System.out.println("3. (orElse) Nombre de usuario (o por defecto): " + nombrePorDefectoNoExistente);


        // --- Parte 2: Extensión - Llamar a .toUpperCase() con .map() ---

        System.out.println("\n--- Extensión: Usando .map() para transformar el valor ---");

        // Escenario 2: Usuario existente con transformación
        Optional<String> usuarioMayusculas = obtenerNombreUsuario(true)
                                            .map(String::toUpperCase); // Transforma el String a mayúsculas si está presente
        
        System.out.println("Usuario existente en mayúsculas (con map y orElse): " + usuarioMayusculas.orElse("NO_EXISTE"));

        // Escenario 4: Usuario no existente con transformación (map no se ejecuta, orElse proporciona el valor)
        Optional<String> usuarioMayusculasNoExistente = obtenerNombreUsuario(false)
                                                        .map(String::toUpperCase);
        
        System.out.println("Usuario no existente en mayúsculas (con map y orElse): " + usuarioMayusculasNoExistente.orElse("NO_EXISTE"));

        // Otra forma de usar map e ifPresent:
        obtenerNombreUsuario(true)
            .map(s -> "Hola, " + s.toUpperCase() + "!") // Transforma el valor y añade un prefijo/sufijo
            .ifPresent(System.out::println); // Imprime el resultado transformado si está presente

        obtenerNombreUsuario(false)
            .map(s -> "Hola, " + s.toUpperCase() + "!")
            .ifPresent(System.out::println); // No imprime nada si no hay valor

    }
}
