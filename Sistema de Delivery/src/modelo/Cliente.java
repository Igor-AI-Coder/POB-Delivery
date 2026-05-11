package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity  // Marca esta classe como tabela no banco
@Table(name = "cliente")
public class Cliente {

	@Id  // Chave primária
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremento
	@Column(name = "id_cliente")
	private int id;

	@Column(name = "nome_cliente", nullable = false)  // Obrigatório
	private String nome;

	@Column(name = "endereco_cliente")
	private String endereco;

	// 1 cliente para N pedidos
	@OneToMany(mappedBy = "cliente",  // Referencia o atributo 'cliente' em Pedido
			   cascade = {CascadeType.PERSIST, CascadeType.MERGE},  // Propaga operações
			   orphanRemoval = true)  // Remove pedidos órfãos
	private List<Pedido> pedidos = new ArrayList<>();

	public Cliente() {}

	public Cliente(String nome, String endereco) {
		this.nome = nome;
		this.endereco = endereco;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void adicionarPedido(Pedido pedido) {
		pedidos.add(pedido);
		pedido.setCliente(this);  // Mantém bidirecionalidade
	}

	public void removerPedido(Pedido pedido) {
		pedidos.remove(pedido);
		pedido.setCliente(null);  // Mantém bidirecionalidade
	}

	@Override
	public String toString() {
		return "Cliente{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", endereco='" + endereco + '\'' +
				", pedidos=" + pedidos.size() +
				'}';
	}
}