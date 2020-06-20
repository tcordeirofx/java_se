package modelo;

public class Cliente extends Registro implements Cloneable {
	 
	private String Nome;
	private String Documento;
	private String Endereco;
	private String Celular;
	private String Email;
	
	public Cliente clone() {
		try {
			return (Cliente) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	} 		
	
	public final String getNome() {
		return Nome;
	}
	public final void setNome(String nome) {
		Nome = nome;
	}
	
	public final String getDocumento() {
		return Documento;
	}
	public final void setDocumento(String documento) {
		Documento = documento;
	}
	
	public final String getEndereco() {
		return Endereco;
	}
	public final void setEndereco(String endereco) {
		Endereco = endereco;
	}
	
	public final String getCelular() {
		return Celular;
	}
	public final void setCelular(String celular) {
		Celular = celular;
	}
	
	public final String getEmail() {
		return Email;
	}
	public final void setEmail(String email) {
		Email = email;
	}

}
