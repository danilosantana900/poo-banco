package code.java;

import java.math.BigDecimal;

public class ContaInvestimento extends Conta {

    public ContaInvestimento(BigDecimal saldo) {
        super(saldo);
        super.tipoConta = TipoContaEnum.INVESTIMENTO;
    }
}
