package ejemplo4a;

import java.util.*;
/*
Se crea un mapa (Map) llamado stock, donde se asocian nombres de productos (como "Lápiz", "Cuaderno") con una cantidad numérica (por ejemplo, cuántos hay en stock).
Luego se agregan tres elementos:
- Se guarda "Lápiz" con cantidad 10.
- Se guarda "Cuaderno" con cantidad 5.
- Se vuelve a guardar "Lápiz", ahora con cantidad 12.
Como un Map no permite claves repetidas, el segundo valor de "Lápiz" (el 12) reemplaza al primero (10).
Después, se imprimen dos cosas:
- Todo el mapa, que mostrará los productos y sus cantidades.
- El valor asociado a "Lápiz", que será 12. 
 */


public class Main {
    public static void main(String[] args) {
        Map<String, Integer> stock = new HashMap<>();
        stock.put("Lápiz", 10);
        stock.put("Cuaderno", 5);
        stock.put("Lápiz", 12); // Sobrescribe el valor anterior​

        System.out.println(stock);

        System.out.println("Stock de lápices: " + stock.get("Lápiz"));

    }

}
