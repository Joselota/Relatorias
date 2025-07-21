package ejemplo8;
import java.util.*;
import java.util.function.Consumer;

/*
 Este programa recorre una lista de nombres y muestra cada uno en mayúsculas, usando una función lambda con Consumer.
 Recordando: Una función lambda es una forma corta y moderna de escribir una función anónima
 */

public class Main {
    public static void main(String[] args) {
        List<String> nombres = List.of("Ingrid", "Pedro", "Camila");
        // Definimos un Consumer que imprime en mayúsculas​
        Consumer<String> imprimirEnMayusculas = nombre -> System.out.println(nombre.toUpperCase());
        // Lo usamos en un forEach​
        nombres.forEach(imprimirEnMayusculas);
    }

}