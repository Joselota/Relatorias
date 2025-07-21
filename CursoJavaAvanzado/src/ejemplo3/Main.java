package ejemplo3;
import java.util.*;
/*
 * Se crea una lista vacía de frutas.
Luego, se agregan tres frutas a la lista:
- Primero "Manzana"
- Después "Banana"
- Y nuevamente "Manzana"
   La lista queda así: ["Manzana", "Banana", "Manzana"]
A continuación, el programa:
- Imprime toda la lista completa, tal como está.
- Luego, accede al segundo elemento de la lista (posición 1, ya que en Java los índices empiezan en 0), que es "Banana", y lo muestra.
 */
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