package grapher.ui.actions;

import java.awt.Color;
import java.awt.Component;

import javax.swing.AbstractAction;
import javax.swing.JTable;

public abstract class Command extends AbstractAction {
	
	private JTable table;
	
	protected Component parent;
	
	
	
	public Command (JTable table, String text) {
		this(table, null, text);
	}
	
	public Command (JTable table, Component parent, String text) {
		super(text);
		this.table = table;
		this.parent = parent;
		backupStack = new Stack <> ();
	}
	
}
