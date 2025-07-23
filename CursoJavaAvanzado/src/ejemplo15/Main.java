package ejemplo15;

public class Main {
    public static void main(String[] args) {
        Cuenta cuenta = new Cuenta(10000);
        try {
            cuenta.transferir(1000); // Debería lanzar la excepción​

        } catch (SaldoInsuficienteException e) {
            System.out.println("❌ ERROR: " + e.getMessage());
        }
        System.out.println("Saldo final: $" + cuenta.getSaldo());
    }
}