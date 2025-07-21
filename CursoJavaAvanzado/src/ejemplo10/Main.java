package ejemplo10;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
 Este programa filtra una lista de palabras y deja solo las que empiezan con la letra "m", usando una función lambda con Predicate.
 Recordar: Un Predicate es una interfaz funcional que representa una condición que devuelve true o false.
 */

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
