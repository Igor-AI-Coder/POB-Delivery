package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Pedido;
import modelo.Produto;
import requisito.FachadaCliente;
import requisito.FachadaPedido;
import requisito.FachadaProduto;

public class TelaPedido {

    private JDialog frame;
    private JTable tablePedidos;
    private JScrollPane scrollPane;
    private JTextField txtClienteId;
    private JTextField txtProdutoId;
    private JLabel lblCliente;
    private JLabel lblProduto;
    private JButton btnAdicionarProd;
    private JButton btnSalvarPedido;
    private JButton btnDeletarPedido;
    private JLabel lblStatus;
    private JLabel lblProdAdicionados;

    private FachadaPedido fachadaPedido;
    private FachadaCliente fachadaCliente;
    private FachadaProduto fachadaProduto;
    
    // Lista em cache local para agrupar os produtos selecionados antes de gerar o pedido
    private List<Produto> produtosSelecionados = new ArrayList<>();

    public TelaPedido() {
        fachadaPedido = new FachadaPedido();
        fachadaCliente = new FachadaCliente();
        fachadaProduto = new FachadaProduto();
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("Painel de Pedidos e Carrinho");
        frame.setBounds(100, 100, 550, 480);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent arg0) {
                listagem();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 11, 490, 180);
        frame.getContentPane().add(scrollPane);

        tablePedidos = new JTable();
        scrollPane.setViewportView(tablePedidos);
        tablePedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePedidos.setShowGrid(true);

        lblCliente = new JLabel("ID Cliente:");
        lblCliente.setBounds(20, 210, 80, 20);
        frame.getContentPane().add(lblCliente);

        txtClienteId = new JTextField();
        txtClienteId.setBounds(100, 210, 80, 20);
        frame.getContentPane().add(txtClienteId);

        lblProduto = new JLabel("ID Produto:");
        lblProduto.setBounds(20, 240, 80, 20);
        frame.getContentPane().add(lblProduto);

        txtProdutoId = new JTextField();
        txtProdutoId.setBounds(100, 240, 80, 20);
        frame.getContentPane().add(txtProdutoId);

        lblProdAdicionados = new JLabel("Produtos no carrinho: 0");
        lblProdAdicionados.setFont(new Font("Tahoma", Font.ITALIC, 11));
        lblProdAdicionados.setBounds(20, 275, 300, 20);
        frame.getContentPane().add(lblProdAdicionados);

        btnAdicionarProd = new JButton("Adicionar ao Carrinho");
        btnAdicionarProd.setBounds(190, 238, 160, 23);
        btnAdicionarProd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int idProd = Integer.parseInt(txtProdutoId.getText());
                    Produto p = fachadaProduto.localizarProduto(idProd);
                    if (p == null) throw new Exception("Produto inexistente.");
                    
                    produtosSelecionados.add(p);
                    lblProdAdicionados.setText("Produtos no carrinho: " + produtosSelecionados.size());
                    txtProdutoId.setText("");
                } catch (Exception ex) {
                    lblStatus.setText("Erro Produto: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnAdicionarProd);

        btnSalvarPedido = new JButton("Finalizar Pedido");
        btnSalvarPedido.setBounds(20, 310, 150, 30);
        btnSalvarPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int idCli = Integer.parseInt(txtClienteId.getText());
                    Cliente c = fachadaCliente.localizarCliente(idCli);
                    if (c == null) throw new Exception("Cliente não encontrado.");

                    fachadaPedido.cadastrarPedido(LocalDate.now(), c, produtosSelecionados);
                    lblStatus.setText("Pedido salvo com sucesso!");
                    
                    produtosSelecionados.clear();
                    lblProdAdicionados.setText("Produtos no carrinho: 0");
                    txtClienteId.setText("");
                    listagem();
                } catch (Exception ex) {
                    lblStatus.setText("Erro Pedido: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnSalvarPedido);

        btnDeletarPedido = new JButton("Deletar Selecionado");
        btnDeletarPedido.setBounds(190, 310, 160, 30);
        btnDeletarPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = tablePedidos.getSelectedRow();
                    if (row < 0) {
                        lblStatus.setText("Selecione um pedido na tabela para excluir.");
                        return;
                    }
                    int idPed = (int) tablePedidos.getValueAt(row, 0);
                    fachadaPedido.deletarPedido(idPed);
                    lblStatus.setText("Pedido excluído do sistema!");
                    listagem();
                } catch (Exception ex) {
                    lblStatus.setText("Erro ao deletar: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnDeletarPedido);

        lblStatus = new JLabel("");
        lblStatus.setForeground(Color.RED);
        lblStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblStatus.setBounds(20, 370, 490, 20);
        frame.getContentPane().add(lblStatus);

        frame.setVisible(true);
    }

    public void listagem() {
        try {
            DefaultTableModel model = new DefaultTableModel();
            tablePedidos.setModel(model);
            model.addColumn("ID Pedido");
            model.addColumn("Data");
            model.addColumn("Cliente");
            model.addColumn("Itens");
            model.addColumn("Total (R$)");

            List<Pedido> lista = fachadaPedido.listarTodosPedidos();
            for (Pedido p : lista) {
                model.addRow(new Object[] { 
                    p.getId(), 
                    p.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 
                    p.getCliente().getNome(), 
                    p.getProdutos().size(), 
                    String.format("%.2f", p.calcularTotal()) 
                });
            }
            tablePedidos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(frame, "Erro: " + erro.getMessage());
        }
    }
}