package repositorio;

import java.util.List;

import jakarta.persistence.TypedQuery;
import modelo.Cliente;
import util.Util;

public class RepositorioCliente extends Repositorio<Cliente> {

    @Override
    public Cliente localizar(Object chave) {
        // Busca pelo nome exato do cliente 
        if (chave instanceof String) {
            String nome = (String) chave;
            TypedQuery<Cliente> q = Util.getManager().createQuery(
                    "select c from Cliente c where c.nome = :x", Cliente.class);
            q.setParameter("x", nome);
            try {
                return q.getSingleResult();
            } catch (Exception e) {
                // Retorna null se não encontrar nenhum cliente com esse nome
                return null; 
            }
        } 
        // Busca pelo ID numérico padrão 
        else if (chave instanceof Integer) {
            try {
                int id = (int) chave;
                return Util.getManager().find(Cliente.class, id);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public List<Cliente> listar() {
        TypedQuery<Cliente> q = Util.getManager().createQuery(
                "select c from Cliente c order by c.id", Cliente.class);
        return q.getResultList();
    }
    
    // Consultas JPQL Específicas
    
    /**
     * Consulta: Quais clientes possuem um determinado texto no endereço?
     */
    public List<Cliente> buscarPorEndereco(String textoEndereco) {
        TypedQuery<Cliente> q = Util.getManager().createQuery(
                "select c from Cliente c where c.endereco like :x", Cliente.class);
        // O % funciona como um curinga no banco de dados (busca em qualquer parte do texto)
        q.setParameter("x", "%" + textoEndereco + "%");
        return q.getResultList();
    }

    // --- NOVA PARTE DO CÓDIGO ADICIONADA ABAIXO ---

    /**
     * Consulta 3: Quais os clientes que tem mais de 2 pedidos do produto 'Pizza'
     */
    public List<Cliente> buscarClientesMaisDe2PedidosDePizza() {
        TypedQuery<Cliente> q = Util.getManager().createQuery(
            "select c from Cliente c JOIN c.pedidos p JOIN p.produtos prod " +
            "where prod.nome = 'Pizza' " +
            "GROUP BY c " +
            "HAVING count(p) > 2", Cliente.class);
        return q.getResultList();
    }
}