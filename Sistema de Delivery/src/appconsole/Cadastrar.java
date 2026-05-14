package appconsole;

import java.time.LocalDate;

import jakarta.persistence.EntityManager;
import modelo.Cliente;
import modelo.Pedido;
import modelo.Produto;
import util.Util;

public class Cadastrar {

	public Cadastrar() {
		try {
			Util.conectar();
			EntityManager manager = Util.getManager();

			System.out.println("Cadastrando produtos, clientes e pedidos...");

			// Cadastra 5 produtos
			Produto[] produtos = new Produto[5];
			produtos[0] = new Produto("Pizza", 85.00);
			produtos[1] = new Produto("Sanduíche", 50.99);
			produtos[2] = new Produto("Pastel", 15.00);
			produtos[3] = new Produto("Coxinha", 12.00);
			produtos[4] = new Produto("Refrigerante", 8.00);

			for (Produto p : produtos) {
				manager.getTransaction().begin();
				manager.persist(p);  // Insere no banco
				manager.getTransaction().commit();  // Confirma a operação
			}

			System.out.println("✓ 5 produtos cadastrados");

			// Cliente 1: João Silva com 3 pedidos
			manager.getTransaction().begin();
			Cliente c1 = new Cliente("João Silva", "Rua A, 123");
			
			c1.adicionarPedido(new Pedido(LocalDate.of(2024, 1, 15), c1));
			c1.getPedidos().get(0).adicionarProduto(produtos[4]);  // Refrigerante
			c1.getPedidos().get(0).adicionarProduto(produtos[2]);  // Pastel
			
			c1.adicionarPedido(new Pedido(LocalDate.of(2024, 1, 20), c1));
			c1.getPedidos().get(1).adicionarProduto(produtos[0]);  // Pizza
			c1.getPedidos().get(1).adicionarProduto(produtos[2]);  // Pastel
			
			c1.adicionarPedido(new Pedido(LocalDate.of(2024, 1, 25), c1));
			c1.getPedidos().get(2).adicionarProduto(produtos[0]);  // Pizza
			c1.getPedidos().get(2).adicionarProduto(produtos[3]);  // Coxinha
			
			c1.adicionarPedido(new Pedido(LocalDate.of(2024, 1, 30), c1));
			c1.getPedidos().get(3).adicionarProduto(produtos[0]);  // Pizza
			
			
			manager.persist(c1);
			manager.getTransaction().commit();

			// Cliente 2: Maria Santos com 2 pedidos
			manager.getTransaction().begin();
			Cliente c2 = new Cliente("Maria Santos", "Rua B, 456");
			
			c2.adicionarPedido(new Pedido(LocalDate.of(2024, 2, 10), c2));
			c2.getPedidos().get(0).adicionarProduto(produtos[3]);  // Coxinha
			c2.getPedidos().get(0).adicionarProduto(produtos[4]);  // Refrigerante
			
			c2.adicionarPedido(new Pedido(LocalDate.of(2024, 2, 15), c2));
			c2.getPedidos().get(1).adicionarProduto(produtos[1]);  // Sanduíche
			c2.getPedidos().get(1).adicionarProduto(produtos[3]);  // Coxinha
			
			manager.persist(c2);
			manager.getTransaction().commit();

			// Cliente 3: José Oliveira com 1 pedido
			manager.getTransaction().begin();
			Cliente c3 = new Cliente("José Oliveira", "Rua C, 789");
			
			c3.adicionarPedido(new Pedido(LocalDate.of(2024, 2, 20), c3));
			c3.getPedidos().get(0).adicionarProduto(produtos[1]);  // Sanduíche
			c3.getPedidos().get(0).adicionarProduto(produtos[2]);  // Pastel
			
			manager.persist(c3);
			manager.getTransaction().commit();

			System.out.println("✓ 3 clientes e 6 pedidos cadastrados");

		} catch (Exception e) {
			System.out.println("ERRO: " + e.getMessage());
			e.printStackTrace();
		}

		Util.desconectar();
		System.out.println("fim do programa");
	}

	public static void main(String[] args) {
		new Cadastrar();
	}
}