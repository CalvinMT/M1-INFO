package grapher.ui.view.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;

import grapher.ui.tool.ToolListener;

public class FunctionList extends JList <String> {
	
	private List <ToolListener> toolListeners = new ArrayList<>();
	
	
	
	public FunctionList (FunctionListModelFromTable <String> model) {
		super();
		
		model.setFunctionList(this);
		setModel(model);
		
		getSelectionModel().addListSelectionListener(l -> {
			if (l.getValueIsAdjusting()) {
				for (ToolListener listener : toolListeners) {
					listener.onFunctionSelection(getSelectedIndices());
				}
			}
		});
	}
	

	
	public void addToolListener (ToolListener l) {
		toolListeners.add(l);
	}
	
}
