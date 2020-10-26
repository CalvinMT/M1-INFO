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
	public void onFunctionRemove(int indices[]) {
		for (int i=indices.length - 1; i>=0; i--) {
			remove(indices[i]);
		}
		list.clearSelection();
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
