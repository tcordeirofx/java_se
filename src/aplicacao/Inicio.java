package aplicacao;

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
			ExibeMensagem(e.getMessage());
		}		
	}
	
	private static void CarregaContexto() {		
		//Inicializa Contexto de Dados
		Contexto.Carregar();
	}
}
