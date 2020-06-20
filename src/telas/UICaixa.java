package telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;

import static aplicacao.Helpers.ClearTable;

import java.awt.Cursor;
import java.awt.SystemColor;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import aplicacao.Contexto;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

import modelo.Venda;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class UICaixa {

	public JFrame frmCaixa;
	private JTable tbVendas;
	private UIClientes clientes = new UIClientes();
	private UIVenda venda = new UIVenda();
	private UICarros carros = new UICarros();
	private jDialogExemplo JFrameTeste = new jDialogExemplo();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)  {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UICaixa window = new UICaixa();
					window.frmCaixa.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UICaixa() throws Exception {
		initialize();
		loadTable();
	}
	
	private void loadTable() {
		ClearTable(tbVendas);
		
		DefaultTableModel model = (DefaultTableModel)tbVendas.getModel();
		
		model.addColumn("Código");
		model.addColumn("Cliente");
		model.addColumn("Itens");
		model.addColumn("Total");
		
		for (Venda venda : Contexto.getVendas()) {
			model.addRow(new Object[] { 
				venda.getId(), 
				venda.getCliente().getNome(), 
				venda.getCarros().size(), 
				venda.getTotal()
			});			
		}		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		frmCaixa = new JFrame();
		frmCaixa.setTitle("Caixa");
		frmCaixa.setSize(new Dimension(802, 440));
		frmCaixa.setBounds(100, 100, 773, 440);
		frmCaixa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnNovaVenda = new JButton("Nova Venda");
		btnNovaVenda.setBounds(450, 30, 101, 34);
		btnNovaVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//venda.frmNovaVenda.setVisible(true);
				JFrameTeste.setVisible(true);
				venda.frmNovaVenda.addWindowFocusListener(new WindowFocusListener() {
					public void windowGainedFocus(WindowEvent arg0) {
						//loadForm();
					}

					public void windowLostFocus(WindowEvent arg0) {
						loadTable();
					}
				});
			}
		});		
		
		JButton btnCarros = new JButton("Carros");
		btnCarros.setBounds(557, 30, 91, 34);
		btnCarros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carros.frmCadastroDeCarros.setVisible(true);
			}
		});
		
		JButton btnClientes = new JButton("Clientes");
		btnClientes.setBounds(654, 30, 91, 34);
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientes.frmCadastroDeClientes.setVisible(true);				
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("Cliente");
		lblNewLabel_1.setBounds(20, 12, 39, 16);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(12, 35, 124, 25);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 76, 733, 2);
		
		JLabel lblCarro = new JLabel("Carro");
		lblCarro.setBounds(156, 12, 33, 16);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(148, 35, 124, 25);
		
		JButton btnNewButton = new JButton("Pesquisar");
		btnNewButton.setBounds(278, 34, 92, 26);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 111, 733, 278);
		
		JLabel lblNewLabel = new JLabel("Exportar?");
		lblNewLabel.setBounds(382, 39, 56, 16);
		lblNewLabel.setForeground(SystemColor.textHighlight);
		lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		tbVendas = new JTable();
		scrollPane.setViewportView(tbVendas);
		frmCaixa.getContentPane().setLayout(null);
		frmCaixa.getContentPane().add(comboBox);
		frmCaixa.getContentPane().add(lblNewLabel_1);
		frmCaixa.getContentPane().add(comboBox_1);
		frmCaixa.getContentPane().add(btnNewButton);
		frmCaixa.getContentPane().add(lblNewLabel);
		frmCaixa.getContentPane().add(btnNovaVenda);
		frmCaixa.getContentPane().add(btnCarros);
		frmCaixa.getContentPane().add(btnClientes);
		frmCaixa.getContentPane().add(lblCarro);
		frmCaixa.getContentPane().add(separator);
		frmCaixa.getContentPane().add(scrollPane);
	}
}
