package grapher.ui.menu;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import grapher.ui.actions.Actions;

public class MenuBar extends JMenuBar {

	private JMenu menuFunction;
	
	private JMenu menuWindow;
	
	private JMenuItem itemColorFunction;
	
	
	
	public MenuBar () {
		super();
		
		initialiseMenuFunction();
		initialiseMenuWindow();
	}
	
	
	
	private void initialiseMenuFunction () {
		menuFunction = new JMenu("Function");

		JMenuItem itemAddFunction = new JMenuItem(Actions.getInstance().actionAddFunction);
		JMenuItem itemEditFunction = new JMenuItem(Actions.getInstance().actionEditFunction);
		itemColorFunction = new JMenuItem(Actions.getInstance().actionColorFunction);
		JMenuItem itemRemoveFunction = new JMenuItem(Actions.getInstance().actionRemoveFunction);

		JMenuItem itemUndo = new JMenuItem(Actions.getInstance().actionUndo);
		JMenuItem itemRedo = new JMenuItem(Actions.getInstance().actionRedo);
		
		itemColorFunction.setEnabled(false);
		
		menuFunction.add(itemAddFunction);
		menuFunction.add(itemEditFunction);
		menuFunction.add(itemColorFunction);
		menuFunction.add(itemRemoveFunction);
		menuFunction.addSeparator();
		menuFunction.add(itemUndo);
		menuFunction.add(itemRedo);
		
		
		add(menuFunction);
	}
	
	
	
	private void initialiseMenuWindow () {
		menuWindow = new JMenu("Window");
		
		JMenu submenuViewMode = new JMenu("View mode");

		ButtonGroup groupFunctionViewMode = new ButtonGroup();
		
		JRadioButtonMenuItem itemListMode = new JRadioButtonMenuItem(Actions.getInstance().actionViewModeList);
		JRadioButtonMenuItem itemTableMode = new JRadioButtonMenuItem(Actions.getInstance().actionViewModeTable);
		
		itemListMode.setSelected(true);
		
		groupFunctionViewMode.add(itemListMode);
		groupFunctionViewMode.add(itemTableMode);
		
		submenuViewMode.add(itemListMode);
		submenuViewMode.add(itemTableMode);
		
		menuWindow.add(submenuViewMode);
		
		add(menuWindow);
	}
	
}
