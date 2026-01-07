package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;
import requisito.Fachada;

public class TelaConsulta {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JLabel label;
	private JComboBox<String> comboBox;

	public TelaConsulta() {
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setResizable(false);
		frame.setTitle("Consultas do Sistema");
		frame.setBounds(100, 100, 729, 385);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 50, 674, 200);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setGridColor(Color.BLACK);
		table.setShowGrid(true);
		scrollPane.setViewportView(table);

		label = new JLabel("");
		label.setForeground(Color.BLUE);
		label.setBounds(21, 300, 688, 14);
		frame.getContentPane().add(label);

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<>(new String[] { 
			"Entregas por Data", 
			"Pedidos por Entregador", 
			"Entregas com mais de N pedidos" 
		}));
		comboBox.setBounds(21, 10, 400, 22);
		frame.getContentPane().add(comboBox);

		button = new JButton("Consultar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = comboBox.getSelectedIndex();
				if (index < 0) {
					label.setText("Selecione uma consulta");
				} else {
					try {
						switch (index) {
						case 0: // Entregas por Data
							String data = JOptionPane.showInputDialog("Digite a data (dd/mm/aaaa):");
							if(data != null) {
								List<Entrega> result1 = Fachada.consultarEntregasPorData(data);
								listarEntregas(result1);
							}
							break;
						case 1: // Pedidos por Entregador
							String nome = JOptionPane.showInputDialog("Digite o nome do Entregador:");
							if(nome != null) {
								List<Pedido> result2 = Fachada.consultarPedidosPorEntregador(nome);
								listarPedidos(result2);
							}
							break;
						case 2: // Entregadores Produtivos
							String nStr = JOptionPane.showInputDialog("Digite o número N de entregas:");
							if(nStr != null) {
								int n = Integer.parseInt(nStr);
								List<Entrega> result3 = Fachada.consultarEntregadoresProdutivos(n);
								listarEntregas(result3);
							}
							break;
						}
					} catch (Exception ex) {
						label.setText(ex.getMessage());
					}
				}
			}
		});
		button.setBounds(440, 10, 120, 23);
		frame.getContentPane().add(button);
	}

	// Métodos auxiliares para preencher a tabela conforme o tipo de retorno

	private void listarEntregas(List<Entrega> lista) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Data");
		model.addColumn("Entregador");
		if (lista != null) {
			for (Entrega e : lista) {
				String ent = (e.getEntregador() != null) ? e.getEntregador().getNome() : "";
				model.addRow(new Object[] { e.getId(), e.getData(), ent });
			}
			label.setText(lista.size() + " entregas encontradas.");
		} else {
			label.setText("Nenhuma entrega encontrada.");
		}
		table.setModel(model);
	}

	private void listarPedidos(List<Pedido> lista) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Descrição");
		model.addColumn("Valor");
		if (lista != null) {
			for (Pedido p : lista) {
				model.addRow(new Object[] { p.getId(), p.getDescricao(), p.getValor() });
			}
			label.setText(lista.size() + " pedidos encontrados.");
		} else {
			label.setText("Nenhum pedido encontrado.");
		}
		table.setModel(model);
	}

	private void listarEntregadores(List<Entregador> lista) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Nome");
		model.addColumn("Qtd Entregas");
		if (lista != null) {
			for (Entregador e : lista) {
				model.addRow(new Object[] { e.getNome(), e.getListaEntregas().size() });
			}
			label.setText(lista.size() + " entregadores encontrados.");
		} else {
			label.setText("Nenhum entregador encontrado.");
		}
		table.setModel(model);
	}
}




