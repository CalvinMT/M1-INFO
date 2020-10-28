package grapher.ui.tool;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JToolBar;

import grapher.ui.actions.Actions;

public class ToolBar extends JToolBar {
	
	private static final Dimension BUTTON_SIZE = new Dimension(30, 30);
	private static final Font BUTTON_TEXT_FONT = new Font("", Font.PLAIN, 18);
	
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

		buttonAdd.setFont(BUTTON_TEXT_FONT);
		buttonRemove.setFont(BUTTON_TEXT_FONT);
		buttonEdit.setFont(BUTTON_TEXT_FONT);
		
		setButtonSize(buttonAdd);
		setButtonSize(buttonRemove);
		setButtonSize(buttonEdit);
		
		add(buttonAdd);
		add(buttonRemove);
		add(buttonEdit);
	}
	
	
	
	private void setButtonSize (JButton button) {
		button.setPreferredSize(BUTTON_SIZE);
		button.setMaximumSize(BUTTON_SIZE);
	}
	
}
