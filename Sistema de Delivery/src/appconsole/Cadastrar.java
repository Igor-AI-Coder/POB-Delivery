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
		Produto produto1 = new Produto(0, "Pizza", 85.00);
		Produto produto2 = new Produto(0, "Hamburguer", 45.00);
		Produto produto3 = new Produto(0, "Pastel", 35.00);
		Produto produto4 = new Produto(0, "Sanduiche", 55.00);
		Produto produto5 = new Produto(0, "Refrigerante", 8.00);
		Produto produto6 = new Produto(0, "Brigadeiro", 30.00);
		Produto produto7 = new Produto(0, "Cachorro Quente", 32.00);
		Produto produto8 = new Produto(0, "Coxinha", 38.00);
		Produto produto9 = new Produto(0, "Agua", 5.00);
		Produto produto10 = new Produto(0, "Acaraje", 40.00);
		
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
		
		// Cliente 1 - João Silva
		Cliente cliente1 = new Cliente("João Silva", "Rua A, 123");
		
		Pedido pedido1 = new Pedido(0, "10/10/2023", cliente1);
		pedido1.adicionarProduto(produto1);
		pedido1.adicionarProduto(produto2);
		
		Pedido pedido2 = new Pedido(0, "10/10/2023", cliente1);
		pedido2.adicionarProduto(produto1);
		pedido2.adicionarProduto(produto4);
		
		Pedido pedido3 = new Pedido(0, "10/10/2023", cliente1);
		pedido3.adicionarProduto(produto1);
		pedido3.adicionarProduto(produto3);
		
		cliente1.adicionarPedido(pedido1);
		cliente1.adicionarPedido(pedido2);
		cliente1.adicionarPedido(pedido3);
		
		manager.store(cliente1);
		manager.commit();
		
		// Cliente 2 - Maria Santos 
		Cliente cliente2 = new Cliente("Maria Santos", "Rua B, 456");
		
		Pedido pedido4 = new Pedido(0, "12/10/2023", cliente2);
		pedido4.adicionarProduto(produto6);
		pedido4.adicionarProduto(produto5);
		
		Pedido pedido5 = new Pedido(0, "13/10/2023", cliente2);
		pedido5.adicionarProduto(produto7);
		pedido5.adicionarProduto(produto8);
		
		cliente2.adicionarPedido(pedido4);
		cliente2.adicionarPedido(pedido5);
		
		manager.store(cliente2);
		manager.commit();
		
		// Cliente 3 - Carlos Costa 
		Cliente cliente3 = new Cliente("Carlos Costa", "Rua C, 789");
		
		Pedido pedido6 = new Pedido(0, "14/10/2023", cliente3);
		pedido6.adicionarProduto(produto9);
		pedido6.adicionarProduto(produto10);
		
		Pedido pedido7 = new Pedido(0, "15/10/2023", cliente3);
		pedido7.adicionarProduto(produto2);
		pedido7.adicionarProduto(produto4);
		
		cliente3.adicionarPedido(pedido6);
		cliente3.adicionarPedido(pedido7);
		
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