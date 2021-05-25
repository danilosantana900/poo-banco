package code.java;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Pessoa implements IPessoa {
    private String nome;
    private String documento;
    private List<Conta> contas;

    public Pessoa () {

    }

    public Pessoa(String nome, String documento) {
        this.nome = nome;
        this.documento = documento;
        this.contas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public List<Conta> getContas() {
        return contas;
    }

    @Override
    public void abrirConta(Conta conta) {
        if (this.contas.size() == 0) {
            contas.add(conta);
        } else {
            if (contaCadastrada(conta)) {
                System.out.println("Conta já cadastrada");
                return;
            }
            contas.add(conta);
        }
        System.out.println("Conta cadastrada com sucesso");
    }

    @Override
    public void fecharConta(Conta conta) {
        if (contaCadastrada(conta) && conta.podeRemover()) {
            contas.remove(conta);
            System.out.println("Conta removida com sucesso");
        } else {
            System.out.println("Conta não encontrada ou possui saldo diferente de zero");
        }
    }

    @Override
    public void sacar(Conta conta, BigDecimal valor) {
        if (saldoInsuficiente(conta, valor)) {
            System.out.println("Valor de saque superior ao disponivel!");
            return;
        }
        conta.setSaldo(conta.getSaldo().subtract(valor));
    }

    @Override
    public void depositar(Conta conta, BigDecimal valor) {
        conta.setSaldo(conta.getSaldo().add(valor));
    }

    @Override
    public void transferir(Conta contaOrigem, Conta contaDestino, BigDecimal valor) {
        if (contaDestino instanceof ContaInvestimento) {
            System.out.println("Utilize método Investir");
            return;
        }
        movimentarValores(contaOrigem, contaDestino, valor);
    }

    @Override
    public void investir(Conta contaOrigem, ContaInvestimento contaInvestimento, BigDecimal valor) {
        if (!(contaInvestimento instanceof ContaInvestimento)) {
            System.out.println("Operação somente pode ser realizada para Conta Investimeto");
            return;
        }
        movimentarValores(contaOrigem, contaInvestimento, valor);
    }

    @Override
    public BigDecimal consultarSaldo(Conta conta) {
        if (conta == null) {
            System.out.println("Conta não existe!");
            return new BigDecimal(0);
        }
        return conta.getSaldo();
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", documento='" + documento + '\'' +
                ", contas=" + contas +
                '}';
    }

    private boolean saldoInsuficiente (Conta conta, BigDecimal valor) {
        return valor.compareTo(conta.getSaldo()) > 0 ? true : false;
    }

    public boolean podeRemover () {
        for (int i = 0; i < this.contas.size(); i++) {
            if (!this.contas.get(i).podeRemover()) {
                return false;
            }
        }
        return true;
    }

    private boolean contaCadastrada (Conta conta) {
        for (int i = 0; i < this.contas.size(); i++) {
            if (this.contas.get(i).equals(conta)) {
                return true;
            }
        }
        return false;
    }

    private void movimentarValores (Conta contaOrigem, Conta contaDestino, BigDecimal valor) {
        if (saldoInsuficiente(contaOrigem, valor)) {
            System.out.println("Valor de transferência superior ao disponível!");
            return;
        }

        if (contaDestino == null) {
            System.out.println("Conta destino não existe!");
            return;
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));
    }
}
