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
import util.Util;

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

        mnProduto = new JMenu("Produtos");
        mnProduto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaProduto();
            }
        });
        menuBar.add(mnProduto);

        mnCliente = new JMenu("Clientes");
        mnCliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaCliente();
            }
        });
        menuBar.add(mnCliente);

        mnPedido = new JMenu("Pedidos");
        mnPedido.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaPedido();
            }
        });
        menuBar.add(mnPedido);

        // NOME ALTERADO PARA APENAS "Consultas"
        mnConsulta = new JMenu("Consultas");
        mnConsulta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaConsulta();
            }
        });
        menuBar.add(mnConsulta);
    }
}