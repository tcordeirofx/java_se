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
import static aplicacao.Helpers.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

import modelo.Carro;
import modelo.Cliente;
import modelo.Venda;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.PopupMenuEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;    

public class UICaixa {

	public JFrame frmCaixa;
	private JTable tbVendas;
	private UIClientes clientes = new UIClientes();
	private UIVenda venda = new UIVenda();
	private UICarros carros = new UICarros();
	
	JComboBox<String> cbCliente;
	JComboBox<String> cbCarro;
	
	JFileChooser fileChooser = new JFileChooser();
		
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
		loadTable(Contexto.getVendas());
		loadForm();
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
	
	private ArrayList<Venda> Filtrar() {
		ArrayList<Venda> lst = new ArrayList<Venda>();
		
		Integer cdCliente = 0;
		Integer cdCarro = 0;
		
		try {
			cdCarro = GetSelectedId(cbCarro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(cdCarro == null) cdCarro = 0;
		}
		
		try {
			cdCliente = GetSelectedId(cbCliente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(cdCliente == null) cdCliente = 0;
		}
		
		for (Venda item : Contexto.getVendas()) {
			
			if(cdCliente > 0)
				if(item.getCliente().getId() != cdCliente)
					continue;
			
			if(cdCarro > 0) {
				Boolean temCarro = false;
				for (Carro carro : item.getCarros()) {
					if(carro.getId() == cdCarro)
						temCarro = true;
				} 
					
				if(!temCarro) continue;
			}
					
			lst.add(item);
		} 
		
		return lst;
	}
	
	private void loadTable(ArrayList<Venda> dataSource) {
		ClearTable(tbVendas);
		
		DefaultTableModel model = (DefaultTableModel)tbVendas.getModel();
		
		model.addColumn("Código");
		model.addColumn("Cliente");
		model.addColumn("Itens");
		model.addColumn("Total");
		
		for (Venda venda : dataSource) {
			model.addRow(new Object[] { 
				venda.getId(), 
				venda.getCliente().getNome(), 
				venda.getCarros().size(), 
				venda.getTotal()
			});			
		}		
	}
	
	private void salvarRelatorio(File file) {
		try {
			String rel = "Código;Cliente;Itens;Veiculos;Total";
			
			for (Venda venda : Contexto.getVendas()) {
				String padrao = "\n%d;%s;%d;%s;%f";
				String veiculos = "";
				
				for (Carro carro : venda.getCarros()) {
					veiculos += (veiculos == "" ? "" : ", ") + carro.getModelo();					
				}
				
				rel += String.format(padrao, 
						venda.getId(),
						venda.getCliente().getNome(),
						venda.getCarros().size(),
						veiculos,
						venda.getTotal());
			}
			
			SalvaArquivo(file, rel);
		} catch (IOException e) {
			e.printStackTrace();
			ExibeMensagem("Falha ao salvar o arquivo.");
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
				venda.frmNovaVenda.setVisible(true);
				venda.frmNovaVenda.addWindowFocusListener(new WindowFocusListener() {
					public void windowGainedFocus(WindowEvent arg0) {						
					}

					public void windowLostFocus(WindowEvent arg0) {
						loadTable(Contexto.getVendas());
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
		
		cbCliente = new JComboBox<String>();
		cbCliente.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				loadClientes();
			}
		});

		cbCliente.setBounds(12, 35, 124, 25);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 76, 733, 2);
		
		JLabel lblCarro = new JLabel("Carro");
		lblCarro.setBounds(156, 12, 33, 16);
		
		cbCarro = new JComboBox<String>();
		cbCarro.setBounds(148, 35, 124, 25);
		cbCarro.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				loadCarros();
			}
		});
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				loadTable(Filtrar());
			}
		});
		btnPesquisar.setBounds(278, 34, 92, 26);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 111, 733, 278);
		
		JLabel btnExportar = new JLabel("Exportar?");
		btnExportar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int result = fileChooser.showSaveDialog(btnExportar);
				if (result == JFileChooser.APPROVE_OPTION) {
					salvarRelatorio(fileChooser.getSelectedFile());
				}
			}
		});
		btnExportar.setBounds(382, 39, 56, 16);
		btnExportar.setForeground(SystemColor.textHighlight);
		btnExportar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		tbVendas = new JTable();
		scrollPane.setViewportView(tbVendas);
		frmCaixa.getContentPane().setLayout(null);
		frmCaixa.getContentPane().add(cbCliente);
		frmCaixa.getContentPane().add(lblNewLabel_1);
		frmCaixa.getContentPane().add(cbCarro);
		frmCaixa.getContentPane().add(btnPesquisar);
		frmCaixa.getContentPane().add(btnExportar);
		frmCaixa.getContentPane().add(btnNovaVenda);
		frmCaixa.getContentPane().add(btnCarros);
		frmCaixa.getContentPane().add(btnClientes);
		frmCaixa.getContentPane().add(lblCarro);
		frmCaixa.getContentPane().add(separator);
		frmCaixa.getContentPane().add(scrollPane);
		
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setDialogTitle("Como deseja salvar o arquivo?"); 
		
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Documentos CSV", "csv"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setSelectedFile(new File("Relatorio_Vendas.csv"));
	}
}
