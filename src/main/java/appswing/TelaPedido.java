
package appswing;

import java.awt.Color;
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
import javax.swing.table.DefaultTableModel;

import modelo.Pedido;
import requisito.Fachada;

public class TelaPedido {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField textDesc;
	private JTextField textValor;
	private JTextField textLocal;
	private JTextField textIdEntrega;
	private JLabel labelMsg;

	public TelaPedido() {
		initialize();
	}

	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setTitle("Gerenciar Pedidos");
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 20, 640, 200);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);

		JLabel lblDesc = new JLabel("Descrição:");
		lblDesc.setBounds(20, 240, 80, 14);
		frame.getContentPane().add(lblDesc);
		textDesc = new JTextField();
		textDesc.setBounds(90, 237, 200, 20);
		frame.getContentPane().add(textDesc);

		JLabel lblValor = new JLabel("Valor:");
		lblValor.setBounds(300, 240, 40, 14);
		frame.getContentPane().add(lblValor);
		textValor = new JTextField();
		textValor.setBounds(340, 237, 80, 20);
		frame.getContentPane().add(textValor);

		JLabel lblLocal = new JLabel("Local:");
		lblLocal.setBounds(20, 270, 80, 14);
		frame.getContentPane().add(lblLocal);
		textLocal = new JTextField();
		textLocal.setBounds(90, 267, 150, 20);
		frame.getContentPane().add(textLocal);

		JButton btnCriar = new JButton("Criar Pedido");
		btnCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double val = Double.parseDouble(textValor.getText());
					Fachada.CriarPedido(val, textDesc.getText(), textLocal.getText());
					labelMsg.setText("Pedido criado!");
					listagem();
				} catch (Exception ex) {
					labelMsg.setText("Erro: " + ex.getMessage());
				}
			}
		});
		btnCriar.setBounds(20, 310, 120, 23);
		frame.getContentPane().add(btnCriar);

		JButton btnApagar = new JButton("Apagar");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0) {
						int id = (int) table.getValueAt(table.getSelectedRow(), 0);
						Fachada.apagarPedido(id);
						labelMsg.setText("Pedido apagado!");
						listagem();
					} else {
						labelMsg.setText("Selecione um pedido.");
					}
				} catch (Exception ex) {
					labelMsg.setText(ex.getMessage());
				}
			}
		});
		btnApagar.setBounds(150, 310, 100, 23);
		frame.getContentPane().add(btnApagar);
		
		// --- Associar a Entrega ---
		JLabel lblIdEntrega = new JLabel("ID Entrega:");
		lblIdEntrega.setBounds(300, 314, 70, 14);
		frame.getContentPane().add(lblIdEntrega);
		textIdEntrega = new JTextField();
		textIdEntrega.setBounds(370, 311, 50, 20);
		frame.getContentPane().add(textIdEntrega);
		
		JButton btnAddEntrega = new JButton("Add na Entrega");
		btnAddEntrega.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0) {
						int idPedido = (int) table.getValueAt(table.getSelectedRow(), 0);
						int idEntrega = Integer.parseInt(textIdEntrega.getText());
						Fachada.AddPedidoNaEntrega(idPedido, idEntrega);
						labelMsg.setText("Pedido adicionado à entrega!");
						listagem();
					}
				} catch (Exception ex) {
					labelMsg.setText(ex.getMessage());
				}
			}
		});
		btnAddEntrega.setBounds(430, 310, 130, 23);
		frame.getContentPane().add(btnAddEntrega);

		labelMsg = new JLabel("");
		labelMsg.setForeground(Color.BLUE);
		labelMsg.setBounds(20, 360, 600, 14);
		frame.getContentPane().add(labelMsg);

		frame.setVisible(true);
	}

	public void listagem() {
		try {
			List<Pedido> lista = Fachada.listarPedidos();
			DefaultTableModel model = new DefaultTableModel();
			model.addColumn("ID");
			model.addColumn("Descrição");
			model.addColumn("Valor");
			model.addColumn("Local");
			model.addColumn("ID Entrega");

			for (Pedido p : lista) {
				int idEnt = (p.getEntrega() != null) ? p.getEntrega().getId() : 0;
				model.addRow(new Object[] { p.getId(), p.getDescricao(), p.getValor(), "Local", idEnt });
			}
			table.setModel(model);
		} catch (Exception ex) {
			labelMsg.setText(ex.getMessage());
		}
	}
}
