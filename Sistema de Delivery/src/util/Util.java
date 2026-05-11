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
	private static final Logger logger = LogManager.getLogger(Util.class);

	static {
		System.setProperty("log4j.configurationFile", "classpath:log4j2.properties");
	}

	public static void conectar() {
		if (manager == null) {
			Properties props = new Properties();
			try {
				props.load(Util.class.getResourceAsStream("/util/ip.properties"));
			} catch (IOException e) {
				throw new RuntimeException("Arquivo ip.properties não encontrado");
			}

			String sgbd = props.getProperty("sgbd");
			String banco = props.getProperty("bd");
			String usuario = props.getProperty("usuario");
			String senha = props.getProperty("senha");
			String ipatual = props.getProperty("ipatual");

			logger.info("Conectando " + sgbd);

			if (sgbd.equals("postgresql")) {
				String url = "jdbc:" + sgbd + "://" + ipatual + ":5432/" + banco;
				factory = criarFactory(url, usuario, senha);
			} else if (sgbd.equals("mysql")) {
				String url = "jdbc:" + sgbd + "://" + ipatual + ":3306/" + banco;
				factory = criarFactory(url, usuario, senha);
			}

			manager = factory.createEntityManager();
			logger.info("Banco conectado com sucesso");
		}
	}

	public static void desconectar() {
		if (manager != null && manager.isOpen()) {
			manager.close();
			factory.close();
			manager = null;
			logger.info("Banco desconectado");
		}
	}

	private static EntityManagerFactory criarFactory(String url, String usuario, String senha) {
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
				.property(JdbcSettings.FORMAT_SQL, true)
				.property(JdbcSettings.HIGHLIGHT_SQL, false)
				.createEntityManagerFactory();
	}

	public static EntityManager getManager() {
		return manager;
	}
}