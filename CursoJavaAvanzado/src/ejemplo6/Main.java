package ejemplo6;

import java.util.*;
//
//Mostrar la diferencia entre una lista mutable (que se puede modificar) y una lista inmutable (que no se puede cambiar).
//

public class Main {
    public static void main(String[] args) {
        // Lista mutable​
        List<String> mutable = new ArrayList<>();
        mutable.add("Hola");
        mutable.set(0, "Chao"); // ✅ Modificación permitida​
        System.out.println("Mutable: " + mutable);
        // Lista inmutable
        List<String> inmutable = List.of("Hola");
        // inmutable.set(0, "Chao"); // ❌ Esto lanzaría una excepción si se descomenta​
        System.out.println("Inmutable: " + inmutable);
    }

}
