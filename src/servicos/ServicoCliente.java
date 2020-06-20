package servicos;

import aplicacao.Contexto;
import interfaces.IServico;
import modelo.Cliente;
import modelo.Registro;

public class ServicoCliente implements IServico {

	final String ERR_F_CRIAR = "Falha ao criar o cliente.";
	final String ERR_F_ATUALIZAR = "Falha ao atualizar o cliente.";
	final String ERR_F_REMOVER = "Falha ao remover o cliente.";
	final String ERR_F_LER_UM = "Falha ao carregar o cliente.";
	
	@Override
	public Registro GetById(int Id) throws Exception {
		Registro ret = null;
		
		try {
			for(Registro obj : Contexto.getClientes()) 
				if(obj.getId().equals(Id))
					ret = obj;	
		} catch (Exception e) {
			throw new Exception(ERR_F_LER_UM);
		}
		
		return ret;	
	}

	@Override
	public void Remove(int id) throws Exception {
		Object obj = GetById(id);
		
		try {
			if(obj != null)
				Contexto.getClientes().remove(obj);
			else
				throw new Exception();
		} catch (Exception e) {
			throw new Exception(ERR_F_REMOVER);
		}
	}

	@Override
	public int ProximoId() throws Exception {
		int id = 0;
	
		try {
			
			for(Registro obj : Contexto.getClientes()) 
				if(obj.getId() > id)
					id = obj.getId();	
			
		} catch (Exception e) {
			throw new Exception(ERR_F_CRIAR);
		}
		
		return ++id;
	}

	@Override
	public void Inserir(Registro item) throws Exception {		
		try {			
			if(item == null) throw new Exception();
			
			item.setId(ProximoId());
			
			Contexto.getClientes().add((Cliente)item);
		} catch (Exception e) {
			throw new Exception(ERR_F_CRIAR);
		}
	}

	@Override
	public void Atualizar(Registro item) throws Exception {			
		try {
			if(item == null) throw new Exception();
			
			int anterior = item.getId();
			
			Remove(anterior);
			
			Contexto.getClientes().add((Cliente)item);
		} catch (Exception e) {
			throw new Exception(ERR_F_ATUALIZAR);
		}
	}	

}
