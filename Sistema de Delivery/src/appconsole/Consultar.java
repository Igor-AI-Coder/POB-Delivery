package appconsole;

import java.util.List;
import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import com.db4o.query.Evaluation;
import com.db4o.query.Candidate;

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
        q.constrain(new FiltroClienteMaisde2Pizzas());
        List<Cliente> resultados3 = q.execute();

        for (Cliente cliente : resultados3) {
            int contadorPizzas = 0;
            // loop para contar quantos pedidos com pizza o cliente tem
            for (Pedido pedido : cliente.getPedidos()) {
                for (Produto produto : pedido.getProdutos()) {
                    if (produto.getNome().equalsIgnoreCase("Pizza")) {
                        contadorPizzas++;
                        break;
                    }
                }
            }
            
            System.out.println(cliente.getNome() + " | " + contadorPizzas + " pedidos");
            // loop para mostrar os detalhes dos pedidos com pizza
            for (Pedido pedido : cliente.getPedidos()) {
                for (Produto produto : pedido.getProdutos()) {
                    if (produto.getNome().equalsIgnoreCase("Pizza")) {
                        System.out.print("  Pedido #" + pedido.getId() + " | " + pedido.getData() + " | R$ " + pedido.calcularTotal() + " | ");
                        for (int i = 0; i < pedido.getProdutos().size(); i++) {
                            Produto p = pedido.getProdutos().get(i);
                            System.out.print(p.getNome() + " (R$ " + p.getPreco() + ")");
                            if (i < pedido.getProdutos().size() - 1) {
                                System.out.print(", ");
                            }
                        }
                        System.out.println();
                        break;
                    }
                }
            }
            System.out.println();
        }
        Util.desconectar();
    }

    public static void main(String[] args) {
        new Consultar();
    }
}

class FiltroClienteMaisde2Pizzas implements Evaluation {

    @Override
    public void evaluate(Candidate candidate) {
        Cliente cliente = (Cliente) candidate.getObject();
        int contadorPedidos = 0;

        for (Pedido pedido : cliente.getPedidos()) {
            for (Produto produto : pedido.getProdutos()) {
                if (produto.getNome().equalsIgnoreCase("Pizza")) {
                    contadorPedidos++;
                    break;
                }
            }
        }

        candidate.include(contadorPedidos > 2);
    }
}