package ejemplo5;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        List<String> lista = new LinkedList<>();
        Set<String> conjunto = new TreeSet<>();
        Map<String, Integer> mapa = new LinkedHashMap<>();

        // Ejemplos de métodos comunes​
        lista.add("uno");
        lista.add("dos");
        System.out.println("Lista: " + lista);
        System.out.println("Contiene 'uno': " + lista.contains("uno"));
        System.out.println("Tamaño de lista: " + lista.size());

        conjunto.add("beta");
        conjunto.add("alfa");
        conjunto.add("beta"); // Ignorado, ya que TreeSet no permite duplicados​

        System.out.println("Set ordenado: " + conjunto);
        System.out.println("Set contiene 'alfa': " + conjunto.contains("alfa"));

        mapa.put("clave1", 100);
        mapa.put("clave2", 200);
        mapa.put("clave1", 300); // Sobrescribe​
        System.out.println("Mapa: " + mapa);
        System.out.println("Valor de 'clave1': " + mapa.get("clave1"));
    }
}
