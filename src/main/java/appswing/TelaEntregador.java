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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
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

import modelo.Entregador;
import requisito.Fachada;

public class TelaEntregador {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField textFieldNome;
	private JButton buttonCriar;
	private JButton buttonApagar;
	private JButton buttonLimpar;
	private JButton buttonAtualizar;
	private JLabel labelMensagem;
	private JLabel labelNome;
	
	// Componentes da Foto
	private JLabel labelFoto;
	private JButton buttonBuscarFoto;
	private JButton buttonLimparFoto;
	private BufferedImage buffer; // Armazena a imagem na memória

	public TelaEntregador() {
		initialize();
	}

	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setResizable(false);
		frame.setTitle("Gerenciar Entregadores");
		frame.setBounds(100, 100, 750, 450);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 30, 500, 180);
		frame.getContentPane().add(scrollPane);

		table = new JTable() {
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		
		// Quando clicar na linha, carrega o nome E a foto
		table.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (table.getSelectedRow() >= 0) {
		            String nome = (String) table.getValueAt(table.getSelectedRow(), 0);
		            textFieldNome.setText(nome);
		            
		            // Chama o método que busca a foto no banco
		            carregarFotoDoEntregador(nome);
		        }
		    }
		});
		
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// --- CAMPOS ---
		labelNome = new JLabel("Nome:");
		labelNome.setHorizontalAlignment(SwingConstants.RIGHT);
		labelNome.setBounds(20, 240, 60, 14);
		frame.getContentPane().add(labelNome);

		textFieldNome = new JTextField();
		textFieldNome.setBounds(90, 237, 300, 20);
		frame.getContentPane().add(textFieldNome);
		textFieldNome.setColumns(10);

		// --- ÁREA DA FOTO ---
		labelFoto = new JLabel("sem foto");
		labelFoto.setHorizontalAlignment(SwingConstants.CENTER);
		labelFoto.setBorder(new LineBorder(Color.GRAY));
		labelFoto.setBounds(550, 30, 150, 150);
		frame.getContentPane().add(labelFoto);

		// --- BOTÕES FOTO ---
		buttonBuscarFoto = new JButton("Buscar Foto");
		buttonBuscarFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarArquivoFoto();
			}
		});
		buttonBuscarFoto.setBounds(550, 190, 150, 23);
		frame.getContentPane().add(buttonBuscarFoto);

		buttonLimparFoto = new JButton("Limpar Foto");
		buttonLimparFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buffer = null;
				labelFoto.setIcon(null);
				labelFoto.setText("sem foto");
			}
		});
		buttonLimparFoto.setBounds(550, 220, 150, 23);
		frame.getContentPane().add(buttonLimparFoto);

		// --- BOTÕES AÇÃO ---
		buttonCriar = new JButton("Criar");
		buttonCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				criar();
			}
		});
		buttonCriar.setBounds(90, 280, 90, 23);
		frame.getContentPane().add(buttonCriar);

		buttonAtualizar = new JButton("Atualizar");
		buttonAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizar();
			}
		});
		buttonAtualizar.setBounds(190, 280, 90, 23);
		frame.getContentPane().add(buttonAtualizar);

		buttonApagar = new JButton("Apagar");
		buttonApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				apagar();
			}
		});
		buttonApagar.setBounds(290, 280, 90, 23);
		frame.getContentPane().add(buttonApagar);

		buttonLimpar = new JButton("Limpar");
		buttonLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldNome.setText("");
				buffer = null;
				labelFoto.setIcon(null);
				labelFoto.setText("sem foto");
				table.clearSelection();
			}
		});
		buttonLimpar.setBounds(390, 280, 90, 23);
		frame.getContentPane().add(buttonLimpar);

		labelMensagem = new JLabel("");
		labelMensagem.setForeground(Color.BLUE);
		labelMensagem.setBounds(21, 330, 600, 14);
		frame.getContentPane().add(labelMensagem);

		frame.setVisible(true);
	}

	// ---------------------------------------------------------
	// MÉTODOS LÓGICOS
	// ---------------------------------------------------------

	public void listagem() {
		try {
			List<Entregador> lista = Fachada.listarEntregador();
			DefaultTableModel model = new DefaultTableModel();
			model.addColumn("Nome");
			model.addColumn("Qtd Entregas");
			
			for (Entregador e : lista) {
				model.addRow(new Object[] { e.getNome(), e.getListaEntregas().size() });
			}
			table.setModel(model);
		} catch (Exception erro) {
			labelMensagem.setText(erro.getMessage());
		}
	}

	// --- MÉTODO BLINDADO PARA CARREGAR A FOTO ---
	public void carregarFotoDoEntregador(String nome) {
		try {
			List<Entregador> lista = Fachada.listarEntregador();
			Entregador ent = null;
			for(Entregador e : lista) {
				if(e.getNome().equals(nome)) {
					ent = e;
					break;
				}
			}

			// Verifica se tem foto E se o array de bytes não está vazio
			if (ent != null && ent.getFoto() != null && ent.getFoto().length > 0) {
				InputStream in = new ByteArrayInputStream(ent.getFoto());
				buffer = ImageIO.read(in); 
				
				// Se a conversão funcionou (a imagem é válida)
				if (buffer != null) {
					ImageIcon icon = new ImageIcon(buffer.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
					labelFoto.setIcon(icon);
					labelFoto.setText("");
				} else {
					// Bytes existem mas não formam imagem válida
					labelFoto.setIcon(null);
					labelFoto.setText("Erro Imagem");
				}
			} else {
				buffer = null;
				labelFoto.setIcon(null);
				labelFoto.setText("sem foto");
			}
		} catch (Exception e) {
			labelMensagem.setText("Erro ao carregar foto: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void criar() {
		try {
			String nome = textFieldNome.getText();
			if (nome.isEmpty()) {
				labelMensagem.setText("Nome vazio.");
				return;
			}
			Fachada.CriarEntregador(nome);
			
			// Se selecionou foto antes, salva agora
			if (buffer != null) {
				salvarFotoNoBanco(nome);
			}
			
			labelMensagem.setText("Entregador criado: " + nome);
			listagem();
			buttonLimpar.doClick();
		} catch (Exception ex) {
			labelMensagem.setText(ex.getMessage());
		}
	}

	public void atualizar() {
		try {
			if (table.getSelectedRow() < 0) {
				labelMensagem.setText("Selecione um entregador na tabela.");
				return;
			}
			String nomeOriginal = (String) table.getValueAt(table.getSelectedRow(), 0);
			
			// Apenas salva a foto no entregador existente
			salvarFotoNoBanco(nomeOriginal);
			
			labelMensagem.setText("Entregador atualizado!");
			listagem();
		} catch (Exception ex) {
			labelMensagem.setText("Erro ao atualizar: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	// Método que converte Buffer -> Bytes -> Fachada
	private void salvarFotoNoBanco(String nomeEntregador) throws Exception {
		List<Entregador> lista = Fachada.listarEntregador();
		Entregador ent = null;
		for(Entregador e : lista) {
			if(e.getNome().equals(nomeEntregador)) {
				ent = e;
				break;
			}
		}
		
		if (ent != null) {
			if (buffer != null) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(buffer, "jpg", baos);
				byte[] bytes = baos.toByteArray();
				
				ent.setFoto(bytes);
			} else {
				ent.setFoto(null);
			}
			Fachada.alterarEntregador(ent);
		}
	}
	
	public void apagar() {
		try {
			String nome = textFieldNome.getText();
			if (nome.isEmpty()) return;
			
			Object[] options = { "Confirmar", "Cancelar" };
			int escolha = JOptionPane.showOptionDialog(null,
					"Apagar entregador " + nome + "?", "Alerta",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
			
			if (escolha == 0) {
				Fachada.excluirEntregador(nome);
				labelMensagem.setText("Apagado: " + nome);
				listagem();
				buttonLimpar.doClick();
			}
		} catch (Exception ex) {
			labelMensagem.setText(ex.getMessage());
		}
	}
	
	public void selecionarArquivoFoto() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagens (jpg, png)", "jpg", "png");
		chooser.setFileFilter(filter);
		
		int result = chooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				File file = chooser.getSelectedFile();
				buffer = ImageIO.read(file); 
				
				if(buffer != null) {
					ImageIcon icon = new ImageIcon(buffer.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
					labelFoto.setIcon(icon);
					labelFoto.setText("");
					labelMensagem.setText("Imagem carregada. Clique em Atualizar para salvar.");
				} else {
					labelMensagem.setText("O arquivo selecionado não é uma imagem válida.");
				}
			} catch (IOException ex) {
				labelMensagem.setText("Erro ao ler imagem: " + ex.getMessage());
			}
		}
	}
}