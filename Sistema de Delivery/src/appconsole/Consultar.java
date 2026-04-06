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
        q = manager.query();
        q.constrain(Cliente.class);
        q.descend("pedidos").descend("produtos").descend("nome").constrain("Pizza").like();
        List<Cliente> clientesComPizza = q.execute();

        for (Cliente cliente : clientesComPizza) {
            int contadorPedidos = 0;

            for (Pedido pedido : cliente.getPedidos()) {
                for (Produto produto : pedido.getProdutos()) {
                    if (produto.getNome().equalsIgnoreCase("Pizza")) {
                        contadorPedidos++;
                        break;
                    }
                }
            }

            if (contadorPedidos > 2) {
                System.out.println("\nCliente: " + cliente.getNome());
                System.out.println("Total de pedidos com Pizza: " + contadorPedidos);
                for (Pedido pedido : cliente.getPedidos()) {
                    boolean temPizza = false;
                    for (Produto produto : pedido.getProdutos()) {
                        if (produto.getNome().equalsIgnoreCase("Pizza")) {
                            temPizza = true;
                            break;
                        }
                    }
                    if (temPizza) {
                        System.out.println("  Pedido #" + pedido.getId() + " - " + pedido.getData() + " - R$ " + pedido.calcularTotal());
                        for (Produto produto : pedido.getProdutos()) {
                            System.out.println("    - " + produto.getNome() + " (R$ " + produto.getPreco() + ")");
                        }
                    }
                }
            }
        }

        Util.desconectar();
        System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplicação");
    }

    public static void main(String[] args) {
        new Consultar();
    }
}