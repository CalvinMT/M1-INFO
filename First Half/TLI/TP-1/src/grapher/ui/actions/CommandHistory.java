package grapher.ui.actions;

import java.util.Stack;

public class CommandHistory {
	
	private static CommandHistory instance;

	private Stack <Command> history;
	private Stack <Command> undoHistory;
	
	
	
	private CommandHistory () {
		history = new Stack <> ();
		undoHistory = new Stack <> ();
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
		Command c = history.pop();
		pushUndo(c);
		return c;
	}
	
	
	
	public void pushUndo (Command c) {
		undoHistory.push(c);
	}
	
	public Command popUndo () {
		if (undoHistory.isEmpty()) {
			return null;
		}
		return undoHistory.pop();
	}
	
}
