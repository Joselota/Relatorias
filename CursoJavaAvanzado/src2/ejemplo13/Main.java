package ejemplo13;

import java.util.function.Supplier;
/*
 Este programa usa un Supplier, que es una función sin argumentos que entrega (provee) un valor cuando se le pide, y luego imprime ese valor.
 */
public class Main {
    public static void main(String[] args) {
        Supplier<String> saludo = () -> "¡Hola desde Supplier!";
        System.out.println(saludo.get());
    }
}
