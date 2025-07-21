package ejemplo10;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> palabras = List.of("mesa", "monitor", "mouse", "móvil");

        Predicate<String> empiezaConM = s -> s.startsWith("m");

        List<String> resultado = palabras.stream()
                                         .filter(empiezaConM)
                                         .collect(Collectors.toList());

        System.out.println("Palabras que comienzan con 'm': " + resultado);
    }

}
