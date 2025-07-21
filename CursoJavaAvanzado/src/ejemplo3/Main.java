package ejemplo3;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        List<String> frutas = new ArrayList<>();
        frutas.add("Manzana");
        frutas.add("Banana");
        frutas.add("Manzana");
        System.out.println(frutas); // Imprime toda la lista​
        System.out.println(frutas.get(1)); // Accede al segundo elemento (índice 1)​
    }

}