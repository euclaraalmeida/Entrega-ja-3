

package appswing;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;

public class TelaPrincipal {
	private JFrame frame;
	private JMenu mnEntregador;
	private JMenu mnEntrega;
	private JMenu mnPedido;
	private JMenu mnConsulta;
	private JLabel label;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaPrincipal() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Sistema de Entregas");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		label = new JLabel("");
		label.setFont(new Font("Tahoma", Font.PLAIN, 26));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setText("Sistema Iniciado");
		label.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		
		// Se tiver imagem, descomente as linhas abaixo e crie a pasta imagens
		// ImageIcon imagem = new ImageIcon(getClass().getResource("/imagens/entrega.jpg"));
		// imagem = new ImageIcon(imagem.getImage().getScaledInstance(label.getWidth(),label.getHeight(), Image.SCALE_DEFAULT));
		// label.setIcon(imagem);
		
		frame.getContentPane().add(label);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnEntregador = new JMenu("Entregador");
		mnEntregador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new TelaEntregador();
			}
		});
		menuBar.add(mnEntregador);
		
		mnEntrega = new JMenu("Entrega");
		mnEntrega.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new TelaEntrega();
			}
		});
		menuBar.add(mnEntrega);

		mnPedido = new JMenu("Pedido");
		mnPedido.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new TelaPedido();
			}
		});
		menuBar.add(mnPedido);
		
		mnConsulta = new JMenu("Consultas");
		mnConsulta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new TelaConsulta();
			}
		});
		menuBar.add(mnConsulta);
	}
}
