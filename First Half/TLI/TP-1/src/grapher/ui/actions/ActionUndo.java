package grapher.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.JTable;

public class ActionUndo extends Command {
	
	public ActionUndo (JTable table, String text) {
		super(table, text);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Command command = (Command) CommandHistory.getInstance().pop();
		if (command != null) {
			command.undo();
		}
	}
	
}
