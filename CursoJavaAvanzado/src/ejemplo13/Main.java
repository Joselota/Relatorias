package ejemplo13;

import java.util.function.Supplier;
public class Main {
    public static void main(String[] args) {
        Supplier<String> saludo = () -> "¡Hola desde Supplier!";
        System.out.println(saludo.get());
    }
}
