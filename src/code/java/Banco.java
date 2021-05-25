package code.java;

import java.util.ArrayList;
import java.util.List;

public class Banco implements IBanco {

    private String nome;
    private List<Pessoa> clientes;

    public Banco() {
        this.nome = "Let's Code Banking";
        this.clientes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Pessoa> getClientes() {
        return clientes;
    }

    public void setClientes(List<Pessoa> clientes) {
        this.clientes = clientes;
    }

    public void cadastrarCliente (Pessoa cliente) {
        if (this.clientes.size() == 0) {
            clientes.add(cliente);
        } else {
            if (clienteCadastrado(cliente.getNome(), cliente.getDocumento())) {
                System.out.println("Cliente já cadastrado");
                return;
            }
            clientes.add(cliente);
        }
        System.out.println("Cliente cadastrado com sucesso");
    }

    public void removerCliente (Pessoa cliente) {
        if (clienteCadastrado(cliente.getNome(), cliente.getDocumento()) && cliente.podeRemover()) {
            clientes.remove(retornaPessoa(cliente));
            System.out.println("Cliente removido com sucesso");
        } else {
            System.out.println("Cliente não encontrado ou possui saldo diferente de zero");
        }
    }

    @Override
    public String toString() {
        return "Banco{" +
                "clientes=" + clientes +
                '}';
    }

    private boolean clienteCadastrado (Pessoa cliente) {
        for (int i = 0; i < this.clientes.size(); i++) {
            if (this.clientes.get(i).equals(cliente)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int selecionarCliente (String nome, String documento) {
        return retornaIndiceCliente(nome, documento);
    }

    private int retornaIndiceCliente (String nome, String documento) {
        for (int i = 0; i < this.clientes.size(); i++) {
            if (this.clientes.get(i).getNome().equalsIgnoreCase(nome) &&
                    this.clientes.get(i).getDocumento().equals(documento)) {
                return i;
            }
        }
        return -1;
    }

    private boolean clienteCadastrado (String nome, String documento) {
        for (int i = 0; i < this.clientes.size(); i++) {
            if (this.clientes.get(i).getNome().equalsIgnoreCase(nome) &&
                this.clientes.get(i).getDocumento().equals(documento)) {
                return true;
            }
        }
        return false;
    }

    private Pessoa retornaPessoa (Pessoa cliente) {
        for (int i = 0; i < this.clientes.size(); i++) {
            if (this.clientes.get(i).getNome().equalsIgnoreCase(cliente.getNome()) &&
                    this.clientes.get(i).getDocumento().equals(cliente.getDocumento())) {
                return this.clientes.get(i);
            }
        }
        return null;
    }
}
