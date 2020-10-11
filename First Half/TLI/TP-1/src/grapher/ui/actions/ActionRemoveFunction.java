package grapher.ui.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import grapher.ui.FunctionListListener;
import grapher.ui.tool.ToolListener;

public class ActionRemoveFunction extends AbstractAction implements FunctionListListener {
	
	JComponent parent;
	
	private List <ToolListener> listeners = new ArrayList<>();

	private int selectedFunction = -1;
	private String selectedFunctionName = "";
	
	
	
	public ActionRemoveFunction (JComponent parent, String text) {
		super(text);
		this.parent = parent;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (selectedFunction >= 0) {
			Object[] options = {"Remove", "Cancel"};
			int answer = JOptionPane.showOptionDialog(
					parent,
					"Do you want to remove " + selectedFunctionName + "? This action cannot be undone.",
					"Remove expression",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE,
					null,
					options,
					options[0]);
			if (answer == 0) {
				for (ToolListener listener : listeners) {
					listener.onFunctionRemove(selectedFunction);
				}
			}
		}
	}
	
	
	
	public void addListener (ToolListener listener) {
		listeners.add(listener);
	}
	
	
	
	@Override
	public void onFunctionSelection(int selected, String name) {
		selectedFunction = selected;
		selectedFunctionName = name;
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
