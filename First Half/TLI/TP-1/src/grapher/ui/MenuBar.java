package grapher.ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {
	
	JMenu menuFunction;

	ActionAddFunction actionAddFunction;
	ActionRemoveFunction actionRemoveFunction;
	
	
	public MenuBar () {
		super();
		
		initialiseMenuFunction();
	}
	
	
	
	private void initialiseMenuFunction () {
		menuFunction = new JMenu("Function");

		actionAddFunction = new ActionAddFunction(this, "Add...");
		actionRemoveFunction = new ActionRemoveFunction(this, "Remove...");

		JMenuItem itemAddFunction = new JMenuItem(actionAddFunction);
		JMenuItem itemRemoveFunction = new JMenuItem(actionRemoveFunction);

		menuFunction.add(itemAddFunction);
		menuFunction.add(itemRemoveFunction);
		
		add(menuFunction);
	}
	
	
	
	public void addListener (ToolListener listener) {
		actionAddFunction.addListener(listener);
		actionRemoveFunction.addListener(listener);
	}
	
	
	
	public ActionRemoveFunction getActionRemoveFunction () {
		return actionRemoveFunction;
	}
	
}
