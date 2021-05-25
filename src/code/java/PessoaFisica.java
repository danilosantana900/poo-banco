package code.java;

import java.util.List;

public class PessoaFisica extends Pessoa {

    public PessoaFisica() {

    }

    public PessoaFisica(String nome, String documento) {
        super(nome, documento);
    }

    @Override
    public void abrirConta(Conta conta) {
        if (possuiContaCorrente() || (conta instanceof ContaCorrente)) {
            super.abrirConta(conta);
        } else {
            System.out.println("Cliente PF deve possuir Conta Corrente");
        }
    }

    private boolean possuiContaCorrente () {
        for (int i = 0; i < super.getContas().size(); i++) {
            if (super.getContas().get(i) instanceof ContaCorrente) {
                return true;
            }
        }
        return false;
    }
}
