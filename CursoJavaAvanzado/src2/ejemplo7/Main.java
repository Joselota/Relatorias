package ejemplo7;

import java.util.*;
/* 
Se tiene una lista con nombres: ["Ingrid", "Pedro", "Ingrid", "Camila", "Pedro", "Pedro"] <- Algunos nombres están repetidos.
El programa recorre cada nombre de esa lista, uno por uno.
Lleva un registro (en un mapa) de cuántas veces aparece cada nombre.
Si el nombre no ha aparecido antes, comienza contando desde 0.
Cada vez que lo encuentra, le suma 1 al contador de ese nombre.
Al final, el programa imprime un resumen con el total de veces que aparece cada nombre.
*/
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
