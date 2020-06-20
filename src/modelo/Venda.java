package modelo;

import java.util.ArrayList;

public class Venda extends Registro implements Cloneable {
	 
	private Cliente Cliente;
	private ArrayList<Carro> Carros;
		
	public Venda clone() {
		try {
			return (Venda) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}	
		
	public final Cliente getCliente() {
		return Cliente;
	}

	public final void setCliente(Cliente cliente) {
		Cliente = cliente;
	}

	public ArrayList<Carro> getCarros() {
		return Carros;
	}

	public void setCarros(ArrayList<Carro> carros) {
		Carros = carros;
	}

	public float getTotal() {		
		Float valores = Float.valueOf(0);
		
		for (Carro carro : Carros)
			valores += carro.getValor();
						
		return valores;		
	}
	
	public Venda() {
		Carros = new ArrayList<Carro>();
	}
}
