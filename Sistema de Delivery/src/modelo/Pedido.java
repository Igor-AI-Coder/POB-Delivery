/**
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistência de Objetos - Etapa 2 (JPA)
 * Prof. Fausto Ayres
 */

package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido20242370034") 
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private int id;
    
    @Column(name = "data_pedido", nullable = false)
    private LocalDate data;  // ✅ LocalDate, não String!
    
    // ===== RELACIONAMENTO N-1: Pedido -> Cliente =====
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    // ===== RELACIONAMENTO N-N: Pedido -> Produtos =====
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "pedido_produto20242370034",  // ✅ Tabela de junção com matrícula
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produtos = new ArrayList<>();

    // ===== Construtores =====
    public Pedido() {}  // ✅ Obrigatório
    
    public Pedido(LocalDate data, Cliente cliente) {  // ✅ LocalDate!
        this.data = data;
        this.cliente = cliente;
    }

    // ===== Getters e Setters =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {  // ✅ Retorna LocalDate
        return data;
    }

    public void setData(LocalDate data) {  // ✅ Recebe LocalDate
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    // ===== Métodos de Relacionamento =====
    public void adicionarProduto(Produto produto) {
        this.produtos.add(produto);
    }

    public void removerProduto(Produto produto) {
        this.produtos.remove(produto);
    }

    public double calcularTotal() {
        double total = 0;
        for (Produto p : produtos) {
            total += p.getPreco();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", data=" + data +
                ", cliente=" + cliente.getNome() +
                ", produtos=" + produtos.size() +
                ", total=R$ " + String.format("%.2f", calcularTotal()) +
                '}';
    }
}