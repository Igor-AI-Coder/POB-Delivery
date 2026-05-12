package appconsole;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Cliente;
import modelo.Pedido;
import util.Util;

public class Alterar {

    public Alterar() {
        Util.conectar();
        EntityManager manager = Util.getManager();

        try {
            // Inicia a transação
            manager.getTransaction().begin();

            System.out.println("Buscando pedidos do cliente 'João'...");
            
            // Lembrar que o JPQL faz um JOIN automático por debaixo dos panos.
            String jpql = "select p from Pedido p where p.cliente.nome like :nome";
            TypedQuery<Pedido> query = manager.createQuery(jpql, Pedido.class);
            query.setParameter("nome", "%João%");
            
            List<Pedido> pedidosDoJoao = query.getResultList();

            if (!pedidosDoJoao.isEmpty()) {
                
                // Pega o primeiro pedido retornado na busca
                Pedido pedidoParaRemover = pedidosDoJoao.get(0);
                
                System.out.println("Pedido encontrado: #" + pedidoParaRemover.getId());
                
                // Remove o pedido da lista do cliente na memória
                Cliente cliente = pedidoParaRemover.getCliente();
                if (cliente != null && cliente.getPedidos() != null) {
                    cliente.removerPedido(pedidoParaRemover);
                }

                // Remove de fato o pedido do banco de dados
                manager.remove(pedidoParaRemover); 
                
                manager.getTransaction().commit();
                
                System.out.println("O pedido foi removido com sucesso.");
            } else {
                System.out.println("Nenhum pedido encontrado para este cliente no banco de dados.");
                manager.getTransaction().rollback(); 
            }

        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            System.out.println("Erro ao alterar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Util.desconectar();
        }
    }

    public static void main(String[] args) {
        new Alterar();
    }
}