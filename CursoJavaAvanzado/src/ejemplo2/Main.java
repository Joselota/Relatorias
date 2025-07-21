package ejemplo2;
import java.util.*;
import java.util.stream.Collectors;

/*
Primero, se crea una lista de nombres que contiene: "Ingrid", "Pedro", "Camila" e "Iván".
Luego, se procesa esa lista paso a paso usando una cadena de operaciones:
- Se filtran solo los nombres que empiezan con la letra "I". En este caso: "Ingrid" e "Iván".
- Luego, esos nombres se transforman a mayúsculas, quedando como: "INGRID" y "IVÁN".
- Después, se ordenan alfabéticamente.
- Finalmente, el resultado se guarda en una nueva lista.
Por último, se imprime esa lista filtrada y transformada en pantalla.
*/

public class Main {
    public static void main(String[] args) {
        List<String> nombres = Arrays.asList("Ingrid", "Pedro", "Camila", "Iván");

        List<String> resultado = nombres.stream()
                .filter(n -> n.startsWith("I"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Filtrados: " + resultado);

    }

}
