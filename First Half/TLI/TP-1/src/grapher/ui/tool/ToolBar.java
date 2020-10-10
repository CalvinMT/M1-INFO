package grapher.ui.tool;

import javax.swing.JButton;
import javax.swing.JToolBar;

import grapher.ui.actions.ActionAddFunction;
import grapher.ui.actions.ActionRemoveFunction;

public class ToolBar extends JToolBar {

	private ActionAddFunction actionAddFunction;
	private ActionRemoveFunction actionRemoveFunction;
	
	private JButton buttonAdd;
	private JButton buttonRemove;
	
	
	
	public ToolBar () {
		super();
		setFloatable(false);
		
		actionAddFunction = new ActionAddFunction(this, "+");
		actionRemoveFunction = new ActionRemoveFunction(this, "-");

		buttonAdd = new JButton(actionAddFunction);
		buttonRemove = new JButton(actionRemoveFunction);
		add(buttonAdd);
		add(buttonRemove);
	}
	
	
	
	public void addListener (ToolListener listener) {
		actionAddFunction.addListener(listener);
		actionRemoveFunction.addListener(listener);
	}
	
	
	
	public ActionRemoveFunction getActionRemoveFunction () {
		return actionRemoveFunction;
	}
	
}
