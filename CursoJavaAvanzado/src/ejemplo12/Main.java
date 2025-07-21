package ejemplo12;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static List<Integer> aplicarFuncion(List<Integer> numeros, Function<Integer, Integer> f) {
        return numeros.stream()
                      .map(f)
                      .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> numeros = List.of(1, 2, 3, 4, 5);
        // Pasando una lambda: multiplicar por 2​

        List<Integer> resultado = aplicarFuncion(numeros, n -> n * 2);
        System.out.println("Resultado: " + resultado); // → [2, 4, 6, 8, 10]​
    }

}
