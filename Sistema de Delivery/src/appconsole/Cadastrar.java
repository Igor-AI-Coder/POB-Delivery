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
		Produto produto2 = new Produto("Hamburguer", 45.00);
		Produto produto3 = new Produto("Pastel", 35.00);
		Produto produto4 = new Produto("Sanduiche", 55.00);
		Produto produto5 = new Produto("Refrigerante", 8.00);
		Produto produto6 = new Produto("Brigadeiro", 30.00);
		Produto produto7 = new Produto("Cachorro Quente", 32.00);
		Produto produto8 = new Produto("Coxinha", 38.00);
		Produto produto9 = new Produto("Agua", 5.00);
		Produto produto10 = new Produto("Acaraje", 40.00);
		
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
		
		// Cliente 1 - João Silva (3 pedidos com Pizza) - Enquadra consulta 3
		Cliente cliente1 = new Cliente("João Silva", "Rua A, 123");
		
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
		
		// Cliente 2 - Maria Santos (sem Pizza)
		Cliente cliente2 = new Cliente("Maria Santos", "Rua B, 456");
		
		Pedido pedido4 = new Pedido("13/10/2023", cliente2);
		pedido4.adicionarProduto(produto6);
		pedido4.adicionarProduto(produto5);
		
		Pedido pedido5 = new Pedido("14/10/2023", cliente2);
		pedido5.adicionarProduto(produto7);
		pedido5.adicionarProduto(produto8);
		
		cliente2.adicionarPedido(pedido4);
		cliente2.adicionarPedido(pedido5);
		
		manager.store(cliente2);
		manager.commit();
		
		// Cliente 3 - Carlos Costa (1 pedido com Pizza)
		Cliente cliente3 = new Cliente("Carlos Costa", "Rua C, 789");
		
		Pedido pedido6 = new Pedido("15/10/2023", cliente3);
		pedido6.adicionarProduto(produto9);
		pedido6.adicionarProduto(produto10);
		
		Pedido pedido7 = new Pedido("16/10/2023", cliente3);
		pedido7.adicionarProduto(produto1);
		pedido7.adicionarProduto(produto4);
		
		cliente3.adicionarPedido(pedido6);
		cliente3.adicionarPedido(pedido7);
		
		manager.store(cliente3);
		manager.commit();
		
		// Cliente 4 - Ana Silva (3 pedidos com Pizza) - Enquadra consulta 3
		Cliente cliente4 = new Cliente("Ana Silva", "Rua D, 321");
		
		Pedido pedido8 = new Pedido("10/10/2023", cliente4);
		pedido8.adicionarProduto(produto1);
		pedido8.adicionarProduto(produto5);
		
		Pedido pedido9 = new Pedido("11/10/2023", cliente4);
		pedido9.adicionarProduto(produto1);
		pedido9.adicionarProduto(produto6);
		
		Pedido pedido10 = new Pedido("17/10/2023", cliente4);
		pedido10.adicionarProduto(produto1);
		pedido10.adicionarProduto(produto9);
		
		cliente4.adicionarPedido(pedido8);
		cliente4.adicionarPedido(pedido9);
		cliente4.adicionarPedido(pedido10);
		
		manager.store(cliente4);
		manager.commit();
		
		// Cliente 5 - Pedro Costa (2 pedidos com Pizza)
		Cliente cliente5 = new Cliente("Pedro Costa", "Rua E, 654");
		
		Pedido pedido11 = new Pedido("18/10/2023", cliente5);
		pedido11.adicionarProduto(produto1);
		pedido11.adicionarProduto(produto2);
		
		Pedido pedido12 = new Pedido("19/10/2023", cliente5);
		pedido12.adicionarProduto(produto3);
		pedido12.adicionarProduto(produto7);
		
		Pedido pedido13 = new Pedido("20/10/2023", cliente5);
		pedido13.adicionarProduto(produto1);
		pedido13.adicionarProduto(produto4);
		
		cliente5.adicionarPedido(pedido11);
		cliente5.adicionarPedido(pedido12);
		cliente5.adicionarPedido(pedido13);
		
		manager.store(cliente5);
		manager.commit();
		
		// Cliente 6 - Lucia Fernandes (3 pedidos com Pizza) - Enquadra consulta 3
		Cliente cliente6 = new Cliente("Lucia Fernandes", "Rua F, 987");
		
		Pedido pedido14 = new Pedido("21/10/2023", cliente6);
		pedido14.adicionarProduto(produto1);
		pedido14.adicionarProduto(produto8);
		
		Pedido pedido15 = new Pedido("22/10/2023", cliente6);
		pedido15.adicionarProduto(produto1);
		pedido15.adicionarProduto(produto10);
		
		Pedido pedido16 = new Pedido("23/10/2023", cliente6);
		pedido16.adicionarProduto(produto1);
		pedido16.adicionarProduto(produto3);
		
		cliente6.adicionarPedido(pedido14);
		cliente6.adicionarPedido(pedido15);
		cliente6.adicionarPedido(pedido16);
		
		manager.store(cliente6);
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