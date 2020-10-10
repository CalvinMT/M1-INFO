package grapher.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import grapher.ui.tool.ToolListener;

public class FunctionList extends JList <String> implements ToolListener {
	
	private DefaultListModel <String> model = new DefaultListModel<>();
	
	private List <FunctionListListener> listeners = new ArrayList<>();
	
	
	
	public FunctionList () {
		super();
		setModel(model);
		
		addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					for (FunctionListListener listener : listeners) {
						listener.onFunctionSelection(getSelectedIndex(), getSelectedValue());
					}
				}
			}
		});
	}
	
	
	
	public void addFunctions (String[] f) {
		for (String s : f) {
			model.addElement(s);
		}
	}
	
	
	
	public void addListener (FunctionListListener l) {
		listeners.add(l);
	}



	@Override
	public void onFunctionAdd(String function) {
		model.addElement(function);
		for (FunctionListListener listener : listeners) {
			listener.onFunctionAdd(function);
		}
	}
	
	@Override
	public void onFunctionRemove(int function) {
		model.remove(function);
		for (FunctionListListener listener : listeners) {
			listener.onFunctionRemove(function);
		}
	}
	
}
