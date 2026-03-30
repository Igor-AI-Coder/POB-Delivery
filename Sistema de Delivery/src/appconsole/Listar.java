package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Cliente;
import modelo.Pedido;
import modelo.Produto;
import util.Util;

public class Listar {
	private ObjectContainer manager;

	public Listar(){
		Util.conectar();
		manager = Util.getManager();
		
		// ===== LISTA DE PRODUTOS =====
		System.out.println("-------Lista de Produtos--------");
		Query q = manager.query();
		q.constrain(Produto.class);  				
		List<Produto> resultadosProdutos = q.execute();
		for (Produto prod : resultadosProdutos) {
			System.out.println(prod);
		}
		
		// ===== LISTA DE CLIENTES =====
		System.out.println("\n-------Lista de Clientes--------");
		Query q2 = manager.query();
		q2.constrain(Cliente.class);  				
		List<Cliente> resultadosClientes = q2.execute();
		for (Cliente cli : resultadosClientes) {
			System.out.println("Cliente: " + cli.getNome());
			System.out.println("Endereço: " + cli.getEndereco());
			System.out.println("Pedidos: " + cli.getPedidos().size());
			for (Pedido pedido : cli.getPedidos()) {
				System.out.println("  - Pedido #" + pedido.getId() + 
					" (" + pedido.getData() + ") - Total: R$ " + pedido.calcularTotal());
			}
			System.out.println();
		}
		
		// ===== LISTA DE PEDIDOS =====
		System.out.println("\n-------Lista de Pedidos--------");
		Query q3 = manager.query();
		q3.constrain(Pedido.class);  				
		List<Pedido> resultadosPedidos = q3.execute();
		for (Pedido ped : resultadosPedidos) {
			System.out.println("Pedido #" + ped.getId());
			System.out.println("Data: " + ped.getData());
			System.out.println("Cliente: " + ped.getCliente().getNome());
			System.out.println("Produtos: " + ped.getProdutos().size());
			for (Produto prod : ped.getProdutos()) {
				System.out.println("  - " + prod.getNome() + " (R$ " + prod.getPreco() + ")");
			}
			System.out.println("Total: R$ " + ped.calcularTotal());
			System.out.println();
		}
		
		Util.desconectar();
		
		System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplicação");
	}

	
	//=================================================
	public static void main(String[] args) {
		new Listar();
	}
}