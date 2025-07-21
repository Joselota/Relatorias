package ejemplo14;

import java.util.List;
/*
 Este programa recorre una lista de letras y las imprime una por una usando un bucle for-each.
 */
public class Main {
    public static void main(String[] args) {
        var lista = List.of("A", "B", "C");
        for (var elemento : lista) {
            System.out.println(elemento);
        }
    }
}
