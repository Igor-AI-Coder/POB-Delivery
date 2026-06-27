package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import requisito.FachadaCliente;

public class TelaCliente {

    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField textFieldNome;
    private JTextField textFieldEndereco;
    private JLabel labelNome;
    private JLabel labelEndereco;
    private JButton btnCriar;
    private JButton btnAtualizar;
    private JButton btnDeletar;
    private JButton btnBuscar;
    private JLabel labelStatus;
    
    private FachadaCliente fachadaCliente;

    public TelaCliente() {
        fachadaCliente = new FachadaCliente();
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("Gerenciamento de Clientes");
        frame.setBounds(100, 100, 600, 420);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent arg0) {
                listagem(null);
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 11, 540, 193);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    textFieldNome.setText((String) table.getValueAt(row, 1));
                    textFieldEndereco.setText((String) table.getValueAt(row, 2));
                }
            }
        });
        scrollPane.setViewportView(table);
        table.setGridColor(Color.BLACK);
        table.setBackground(Color.WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);

        labelNome = new JLabel("Nome:");
        labelNome.setHorizontalAlignment(SwingConstants.RIGHT);
        labelNome.setBounds(20, 230, 70, 14);
        frame.getContentPane().add(labelNome);

        labelEndereco = new JLabel("Endereço:");
        labelEndereco.setHorizontalAlignment(SwingConstants.RIGHT);
        labelEndereco.setBounds(20, 260, 70, 14);
        frame.getContentPane().add(labelEndereco);

        textFieldNome = new JTextField();
        textFieldNome.setBounds(100, 228, 180, 20);
        frame.getContentPane().add(textFieldNome);

        textFieldEndereco = new JTextField();
        textFieldEndereco.setBounds(100, 258, 180, 20);
        frame.getContentPane().add(textFieldEndereco);

        btnCriar = new JButton("Criar");
        btnCriar.setBounds(300, 225, 120, 25);
        btnCriar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    fachadaCliente.cadastrarCliente(textFieldNome.getText(), textFieldEndereco.getText());
                    labelStatus.setText("Cliente cadastrado com sucesso!");
                    limparCampos();
                    listagem(null);
                } catch (Exception ex) {
                    labelStatus.setText("Erro: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnCriar);

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(430, 225, 120, 25);
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = table.getSelectedRow();
                    if (row < 0) {
                        labelStatus.setText("Selecione um cliente para atualizar.");
                        return;
                    }
                    int id = (int) table.getValueAt(row, 0);
                    fachadaCliente.atualizarCliente(id, textFieldNome.getText(), textFieldEndereco.getText());
                    labelStatus.setText("Dados do cliente atualizados!");
                    limparCampos();
                    listagem(null);
                } catch (Exception ex) {
                    labelStatus.setText("Erro: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnAtualizar);

        btnDeletar = new JButton("Deletar");
        btnDeletar.setBounds(300, 260, 120, 25);
        btnDeletar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = table.getSelectedRow();
                    if (row < 0) {
                        labelStatus.setText("Selecione um cliente para remover.");
                        return;
                    }
                    int id = (int) table.getValueAt(row, 0);
                    fachadaCliente.deletarCliente(id);
                    labelStatus.setText("Cliente removido!");
                    limparCampos();
                    listagem(null);
                } catch (Exception ex) {
                    labelStatus.setText("Erro: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnDeletar);

        btnBuscar = new JButton("Buscar Endereço");
        btnBuscar.setBounds(430, 260, 120, 25);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String busca = textFieldEndereco.getText();
                if (busca.isEmpty()) {
                    listagem(null);
                    labelStatus.setText("Listando todos os clientes.");
                } else {
                    listagem(busca);
                    labelStatus.setText("Resultados da busca para o endereço: " + busca);
                }
            }
        });
        frame.getContentPane().add(btnBuscar);

        labelStatus = new JLabel("");
        labelStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
        labelStatus.setForeground(Color.RED);
        labelStatus.setBounds(20, 310, 540, 14);
        frame.getContentPane().add(labelStatus);

        frame.setVisible(true);
    }

    private void limparCampos() {
        textFieldNome.setText("");
        textFieldEndereco.setText("");
        table.clearSelection();
    }

    public void listagem(String buscaEndereco) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);
            model.addColumn("ID");
            model.addColumn("Nome");
            model.addColumn("Endereço");

            List<Cliente> lista;
            // Verifica se tem um texto de busca, se sim aciona o método específico da fachada
            if (buscaEndereco != null && !buscaEndereco.trim().isEmpty()) {
                lista = fachadaCliente.consultarClientesPorEndereco(buscaEndereco);
            } else {
                lista = fachadaCliente.listarClientes();
            }

            for (Cliente c : lista) {
                model.addRow(new Object[] { c.getId(), c.getNome(), c.getEndereco() });
            }
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(frame, "Erro na listagem: " + erro.getMessage());
        }
    }
}