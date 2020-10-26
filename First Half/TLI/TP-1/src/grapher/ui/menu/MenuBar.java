package grapher.ui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import grapher.ui.actions.Actions;

public class MenuBar extends JMenuBar {

	JMenu menuFunction;
	
	JMenu menuWindow;
	
	
	public MenuBar () {
		super();
		
		initialiseMenuFunction();
		initialiseMenuWindow();
	}
	
	
	
	private void initialiseMenuFunction () {
		menuFunction = new JMenu("Function");

		JMenuItem itemAddFunction = new JMenuItem(Actions.getInstance().actionAddFunction);
		JMenuItem itemEditFunction = new JMenuItem(Actions.getInstance().actionEditFunction);
		JMenuItem itemRemoveFunction = new JMenuItem(Actions.getInstance().actionRemoveFunction);

		JMenuItem itemUndo = new JMenuItem(Actions.getInstance().actionUndo);
		JMenuItem itemRedo = new JMenuItem(Actions.getInstance().actionRedo);

		menuFunction.add(itemAddFunction);
		menuFunction.add(itemEditFunction);
		menuFunction.add(itemRemoveFunction);
		menuFunction.addSeparator();
		menuFunction.add(itemUndo);
		menuFunction.add(itemRedo);
		
		
		add(menuFunction);
	}
	
	
	
	private void initialiseMenuWindow () {
		menuWindow = new JMenu("Window");
		
		JMenu submenuViewMode = new JMenu("View mode");
		
		ItemListMode itemListMode = new ItemListMode(Actions.getInstance().actionViewModeList);
		ItemTableMode itemTableMode = new ItemTableMode(Actions.getInstance().actionViewModeTable);
		
		itemListMode.setSelected(true);

		Actions.getInstance().actionViewModeList.addListener(itemListMode);
		Actions.getInstance().actionViewModeTable.addListener(itemListMode);
		
		Actions.getInstance().actionViewModeList.addListener(itemTableMode);
		Actions.getInstance().actionViewModeTable.addListener(itemTableMode);
		
		submenuViewMode.add(itemListMode);
		submenuViewMode.add(itemTableMode);
		
		menuWindow.add(submenuViewMode);
		
		add(menuWindow);
	}
	
}
