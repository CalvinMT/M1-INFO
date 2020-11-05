package grapher.ui.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ActionAddFunction extends Command {
	
	private List <FunctionActionListener> listeners = new ArrayList<>();
	
	
	
	public ActionAddFunction (JTable table, Component parent, String text) {
		super(table, parent, text);
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
			doBackup();
			for (FunctionActionListener listener : listeners) {
				listener.onFunctionAdd(newExpression);
			}
		}
	}
	
	
	
	public void addListener (FunctionActionListener listener) {
		listeners.add(listener);
	}
	
}
