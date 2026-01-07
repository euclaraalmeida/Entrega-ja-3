
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Entrega;
import requisito.Fachada;

public class TelaEntrega {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField textData;
	private JTextField textEntregador;
	private JLabel labelMsg;

	public TelaEntrega() {
		initialize();
	}

	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setTitle("Gerenciar Entregas");
		frame.setBounds(100, 100, 650, 450);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 20, 600, 200);
		frame.getContentPane().add(scrollPane);

		table = new JTable() {
			public boolean isCellEditable(int rowIndex, int vColIndex) { return false; }
		};
		table.setGridColor(Color.BLACK);
		table.setShowGrid(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() >= 0) {
					// Pega o nome do entregador da coluna 2
					Object ent = table.getValueAt(table.getSelectedRow(), 2);
					textEntregador.setText(ent != null ? ent.toString() : "");
				}
			}
		});

		JLabel lblData = new JLabel("Data (dd/mm/aaaa):");
		lblData.setBounds(20, 240, 120, 14);
		frame.getContentPane().add(lblData);

		textData = new JTextField();
		textData.setBounds(140, 237, 100, 20);
		frame.getContentPane().add(textData);

		JLabel lblEntregador = new JLabel("Nome Entregador:");
		lblEntregador.setBounds(20, 270, 120, 14);
		frame.getContentPane().add(lblEntregador);

		textEntregador = new JTextField();
		textEntregador.setBounds(140, 267, 150, 20);
		frame.getContentPane().add(textEntregador);

		JButton btnCriar = new JButton("Criar Entrega");
		btnCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Fachada.CriarEntrega(textData.getText(), textEntregador.getText());
					labelMsg.setText("Entrega criada!");
					listagem();
				} catch (Exception ex) {
					labelMsg.setText(ex.getMessage());
				}
			}
		});
		btnCriar.setBounds(20, 310, 120, 23);
		frame.getContentPane().add(btnCriar);
		
		JButton btnRemoverEnt = new JButton("Remover do Entregador");
		btnRemoverEnt.setToolTipText("Remove a entrega selecionada do entregador informado");
		btnRemoverEnt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					try {
						int idEntrega = (int) table.getValueAt(table.getSelectedRow(), 0);
						String nomeEnt = textEntregador.getText();
						Fachada.removerEntregaDoEntregador(nomeEnt, idEntrega);
						labelMsg.setText("Entrega removida do entregador.");
						listagem();
					} catch (Exception ex) {
						labelMsg.setText(ex.getMessage());
					}
				} else {
					labelMsg.setText("Selecione uma entrega na tabela.");
				}
			}
		});
		btnRemoverEnt.setBounds(150, 310, 180, 23);
		frame.getContentPane().add(btnRemoverEnt);

		labelMsg = new JLabel("");
		labelMsg.setForeground(Color.BLUE);
		labelMsg.setBounds(20, 360, 600, 14);
		frame.getContentPane().add(labelMsg);

		frame.setVisible(true);
	}

	public void listagem() {
		try {
			List<Entrega> lista = Fachada.listarEntregas();
			DefaultTableModel model = new DefaultTableModel();
			model.addColumn("ID");
			model.addColumn("Data");
			model.addColumn("Entregador");
			model.addColumn("Pedidos");

			for (Entrega e : lista) {
				String nomeEnt = (e.getEntregador() != null) ? e.getEntregador().getNome() : "Sem Entregador";
				model.addRow(new Object[] { e.getId(), e.getData(), nomeEnt, e.getListaPedidos().size() });
			}
			table.setModel(model);
		} catch (Exception ex) {
			labelMsg.setText(ex.getMessage());
		}
	}
}
