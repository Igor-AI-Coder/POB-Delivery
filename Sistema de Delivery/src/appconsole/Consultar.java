package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
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
        //Busca todos os clientes do banco usando uma consulta SODA simples
        q = manager.query();
        q.constrain(Cliente.class);
        List<Cliente> todosClientes = q.execute();

        //percorre a lista de todos os clientes retornados
        for (Cliente cliente : todosClientes) {
            int contadorPedidos = 0;

            //entra na lista de pedidos de cada cliente
            for (Pedido pedido : cliente.getPedidos()) {
                
                //verifica os produtos dentro de cada pedido
                for (Produto produto : pedido.getProdutos()) {
                    if (produto.getNome().equalsIgnoreCase("Pizza")) {
                        contadorPedidos++;
                        break;
                    }
                }
            }

            //só imprime o cliente na tela se ele bateu a meta (mais de 2 pedidos)
            if (contadorPedidos > 2) {
                System.out.println(cliente);
            }
        }

        Util.desconectar();
    }

    public static void main(String[] args) {
        new Consultar();
    }
}