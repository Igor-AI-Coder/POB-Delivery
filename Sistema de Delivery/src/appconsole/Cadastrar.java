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
		Produto produto1 = new Produto("Pizza", 85.00);
		Produto produto2 = new Produto("Sanduiche", 28.00);
		Produto produto3 = new Produto("Pastel", 15.00);
		Produto produto4 = new Produto("Coxinha", 12.00);
		Produto produto5 = new Produto("Refrigerante", 8.00);
		Produto produto6 = new Produto("Agua", 5.00);
		Produto produto7 = new Produto("Cachorro Quente", 18.00);
		Produto produto8 = new Produto("Brigadeiro", 10.00);
		Produto produto9 = new Produto("Acaraje", 15.00);
		Produto produto10 = new Produto("Risole", 12.00);
		
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
		manager.store(produto6);
		manager.commit();
		manager.store(produto7);
		manager.commit();
		manager.store(produto8);
		manager.commit();
		manager.store(produto9);
		manager.commit();
		manager.store(produto10);
		manager.commit();
		
		// ===== CRIANDO CLIENTES E PEDIDOS =====
		
		// Cliente 1 - João (3 pedidos com Pizza) - Enquadra consulta 3
		Cliente cliente1 = new Cliente("João", "Rua A, 123");
		
		Pedido pedido1 = new Pedido("10/10/2023", cliente1);
		pedido1.adicionarProduto(produto1);
		pedido1.adicionarProduto(produto2);
		
		Pedido pedido2 = new Pedido("11/10/2023", cliente1);
		pedido2.adicionarProduto(produto1);
		pedido2.adicionarProduto(produto4);
		
		Pedido pedido3 = new Pedido("12/10/2023", cliente1);
		pedido3.adicionarProduto(produto1);
		pedido3.adicionarProduto(produto3);
		
		cliente1.adicionarPedido(pedido1);
		cliente1.adicionarPedido(pedido2);
		cliente1.adicionarPedido(pedido3);
		
		manager.store(cliente1);
		manager.commit();
		
		// Cliente 2 - Yago (3 pedidos com Pizza) - Enquadra consulta 3
		Cliente cliente2 = new Cliente("Yago", "Rua B, 456");
		
		Pedido pedido4 = new Pedido("13/10/2023", cliente2);
		pedido4.adicionarProduto(produto1);
		pedido4.adicionarProduto(produto7);
		
		Pedido pedido5 = new Pedido("14/10/2023", cliente2);
		pedido5.adicionarProduto(produto1);
		pedido5.adicionarProduto(produto8);
		
		Pedido pedido6 = new Pedido("15/10/2023", cliente2);
		pedido6.adicionarProduto(produto1);
		pedido6.adicionarProduto(produto2);
		
		cliente2.adicionarPedido(pedido4);
		cliente2.adicionarPedido(pedido5);
		cliente2.adicionarPedido(pedido6);
		
		manager.store(cliente2);
		manager.commit();
		
		// Cliente 3 - Bob 
		Cliente cliente3 = new Cliente("Bob", "Rua C, 789");
		
		Pedido pedido7 = new Pedido("16/10/2023", cliente3);
		pedido7.adicionarProduto(produto2);
		pedido7.adicionarProduto(produto5);
		
		Pedido pedido8 = new Pedido("17/10/2023", cliente3);
		pedido8.adicionarProduto(produto3);
		pedido8.adicionarProduto(produto6);
		
		cliente3.adicionarPedido(pedido7);
		cliente3.adicionarPedido(pedido8);
		
		manager.store(cliente3);
		manager.commit();
		
		// Cliente 4 - Carlos
		Cliente cliente4 = new Cliente("Carlos", "Rua D, 321");
		
		Pedido pedido9 = new Pedido("18/10/2023", cliente4);
		pedido9.adicionarProduto(produto4);
		pedido9.adicionarProduto(produto9);
		
		Pedido pedido10 = new Pedido("19/10/2023", cliente4);
		pedido10.adicionarProduto(produto1);
		pedido10.adicionarProduto(produto7);
		
		cliente4.adicionarPedido(pedido9);
		cliente4.adicionarPedido(pedido10);
		
		manager.store(cliente4);
		manager.commit();
		
		// Cliente 5 - Pedro 
		Cliente cliente5 = new Cliente("Pedro", "Rua E, 654");
		
		Pedido pedido11 = new Pedido("20/10/2023", cliente5);
		pedido11.adicionarProduto(produto1);
		pedido11.adicionarProduto(produto3);
		
		Pedido pedido12 = new Pedido("21/10/2023", cliente5);
		pedido12.adicionarProduto(produto2);
		pedido12.adicionarProduto(produto8);
		
		cliente5.adicionarPedido(pedido11);
		cliente5.adicionarPedido(pedido12);
		
		manager.store(cliente5);
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