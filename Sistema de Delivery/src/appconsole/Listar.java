package appconsole;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Cliente;
import modelo.Pedido;
import modelo.Produto;
import util.Util;

public class Listar {

	public Listar() {
		try {
			Util.conectar();
			EntityManager manager = Util.getManager();

			System.out.println("------- Lista de Produtos --------");

			TypedQuery<Produto> q1 = manager.createQuery("SELECT p FROM Produto p", Produto.class);
			List<Produto> resultadosProdutos = q1.getResultList();
			for (Produto prod : resultadosProdutos) {
				System.out.println(prod);
			}

			System.out.println("\n------- Lista de Clientes --------");
			TypedQuery<Cliente> q2 = manager.createQuery("SELECT c FROM Cliente c", Cliente.class);
			List<Cliente> resultadosClientes = q2.getResultList();
			for (Cliente cli : resultadosClientes) {
				System.out.println("Cliente: " + cli.getNome());
				System.out.println("Endereço: " + cli.getEndereco());
				System.out.println("Pedidos: " + cli.getPedidos().size());
				for (Pedido pedido : cli.getPedidos()) {
					System.out.println("  - Pedido #" + pedido.getId() + 
						" | Data: " + pedido.getData() + " | Total: R$ " + String.format("%.2f", pedido.calcularTotal()));
				}
				System.out.println();
			}

			System.out.println("------- Lista de Pedidos --------");
			TypedQuery<Pedido> q3 = manager.createQuery("SELECT p FROM Pedido p", Pedido.class);
			List<Pedido> resultadosPedidos = q3.getResultList();
			for (Pedido ped : resultadosPedidos) {
				System.out.println("Pedido #" + ped.getId());
				System.out.println("Data: " + ped.getData());
				System.out.println("Cliente: " + ped.getCliente().getNome());
				System.out.println("Produtos: " + ped.getProdutos().size());
				for (Produto prod : ped.getProdutos()) {
					System.out.println("  - " + prod.getNome() + " (R$ " + String.format("%.2f", prod.getPreco()) + ")");
				}
				System.out.println("Total: R$ " + String.format("%.2f", ped.calcularTotal()));
				System.out.println();
			}

		} catch (Exception e) {
			System.out.println("ERRO: " + e.getMessage());
			e.printStackTrace();
		} finally {
			Util.desconectar();
		}
		
		System.out.println("fim do programa");
	}

	public static void main(String[] args) {
		new Listar();
	}
}