package requisito;

import java.util.List;
import modelo.Cliente;
import repositorio.Repositorio;
import repositorio.RepositorioCliente;

public class FachadaCliente {
    
    private RepositorioCliente repoCliente;

    public FachadaCliente() {
        this.repoCliente = new RepositorioCliente();
    }

    // Regra: Não pode cadastrar cliente sem nome ou sem endereço.
    // Regra: O nome do cliente precisa ser único (opcional, mas boa prática seguindo o Produto).
    public Cliente cadastrarCliente(String nome, String endereco) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O nome do cliente não pode ser vazio.");
        }
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new Exception("O endereço do cliente não pode ser vazio.");
        }

        try {
            Repositorio.begin();
            
            // Verifica se já existe um cliente com este nome
            Cliente c = repoCliente.localizar(nome);
            if (c != null) {
                Repositorio.rollback();
                throw new Exception("Já existe um cliente cadastrado com este nome.");
            }

            c = new Cliente(nome, endereco);
            repoCliente.criar(c);
            
            Repositorio.commit();
            return c;
        } catch (Exception e) {
            Repositorio.rollback();
            throw new Exception("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    // Atualização de dados mantendo as validações de texto
    public Cliente atualizarCliente(int id, String nome, String endereco) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O nome do cliente não pode ser vazio.");
        }
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new Exception("O endereço do cliente não pode ser vazio.");
        }

        try {
            Repositorio.begin();
            
            Cliente c = repoCliente.localizar(id);
            if (c == null) {
                Repositorio.rollback();
                throw new Exception("Cliente não encontrado.");
            }

            c.setNome(nome);
            c.setEndereco(endereco);
            repoCliente.atualizar(c);
            
            Repositorio.commit();
            return c;
        } catch (Exception e) {
            Repositorio.rollback();
            throw new Exception("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public void deletarCliente(int id) throws Exception {
        try {
            Repositorio.begin();
            
            Cliente c = repoCliente.localizar(id);
            if (c == null) {
                Repositorio.rollback();
                throw new Exception("Cliente não encontrado.");
            }
            
            // Graças ao CascadeType.REMOVE e orphanRemoval = true na classe Cliente, 
            // ao deletar o cliente, o Hibernate lidará com os pedidos associados a ele.
            repoCliente.deletar(c);
            
            Repositorio.commit();
        } catch (Exception e) {
            Repositorio.rollback();
            throw new Exception("Erro ao deletar cliente: " + e.getMessage());
        }
    }

    public Cliente localizarCliente(int id) {
        return repoCliente.localizar(id);
    }

    public List<Cliente> listarClientes() {
        return repoCliente.listar();
    }

    // Consulta JPQL extra 
    public List<Cliente> consultarClientesPorEndereco(String textoBusca) throws Exception {
        if (textoBusca == null || textoBusca.trim().isEmpty()) {
            throw new Exception("Informe um texto válido para a busca de endereço.");
        }
        return repoCliente.buscarPorEndereco(textoBusca);
    }
}