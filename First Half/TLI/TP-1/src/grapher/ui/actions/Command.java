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
	
	
	
	public Command (JTable table, String text) {
		this(table, null, text);
	}
	
	public Command (JTable table, Component parent, String text) {
		super(text);
		this.table = table;
		this.parent = parent;
		backupStack = new Stack <> ();
	}
	
	
	
	protected void undo () {
		if (! backupStack.isEmpty()) {
			for (int i=0; i<table.getRowCount(); i++) {
				table.addRowSelectionInterval(i, i);
			}
			((FunctionTable) table).removeFunction();
			Object data[][] = backupStack.pop();
			int nbRow = data.length;
			for (int i=0; i<nbRow; i++) {
				((FunctionTable) table).addFunction((String) data[i][0], (Color) data[i][1]);
			}
		}
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
		CommandHistory.getInstance().push(this);
	}
	
}
