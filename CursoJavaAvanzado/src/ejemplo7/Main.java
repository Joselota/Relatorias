package ejemplo7;

import java.util.*;
public class Main {
    public static void main(String[] args) {
        List<String> nombres = Arrays.asList("Ingrid", "Pedro", "Ingrid", "Camila", "Pedro", "Pedro");
        Map<String, Integer> conteo = contarNombres(nombres);
        System.out.println(conteo); // → {Ingrid=2, Pedro=3, Camila=1}​
    }

    public static Map<String, Integer> contarNombres(List<String> lista) {
        Map<String, Integer> mapa = new HashMap<>();

        for (String nombre : lista) {
            mapa.put(nombre, mapa.getOrDefault(nombre, 0) + 1);
        }
        return mapa;
    }

}
