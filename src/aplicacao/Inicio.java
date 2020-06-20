package aplicacao;

import java.util.ArrayList;

import modelo.Carro;
import modelo.Cliente;
import modelo.Venda;
import telas.UICaixa;
import static aplicacao.Helpers.*;

public class Inicio {

	public static void main(String[] args) {
		UICaixa caixa;
			
		try {			
			CarregaContexto();
			
			caixa = new UICaixa();
			caixa.frmCaixa.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			ExibeMensagem(e.getMessage());
		}		
	}
	
	private static void CarregaContexto() {
		
		//Inicializa Contexto de Dados
		Contexto.Carregar();
		//Seed();
	}
	
	private static void Seed() {
		ArrayList<Carro> carros = Contexto.getCarros();		
		ArrayList<Cliente> clientes = Contexto.getClientes();
		ArrayList<Venda> vendas = Contexto.getVendas();
		
		for (int i = 0; i < 5; i++) {
			Carro carro = new Carro();
			carro.setMarca("Marca " + i);
			carro.setModelo("Modelo "+ i);
			carro.setValor(100);
			carro.setId(i+1);
			carros.add(carro);			
		}
		
		for (int i = 0; i < 5; i++) {
			Cliente cliente = new Cliente();
			cliente.setCelular(Double.toString(Math.random() * 10));
			cliente.setDocumento(Double.toString(Math.random() * 10));
			cliente.setEmail("email@email.com");
			cliente.setEndereco("Rua X das quantas 15 Vl Leopoldina - RJ");
			cliente.setId(i+1);
			cliente.setNome("Cliente " + i);
			clientes.add(cliente);			
		}
		
		for (int i = 0; i < 5; i++) {
			Venda venda = new Venda();
			venda.setCarros(carros);
			venda.setCliente(clientes.get(2));
			vendas.add(venda);
		}	
	}
}
