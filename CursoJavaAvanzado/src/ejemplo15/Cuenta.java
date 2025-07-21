package ejemplo15;

public class Cuenta {
    private double saldo;
    public Cuenta(double saldoInicial) {
        this.saldo = saldoInicial;
    }
    public void transferir(double monto) {
        if (monto > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente para transferir $" + monto);
        }
        saldo -= monto;
        System.out.println("Transferencia exitosa. Nuevo saldo: $" + saldo);
    }
    public double getSaldo() {
        return saldo;
    }

}