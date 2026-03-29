package util;

import java.util.Properties;
import javax.swing.JOptionPane;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;

import modelo.Cliente;
import modelo.Pedido;
import modelo.Produto;

public class Util {
    private static ObjectContainer manager;
    private static String ipservidor;

    public static void conectar() {
        try {
            Properties props = new Properties();
            props.load(Util.class.getResourceAsStream("/util/ip.properties")); // lê ip
            ipservidor = props.getProperty("ipatual");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro IP");
            System.exit(0);
        }

        if (ipservidor.equals("localhost"))
            conectarBancoLocal(); // local
        else
            conectarBancoRemoto(); // remoto

        ControleID.ativar(manager); // ativa auto ID
    }

    private static void conectarBancoLocal() {
        if (manager != null)
            return; // já conectado

        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().messageLevel(0); // sem log

        // ===== CONFIG CLASSES =====

        // Cliente -> pedidos
        config.common().objectClass(Cliente.class).cascadeOnUpdate(true);   // atualiza pedidos
        config.common().objectClass(Cliente.class).cascadeOnActivate(true); // carrega pedidos
        config.common().objectClass(Cliente.class).cascadeOnDelete(false);  // NÃO deleta

        // Pedido -> produtos
        config.common().objectClass(Pedido.class).cascadeOnUpdate(true);   // atualiza produtos
        config.common().objectClass(Pedido.class).cascadeOnActivate(true); // carrega produtos
        config.common().objectClass(Pedido.class).cascadeOnDelete(false);  // NÃO deleta

        // Produto (independente)
        config.common().objectClass(Produto.class).cascadeOnUpdate(false);  // sem cascata
        config.common().objectClass(Produto.class).cascadeOnActivate(true);// carrega
        config.common().objectClass(Produto.class).cascadeOnDelete(false); // NÃO deleta

        try {
            manager = Db4oEmbedded.openFile(config, "banco.db4o"); // abre banco
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro banco local");
            System.exit(0);
        }
    }

    private static void conectarBancoRemoto() {
        if (manager != null)
            return; // já conectado

        ClientConfiguration config = Db4oClientServer.newClientConfiguration();
        config.common().messageLevel(0); // sem log

        // mesma config do local

        config.common().objectClass(Cliente.class).cascadeOnUpdate(true);
        config.common().objectClass(Cliente.class).cascadeOnActivate(true);
        config.common().objectClass(Cliente.class).cascadeOnDelete(false);

        config.common().objectClass(Pedido.class).cascadeOnUpdate(true);
        config.common().objectClass(Pedido.class).cascadeOnActivate(true);
        config.common().objectClass(Pedido.class).cascadeOnDelete(false);

        config.common().objectClass(Produto.class).cascadeOnUpdate(false);
        config.common().objectClass(Produto.class).cascadeOnActivate(true);
        config.common().objectClass(Produto.class).cascadeOnDelete(false);

        try {
            manager = Db4oClientServer.openClient(config, ipservidor, 34000, "usuario1", "senha1"); // conecta
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro banco remoto");
            System.exit(0);
        }
    }

    public static void desconectar() {
        if (manager != null) {
            manager.close(); // fecha banco
            manager = null;
        }
    }

    public static ObjectContainer getManager() {
        return manager; // retorna conexão
    }

    public static String getIPservidor() {
        return ipservidor; // retorna ip
    }
}