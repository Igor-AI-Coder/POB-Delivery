/**
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistência de Objetos - Etapa 2 (JPA)
 * Prof. Fausto Ayres
 */

package modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto")  
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private int id;
    
    @Column(name = "nome_produto", nullable = false, length = 100)
    private String nome;
    
    @Column(name = "preco_produto", nullable = false)
    private double preco;

    // ===== Construtores =====
    public Produto() {}  // ✅ Obrigatório para JPA
    
    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    // ===== Getters e Setters =====
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