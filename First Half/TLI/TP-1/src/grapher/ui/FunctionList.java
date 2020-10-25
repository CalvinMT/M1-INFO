package grapher.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;

import grapher.ui.tool.ToolListener;

public class FunctionList extends JList <String> {
	
	private List <ToolListener> toolListeners = new ArrayList<>();
	
	private CustomListSelectionModel customListSelectionModel;
	
	
	
	public FunctionList (FunctionListModelFromTable <String> model) {
		super();
		
		setModel(model);
		
		customListSelectionModel = new CustomListSelectionModel();
		setSelectionModel(customListSelectionModel);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased (MouseEvent e) {
				int row = locationToIndex(e.getPoint());
				for (ToolListener listener : toolListeners) {
					listener.onFunctionSelection(row);
				}
			}
		});
	}
	

	
	public void addToolListener (ToolListener l) {
		toolListeners.add(l);
	}
	
}
