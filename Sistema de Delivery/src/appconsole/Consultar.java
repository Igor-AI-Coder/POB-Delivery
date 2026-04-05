package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import modelo.Cliente;
import modelo.Pedido;
import modelo.Produto;
import util.Util;

public class Consultar {
    private ObjectContainer manager;

    public Consultar() {
        Util.conectar();
        manager = Util.getManager();

        System.out.println("\n--- 1. Quais os pedidos da data 10/10/2023 ---");
        Query q = manager.query();
        q.constrain(Pedido.class);
        q.descend("data").constrain("10/10/2023"); 
        List<Pedido> resultados1 = q.execute();

        for (Pedido p : resultados1) {
            System.out.println(p);
        }

        System.out.println("\n--- 2. Quais os pedidos contendo produto de preco > 50.0 ---");
        q = manager.query();
        q.constrain(Pedido.class);
        q.descend("produtos").descend("preco").constrain(50.0).greater(); 
        List<Pedido> resultados2 = q.execute();

        for (Pedido p : resultados2) {
            System.out.println(p);
        }

        System.out.println("\n--- 3. Quais os clientes que tem mais de 2 pedidos do produto 'Pizza' ---");
        q = manager.query();
        q.constrain(Cliente.class);
        q.constrain(new FiltroCliente(2, "Pizza")); // Aplica o filtro Evaluation
        List<Cliente> resultados3 = q.execute();

        for (Cliente cliente : resultados3) {
            System.out.println(cliente);
        }

        Util.desconectar();
    }

    public static void main(String[] args) {
        new Consultar();
    }
}

// Classe interna para filtragem no db4o (Evaluation)
class FiltroCliente implements Evaluation {
    private int quantidadeMinima;
    private String nomeProduto;

    public FiltroCliente(int quantidadeMinima, String nomeProduto) {
        this.quantidadeMinima = quantidadeMinima;
        this.nomeProduto = nomeProduto;
    }

    public void evaluate(Candidate candidate) {
        // Obter cada objeto da classe Cliente que está sendo avaliado pelo banco
        Cliente cliente = (Cliente) candidate.getObject();
        int contadorPedidos = 0;

        // entra na lista de pedidos de cada cliente
        for (Pedido pedido : cliente.getPedidos()) {
            
            // verifica os produtos dentro de cada pedido
            for (Produto produto : pedido.getProdutos()) {
                if (produto.getNome().equalsIgnoreCase(nomeProduto)) {
                    contadorPedidos++;
                    break; // para não contar o mesmo pedido mais de uma vez se tiver duas pizzas
                }
            }
        }

        // só inclui o cliente no resultado da consulta se ele bateu a meta
        if (contadorPedidos > quantidadeMinima) {
            candidate.include(true); // incluir objeto no resultado
        } else {
            candidate.include(false); // excluir objeto do resultado
        }
    }
}