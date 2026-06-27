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

import modelo.Produto;
import requisito.FachadaProduto;

public class TelaProduto {

    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JLabel labelNome;
    private JLabel labelPreco;
    private JTextField textFieldNome;
    private JTextField textFieldPreco;
    private JButton btnCriar;
    private JButton btnAtualizar;
    private JButton btnDeletar;
    private JLabel labelStatus;
    
    private FachadaProduto fachada;

    public TelaProduto() {
        fachada = new FachadaProduto();
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("Gerenciar Produtos");
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
        // Evento seguro para preencher os campos ao clicar em um item
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    Object valNome = table.getValueAt(row, 1);
                    Object valPreco = table.getValueAt(row, 2);
                    
                    textFieldNome.setText(valNome != null ? valNome.toString() : "");
                    textFieldPreco.setText(valPreco != null ? valPreco.toString() : "");
                }
            }
        });
        scrollPane.setViewportView(table);
        table.setGridColor(Color.BLACK);
        table.setBackground(Color.WHITE);
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);

        labelNome = new JLabel("Nome:");
        labelNome.setHorizontalAlignment(SwingConstants.RIGHT);
        labelNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelNome.setBounds(20, 230, 60, 14);
        frame.getContentPane().add(labelNome);

        labelPreco = new JLabel("Preço:");
        labelPreco.setHorizontalAlignment(SwingConstants.RIGHT);
        labelPreco.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelPreco.setBounds(20, 260, 60, 14);
        frame.getContentPane().add(labelPreco);

        textFieldNome = new JTextField();
        textFieldNome.setBounds(90, 228, 150, 20);
        frame.getContentPane().add(textFieldNome);
        textFieldNome.setColumns(10);

        textFieldPreco = new JTextField();
        textFieldPreco.setBounds(90, 258, 150, 20);
        frame.getContentPane().add(textFieldPreco);
        textFieldPreco.setColumns(10);

        // --- BOTÃO CRIAR ---
        btnCriar = new JButton("Criar");
        btnCriar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnCriar.setBounds(260, 225, 90, 25);
        btnCriar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = textFieldNome.getText();
                    double preco = Double.parseDouble(textFieldPreco.getText());
                    fachada.criarProduto(nome, preco);
                    labelStatus.setText("Produto criado com sucesso!");
                    limparCampos();
                    listagem();
                } catch (NumberFormatException ex) {
                    labelStatus.setText("Erro: Preço inválido.");
                } catch (Exception ex) {
                    labelStatus.setText("Erro: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnCriar);

        // --- BOTÃO ATUALIZAR ---
        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnAtualizar.setBounds(360, 225, 100, 25);
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = table.getSelectedRow();
                    if (row < 0) {
                        labelStatus.setText("Selecione um produto na tabela para atualizar.");
                        return;
                    }
                    
                    // Extração de ID com conversão segura
                    Object idObj = table.getValueAt(row, 0);
                    if (idObj == null) {
                        labelStatus.setText("Erro: ID inválido.");
                        return;
                    }
                    int id = Integer.parseInt(idObj.toString());
                    
                    String nome = textFieldNome.getText();
                    double preco = Double.parseDouble(textFieldPreco.getText());
                    
                    fachada.atualizarProduto(id, nome, preco);
                    labelStatus.setText("Produto atualizado com sucesso!");
                    limparCampos();
                    listagem();
                } catch (NumberFormatException ex) {
                    labelStatus.setText("Erro: Preço numérico inválido.");
                } catch (Exception ex) {
                    labelStatus.setText("Erro: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnAtualizar);

        // --- BOTÃO DELETAR ---
        btnDeletar = new JButton("Deletar");
        btnDeletar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnDeletar.setBounds(260, 260, 90, 25);
        btnDeletar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = table.getSelectedRow();
                    if (row < 0) {
                        labelStatus.setText("Selecione um produto na tabela para deletar.");
                        return;
                    }
                    
                    // Extração de ID blindada contra nulos e vazios
                    Object idObj = table.getValueAt(row, 0);
                    if (idObj == null) {
                        labelStatus.setText("Erro: O produto selecionado possui ID nulo.");
                        return;
                    }
                    int id = Integer.parseInt(idObj.toString());
                    
                    fachada.deletarProduto(id);
                    labelStatus.setText("Produto deletado com sucesso!");
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
        textFieldPreco.setText("");
        table.clearSelection();
    }

    public void listagem() {
        try {
            // Criação do modelo da tabela BLOQUEANDO a edição das células
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Retorna false para impedir que a célula vire um campo de texto ao clicar
                }
            };
            
            table.setModel(model);

            model.addColumn("ID");
            model.addColumn("Nome");
            model.addColumn("Preço (R$)");

            List<Produto> lista = fachada.listarProdutos();
            for (Produto p : lista) {
                model.addRow(new Object[] { p.getId(), p.getNome(), p.getPreco() });
            }

            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(frame, "Erro ao listar: " + erro.getMessage());
        }
    }
}