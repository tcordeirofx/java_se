package aplicacao;

import java.util.ArrayList;

import modelo.Carro;
import modelo.Cliente;
import modelo.Venda;

public class Contexto {

	private static ArrayList<Cliente> Clientes;
	private static ArrayList<Carro> Carros;
	private static ArrayList<Venda> Vendas;
	
	public static final ArrayList<Cliente> getClientes() {
		return Clientes;
	}
	
	public static final ArrayList<Carro> getCarros() {
		return Carros;
	}
	
	public static final ArrayList<Venda> getVendas() {
		return Vendas;
	}
	
	
	public static void main(String[] args) {
		Carros = new ArrayList<Carro>();
	}
	
	public Contexto() {
		Carros = new ArrayList<Carro>();
	}
	
	public static void Carregar() {
		Carros = new ArrayList<Carro>();
		Vendas = new ArrayList<Venda>();
		Clientes = new ArrayList<Cliente>();
	}

}
