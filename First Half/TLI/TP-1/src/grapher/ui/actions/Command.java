package grapher.ui.actions;

import java.awt.Color;
import java.awt.Component;
import java.util.Stack;

import javax.swing.AbstractAction;
import javax.swing.JTable;

import grapher.ui.FunctionTable;

public abstract class Command extends AbstractAction {
	
	private JTable table;
	
	protected Component parent;
	
	private Stack <Object[][]> backupStack;
	
	private CommandListener commandListener;
	
	
	
	public Command (JTable table, String text) {
		this(table, null, text);
	}
	
	public Command (JTable table, Component parent, String text) {
		super(text);
		this.table = table;
		this.parent = parent;
		backupStack = new Stack <> ();
	}
	
	
	
	public void addCommandListener (CommandListener l) {
		commandListener = l;
	}
	protected void doBackup () {
		int nbRow = table.getRowCount();
		int nbColumn = table.getColumnCount();
		Object data[][] = new Object[nbRow][nbColumn];
		for (int i=0; i<nbRow; i++) {
			for (int j=0; j<nbColumn; j++) {
				data[i][j] = table.getModel().getValueAt(i, j);
			}
		}
		backupStack.push(data);
		commandListener.onCommandBackup(this);
	}
	
}
