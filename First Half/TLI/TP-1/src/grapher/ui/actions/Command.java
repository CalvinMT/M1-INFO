package grapher.ui.actions;

import java.awt.Color;
import java.awt.Component;
import java.util.Stack;

import javax.swing.AbstractAction;
import javax.swing.JTable;

import grapher.ui.view.table.FunctionTable;

public abstract class Command extends AbstractAction {
	
	private JTable table;
	
	protected Component parent;

	private Stack <Object[][]> backupStack;
	private Stack <Object[][]> undoBackupStack;
	
	
	
	public Command (JTable table, String text) {
		this(table, null, text);
	}
	
	public Command (JTable table, Component parent, String text) {
		super(text);
		this.table = table;
		this.parent = parent;
		backupStack = new Stack <> ();
		undoBackupStack = new Stack <> ();
	}
	
	
	
	protected void undo () {
		if (! backupStack.isEmpty()) {
			doUndoBackup();
			((FunctionTable) table).removeAllFunctions();
			Object data[][] = backupStack.pop();
			int nbRow = data.length;
			for (int i=0; i<nbRow; i++) {
				((FunctionTable) table).addFunction((String) data[i][0], (Color) data[i][1]);
			}
		}
	}
	
	protected void redo () {
		if (! undoBackupStack.isEmpty()) {
			doBackup();
			((FunctionTable) table).removeAllFunctions();
			Object data[][] = undoBackupStack.pop();
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
	
	private void doUndoBackup () {
		int nbRow = table.getRowCount();
		int nbColumn = table.getColumnCount();
		Object data[][] = new Object[nbRow][nbColumn];
		for (int i=0; i<nbRow; i++) {
			for (int j=0; j<nbColumn; j++) {
				data[i][j] = table.getModel().getValueAt(i, j);
			}
		}
		undoBackupStack.push(data);
	}
	
}
