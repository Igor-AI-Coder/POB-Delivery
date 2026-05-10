/**
 * IFPB - SI
 * POB - Persistencia de Objetos - Etapa 2 (JPA)
 */

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
			
			// ===== CADASTRANDO PRODUTOS =====
			manager.getTransaction().begin();
			Produto produto1 = new Produto("Pizza", 85.00);
			manager.persist(produto1);
			manager.getTransaction().commit();
			
			manager.getTransaction().begin();
			Produto produto2 = new Produto("Sanduíche", 28.00);
			manager.persist(produto2);
			manager.getTransaction().commit();
			
			manager.getTransaction().begin();
			Produto produto3 = new Produto("Pastel", 15.00);
			manager.persist(produto3);
			manager.getTransaction().commit();
			
			manager.getTransaction().begin();
			Produto produto4 = new Produto("Coxinha", 12.00);
			manager.persist(produto4);
			manager.getTransaction().commit();
			
			manager.getTransaction().begin();
			Produto produto5 = new Produto("Refrigerante", 8.00);
			manager.persist(produto5);
			manager.getTransaction().commit();
			
			System.out.println("✓ 5 produtos cadastrados");
			
			// ===== CADASTRANDO CLIENTES E PEDIDOS =====
			// Cliente 1 - João
			manager.getTransaction().begin();
			Cliente cliente1 = new Cliente("João Silva", "Rua A, 123");
			
			Pedido pedido1 = new Pedido(LocalDate.of(2024, 1, 15), cliente1);
			pedido1.adicionarProduto(produto1);  // Pizza
			pedido1.adicionarProduto(produto2);  // Sanduíche
			
			Pedido pedido2 = new Pedido(LocalDate.of(2024, 1, 20), cliente1);
			pedido2.adicionarProduto(produto1);  // Pizza
			pedido2.adicionarProduto(produto3);  // Pastel
			
			Pedido pedido3 = new Pedido(LocalDate.of(2024, 1, 25), cliente1);
			pedido3.adicionarProduto(produto1);  // Pizza
			pedido3.adicionarProduto(produto4);  // Coxinha
			
			cliente1.adicionarPedido(pedido1);
			cliente1.adicionarPedido(pedido2);
			cliente1.adicionarPedido(pedido3);
			
			manager.persist(cliente1);
			manager.getTransaction().commit();
			
			// Cliente 2 - Maria
			manager.getTransaction().begin();
			Cliente cliente2 = new Cliente("Maria Santos", "Rua B, 456");
			
			Pedido pedido4 = new Pedido(LocalDate.of(2024, 2, 10), cliente2);
			pedido4.adicionarProduto(produto1);  // Pizza
			pedido4.adicionarProduto(produto5);  // Refrigerante
			
			Pedido pedido5 = new Pedido(LocalDate.of(2024, 2, 15), cliente2);
			pedido5.adicionarProduto(produto2);  // Sanduíche
			pedido5.adicionarProduto(produto4);  // Coxinha
			
			cliente2.adicionarPedido(pedido4);
			cliente2.adicionarPedido(pedido5);
			
			manager.persist(cliente2);
			manager.getTransaction().commit();
			
			// Cliente 3 - José
			manager.getTransaction().begin();
			Cliente cliente3 = new Cliente("José Oliveira", "Rua C, 789");
			
			Pedido pedido6 = new Pedido(LocalDate.of(2024, 2, 20), cliente3);
			pedido6.adicionarProduto(produto1);  // Pizza
			pedido6.adicionarProduto(produto3);  // Pastel
			
			cliente3.adicionarPedido(pedido6);
			
			manager.persist(cliente3);
			manager.getTransaction().commit();
			
			System.out.println("✓ 3 clientes e 6 pedidos cadastrados");
			
		}
		catch (Exception e) {
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