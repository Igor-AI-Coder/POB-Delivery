package appconsole;

import java.util.List;
import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import modelo.Cliente;
import modelo.Pedido;
import util.Util;

public class Alterar {
    private ObjectContainer manager;

    public Alterar() {
        Util.conectar();
        manager = Util.getManager();

        // Alteração:
        // remover pedido de ID 1 do cliente "joao"

        Query q = manager.query();
        q.constrain(Cliente.class);
        q.descend("nome").constrain("joao").like();
        List<Cliente> resultados = q.execute();

        if (resultados.size() > 0) {
            Cliente cliente = resultados.get(0);
            System.out.println("Cliente encontrado: " + cliente);

            Pedido pedidoParaRemover = null;
            
            // Procura o pedido alvo dentro da lista do cliente
            for (Pedido p : cliente.getPedidos()) {
                if (p.getId() == 1) {
                    pedidoParaRemover = p;
                    break;
                }
            }

            if (pedidoParaRemover != null) {
                cliente.removerPedido(pedidoParaRemover);
                manager.store(cliente);

                //Remoção caso vire orfão dentro do sistema
                manager.delete(pedidoParaRemover);
                System.out.println("Pedido removido por ficar orfão no sistema");
                
                manager.commit();
                System.out.println("O pedido foi removido do cliente com sucesso.");
                System.out.println(cliente);
            } else {
                System.out.println("Pedido nao encontrado na lista deste cliente");
            }
        } else {
            System.out.println("Cliente nao encontrado");
        }

        Util.desconectar();
        System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplicação");
    }

    public static void main(String[] args) {
        new Alterar();
    }
}