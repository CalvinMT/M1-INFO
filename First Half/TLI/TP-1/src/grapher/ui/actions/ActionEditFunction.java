package grapher.ui.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import grapher.ui.FunctionTableListener;
import grapher.ui.tool.ToolListener;

public class ActionEditFunction extends Command implements FunctionTableListener {
	
	private List <ToolListener> listeners = new ArrayList<>();

	private int selectedFunction = -1;
	private String selectedFunctionName = "";
	
	
	
	public ActionEditFunction (JTable table, Component parent, String text) {
		super(table, parent, text);
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
	public void onFunctionSelection(int indices[], String function) {
		if (indices.length == 1) {
			selectedFunction = indices[0];
			selectedFunctionName = function;
		}
		else {
			selectedFunction = -1;
			selectedFunctionName = "";
		}
	}
	
	@Override
	public void onFunctionAdd(String function) {
		return;
	}
	
	@Override
	public void onFunctionRemove(int indices[]) {
		return;
	}
	
	@Override
	public void onFunctionEdit(int index, String function) {
		return;
	}
	
}
