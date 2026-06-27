package appswing;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import util.Util; // Importação essencial para ligar o banco de dados!

public class TelaPrincipal {
    private JFrame frame;
    private JMenu mnProduto;
    private JMenu mnCliente;
    private JMenu mnPedido;
    private JMenu mnConsulta;
    private JLabel labelTitulo;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaPrincipal window = new TelaPrincipal();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaPrincipal() {
        // 1. INICIA A CONEXÃO COM O BANCO DE DADOS AQUI!
        // Sem isso, nenhuma das telas vai conseguir carregar ou criar informações.
        try {
            Util.conectar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados (Verifique o servidor): " + e.getMessage());
        }
        
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("POB Delivery - Painel Administrativo");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);

        // 2. Garante que o banco seja desconectado corretamente quando fechar a janela no "X"
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Util.desconectar();
            }
        });

        labelTitulo = new JLabel("Sistema de Gestão de Delivery");
        labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitulo.setBounds(0, 0, 584, 339);
        frame.getContentPane().add(labelTitulo);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // --- MENU PRODUTO ---
        mnProduto = new JMenu("Produtos");
        mnProduto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaProduto();
            }
        });
        menuBar.add(mnProduto);

        // --- MENU CLIENTE ---
        mnCliente = new JMenu("Clientes");
        mnCliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaCliente(); // Agora está destravado e acionando a tela correta!
            }
        });
        menuBar.add(mnCliente);

        // --- MENU PEDIDO ---
        mnPedido = new JMenu("Pedidos");
        mnPedido.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaPedido(); // Agora está destravado e acionando a tela correta!
            }
        });
        menuBar.add(mnPedido);

        // --- MENU CONSULTA ---
        mnConsulta = new JMenu("Consultas Relatórios");
        mnConsulta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaConsulta(); // Aba adicionada fisicamente à barra principal!
            }
        });
        menuBar.add(mnConsulta);
    }
}