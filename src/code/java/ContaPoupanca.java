package code.java;

import java.math.BigDecimal;

public class ContaPoupanca extends Conta {

    public ContaPoupanca(BigDecimal saldo) {
        super(saldo);
        super.tipoConta = TipoContaEnum.POUPANCA;
    }
}
