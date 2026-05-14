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
            manager.getTransaction().begin();

            System.out.println("Buscando pelo cliente 'João'...");
            
            //JPQL: Agora busca direto cliente
            String jpql = "select c from Cliente c where c.nome like :nome";
            TypedQuery<Cliente> query = manager.createQuery(jpql, Cliente.class);
            query.setParameter("nome", "%João%");
            
            List<Cliente> clientesEncontrados = query.getResultList();

            if (!clientesEncontrados.isEmpty()) { //verifica se existe o cliente
                
                Cliente cliente = clientesEncontrados.get(0);
                System.out.println("Cliente encontrado: " + cliente.getNome());
                
                if (cliente.getPedidos() != null && !cliente.getPedidos().isEmpty()) { //verifica se o cliente tem algum pedido cadastrado
                    
                    Pedido pedidoParaRemover = cliente.getPedidos().get(0);
                    
                    System.out.println("Pedido encontrado para remoção: #" + pedidoParaRemover.getId());
                    
                    cliente.removerPedido(pedidoParaRemover);

                    manager.remove(pedidoParaRemover); 
                    
                    manager.getTransaction().commit();
                    System.out.println("O pedido foi removido com sucesso.");
                    
                } else {
                    System.out.println("O cliente encontrado não possui nenhum pedido cadastrado.");
                    manager.getTransaction().rollback();
                }

            } else {
                System.out.println("Nenhum cliente com esse nome foi encontrado no banco de dados.");
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