/**
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistência de Objetos - Etapa 2 (JPA)
 * Prof. Fausto Ayres
 */

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

@Entity
@Table(name = "cliente20242370034") 
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private int id;
    
    @Column(name = "nome_cliente", nullable = false)
    private String nome;
    
    @Column(name = "endereco_cliente")
    private String endereco;

    // ===== RELACIONAMENTO 1-N: Cliente -> Pedidos =====
    @OneToMany(mappedBy = "cliente", 
               cascade = {CascadeType.PERSIST, CascadeType.MERGE}, 
               orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();

    // ===== Construtores =====
    public Cliente() {}
    
    public Cliente(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    // ===== Métodos de Relacionamento =====
    public void adicionarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setCliente(this);  // ✅ Manter bidirecional
    }

    public void removerPedido(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.setCliente(null);  // ✅ Manter bidirecional
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