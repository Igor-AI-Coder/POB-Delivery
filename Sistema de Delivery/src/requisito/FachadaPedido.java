package requisito;

import java.time.LocalDate;
import java.util.List;
import modelo.Cliente;
import modelo.Pedido;
import modelo.Produto;
import repositorio.Repositorio;
import repositorio.RepositorioPedido;

public class FachadaPedido {

    private RepositorioPedido repoPedido;

    public FachadaPedido() {
        this.repoPedido = new RepositorioPedido();
    }

    /**
     * Regra de Negócio / Requisito: Cadastrar um novo pedido no sistema
     */
    public void cadastrarPedido(LocalDate data, Cliente cliente, List<Produto> produtos) throws Exception {
        if (data == null) {
            throw new Exception("A data do pedido não pode ser nula.");
        }
        if (cliente == null) {
            throw new Exception("Todo pedido precisa estar associado a um cliente válido.");
        }
        if (produtos == null || produtos.isEmpty()) {
            throw new Exception("Não é possível gerar um pedido sem produtos selecionados.");
        }

        try {
            Repositorio.begin();

            Pedido novoPedido = new Pedido(data, cliente);
            for (Produto prod : produtos) {
                novoPedido.adicionarProduto(prod);
            }
            
            cliente.adicionarPedido(novoPedido);

            repoPedido.criar(novoPedido);
            
            Repositorio.commit();
        } catch (Exception e) {
            Repositorio.rollback();
            throw new Exception("Erro ao salvar o pedido: " + e.getMessage());
        }
    }

 
    public void deletarPedido(int id) throws Exception {
        Pedido pedido = repoPedido.localizar(id);
        if (pedido == null) {
            throw new Exception("Pedido com ID #" + id + " não foi encontrado.");
        }

        try {
            Repositorio.begin();
            
            // Remove a referência bidirecional para evitar inconsistências na sessão do Hibernate
            Cliente cliente = pedido.getCliente();
            if (cliente != null) {
                cliente.removerPedido(pedido);
            }

            repoPedido.deletar(pedido);
            
            Repositorio.commit();
        } catch (Exception e) {
            Repositorio.rollback();
            throw new Exception("Erro ao deletar o pedido: " + e.getMessage());
        }
    }

    /**
     * Retorna a listagem completa de todos os pedidos
     */
    public List<Pedido> listarTodosPedidos() {
        return repoPedido.listar();
    }

    /**
     * Consulta 1
     */
    public List<Pedido> consultarPedidosPorData(LocalDate data) throws Exception {
        if (data == null) {
            throw new Exception("Informe uma data válida para a consulta.");
        }
        return repoPedido.buscarPorData(data);
    }

    /**
     * Consulta 2
     */
    public List<Pedido> consultarPedidosComProdutosAcimaDe(double preco) throws Exception {
        if (preco < 0) {
            throw new Exception("O valor de preço para filtro não pode ser negativo.");
        }
        return repoPedido.buscarPorPrecoProdutoMaiorQue(preco);
    }
}