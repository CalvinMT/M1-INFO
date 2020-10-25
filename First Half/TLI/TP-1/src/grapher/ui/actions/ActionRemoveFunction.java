package grapher.ui.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

import grapher.ui.tool.ToolListener;

public class ActionRemoveFunction extends AbstractAction {
	
	JComponent parent;
	
	private List <ToolListener> listeners = new ArrayList<>();
	
	
	
	public ActionRemoveFunction (JComponent parent, String text) {
		super(text);
		this.parent = parent;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (ToolListener listener : listeners) {
			listener.onFunctionRemove();
		}
	}
	
	
	
	public void addListener (ToolListener listener) {
		listeners.add(listener);
	}
	
}
