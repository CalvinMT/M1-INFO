package grapher.ui.actions;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JTable;

import grapher.ui.view.FunctionColorChooserListener;
import grapher.ui.view.table.FunctionTableListener;

public class ActionColorFunction extends Command implements FunctionTableListener {
	
	private List <FunctionColorChooserListener> listeners = new ArrayList<>();

	private int selectedFunction = -1;
	private String selectedFunctionName = "";
	private Color selectFunctionColor = Color.RED;
	
	
	
	public ActionColorFunction (JTable table, Component parent, String text) {
		super(table, parent, text);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (selectedFunction >= 0) {
			Color newColor = JColorChooser.showDialog(null, "New colour for " + selectedFunctionName, selectFunctionColor);
			if (newColor != null) {
				doBackup();
				for (FunctionColorChooserListener listener : listeners) {
					listener.onColorChosen(selectedFunction, newColor);
				}
				selectFunctionColor = newColor;
			}
		}
	}
	
	
	
	public void addListener (FunctionColorChooserListener listener) {
		listeners.add(listener);
	}
	
	
	
	@Override
	public void onFunctionSelection(int indices[], String function, Color color) {
		if (indices.length == 1) {
			selectedFunction = indices[0];
			selectedFunctionName = function;
			if (color != null) {
				selectFunctionColor = color;
			}
			else {
				selectFunctionColor = Color.RED;
			}
		}
		else {
			selectedFunction = -1;
			selectedFunctionName = "";
			selectFunctionColor = Color.RED;
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
