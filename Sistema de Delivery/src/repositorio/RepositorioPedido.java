package repositorio;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.TypedQuery;
import modelo.Pedido;
import util.Util;

public class RepositorioPedido extends Repositorio<Pedido> {

    @Override
    public Pedido localizar(Object chave) {
        try {
            int id = (int) chave;
            return Util.getManager().find(Pedido.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Pedido> listar() {
        TypedQuery<Pedido> query = Util.getManager().createQuery(
            "SELECT p FROM Pedido p", Pedido.class);
        return query.getResultList();
    }

    // --- CONSULTAS JPQL ---

    /**
     * Consulta 1: Quais os pedidos de uma determinada data
     */
    public List<Pedido> buscarPorData(LocalDate dataBusca) {
        TypedQuery<Pedido> query = Util.getManager().createQuery(
            "SELECT p FROM Pedido p WHERE p.data = :data", Pedido.class);
        query.setParameter("data", dataBusca);
        return query.getResultList();
    }

    /**
     * Consulta 2: Quais os pedidos contendo produto de preço maior que o valor informado
     */
    public List<Pedido> buscarPorPrecoProdutoMaiorQue(double precoVariavel) {
        TypedQuery<Pedido> query = Util.getManager().createQuery(
            "SELECT DISTINCT p FROM Pedido p JOIN p.produtos prod WHERE prod.preco > :preco", Pedido.class);
        query.setParameter("preco", precoVariavel);
        return query.getResultList();
    }
}