

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
	private JLabel labelMensagem;
	private JLabel labelNome;

	public TelaEntregador() {
		initialize();
	}

	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setResizable(false);
		frame.setTitle("Gerenciar Entregadores");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 30, 540, 180);
		frame.getContentPane().add(scrollPane);

		table = new JTable() {
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() >= 0) {
					String nome = (String) table.getValueAt(table.getSelectedRow(), 0);
					textFieldNome.setText(nome);
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

		labelNome = new JLabel("Nome:");
		labelNome.setHorizontalAlignment(SwingConstants.RIGHT);
		labelNome.setBounds(20, 240, 60, 14);
		frame.getContentPane().add(labelNome);

		textFieldNome = new JTextField();
		textFieldNome.setBounds(90, 237, 300, 20);
		frame.getContentPane().add(textFieldNome);
		textFieldNome.setColumns(10);

		buttonCriar = new JButton("Criar");
		buttonCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				criar();
			}
		});
		buttonCriar.setBounds(90, 280, 90, 23);
		frame.getContentPane().add(buttonCriar);

		buttonApagar = new JButton("Apagar");
		buttonApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				apagar();
			}
		});
		buttonApagar.setBounds(190, 280, 90, 23);
		frame.getContentPane().add(buttonApagar);

		buttonLimpar = new JButton("Limpar");
		buttonLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldNome.setText("");
			}
		});
		buttonLimpar.setBounds(300, 280, 90, 23);
		frame.getContentPane().add(buttonLimpar);

		labelMensagem = new JLabel("");
		labelMensagem.setForeground(Color.BLUE);
		labelMensagem.setBounds(21, 330, 540, 14);
		frame.getContentPane().add(labelMensagem);

		frame.setVisible(true);
	}

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

	public void criar() {
		try {
			String nome = textFieldNome.getText();
			if (nome.isEmpty()) {
				labelMensagem.setText("Nome vazio.");
				return;
			}
			Fachada.CriarEntregador(nome);
			labelMensagem.setText("Entregador criado: " + nome);
			listagem();
		} catch (Exception ex) {
			labelMensagem.setText(ex.getMessage());
		}
	}

	public void apagar() {
		try {
			String nome = textFieldNome.getText();
			if (nome.isEmpty()) {
				labelMensagem.setText("Selecione um entregador para apagar.");
				return;
			}
			Fachada.excluirEntregador(nome);
			labelMensagem.setText("Entregador apagado: " + nome);
			textFieldNome.setText("");
			listagem();
		} catch (Exception ex) {
			labelMensagem.setText(ex.getMessage());
		}
	}
}
