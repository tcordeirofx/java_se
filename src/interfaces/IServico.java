package interfaces;

import modelo.Registro;

public interface IServico {
	
	Registro GetById(int Id) throws Exception;
	
	void Remove(int id) throws Exception;
	
	int ProximoId() throws Exception;
	
	void Inserir(Registro item) throws Exception;
	
	void Atualizar(Registro item) throws Exception;
	
}
