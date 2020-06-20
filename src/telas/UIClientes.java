package telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import aplicacao.Contexto;
import static aplicacao.Helpers.*;
import modelo.Cliente;
import servicos.ServicoCliente;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UIClientes {

	final String ERR_VALIDACAO = "Todos os campos são obrigatórios.";
	
	private ServicoCliente servico = new ServicoCliente();
	private Cliente Current = new Cliente();
	
	public JFrame frmCadastroDeClientes;
	private JTable table;
	private JTextField txtNome;
	private JTextField txtEndereco;
	private JLabel lblEndereo;
	private JTextField txtEmail;
	private JLabel lblTelefone;
	private JTextField txtDoc;
	private JLabel lblEmail;
	private JButton btnSalvar;
	private JButton btnExcluir;
	private JButton btnNovo;
	private JLabel lblCelular;
	private JTextField txtCelular;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIClientes window = new UIClientes();
					window.frmCadastroDeClientes.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UIClientes() throws Exception {
		initialize();
		loadTable();
		loadForm();
	}
	
	private boolean Valido(){
		if(Current == null) return false;
		
		if(NuloOuVazio(Current.getNome())) return false;
		if(NuloOuVazio(Current.getDocumento())) return false;
		if(NuloOuVazio(Current.getEmail())) return false;
		if(NuloOuVazio(Current.getEndereco())) return false;
		if(NuloOuVazio(Current.getCelular())) return false;
		
		return true;
	}
	
	private void loadTable() {
		ClearTable(table);
		
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		
		model.addColumn("Código");
		model.addColumn("Nome");
		model.addColumn("Celular");
		model.addColumn("E-mail");
		
		for (Cliente cliente : Contexto.getClientes()) {
			model.addRow(new Object[] { 
				cliente.getId(),
				cliente.getNome(), 
				cliente.getCelular(),
				cliente.getEmail()
			});			
		}		
	}
	
	private void loadForm() {		
		txtCelular.setText(Current.getCelular());
		txtDoc.setText(Current.getDocumento());
		txtEmail.setText(Current.getEmail());
		txtEndereco.setText(Current.getEndereco());
		txtNome.setText(Current.getNome());
		
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
			
			Current = new Cliente();
			loadTable();
			loadForm();
			
		} catch (Exception e) {
			e.printStackTrace();
			ExibeMensagem(e.getMessage());
		}
	}
	
	private void NovoItem () {
		Current = new Cliente();
		loadForm();
		table.clearSelection();
	}
	
	private void CriarItem () {
		try {
								
			Current.setNome(txtNome.getText());
			Current.setCelular(txtCelular.getText());
			Current.setDocumento(txtDoc.getText());
			Current.setEmail(txtEmail.getText());
			Current.setEndereco(txtEndereco.getText());
			
			if(!Valido()) {
				ExibeMensagem(ERR_VALIDACAO);
				return;
			}
			
			servico.Inserir(Current);
			
			Current = new Cliente();
			
			loadTable();
			loadForm();
			
		} catch (Exception e) {
			e.printStackTrace();
			ExibeMensagem(e.getMessage());
		}
	}
	
	private void AtualizarItem () {
		try {
					
			Current.setNome(txtNome.getText());
			Current.setCelular(txtCelular.getText());
			Current.setDocumento(txtDoc.getText());
			Current.setEmail(txtEmail.getText());
			Current.setEndereco(txtEndereco.getText());
			
			if(!Valido()) {
				ExibeMensagem(ERR_VALIDACAO);
				return;
			}
			
			servico.Atualizar(Current);
			
			Current = new Cliente();
			
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
			Current = (Cliente)servico.GetById(id);
			
			loadForm();
			
		} catch (Exception e) {
			e.printStackTrace();
			ExibeMensagem(e.getMessage());
		}		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCadastroDeClientes = new JFrame();
		frmCadastroDeClientes.setResizable(false);
		frmCadastroDeClientes.setTitle("Cadastro de Clientes");
		frmCadastroDeClientes.setBounds(200, 200, 649, 463);
		frmCadastroDeClientes.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblNome = new JLabel("Nome");
		
		txtNome = new JTextField();
		txtNome.setColumns(10);
		
		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		
		lblEndereo = new JLabel("Endere\u00E7o");
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		
		lblTelefone = new JLabel("Documento");
		
		txtDoc = new JTextField();
		txtDoc.setColumns(10);
		
		lblEmail = new JLabel("E-mail");
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Current.getId() > 0)
					AtualizarItem();
				else
					CriarItem();
			}
		});
		
		lblCelular = new JLabel("Celular");
		
		txtCelular = new JTextField();
		txtCelular.setColumns(10);
		
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
		GroupLayout groupLayout = new GroupLayout(frmCadastroDeClientes.getContentPane());
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
							.addGap(34)
							.addComponent(lblNome)
							.addGap(4)
							.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
							.addGap(55)
							.addComponent(lblTelefone)
							.addGap(5)
							.addComponent(txtDoc, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(15)
							.addComponent(lblEndereo)
							.addGap(5)
							.addComponent(txtEndereco, GroupLayout.PREFERRED_SIZE, 391, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(32)
							.addComponent(lblEmail)
							.addGap(5)
							.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
							.addGap(77)
							.addComponent(lblCelular)
							.addGap(4)
							.addComponent(txtCelular, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(65)
							.addComponent(btnSalvar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNovo, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)
					.addGap(20)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNome))
						.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblTelefone))
						.addComponent(txtDoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblEndereo))
						.addComponent(txtEndereco, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblEmail))
						.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblCelular))
						.addComponent(txtCelular, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnSalvar)
							.addComponent(btnExcluir))
						.addComponent(btnNovo)))
		);
		frmCadastroDeClientes.getContentPane().setLayout(groupLayout);
	}
}
