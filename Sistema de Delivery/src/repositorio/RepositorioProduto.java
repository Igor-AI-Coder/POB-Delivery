package repositorio;

import java.util.List;
import jakarta.persistence.TypedQuery;
import modelo.Produto;
import util.Util;

public class RepositorioProduto extends Repositorio<Produto> {

    @Override
    public Produto localizar(Object chave) {
        // Busca pelo nome exato do produto (útil para validações de não repetição)
        if (chave instanceof String) {
            String nome = (String) chave;
            TypedQuery<Produto> q = Util.getManager().createQuery(
                    "select p from Produto p where p.nome = :x", Produto.class);
            q.setParameter("x", nome);
            return q.getSingleResultOrNull();
        } 
        // Busca pelo ID numérico padrão (útil para deleções/atualizações de CRUD)
        else if (chave instanceof Integer) {
            int id = (int) chave;
            return Util.getManager().find(Produto.class, id);
        }
        return null;
    }

    @Override
    public List<Produto> listar() {
        TypedQuery<Produto> q = Util.getManager().createQuery(
                "select p from Produto p order by p.id", Produto.class);
        return q.getResultList();
    }
}