package code.java;

public interface IBanco {

    void cadastrarCliente (Pessoa cliente);

    void removerCliente (Pessoa cliente);

    int selecionarCliente (String nome, String documento);
}
