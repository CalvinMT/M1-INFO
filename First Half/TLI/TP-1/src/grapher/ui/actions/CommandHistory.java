package grapher.ui.actions;

import java.util.Stack;

public class CommandHistory implements CommandListener {
	
	private static CommandHistory instance;
	
	private Stack <Command> history;
	
	
	
	private CommandHistory () {
		history = new Stack <> ();
	}
	
	public static CommandHistory getInstance () {
		if (instance == null) {
			instance = new CommandHistory();
		}
		return instance;
	}
	
	
	
	public void push (Command c) {
		history.push(c);
	}
	
	public Command pop () {
		if (history.isEmpty()) {
			return null;
		}
		return history.pop();
	}
	
	
	
	@Override
	public void onCommandBackup(Command c) {
		history.push(c);
	}
	
}
