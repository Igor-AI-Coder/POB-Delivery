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

        // Busca o cliente pelo nome
        Query q = manager.query();
        q.constrain(Cliente.class);
        q.descend("nome").constrain("João").like();
        List<Cliente> resultados = q.execute();

        if (resultados.size() > 0) {
            Cliente cliente = resultados.get(0);
            System.out.println("Cliente encontrado: " + cliente.getNome());

            // Percorre a lista de pedidos do cliente para apagar os dependentes
            for (Pedido pedido : cliente.getPedidos()) {
                manager.delete(pedido);
                System.out.println("Pedido ID " + pedido.getId() + " apagado por ficar órfão.");
            }

            // Após tratar os órfãos, deleta o cliente
            manager.delete(cliente);
            manager.commit();

            System.out.println("Cliente apagado com sucesso.");
        } else {
            System.out.println("Cliente nao encontrado");
        }

        Util.desconectar();
    }

    public static void main(String[] args) {
        new Deletar();
    }
}