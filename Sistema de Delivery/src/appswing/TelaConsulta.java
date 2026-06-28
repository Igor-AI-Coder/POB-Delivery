package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Pedido;
import requisito.FachadaCliente;
import requisito.FachadaPedido;

public class TelaConsulta {

    private JDialog frame;
    private JTable tableResultados;
    private JScrollPane scrollPane;
    private JTextField txtParametro;
    private JLabel lblParametro;
    private JButton btnConsulta1;
    private JButton btnConsulta2;
    private JButton btnConsulta3;
    private JLabel lblStatus;

    private FachadaPedido fachadaPedido;
    private FachadaCliente fachadaCliente;

    public TelaConsulta() {
        fachadaPedido = new FachadaPedido();
        fachadaCliente = new FachadaCliente();
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("Central de Consultas (JPQL)");
        frame.setBounds(100, 100, 680, 430);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        lblParametro = new JLabel("Filtro/Argumento:");
        lblParametro.setBounds(20, 20, 110, 20);
        frame.getContentPane().add(lblParametro);

        txtParametro = new JTextField();
        txtParametro.setBounds(135, 20, 120, 20);
        frame.getContentPane().add(txtParametro);
        txtParametro.setColumns(10);

        // --- BOTÃO CONSULTA 1 ---
        btnConsulta1 = new JButton("1. Pedidos por Data");
        btnConsulta1.setBounds(20, 60, 180, 25);
        btnConsulta1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate data = LocalDate.parse(txtParametro.getText(), formatter);
                    List<Pedido> pedidos = fachadaPedido.consultarPedidosPorData(data);
                    
                    DefaultTableModel model = new DefaultTableModel();
                    model.addColumn("ID Pedido");
                    model.addColumn("Data");
                    model.addColumn("Cliente");
                    model.addColumn("Valor Total (R$)");
                    
                    for(Pedido p : pedidos) {
                        model.addRow(new Object[]{p.getId(), p.getData().format(formatter), p.getCliente().getNome(), String.format("%.2f", p.calcularTotal())});
                    }
                    tableResultados.setModel(model);
                    lblStatus.setText("Consulta 1 realizada. Encontrados: " + pedidos.size());
                } catch(Exception ex) {
                    lblStatus.setText("Formatos válidos: dd/MM/yyyy. Erro: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnConsulta1);

        // --- BOTÃO CONSULTA 2 ---
        btnConsulta2 = new JButton("2. Itens Acima de Preço");
        btnConsulta2.setBounds(215, 60, 190, 25);
        btnConsulta2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double preco = Double.parseDouble(txtParametro.getText());
                    List<Pedido> pedidos = fachadaPedido.consultarPedidosComProdutosAcimaDe(preco);
                    
                    DefaultTableModel model = new DefaultTableModel();
                    model.addColumn("ID Pedido");
                    model.addColumn("Cliente");
                    model.addColumn("Valor Total");
                    
                    for(Pedido p : pedidos) {
                        model.addRow(new Object[]{p.getId(), p.getCliente().getNome(), String.format("%.2f", p.calcularTotal())});
                    }
                    tableResultados.setModel(model);
                    lblStatus.setText("Consulta 2 realizada. Encontrados: " + pedidos.size());
                } catch(Exception ex) {
                    lblStatus.setText("Informe um valor numérico decimal válido. Erro: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnConsulta2);

        // --- BOTÃO CONSULTA 3 (ORIGINAL DA ETAPA 2) ---
        btnConsulta3 = new JButton("3. Clientes (+2 Pizzas)");
        btnConsulta3.setBounds(420, 60, 210, 25);
        btnConsulta3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Executa a consulta da Fachada (não precisa ler a caixa de texto)
                    List<Cliente> clientes = fachadaCliente.consultarClientesMaisDe2Pizzas();
                    
                    DefaultTableModel model = new DefaultTableModel();
                    model.addColumn("ID Cliente");
                    model.addColumn("Nome do Cliente");
                    model.addColumn("Endereço");
                    
                    for(Cliente c : clientes) {
                        model.addRow(new Object[]{c.getId(), c.getNome(), c.getEndereco()});
                    }
                    tableResultados.setModel(model);
                    lblStatus.setText("Consulta 3 realizada. Encontrados: " + clientes.size());
                } catch(Exception ex) {
                    lblStatus.setText("Erro: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnConsulta3);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 105, 610, 230);
        frame.getContentPane().add(scrollPane);

        tableResultados = new JTable();
        scrollPane.setViewportView(tableResultados);

        lblStatus = new JLabel("Selecione uma consulta para visualizar os dados.");
        lblStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblStatus.setForeground(Color.BLUE);
        lblStatus.setBounds(20, 350, 585, 20);
        frame.getContentPane().add(lblStatus);

        frame.setVisible(true);
    }
}