package telas;

import java.awt.EventQueue;

import javax.swing.JFrame;

import aplicacao.Contexto;
import modelo.Carro;
import modelo.Cliente;
import modelo.Venda;
import servicos.ServicoCarro;
import servicos.ServicoCliente;
import servicos.ServicoVenda;

import static aplicacao.Helpers.*;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;

public class UIVenda {

	final String ERR_ADD_VALIDACAO = "Selecione um carro!";
	final String ERR_VALIDACAO = "Todos os campos são obrigatórios.";

	private ServicoCliente servicoCliente = new ServicoCliente();
	private ServicoCarro servicoCarro = new ServicoCarro();
	private ServicoVenda servicoVenda = new ServicoVenda();
	
	private Venda Current = new Venda();

	public JFrame frmNovaVenda;

	JComboBox<String> cbCliente;
	JComboBox<String> cbCarro;

	static Object Teste;
	private JTable tbCarros;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIVenda window = new UIVenda();
					window.frmNovaVenda.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UIVenda() {
		initialize();
		loadForm();
	}

	private boolean AddCarroValido() throws Exception {
		Integer cod = GetSelectedId(cbCarro);

		return cod != null && cod > 0;
	}
	
	private boolean ClienteValido() throws Exception {
		Integer cod = GetSelectedId(cbCliente);

		if(!(cod != null && cod > 0)) return false;
		
		if(Current.getCarros().size() <= 0) return false; 
		
		return true;
	}

	public void loadForm() {
		cbCliente.removeAllItems();
		cbCarro.removeAllItems();

		cbCliente.addItem("Selecione");
		cbCarro.addItem("Selecione");

		for (Cliente item : Contexto.getClientes()) {
			cbCliente.addItem(item.getId() + " - " + item.getNome());
		}

		for (Carro item : Contexto.getCarros()) {
			cbCarro.addItem(item.getId() + " - " + item.getModelo());
		}
	}

	private void addCarro() {
		try {
			if (!AddCarroValido()) {
				ExibeMensagem(ERR_ADD_VALIDACAO);
				return;
			}

			Integer cod = GetSelectedId(cbCarro);

			Current.getCarros().add((Carro) servicoCarro.GetById(cod));

			loadTableCarro();
		} catch (Exception e) {
			ExibeMensagem(e.getMessage());
			e.printStackTrace();
		}
	}

	private void loadTableCarro() {
		ClearTable(tbCarros);

		DefaultTableModel model = (DefaultTableModel) tbCarros.getModel();
		model.addColumn("Código");
		model.addColumn("Modelo");
		model.addColumn("Marca");
		model.addColumn("Valor");

		for (Carro carro : Current.getCarros()) {
			model.addRow(new Object[] { carro.getId(), carro.getModelo(), carro.getMarca(), carro.getValor() });
		}
	}

	private void fechaVenda() {
		try {
			Integer cod = GetSelectedId(cbCliente);
			
			Current.setCliente((Cliente) servicoCliente.GetById(cod));
			
			if (!ClienteValido()) {
				ExibeMensagem(ERR_VALIDACAO);
				return;
			}
			
			servicoVenda.Inserir(Current);
			
			frmNovaVenda.setVisible(false);
		} catch (Exception e) {
			ExibeMensagem(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNovaVenda = new JFrame();
		frmNovaVenda.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				String t = "";
			}
		});

		frmNovaVenda.addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent arg0) {
				//loadForm();
			}

			public void windowLostFocus(WindowEvent arg0) {
				String t = "";
			}
		});

		frmNovaVenda.setTitle("Nova Venda");
		frmNovaVenda.setBounds(100, 100, 568, 362);
		frmNovaVenda.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frmNovaVenda.getContentPane().setLayout(null);

		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCliente.setBounds(20, 20, 37, 14);
		frmNovaVenda.getContentPane().add(lblCliente);

		cbCliente = new JComboBox<String>();
		cbCliente.setBounds(67, 17, 149, 20);
		frmNovaVenda.getContentPane().add(cbCliente);

		JLabel lblNewLabel = new JLabel("Carro");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(20, 48, 34, 14);
		frmNovaVenda.getContentPane().add(lblNewLabel);

		cbCarro = new JComboBox<String>();
		cbCarro.setBounds(67, 45, 149, 20);
		frmNovaVenda.getContentPane().add(cbCarro);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addCarro();
			}
		});
		btnAdicionar.setBounds(220, 44, 91, 23);
		frmNovaVenda.getContentPane().add(btnAdicionar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 98, 532, 194);
		frmNovaVenda.getContentPane().add(scrollPane);

		tbCarros = new JTable();
		scrollPane.setViewportView(tbCarros);

		JLabel lblNewLabel_1 = new JLabel("Carros Adicionados: ");
		lblNewLabel_1.setBounds(10, 84, 111, 14);
		frmNovaVenda.getContentPane().add(lblNewLabel_1);

		JButton btnFecharVenda = new JButton("Finalizar Venda");
		btnFecharVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fechaVenda();
			}
		});
		btnFecharVenda.setBounds(396, 24, 146, 46);
		frmNovaVenda.getContentPane().add(btnFecharVenda);
	}
}
