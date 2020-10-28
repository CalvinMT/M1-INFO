package grapher.ui.tool;

import javax.swing.JButton;
import javax.swing.JToolBar;

import grapher.ui.actions.Actions;

public class ToolBar extends JToolBar {
	
	private JButton buttonAdd;
	private JButton buttonRemove;
	private JButton buttonEdit;
	
	
	
	public ToolBar () {
		super();
		setFloatable(false);
		
		buttonAdd = new JButton(Actions.getInstance().actionAddFunction);
		buttonRemove = new JButton(Actions.getInstance().actionRemoveFunction);
		buttonEdit = new JButton(Actions.getInstance().actionEditFunction);
		
		buttonAdd.setText("+");
		buttonRemove.setText("-");
		buttonEdit.setText("✎");
		
		add(buttonAdd);
		add(buttonRemove);
		add(buttonEdit);
	}
	
}
