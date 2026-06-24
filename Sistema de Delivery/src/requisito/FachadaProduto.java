package requisito;

import java.util.List;
import modelo.Produto;
import repositorio.Repositorio;
import repositorio.RepositorioProduto;

public class FachadaProduto {
    
    private RepositorioProduto repoProduto;

    public FachadaProduto() {
        this.repoProduto = new RepositorioProduto();
    }

    // Regra: Não pode cadastrar produto sem nome ou com preço menor ou igual a 0.
    // Regra: O nome do produto precisa ser único.
    public Produto criarProduto(String nome, double preco) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O nome do produto não pode ser vazio.");
        }
        if (preco <= 0) {
            throw new Exception("O preço do produto deve ser maior que zero.");
        }

        try {
            Repositorio.begin();
            
            Produto p = repoProduto.localizar(nome);
            if (p != null) {
                Repositorio.rollback();
                throw new Exception("Produto já existe com este nome.");
            }

            p = new Produto(nome, preco);
            repoProduto.criar(p);
            
            Repositorio.commit();
            return p;
        } catch (Exception e) {
            Repositorio.rollback();
            throw new Exception("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    // Atualização de dados mantendo a validação de preço
    public Produto atualizarProduto(int id, String nome, double preco) throws Exception {
        if (preco <= 0) {
            throw new Exception("O preço do produto deve ser maior que zero.");
        }

        try {
            Repositorio.begin();
            
            Produto p = repoProduto.localizar(id);
            if (p == null) {
                Repositorio.rollback();
                throw new Exception("Produto não encontrado.");
            }

            p.setNome(nome);
            p.setPreco(preco);
            repoProduto.atualizar(p);
            
            Repositorio.commit();
            return p;
        } catch (Exception e) {
            Repositorio.rollback();
            throw new Exception("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    // Método isolado para o upload do campo @Lob (Foto) que usaremos na TelaProduto
    public Produto atualizarFoto(int id, byte[] foto) throws Exception {
        try {
            Repositorio.begin();
            
            Produto p = repoProduto.localizar(id);
            if (p == null) {
                Repositorio.rollback();
                throw new Exception("Produto não encontrado.");
            }

            p.setFoto(foto);
            repoProduto.atualizar(p);
            
            Repositorio.commit();
            return p;
        } catch (Exception e) {
            Repositorio.rollback();
            throw new Exception("Erro ao atualizar foto: " + e.getMessage());
        }
    }

    public void deletarProduto(int id) throws Exception {
        try {
            Repositorio.begin();
            
            Produto p = repoProduto.localizar(id);
            if (p == null) {
                Repositorio.rollback();
                throw new Exception("Produto não encontrado.");
            }
            
            repoProduto.deletar(p);
            
            Repositorio.commit();
        } catch (Exception e) {
            Repositorio.rollback();
            throw new Exception("Erro ao deletar produto: " + e.getMessage());
        }
    }

    public Produto localizarProduto(int id) {
        return repoProduto.localizar(id);
    }

    public List<Produto> listarProdutos() {
        return repoProduto.listar();
    }
}