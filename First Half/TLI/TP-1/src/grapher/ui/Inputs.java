package grapher.ui;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import grapher.ui.actions.Actions;

public class Inputs {
	
	private static Inputs instance;

	private static final String UNDO = "UNDO";
	private static final String REDO = "REDO";
	
	
	
	private Inputs (InputMap inputMap, ActionMap actionMap) {
		initialiseInputMap(inputMap);
		initialiseActionMap(actionMap);
	}
	
	public static Inputs getInstance (InputMap inputMap, ActionMap actionMap) {
		if (instance == null) {
			instance = new Inputs(inputMap, actionMap);
		}
		return instance;
	}
	

	
	private void initialiseInputMap (InputMap inputMap) {
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), UNDO);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), REDO);
	}
	
	private void initialiseActionMap (ActionMap actionMap) {
		actionMap.put(UNDO, Actions.getInstance().actionUndo);
		actionMap.put(REDO, Actions.getInstance().actionRedo);
	}
	
}
