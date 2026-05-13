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
	
	private int id;
	
	private String nome;
	
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