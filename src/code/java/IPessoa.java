package code.java;

import java.math.BigDecimal;

public interface IPessoa {

    void abrirConta(Conta conta);

    void fecharConta(Conta conta);

    void sacar(Conta conta, BigDecimal valor);

    void depositar(Conta conta, BigDecimal valor);

    void transferir(Conta contaOrigem, Conta contaDestino, BigDecimal valor);

    void investir(Conta contaOrigem, ContaInvestimento contaInvestimento, BigDecimal valor);

    BigDecimal consultarSaldo(Conta conta);
}
