package aplicacao;

import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.JComboBox;

public class Helpers {
	
	public static final String EMPTY = "";
	
	public static Boolean NuloOuVazio(String str) {
		if(str == null || str.trim().equals(Helpers.EMPTY)) return true;
		return false;
	}
	
	public static Integer GetSelectedId (JTable table) {
		int index = table.getSelectedRow();
		
		if(index < 0) return 0;
		
		return (int)table.getValueAt(index, 0);	
	}
	
	public static Integer GetSelectedId (JComboBox<String> comboBox) throws Exception {
		try {
			String selected = (String)comboBox.getSelectedItem();
			
			String cod = selected.split("-")[0].trim();
			
			return Integer.parseInt(cod);			
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void ClearTable(JTable table) {
		TableModel newModel = new DefaultTableModel();
		table.setModel(newModel);
	}
	
	public static void ExibeMensagem(String msg) {
		showMessageDialog(null, msg);
	}
	
	public static void Get(String msg) {
		showMessageDialog(null, msg);
	}
}
