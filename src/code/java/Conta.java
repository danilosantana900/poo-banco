package code.java;

import java.math.BigDecimal;

public class Conta implements IConta {

    private BigDecimal saldo = new BigDecimal(0);
    protected TipoContaEnum tipoConta;

    public Conta(BigDecimal saldo) {
        this.saldo = saldo;
        this.tipoConta = TipoContaEnum.CORRENTE;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getSaldo() {
        return this.saldo;
    }

    public TipoContaEnum getTipoConta() {
        return tipoConta;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "tipoConta=" + tipoConta +
                ", saldo=" + saldo +
                '}';
    }

    public boolean podeRemover () {
        return this.saldo.compareTo(BigDecimal.valueOf(0)) == 0;
    }
}
