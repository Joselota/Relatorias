package ejemplo8;
import java.util.*;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        List<String> nombres = List.of("Ingrid", "Pedro", "Camila");
        // Definimos un Consumer que imprime en mayúsculas​
        Consumer<String> imprimirEnMayusculas = nombre -> System.out.println(nombre.toUpperCase());
        // Lo usamos en un forEach​
        nombres.forEach(imprimirEnMayusculas);
    }

}