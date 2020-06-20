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
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

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

	JButton btnExcluir;
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
		load();
	}
	
	private void clear() {
		Current = new Venda();
				
		cbCarro.setSelectedIndex(0);
		cbCliente.setSelectedIndex(0);
		
		loadTableCarro();
	}
	
	private void load() {
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
		loadCarros();
		loadClientes();
	}
	
	public void loadCarros() {
		cbCarro.removeAllItems();

		cbCarro.addItem("Selecione");

		for (Carro item : Contexto.getCarros()) {
			cbCarro.addItem(item.getId() + " - " + item.getModelo());
		}
	}
	
	public void loadClientes() {
		cbCliente.removeAllItems();
		
		cbCliente.addItem("Selecione");
		
		for (Cliente item : Contexto.getClientes()) {
			cbCliente.addItem(item.getId() + " - " + item.getNome());
		}
	}

	private void addCarro() {
		try {
			if (!AddCarroValido()) {
				ExibeMensagem(ERR_ADD_VALIDACAO);
				return;
			}

			Integer cod = GetSelectedId(cbCarro);

			Current.getCarros().add(((Carro) servicoCarro.GetById(cod)).clone());

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
		
		btnExcluir.setEnabled(false);
	}

	private void fechaVenda() {
		try {
			
			if (!ClienteValido()) {
				ExibeMensagem(ERR_VALIDACAO);
				return;
			}
			
			Integer cod = GetSelectedId(cbCliente);
			
			Current.setCliente(((Cliente) servicoCliente.GetById(cod)).clone());
			
			servicoVenda.Inserir(Current);
			
			clear();
			frmNovaVenda.setVisible(false);
		} catch (Exception e) {
			ExibeMensagem(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void LinhaSelecionada() {
		btnExcluir.setEnabled(true);
	}
	
	private void ExcluirItem() {
		try {			
			if(tbCarros.getSelectedRow() >= 0) {
				Current.getCarros().remove(tbCarros.getSelectedRow());			
				tbCarros.clearSelection();
				loadTableCarro();
			} else 
				btnExcluir.setEnabled(false);
		} catch (Exception e) {
			e.printStackTrace();
			ExibeMensagem(e.getMessage());
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNovaVenda = new JFrame();
		frmNovaVenda.setTitle("Nova Venda");
		frmNovaVenda.setBounds(100, 100, 568, 374);
		frmNovaVenda.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmNovaVenda.getContentPane().setLayout(null);

		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCliente.setBounds(0, 20, 57, 14);
		frmNovaVenda.getContentPane().add(lblCliente);

		cbCliente = new JComboBox<String>();
		cbCliente.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				loadClientes();
			}
		});
		cbCliente.setBounds(67, 17, 149, 20);
		frmNovaVenda.getContentPane().add(cbCliente);

		JLabel lblNewLabel = new JLabel("Carro");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(0, 48, 57, 14);
		frmNovaVenda.getContentPane().add(lblNewLabel);

		cbCarro = new JComboBox<String>();
		cbCarro.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				loadCarros();
			}
		});
		cbCarro.setBounds(67, 45, 149, 20);
		frmNovaVenda.getContentPane().add(cbCarro);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addCarro();
			}
		});
		btnAdicionar.setBounds(226, 44, 91, 23);
		frmNovaVenda.getContentPane().add(btnAdicionar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 98, 532, 194);
		frmNovaVenda.getContentPane().add(scrollPane);

		tbCarros = new JTable();
		tbCarros.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {				
				LinhaSelecionada();
			}
		});
		scrollPane.setViewportView(tbCarros);

		JLabel lblNewLabel_1 = new JLabel("Carros Adicionados: ");
		lblNewLabel_1.setBounds(10, 84, 131, 14);
		frmNovaVenda.getContentPane().add(lblNewLabel_1);

		JButton btnFecharVenda = new JButton("Finalizar Venda");
		btnFecharVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fechaVenda();
			}
		});
		btnFecharVenda.setBounds(396, 24, 146, 46);
		frmNovaVenda.getContentPane().add(btnFecharVenda);
		
		btnExcluir = new JButton("Remover Item");
		btnExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ExcluirItem();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setBounds(416, 295, 126, 23);
		frmNovaVenda.getContentPane().add(btnExcluir);
	}
}
