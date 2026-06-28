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
        frame.setBounds(100, 100, 520, 420);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent arg0) {
                listagem();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 11, 460, 193);
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
        textFieldNome.setBounds(100, 228, 150, 20);
        frame.getContentPane().add(textFieldNome);

        textFieldEndereco = new JTextField();
        textFieldEndereco.setBounds(100, 258, 150, 20);
        frame.getContentPane().add(textFieldEndereco);

        btnCriar = new JButton("Criar");
        btnCriar.setBounds(260, 225, 100, 25);
        btnCriar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    fachadaCliente.cadastrarCliente(textFieldNome.getText(), textFieldEndereco.getText());
                    labelStatus.setText("Cliente cadastrado com sucesso!");
                    limparCampos();
                    listagem();
                } catch (Exception ex) {
                    labelStatus.setText("Erro: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnCriar);

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(370, 225, 110, 25);
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
                    listagem();
                } catch (Exception ex) {
                    labelStatus.setText("Erro: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnAtualizar);

        btnDeletar = new JButton("Deletar");
        btnDeletar.setBounds(260, 260, 100, 25);
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
                    listagem();
                } catch (Exception ex) {
                    labelStatus.setText("Erro: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnDeletar);

        labelStatus = new JLabel("");
        labelStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
        labelStatus.setForeground(Color.RED);
        labelStatus.setBounds(20, 310, 460, 14);
        frame.getContentPane().add(labelStatus);

        frame.setVisible(true);
    }

    private void limparCampos() {
        textFieldNome.setText("");
        textFieldEndereco.setText("");
        table.clearSelection();
    }

    public void listagem() {
        try {
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table.setModel(model);
            model.addColumn("ID");
            model.addColumn("Nome");
            model.addColumn("Endereço");

            List<Cliente> lista = fachadaCliente.listarClientes();

            for (Cliente c : lista) {
                model.addRow(new Object[] { c.getId(), c.getNome(), c.getEndereco() });
            }
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(frame, "Erro na listagem: " + erro.getMessage());
        }
    }
}