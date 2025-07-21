package ejemplo4;
import java.util.*;
/*
Se crea un conjunto (Set) de países usando HashSet.
Luego, se agregan tres países al conjunto:
- "Chile"
- "Argentina"
- Nuevamente "Chile"
Como un Set no permite elementos duplicados, el segundo "Chile" no se agrega.

Recordar: HashSet es una colección en Java que almacena elementos únicos sin un orden garantizado, optimizada para búsquedas y adiciones rápidas.

 */
public class Main {
    public static void main(String[] args) {
        Set<String> paises = new HashSet<>();
        paises.add("Chile");
        paises.add("Argentina");
        paises.add("Chile");

        System.out.println(paises);
    }

}
