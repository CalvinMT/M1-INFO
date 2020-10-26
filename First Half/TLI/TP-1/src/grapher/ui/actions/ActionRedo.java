package grapher.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.JTable;

public class ActionRedo extends Command {
	
	public ActionRedo (JTable table, String text) {
		super(table, text);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Command command = (Command) CommandHistory.getInstance().popUndo();
		if (command != null) {
			command.redo();
		}
	}
	
}
