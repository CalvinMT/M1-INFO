package grapher.ui.view.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;

import grapher.ui.actions.FunctionActionListener;

public class FunctionList extends JList <String> {
	
	private List <FunctionActionListener> functionActionListeners = new ArrayList<>();
	
	
	
	public FunctionList (FunctionListModelFromTable <String> model) {
		super();
		
		model.setFunctionList(this);
		setModel(model);
		
		getSelectionModel().addListSelectionListener(l -> {
			if (l.getValueIsAdjusting()) {
				for (FunctionActionListener listener : functionActionListeners) {
					listener.onFunctionSelection(getSelectedIndices());
				}
			}
		});
	}
	

	
	public void addToolListener (FunctionActionListener l) {
		functionActionListeners.add(l);
	}
	
}
