package code.java;

import java.math.BigDecimal;
import java.util.List;

public class PessoaJuridica extends Pessoa {

    public PessoaJuridica() {

    }

    public PessoaJuridica(String nome, String documento) {
        super(nome, documento);
    }

    @Override
    public void abrirConta(Conta conta) {
        if (conta instanceof ContaPoupanca) {
            System.out.println("PJ não pode criar Conta Poupança");
            return;
        }
        super.abrirConta(conta);
    }

    @Override
    public void sacar(Conta conta, BigDecimal valor) {
        super.sacar(conta, somarTaxaPessoaJuridica(valor));
    }

    @Override
    public void transferir(Conta contaOrigem, Conta contaDestino, BigDecimal valor) {
        super.transferir(contaOrigem, contaDestino, somarTaxaPessoaJuridica(valor));
    }

    @Override
    public BigDecimal consultarSaldo(Conta conta) {
        if (conta instanceof ContaInvestimento) {
            return BigDecimal.valueOf(super.consultarSaldo(conta).doubleValue() * 1.02);
        } else {
            return super.consultarSaldo(conta);
        }
    }

    private BigDecimal somarTaxaPessoaJuridica (BigDecimal valor) {
        return valor.add(valor.multiply(new BigDecimal(0.005)));
    }
}
