package appconsole;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Cliente;
import modelo.Pedido;
import util.Util;

public class Deletar {

    public Deletar() {
        Util.conectar();
        EntityManager manager = Util.getManager();

        try {
            manager.getTransaction().begin();

            // Busca o cliente pelo nome usando JPQL 
            TypedQuery<Cliente> query = manager.createQuery("select c from Cliente c where c.nome like :nome", Cliente.class);
            query.setParameter("nome", "%João%"); // parâmetro nomeado como vimos nos slides 
            List<Cliente> resultados = query.getResultList(); // pega a lista

            if (!resultados.isEmpty()) {
                Cliente cliente = resultados.get(0);
                System.out.println("Cliente encontrado: " + cliente.getNome());

                // Apagando os pedidos do João pra não dar erro de violação de chave (órfãos)
                for (Pedido pedido : cliente.getPedidos()) {
                    manager.remove(pedido); // joga pro manager apagar 
                    System.out.println("Pedido ID " + pedido.getId() + " apagado.");
                }
                
                // Limpa a lista na memória
                cliente.getPedidos().clear();

                // Agora sim, apaga o cliente
                manager.remove(cliente);
                manager.getTransaction().commit(); // confirma a transação

                System.out.println("Cliente apagado com sucesso.");
            } else {
                System.out.println("Cliente não encontrado.");
                manager.getTransaction().rollback(); // deu ruim, desfaz
            }

        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            System.out.println("Erro ao deletar: " + e.getMessage());
        } finally {
            Util.desconectar();
        }
    }

    public static void main(String[] args) {
        new Deletar();
    }
}