package ejemplo2;
import java.util.*;
import java.util.stream.Collectors;

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
