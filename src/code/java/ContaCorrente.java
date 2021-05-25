package code.java;

import java.math.BigDecimal;

public class ContaCorrente extends Conta {

    public ContaCorrente(BigDecimal saldo) {
        super(saldo);
        super.tipoConta = TipoContaEnum.CORRENTE;
    }
}
