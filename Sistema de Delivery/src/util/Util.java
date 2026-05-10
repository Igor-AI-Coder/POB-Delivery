/**
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

package util;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceConfiguration;
import jakarta.persistence.PersistenceUnitTransactionType;

public class Util {
	private static EntityManager manager;
	private static EntityManagerFactory factory;

	static {
		// Força o Log4j2 a usar o arquivo em META-INF quando estiver no classpath.
		if (System.getProperty("log4j.configurationFile") == null) {
			System.setProperty("log4j.configurationFile", "classpath:META-INF/log4j2.properties");
		}
	}

	private static final Logger logger = LogManager.getLogger(Util.class);

	@SuppressWarnings("unused")
	public static void conectar() {
		if (manager == null) {
			// ler dicionario do arquivo "ip.properties" 
			Properties propriedades = new Properties();
			try {
				propriedades.load(Util.class.getResourceAsStream("/util/ip.properties"));
			} catch (IOException e) {
				throw new RuntimeException("arquivo /util/ip.properties inexistente");
			}
			
			//acessar os dados do dicionario
			String sgbd = propriedades.getProperty("sgbd");
			String banco = propriedades.getProperty("bd");
			String usuario = propriedades.getProperty("usuario");
			String senha = propriedades.getProperty("senha");
			String ipatual = propriedades.getProperty("ipatual");

			if (sgbd.equals("postgresql")) {
				logger.info("----conectando postgresql");
				factory = Persistence.createEntityManagerFactory("hibernate-postgresql");

				// String url = "jdbc:" + sgbd + "://" + ipatual + ":5432/" + banco;
				// factory = alterarConfiguracao(url, usuario, senha);
			}

			if (sgbd.equals("mysql")) {
				logger.info("----conectando mysql");
				factory = Persistence.createEntityManagerFactory("hibernate-mysql");

				// String url = "jdbc:" + sgbd + "://" + ipatual + ":3306/" + banco;
				// factory = alterarConfiguracao(url, usuario, senha);
			}
			
			manager = factory.createEntityManager();
			logger.info("-------- conectou banco de dados");
		}
	}

	public static void desconectar() {
		if (manager != null && manager.isOpen()) {
			manager.close();
			factory.close();
			manager = null;
			logger.debug("-------- desconectou banco de dados");
		}
	}

	public static EntityManagerFactory alterarConfiguracao(String url, String usuario, String senha) {
		// faz o mesmo trabalho do persistence.xml, mas de forma programatica
		return new PersistenceConfiguration("hibernate-postgresql")
				.transactionType(PersistenceUnitTransactionType.RESOURCE_LOCAL)
				.provider(HibernatePersistenceProvider.class.getName())
				.managedClass(modelo.Produto.class)
				.managedClass(modelo.Cliente.class)
				.managedClass(modelo.Pedido.class)
				.property(PersistenceConfiguration.JDBC_URL, url)
				.property(PersistenceConfiguration.JDBC_USER, usuario)
				.property(PersistenceConfiguration.JDBC_PASSWORD, senha)
				.property("hibernate.hbm2ddl.auto", "update")
				.property(JdbcSettings.SHOW_SQL, false)
				.property(JdbcSettings.FORMAT_SQL, false)
				.property(JdbcSettings.HIGHLIGHT_SQL, false)
				.createEntityManagerFactory();

	}

	public static EntityManager getManager() {
		return manager;
	}

}