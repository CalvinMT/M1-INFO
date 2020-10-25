package grapher.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import grapher.ui.tool.ToolListener;

public class FunctionList extends JList <String> {
	
	private List <ToolListener> toolListeners = new ArrayList<>();
	
	
	
	public FunctionList (ListModelFromTable <String> model) {
		super();
		setModel(model);
		
		addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					for (ToolListener listener : toolListeners) {
						listener.onFunctionSelection(getSelectedIndex());
					}
				}
			}
		});
	}
	

	
	public void addToolListener (ToolListener l) {
		toolListeners.add(l);
	}
	
}
