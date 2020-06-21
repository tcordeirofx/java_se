package telas;

import static aplicacao.Helpers.ClearTable;
import static aplicacao.Helpers.ExibeMensagem;
import static aplicacao.Helpers.GetSelectedId;
import static aplicacao.Helpers.NuloOuVazio;
import static aplicacao.Helpers.SalvaArquivo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

import aplicacao.Contexto;
import modelo.Carro;
import servicos.ServicoCarro;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Cursor;

public class UICarros {
	
	final String ERR_VALIDACAO = "Todos os campos são obrigatórios.";
	
	private ServicoCarro servico = new ServicoCarro();
	private Carro Current = new Carro();

	public JFrame frmCadastroDeCarros;
	private JTable table;
	private JTextField txtModelo;
	private JLabel lblTelefone;
	private JTextField txtMarca;
	private JButton btnSalvar;
	private JButton btnExcluir;
	private JButton btnNovo;
	private JLabel lblCelular;
	private JTextField txtValor;

	JFileChooser fileChooser = new JFileChooser();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UICarros window = new UICarros();
					window.frmCadastroDeCarros.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UICarros() {
		initialize();
		loadTable();
		loadForm();
	}
	
	private boolean Valido(){
		if(Current == null) return false;
		
		if(NuloOuVazio(Current.getMarca())) return false;
		if(NuloOuVazio(Current.getModelo())) return false;
		if(Current.getValor() <= 0) return false;
		
		return true;
	}
	
	private void loadForm() {		
		txtMarca.setText(Current.getMarca());
		txtModelo.setText(Current.getModelo());
		txtValor.setText(String.valueOf(Current.getValor()));
		refreshBotoes();
	}
	
	private void refreshBotoes () {
		Boolean temId = Current.getId() > 0;
		
		btnExcluir.setEnabled(temId);
		btnNovo.setEnabled(temId);
	}
	
	private void ExcluirItem () {
		try {
			
			int id = GetSelectedId(table);
			servico.Remove(id);
			
			Current = new Carro();
			loadTable();
			loadForm();
			
		} catch (Exception e) {
			e.printStackTrace();
			ExibeMensagem(e.getMessage());
		}
	}
	
	private void NovoItem () {
		Current = new Carro();
		loadForm();
		table.clearSelection();
	}
	
	private void CriarItem () {
		try {							
			
			try {
				Current.setValor(Float.valueOf(txtValor.getText()));
			} catch (Exception e) {
				throw new Exception("Formato de valor incorreto!");
			}			
			
			Current.setModelo(txtModelo.getText());
			Current.setMarca(txtMarca.getText());
			
			if(!Valido()) {
				ExibeMensagem(ERR_VALIDACAO);
				return;
			}
			
			servico.Inserir(Current);
			
			Current = new Carro();
			
			loadTable();
			loadForm();
			
		} catch (Exception e) {
			e.printStackTrace();
			ExibeMensagem(e.getMessage());
		}
	}
	
	private void AtualizarItem () {
		try {
					
			try {
				Current.setValor(Float.valueOf(txtValor.getText()));
			} catch (Exception e) {
				throw new Exception("Formato de valor incorreto!");
			}
			
			Current.setModelo(txtModelo.getText());
			Current.setMarca(txtMarca.getText());
			
			if(!Valido()) {
				ExibeMensagem(ERR_VALIDACAO);
				return;
			}
			
			servico.Atualizar(Current);
			
			Current = new Carro();
			
			loadTable();
			loadForm();
						
		} catch (Exception e) {
			e.printStackTrace();
			ExibeMensagem(e.getMessage());
		}
	}
	
	private void LinhaSelecionada() {				
		try {
			
			int id = GetSelectedId(table);		
			Current = (Carro)servico.GetById(id);
			
			loadForm();
			
		} catch (Exception e) {
			e.printStackTrace();
			ExibeMensagem(e.getMessage());
		}		
	}
	
	private void loadTable() {
		ClearTable(table);
		
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.addColumn("Código");
		model.addColumn("Modelo");
		model.addColumn("Marca");
		model.addColumn("Valor");
		
		for (Carro carro : Contexto.getCarros()) {
			model.addRow(new Object[] { 
				carro.getId(), 
				carro.getModelo(), 
				carro.getMarca(),
				carro.getValor()
			});			
		}	
	}
	
	private void salvarRelatorio(File file) {
		try {
			String rel = "Código;Modelo;Marca;Valor";
			
			for (Carro carro : Contexto.getCarros()) {
				String padrao = "\n%d;%s;%s;%f";
								
				rel += String.format(padrao, 
						carro.getId(),
						carro.getModelo(),
						carro.getMarca(),						
						carro.getValor());
			}
			
			SalvaArquivo(file, rel);
		} catch (IOException e) {
			e.printStackTrace();
			ExibeMensagem("Falha ao salvar o arquivo.");
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCadastroDeCarros = new JFrame();
		frmCadastroDeCarros.setResizable(false);
		frmCadastroDeCarros.setTitle("Cadastro de Carros");
		frmCadastroDeCarros.setBounds(100, 100, 649, 431);
		frmCadastroDeCarros.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblNome = new JLabel("Modelo");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtModelo = new JTextField();
		txtModelo.setColumns(10);
		
		lblTelefone = new JLabel("Marca");
		lblTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtMarca = new JTextField();
		txtMarca.setColumns(10);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Current.getId() > 0)
					AtualizarItem();
				else
					CriarItem();
			}
		});
		
		lblCelular = new JLabel("Valor");
		lblCelular.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtValor = new JTextField();
		txtValor.setColumns(10);
		
		JSeparator separator = new JSeparator();
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {				
				LinhaSelecionada();
			}
		});

		scrollPane.setViewportView(table);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ExcluirItem();
			}
		});
		
		btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NovoItem();
			}
		});
		
		JLabel btnExportar = new JLabel("Exportar?");
		btnExportar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExportar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int result = fileChooser.showSaveDialog(btnExportar);
				if (result == JFileChooser.APPROVE_OPTION) {
					salvarRelatorio(fileChooser.getSelectedFile());
				}
			}
		});
		btnExportar.setForeground(SystemColor.textHighlight);
		
		GroupLayout groupLayout = new GroupLayout(frmCadastroDeCarros.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 613, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 613, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblCelular, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtValor, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtModelo, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnSalvar)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(btnNovo, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblTelefone, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtMarca, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE))
								.addComponent(btnExportar, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtModelo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtMarca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTelefone)
						.addComponent(lblNome))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtValor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCelular))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSalvar)
						.addComponent(btnExcluir)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnNovo)
							.addComponent(btnExportar)))
					.addGap(17))
		);
		frmCadastroDeCarros.getContentPane().setLayout(groupLayout);
		
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setDialogTitle("Como deseja salvar o arquivo?"); 
		
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Documentos CSV", "csv"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setSelectedFile(new File("Relatorio_Carros.csv"));
	}
}
