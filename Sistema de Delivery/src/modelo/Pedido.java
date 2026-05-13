package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity  // Marca esta classe como tabela no banco
@Table(name = "pedido20242370034")  // Nome da tabela (pode manter se o professor quiser nome específico)
public class Pedido {

    @Id  // Chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremento
    private int id;

    private LocalDate data;

    // N pedidos para 1 cliente
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Cliente cliente;

    // N pedidos para N produtos (tabela de junção)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Produto> produtos = new ArrayList<>();

    public Pedido() {}

    public Pedido(LocalDate data, Cliente cliente) {
        this.data = data;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
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

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public void removerProduto(Produto produto) {
        produtos.remove(produto);
    }

    public double calcularTotal() {
        return produtos.stream().mapToDouble(Produto::getPreco).sum();
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