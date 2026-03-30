package appconsole;

import com.db4o.ObjectContainer;

import modelo.Cliente;
import modelo.Pedido;
import modelo.Produto;
import util.Util;

public class Cadastrar {

	public Cadastrar(){
		Util.conectar();
		ObjectContainer manager = Util.getManager();

		System.out.println("cadastrando...");
		
		// ===== CRIANDO PRODUTOS =====
		Produto produto1 = new Produto(0, "Notebook", 3500.00);
		Produto produto2 = new Produto(0, "Mouse", 150.00);
		Produto produto3 = new Produto(0, "Teclado", 450.00);
		Produto produto4 = new Produto(0, "Monitor", 1200.00);
		Produto produto5 = new Produto(0, "Webcam", 250.00);
		
		manager.store(produto1);
		manager.commit();
		manager.store(produto2);
		manager.commit();
		manager.store(produto3);
		manager.commit();
		manager.store(produto4);
		manager.commit();
		manager.store(produto5);
		manager.commit();
		
		// ===== CRIANDO CLIENTES E PEDIDOS =====
		
		// Cliente 1
		Cliente cliente1 = new Cliente("João Silva", "Rua A, 123");
		
		Pedido pedido1 = new Pedido(0, "20/03/2026", cliente1);
		pedido1.adicionarProduto(produto1);
		pedido1.adicionarProduto(produto2);
		
		Pedido pedido2 = new Pedido(0, "25/03/2026", cliente1);
		pedido2.adicionarProduto(produto3);
		pedido2.adicionarProduto(produto4);
		
		cliente1.adicionarPedido(pedido1);
		cliente1.adicionarPedido(pedido2);
		
		manager.store(cliente1);
		manager.commit();
		
		// Cliente 2
		Cliente cliente2 = new Cliente("Maria Santos", "Rua B, 456");
		
		Pedido pedido3 = new Pedido(0, "28/03/2026", cliente2);
		pedido3.adicionarProduto(produto1);
		pedido3.adicionarProduto(produto5);
		
		cliente2.adicionarPedido(pedido3);
		
		manager.store(cliente2);
		manager.commit();
		
		// Cliente 3
		Cliente cliente3 = new Cliente("Carlos Costa", "Rua C, 789");
		
		Pedido pedido4 = new Pedido(0, "29/03/2026", cliente3);
		pedido4.adicionarProduto(produto2);
		pedido4.adicionarProduto(produto3);
		pedido4.adicionarProduto(produto4);
		
		cliente3.adicionarPedido(pedido4);
		
		manager.store(cliente3);
		manager.commit();
		
		Util.desconectar();
		
		System.out.println("fim do programa");
	}


	public void cadastrar(){
	}	


	//=================================================
	public static void main(String[] args) {
		new Cadastrar();
	}
}