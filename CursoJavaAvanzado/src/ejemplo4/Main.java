package ejemplo4;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<String> paises = new HashSet<>();
        paises.add("Chile");
        paises.add("Argentina");
        paises.add("Chile");

        System.out.println(paises);
    }

}
