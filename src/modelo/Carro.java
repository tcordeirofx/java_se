package modelo;

public class Carro extends Registro implements Cloneable {
 
	private String Modelo;
	private String Marca;
	private float Valor;
	
	public Carro clone() {
		try {
			return (Carro) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	} 
	
	public final String getModelo() {
		return Modelo;
	}
	public final void setModelo(String modelo) {
		Modelo = modelo;
	}
	
	public final String getMarca() {
		return Marca;
	}
	public final void setMarca(String marca) {
		Marca = marca;
	}
	
	public final float getValor() {
		return Valor;
	}
	public final void setValor(float valor) {
		Valor = valor;
	}
	
}
