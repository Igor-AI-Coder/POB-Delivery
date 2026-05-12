package modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity  // Marca esta classe como tabela no banco
@Table(name = "produto20242370034")
public class Produto {

	@Id  // Chave primária
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremento
	@Column(name = "id_produto")
	private int id;

	@Column(name = "nome_produto", nullable = false, length = 100)  // Obrigatório, máx 100 caracteres
	private String nome;

	@Column(name = "preco_produto", nullable = false)  // Obrigatório
	private double preco;

	public Produto() {}

	public Produto(String nome, double preco) {
		this.nome = nome;
		this.preco = preco;
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

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	@Override
	public String toString() {
		return "Produto{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", preco=" + String.format("%.2f", preco) +
				'}';
	}
}