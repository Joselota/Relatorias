package ejemplo18;

public class DemoErrores {
    public static void main(String[] args) {
        try {
            String valor = null;
            int longitud = valor.length();
            System.out.println(longitud);
        } catch (NullPointerException e) {
            System.err.println("Error: objeto nulo");
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
        }
    }

}
