package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Cliente;
import util.Util;
import java.util.List;

public class Deletar {

    public Deletar() {
        Util.conectar();
        EntityManager manager = Util.getManager();

        try {
            manager.getTransaction().begin();

            // Busca o cliente pelo nome usando JPQL 
            TypedQuery<Cliente> query = manager.createQuery("select c from Cliente c where c.nome like :nome", Cliente.class);
            query.setParameter("nome", "%João%");
            List<Cliente> resultados = query.getResultList();

            if (!resultados.isEmpty()) {
                Cliente cliente = resultados.get(0);
                System.out.println("Cliente encontrado: " + cliente.getNome());

                // NÃO PRECISA remover os pedidos manualmente, o CascadeType.REMOVE faz isso!
                manager.remove(cliente);
                manager.getTransaction().commit();

                System.out.println("Cliente (e pedidos) apagado com sucesso.");
            } else {
                System.out.println("Cliente não encontrado.");
                manager.getTransaction().rollback();
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