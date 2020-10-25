package grapher.ui;

import javax.swing.DefaultListModel;

public class ListModelFromTable <E> extends DefaultListModel <String> implements FunctionListListener {
	
	public ListModelFromTable () {
		super();
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
	public void onFunctionSelection(int index, String function) {
		return;
	}
	
}
