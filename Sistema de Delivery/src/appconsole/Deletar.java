package appconsole;

import java.util.List;
import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import modelo.Cliente;
import modelo.Pedido;
import util.Util;

public class Deletar {
    private ObjectContainer manager;

    public Deletar() {
        Util.conectar();
        manager = Util.getManager();

        //Deletando o pedido de id 2
        Query q = manager.query();
        q.constrain(Pedido.class);
        q.descend("id").constrain(2);
        List<Pedido> resultados = q.execute();

        if (resultados.size() > 0) {
            Pedido pedido = resultados.get(0);
            System.out.println("Pedido encontrado: " + pedido);


            Cliente cliente = pedido.getCliente();
            if (cliente != null) {
                cliente.removerPedido(pedido);
                manager.store(cliente);
                System.out.println("removeu pedido da lista do cliente... " + cliente.getNome());
            }

            // O pedido não precisa ser removido dos produtos, pois o relacionamento
            // parte do Pedido para o Produto, e os Produtos independem dos pedidos.
            manager.delete(pedido);
            manager.commit();
            
            System.out.println("pedido apagado com sucesso.");
        } else {
            System.out.println("Pedido nao encontrado");
        }

        Util.desconectar();
        System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplicação");
    }

    public static void main(String[] args) {
        new Deletar();
    }
}