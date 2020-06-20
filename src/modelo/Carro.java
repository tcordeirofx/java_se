package modelo;

public class Carro extends Registro {
	
	private String Modelo;
	private String Marca;
	private float Valor;
	
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
