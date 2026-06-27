package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    
    // Novos componentes para a Imagem (@Lob)
    private JLabel labelFoto;
    private JButton btnEscolherFoto;
    private JButton btnSalvarFoto;
    private byte[] fotoAtualEmMemoria; // Guarda os bytes da imagem escolhida temporariamente
    
    private FachadaProduto fachada;

    public TelaProduto() {
        fachada = new FachadaProduto();
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("Gerenciar Produtos");
        // Janela alargada para 700px para caber a foto
        frame.setBounds(100, 100, 700, 420);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent arg0) {
                listagem();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 20, 460, 190);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        // Evento seguro para preencher os campos e carregar a foto ao clicar num item
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    Object idObj = table.getValueAt(row, 0);
                    Object valNome = table.getValueAt(row, 1);
                    Object valPreco = table.getValueAt(row, 2);
                    
                    textFieldNome.setText(valNome != null ? valNome.toString() : "");
                    textFieldPreco.setText(valPreco != null ? valPreco.toString() : "");
                    
                    // Lógica para buscar e exibir a foto
                    if (idObj != null) {
                        try {
                            int id = Integer.parseInt(idObj.toString());
                            Produto p = fachada.localizarProduto(id);
                            
                            fotoAtualEmMemoria = null; // Limpa a memória temporária
                            if (p != null && p.getFoto() != null) {
                                ImageIcon icon = new ImageIcon(p.getFoto());
                                Image img = icon.getImage().getScaledInstance(labelFoto.getWidth(), labelFoto.getHeight(), Image.SCALE_SMOOTH);
                                labelFoto.setIcon(new ImageIcon(img));
                                labelFoto.setText("");
                            } else {
                                labelFoto.setIcon(null);
                                labelFoto.setText("Sem Imagem");
                            }
                        } catch (Exception ex) {
                            labelFoto.setIcon(null);
                            labelFoto.setText("Erro ao carregar");
                        }
                    }
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
                    
                    Object idObj = table.getValueAt(row, 0);
                    if (idObj == null) return;
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
                    
                    Object idObj = table.getValueAt(row, 0);
                    if (idObj == null) return;
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

        // --- SECÇÃO DA IMAGEM ---
        labelFoto = new JLabel("Sem Imagem");
        labelFoto.setHorizontalAlignment(SwingConstants.CENTER);
        labelFoto.setBorder(new LineBorder(Color.GRAY));
        labelFoto.setBounds(500, 20, 150, 150);
        frame.getContentPane().add(labelFoto);

        btnEscolherFoto = new JButton("Escolher Foto...");
        btnEscolherFoto.setBounds(500, 180, 150, 25);
        btnEscolherFoto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Selecione a imagem do produto");
                fc.setFileFilter(new FileNameExtensionFilter("Imagens (*.png, *.jpg, *.jpeg)", "png", "jpg", "jpeg"));
                
                int res = fc.showOpenDialog(frame);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        fotoAtualEmMemoria = Files.readAllBytes(file.toPath());
                        ImageIcon icon = new ImageIcon(fotoAtualEmMemoria);
                        Image img = icon.getImage().getScaledInstance(labelFoto.getWidth(), labelFoto.getHeight(), Image.SCALE_SMOOTH);
                        labelFoto.setIcon(new ImageIcon(img));
                        labelFoto.setText("");
                        labelStatus.setText("Foto escolhida. Clique em 'Salvar Foto' para guardar no banco.");
                    } catch (Exception ex) {
                        labelStatus.setText("Erro ao ler o ficheiro de imagem.");
                    }
                }
            }
        });
        frame.getContentPane().add(btnEscolherFoto);

        btnSalvarFoto = new JButton("Salvar Foto BD");
        btnSalvarFoto.setBounds(500, 215, 150, 25);
        btnSalvarFoto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = table.getSelectedRow();
                    if (row < 0) {
                        labelStatus.setText("Selecione um produto na tabela primeiro!");
                        return;
                    }
                    if (fotoAtualEmMemoria == null) {
                        labelStatus.setText("Escolha uma foto antes de guardar!");
                        return;
                    }
                    
                    int id = Integer.parseInt(table.getValueAt(row, 0).toString());
                    fachada.atualizarFoto(id, fotoAtualEmMemoria);
                    labelStatus.setText("Foto guardada no Banco de Dados com sucesso!");
                } catch (Exception ex) {
                    labelStatus.setText("Erro ao guardar foto: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnSalvarFoto);

        labelStatus = new JLabel("");
        labelStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
        labelStatus.setForeground(Color.RED);
        labelStatus.setBounds(20, 310, 630, 14);
        frame.getContentPane().add(labelStatus);

        frame.setVisible(true);
    }

    private void limparCampos() {
        textFieldNome.setText("");
        textFieldPreco.setText("");
        labelFoto.setIcon(null);
        labelFoto.setText("Sem Imagem");
        fotoAtualEmMemoria = null;
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