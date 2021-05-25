package code.java;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Aplicacao {

    Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Aplicacao aplicacao = new Aplicacao();
        aplicacao.iniciar();
    }

    private void iniciar() {
        menuBanco();
        //testesDevenvolvimento();
    }

    private void menuBanco() {
        Banco banco = new Banco();
        int opcao = 0;
        do {
            System.out.printf("\n+------------------------------------------------------------+\n" +
                            "%s \n" +
                            "  Digite a opção desejada \n" +
                            "  [1] - para cadastrar Cliente \n" +
                            "  [2] - para remover Cliente \n" +
                            "  [3] - para acessar cliente \n" +
                            "  [9] - para SAIR \n" +
                            "+------------------------------------------------------------+\n",
                            banco.getNome().toUpperCase(Locale.ROOT));

            System.out.printf("Informe a opção: ");
            opcao = input.nextInt();

            switch (opcao) {
                case 1: adicionarCliente(banco); break;
                case 2: removerCliente(banco); break;
                case 3: menuCliente(banco); break;
            }
        } while (opcao != 9);
    }

    private void menuCliente(Banco banco) {
        String nome, documento;
        System.out.printf("Informe nome: ");
        nome = input.next();
        System.out.printf("Informe documento: ");
        documento = input.next();

        int indice = banco.selecionarCliente(nome, documento);

        if (indice < 0) {
            System.out.println("Cliente não cadastrado");
            return;
        }

        int opcao = 0;
        do {
            System.out.printf("\n+------------------------------------------------------------+\n" +
                            "%s \n" +
                            "CLIENTE: %s \n" +
                            "  Digite a opção desejada \n" +
                            "  [1] - abrir conta \n" +
                            "  [2] - fechar conta \n" +
                            "  [3] - sacar \n" +
                            "  [4] - depositar \n" +
                            "  [5] - transferir \n" +
                            "  [6] - investir \n" +
                            "  [7] - consultar saldo \n" +
                            "  [9] - para SAIR \n" +
                            "+------------------------------------------------------------+\n",
                            banco.getNome().toUpperCase(Locale.ROOT),
                            banco.getClientes().get(indice).getNome());

            System.out.printf("Informe a opção: ");
            opcao = input.nextInt();

            switch (opcao) {
                case 1: abrirConta(banco.getClientes().get(indice)); break;
                case 2: fecharConta(banco.getClientes().get(indice)); break;
                case 3: sacar(banco.getClientes().get(indice)); break;
                case 4: depositar(banco.getClientes().get(indice)); break;
                case 5: tranferir(banco.getClientes().get(indice)); break;
                case 6: investir(banco.getClientes().get(indice)); break;
                case 7: consultar(banco.getClientes().get(indice)); break;
            }
        } while (opcao != 9);
    }

    private void consultar(Pessoa pessoa) {
        int indice = retornaIndiceConta(pessoa, "para consultar o saldo");

        if (indice < 0) {
            System.out.printf("Indice informado não encontrado \n");
            return;
        }
        Conta conta = pessoa.getContas().get(indice);
        System.out.printf("O saldo da conta [%d] tipo %s R$ %.2f",
                indice + 1,
                conta.getTipoConta(),
                pessoa.consultarSaldo(conta));
    }

    private void investir(Pessoa pessoa) {
        int indiceContaOrigem = retornaIndiceConta(pessoa, "origem");
        int indiceContaInvestimento = retornaIndiceConta(pessoa, "investimento");

        if (indiceContaOrigem < 0 || indiceContaInvestimento < 0) {
            System.out.printf("um dos indice das contas informadas não existe \n");
            return;
        }

        System.out.println("Informe o valor a ser investido : ");
        BigDecimal valor = new BigDecimal(0);
        valor = input.nextBigDecimal();

        Conta contaOrigem = pessoa.getContas().get(indiceContaOrigem);
        Conta contaInvestimento = pessoa.getContas().get(indiceContaInvestimento);
        pessoa.transferir(contaOrigem, contaInvestimento, valor);
    }

    private void tranferir(Pessoa pessoa) {
        int indiceContaOrigem = retornaIndiceConta(pessoa, "origem");
        int indiceContaDestino = retornaIndiceConta(pessoa, "destino");

        if (indiceContaOrigem < 0) {
            System.out.printf("Indice conta origem informado não encontrado \n");
            return;
        }

        if (indiceContaDestino < 0) {
            System.out.printf("Indice conta destino informado não encontrado \n");
            return;
        }

        System.out.println("Informe o valor a ser transferido : ");
        BigDecimal valor = new BigDecimal(0);
        valor = input.nextBigDecimal();

        Conta contaOrigem = pessoa.getContas().get(indiceContaOrigem);
        Conta contaDestino = pessoa.getContas().get(indiceContaDestino);
        pessoa.transferir(contaOrigem, contaDestino, valor);
    }

    private void depositar(Pessoa pessoa) {
        int indice = retornaIndiceConta(pessoa);

        if (indice < 0) {
            System.out.printf("Indice informado não encontrado \n");
            return;
        }
        System.out.println("Informe o valor a ser depositado : ");
        BigDecimal valor = new BigDecimal(0);
        valor = input.nextBigDecimal();

        Conta contaDeposito = pessoa.getContas().get(indice);
        pessoa.depositar(contaDeposito, valor);
    }

    private void sacar(Pessoa pessoa) {
        int indice = retornaIndiceConta(pessoa);

        if (indice < 0) {
            System.out.printf("Indice informado não encontrado \n");
            return;
        }
        System.out.println("Informe o valor a ser sacado : ");
        BigDecimal valor = new BigDecimal(0);
        valor = input.nextBigDecimal();

        Conta contaSacar = pessoa.getContas().get(indice);
        pessoa.sacar(contaSacar, valor);
    }

    private void fecharConta(Pessoa pessoa) {
        int indice = retornaIndiceConta(pessoa, "a ser removida");

        if (indice < 0) {
            System.out.printf("Indice informado não encontrado \n");
            return;
        }
        Conta contaRemover = pessoa.getContas().get(indice);
        pessoa.fecharConta(contaRemover);
    }

    private void abrirConta(Pessoa pessoa) {
        mostrarContasCliente(pessoa);
        int opcao = 0;
        do {
            System.out.printf("\n+------------------------------------------------------------+\n" +
                            "  Qual conta deseja abrir \n" +
                            "  [1] - conta corrente \n" +
                            "  [2] - conta poupança \n" +
                            "  [3] - conta investimento \n" +
                            "  [9] - para SAIR \n" +
                            "+------------------------------------------------------------+\n");

            System.out.printf("Informe a opção: ");
            opcao = input.nextInt();
            BigDecimal valor = new BigDecimal(0);

            switch (opcao) {
                case 1:
                    ContaCorrente contaCorrente = new ContaCorrente(valor);
                    pessoa.abrirConta(contaCorrente);
                    break;
                case 2:
                    ContaPoupanca contaPoupanca = new ContaPoupanca(valor);
                    pessoa.abrirConta(contaPoupanca);
                    break;
                case 3:
                    ContaInvestimento contaInvestimento = new ContaInvestimento(valor);
                    pessoa.abrirConta(contaInvestimento);
                    break;
            }
        } while (opcao != 9);
    }

    private void removerCliente(Banco banco) {
        String nome, documento;
        System.out.printf("Informe nome: ");
        nome = input.next();
        System.out.printf("Informe documento: ");
        documento = input.next();

        Pessoa pessoa = new Pessoa(nome, documento);
        banco.removerCliente(pessoa);

        System.out.println(banco.getClientes().toString());
    }

    private void adicionarCliente(Banco banco) {
        int opcao = 0;
        String nome, documento;
        System.out.printf("Digite [1] PJ ou para [2] PF: ");
        opcao = input.nextInt();

        System.out.printf("Informe nome: ");
        nome = input.next();
        System.out.printf("Informe documento: ");
        documento = input.next();

        switch (opcao) {
            case 1:
                PessoaJuridica clientePJ = new PessoaJuridica(nome, documento);
                banco.cadastrarCliente(clientePJ);
                break;
            case 2:
                PessoaFisica clientePF = new PessoaFisica(nome, documento);
                banco.cadastrarCliente(clientePF);
                break;
        }
        System.out.println(banco.getClientes().toString());
    }

    private void mostrarContasCliente(Pessoa pessoa) {
        System.out.printf("\n+------------------------------------------------------------+\n" +
                        "  Cliente: %s possui a(s) contas(s) :  \n" +
                        "  Contas : %s \n" +
                        "+------------------------------------------------------------+\n",
                        pessoa.getNome(),
                        pessoa.getContas().toString());
    }

    private int retornaIndiceConta(Pessoa pessoa) {
        return retornaIndiceConta(pessoa, "");
    }

    private int retornaIndiceConta(Pessoa pessoa, String mensagem) {
        if (pessoa.getContas().size() == 0) {
            System.out.println("Cliente não possui conta(s) ");
            return -1;
        }

        int indice = -1;
        mostrarContasCliente(pessoa);
        System.out.printf("Informe o indice da conta %s : \n", mensagem);
        indice = input.nextInt();

        if (indice > 0 && indice <= pessoa.getContas().size()) {
            return (indice -1);
        } else {
            return -1;
        }
    }

    private void testesDevenvolvimento() {
        Banco banco = new Banco();
        System.out.println("<<<<<<<<<< Aplicação Bancária inicializada >>>>>>>>>>");

        Pessoa cliente1 = new PessoaFisica("Danilo", "123");
        Pessoa cliente2 = new PessoaJuridica("Michele", "321");

        banco.cadastrarCliente(cliente1);
        banco.cadastrarCliente(cliente2);
        System.out.println(banco.toString());

        banco.removerCliente(cliente2);
        System.out.println(banco.toString());

        System.out.printf("\n>>>>>>>>>> Casos 1 <<<<<<<<<<\n");
        for (Pessoa p : banco.getClientes()) {
            System.out.printf("Cliente: %s\n", p.toString());
        }

        BigDecimal valorContaCorrente = new BigDecimal(100);
        System.out.printf("Abertura CC com R$ %.2f \n", valorContaCorrente);
        ContaCorrente contaCorrente = new ContaCorrente(new BigDecimal(0));
        banco.getClientes().get(0).abrirConta(contaCorrente);
        banco.getClientes().get(0).depositar(contaCorrente, valorContaCorrente);
        System.out.printf("Saldo CC: %.2f  \n", banco.getClientes().get(0).getContas().get(0).getSaldo());

        BigDecimal valorSaque = new BigDecimal(99.50);
        System.out.printf("Saque de %.2f \n", valorSaque);
        banco.getClientes().get(0).sacar(contaCorrente, valorSaque);
        System.out.printf("Saldo CC: %.2f  \n", banco.getClientes().get(0).getContas().get(0).getSaldo());

        System.out.printf("Abertura CI com R$ %.2f \n", 0d);
        ContaInvestimento contaInvestimento = new ContaInvestimento(new BigDecimal(0));
        banco.getClientes().get(0).abrirConta(contaInvestimento);
        System.out.printf("Saldo CI: %.2f  \n", banco.getClientes().get(0).getContas().get(1).getSaldo());

        System.out.printf("Transferencia de CC para CI com R$ %.2f \n", contaCorrente.getSaldo());
        banco.getClientes().get(0).transferir(contaCorrente, contaInvestimento, contaCorrente.getSaldo());
        System.out.printf("Saldo CC: %.2f  \n", banco.getClientes().get(0).getContas().get(0).getSaldo());
        System.out.printf("Saldo CI: %.2f  \n", banco.getClientes().get(0).getContas().get(1).getSaldo());

        BigDecimal valorPoupanca = new BigDecimal(5);
        System.out.printf("Abertura CP com R$ %.2f \n", valorPoupanca);
        ContaPoupanca contaPoupanca = new ContaPoupanca(valorPoupanca);
        banco.getClientes().get(0).abrirConta(contaPoupanca);
        System.out.printf("Saldo CP: %.2f  \n", banco.getClientes().get(0).getContas().get(2).getSaldo());

        System.out.println("-------------------");
        System.out.printf("Saldo CC: %.2f  \n", banco.getClientes().get(0).getContas().get(0).getSaldo());
        System.out.printf("Saldo CI: %.2f  \n", banco.getClientes().get(0).getContas().get(1).getSaldo());
        System.out.printf("Saldo CP: %.2f  \n", banco.getClientes().get(0).getContas().get(2).getSaldo());
        System.out.printf("Pode Remover CC: %s  \n", banco.getClientes().get(0).getContas().get(0).podeRemover());

        System.out.printf("\n>>>>>>>>>> Casos 2 <<<<<<<<<<\n");
        BigDecimal valorInvestimento = new BigDecimal(10);
        Pessoa cliente3 = new PessoaJuridica("Maria", "111");
        banco.cadastrarCliente(cliente3);
        System.out.printf("Abertura CI com R$ %.2f \n", valorInvestimento);
        Conta contaInvestimento2 = new ContaInvestimento(valorInvestimento);
        banco.getClientes().get(1).abrirConta(contaInvestimento2);
        System.out.printf("Consulta de Saldo CI: %.2f  \n", banco.getClientes().get(1).consultarSaldo(contaInvestimento2));

        System.out.printf("\n>>>>>>>>>> Casos 3 <<<<<<<<<<\n");
        banco.cadastrarCliente(cliente3);
        Pessoa cliente4 = new PessoaFisica("Roseli", "222");
        banco.cadastrarCliente(cliente4);
        Pessoa cliente5 = new PessoaJuridica("Não Cadastrado", "333");
        banco.removerCliente(cliente5);
        banco.cadastrarCliente(cliente5);
        banco.removerCliente(cliente5);
    }
}