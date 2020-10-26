package grapher.ui;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class FunctionListModelFromTable <E> extends DefaultListModel <String> implements FunctionTableListener {
	
	JList <String> list;
	
	
	
	public void setFunctionList(JList <String> l) {
		list = l;
	}
	
	
	
	@Override
	public void onFunctionAdd(String function) {
		addElement(function);
	}
	
	@Override
	public void onFunctionRemove(int index) {
		remove(index);
	}
	
	@Override
	public void onFunctionEdit(int index, String function) {
		set(index, function);
	}
	
	@Override
	public void onFunctionSelection(int indices[], String function) {
		list.setSelectedIndices(indices);
	}
	
}
