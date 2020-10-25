package grapher.ui.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import grapher.ui.FunctionTableListener;
import grapher.ui.tool.ToolListener;

public class ActionEditFunction extends AbstractAction implements FunctionTableListener {
	
	JComponent parent;
	
	private List <ToolListener> listeners = new ArrayList<>();

	private int selectedFunction = -1;
	private String selectedFunctionName = "";
	
	
	
	public ActionEditFunction (JComponent parent, String text) {
		super(text);
		this.parent = parent;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (selectedFunction >= 0) {
			String newExpression = (String)JOptionPane.showInputDialog(
	                parent,
	                "New expression:",
	                "Edit expression",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                null,
	                selectedFunctionName);
			if (newExpression != null  &&  newExpression.length() > 0) {
				for (ToolListener listener : listeners) {
					listener.onFunctionEdit(selectedFunction, newExpression);
				}
			}
		}
	}
	
	
	
	public void addListener (ToolListener listener) {
		listeners.add(listener);
	}
	
	
	
	@Override
	public void onFunctionSelection(int index, String function) {
		selectedFunction = index;
		selectedFunctionName = function;
	}
	
	@Override
	public void onFunctionAdd(String function) {
		return;
	}
	
	@Override
	public void onFunctionRemove(int index) {
		return;
	}
	
	@Override
	public void onFunctionEdit(int index, String function) {
		return;
	}
	
}
