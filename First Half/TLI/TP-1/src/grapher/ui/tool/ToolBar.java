package grapher.ui.tool;

import javax.swing.JButton;
import javax.swing.JToolBar;

import grapher.ui.actions.Actions;

public class ToolBar extends JToolBar {
	
	private JButton buttonAdd;
	private JButton buttonRemove;
	
	
	
	public ToolBar () {
		super();
		setFloatable(false);

		buttonAdd = new JButton(Actions.getInstance().actionAddFunction);
		buttonRemove = new JButton(Actions.getInstance().actionRemoveFunction);

		buttonAdd.setText("+");
		buttonRemove.setText("-");
		
		add(buttonAdd);
		add(buttonRemove);
	}
	
}
