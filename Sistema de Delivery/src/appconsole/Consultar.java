package appconsole;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Cliente;
import modelo.Pedido;
import modelo.Produto;
import util.Util;

public class Consultar {

    public Consultar() {
        Util.conectar();
        EntityManager manager = Util.getManager();

        try {
            System.out.println("\n--- 1. Quais os pedidos da data 15/01/2024 ---");
            // Usando LocalDate 
            LocalDate dataBusca = LocalDate.of(2024, 1, 15);
            TypedQuery<Pedido> q1 = manager.createQuery("select p from Pedido p where p.data = :data", Pedido.class);
            q1.setParameter("data", dataBusca); // Consulta parametrizada com data 
            List<Pedido> resultados1 = q1.getResultList();

            for (Pedido p : resultados1) {
                System.out.println(p);
            }

            System.out.println("\n--- 2. Quais os pedidos contendo produto de preco > 50.0 ---");
            // JPQL com JOIN pra acessar a lista de produtos lá de dentro 
            TypedQuery<Pedido> q2 = manager.createQuery(
                "select p from Pedido p JOIN p.produtos prod where prod.preco > :preco", Pedido.class);
            q2.setParameter("preco", 50.0);
            List<Pedido> resultados2 = q2.getResultList();

            for (Pedido p : resultados2) {
                System.out.println("Pedido ID: " + p.getId());
                for (Produto prod : p.getProdutos()) {
                    System.out.println("  - Produto: " + prod.getNome() + " | Preço: R$ " + String.format("%.2f", prod.getPreco()));
                }
            }

            System.out.println("\n--- 3. Quais os clientes que tem mais de 2 pedidos do produto 'Pizza' ---");
            // Matamos a classe de filtro!
            // Usamos JOIN pra ligar Cliente -> Pedido -> Produto
            // E o GROUP BY com HAVING pra contar os pedidos 
            TypedQuery<Cliente> q3 = manager.createQuery(
                "select c from Cliente c JOIN c.pedidos p JOIN p.produtos prod " +
                "where prod.nome = 'Pizza' " +
                "GROUP BY c " +
                "HAVING count(p) > 2", Cliente.class);
            List<Cliente> resultados3 = q3.getResultList();

            for (Cliente cliente : resultados3) {
                System.out.println("Cliente: " + cliente.getNome());

                for (Pedido pedido : cliente.getPedidos()) {
                    System.out.println("  - Pedido ID: " + pedido.getId());

                    for (Produto prod : pedido.getProdutos()) {
                        System.out.println("      * Produto: " + prod.getNome());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro na consulta: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Util.desconectar();
        }
    }

    public static void main(String[] args) {
        new Consultar();
    }
}