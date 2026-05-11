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

@Entity  // Marca esta classe como tabela no banco
@Table(name = "pedido")  // Nome da tabela
public class Pedido {

	@Id  // Chave primária
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremento
	@Column(name = "id_pedido")  // Nome da coluna
	private int id;

	@Column(name = "data_pedido", nullable = false)  // Coluna obrigatória
	private LocalDate data;

	// N pedidos para 1 cliente
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "cliente_id", nullable = false)  // Chave estrangeira
	private Cliente cliente;

	// N pedidos para N produtos (tabela de junção)
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name = "pedido_produto",  // Nome da tabela de junção
		joinColumns = @JoinColumn(name = "pedido_id"),
		inverseJoinColumns = @JoinColumn(name = "produto_id")
	)
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