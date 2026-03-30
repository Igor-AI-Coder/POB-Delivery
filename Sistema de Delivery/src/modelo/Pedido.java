package modelo;

import java.util.ArrayList;
import java.util.List;


public class Pedido {
    private int id;
    private String data;
    private Cliente cliente;
    private List<Produto> produtos;

    public Pedido(int id, String data, Cliente cliente) {
        this.id = id;
        this.data = data;
        this.cliente = cliente;
        this.produtos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
                ", produtos=" + produtos +
                ", total=" + calcularTotal() +
                '}';
    }
}