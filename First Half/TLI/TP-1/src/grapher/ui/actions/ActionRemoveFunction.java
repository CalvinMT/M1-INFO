package grapher.ui.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

public class ActionRemoveFunction extends Command {
	
	private List <FunctionActionListener> listeners = new ArrayList<>();
	
	private JTable table;
	
	
	
	public ActionRemoveFunction (JTable table, Component parent, String text) {
		super(table, parent, text);
		this.table = table;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (table.getSelectedRows().length > 0) {
			doBackup();
			for (FunctionActionListener listener : listeners) {
				listener.onFunctionRemove();
			}
		}
	}
	
	
	
	public void addListener (FunctionActionListener listener) {
		listeners.add(listener);
	}
	
}
