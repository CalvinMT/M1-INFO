package grapher.ui.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import grapher.ui.FunctionListListener;
import grapher.ui.tool.ToolListener;

public class ActionAddFunction extends AbstractAction implements FunctionListListener {
	
	JComponent parent;
	
	private List <ToolListener> listeners = new ArrayList<>();
	
	
	
	public ActionAddFunction (JComponent parent, String text) {
		super(text);
		this.parent = parent;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String newExpression = (String)JOptionPane.showInputDialog(
                parent,
                "New expression:",
                "Add expression",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "");
		if (newExpression != null  &&  newExpression.length() > 0) {
			for (ToolListener listener : listeners) {
				listener.onFunctionAdd(newExpression);
			}
		}
	}
	
	
	
	public void addListener (ToolListener listener) {
		listeners.add(listener);
	}
	
	
	
	@Override
	public void onFunctionSelection(int selected, String name) {
		return;
	}
	
	@Override
	public void onFunctionAdd(String function) {
		return;
	}
	
	@Override
	public void onFunctionRemove(int function) {
		return;
	}
	
	@Override
	public void onFunctionEdit(int index, String function) {
		return;
	}
	
}
