package grapher.ui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import grapher.ui.actions.Actions;
import grapher.ui.view.ViewModeListener;

public class MenuBar extends JMenuBar implements ViewModeListener {

	JMenu menuFunction;
	
	JMenu menuWindow;
	
	JMenuItem itemColorFunction;
	
	
	public MenuBar () {
		super();
		
		initialiseMenuFunction();
		initialiseMenuWindow();
		
		Actions.getInstance().actionViewModeList.addListener(this);
		Actions.getInstance().actionViewModeTable.addListener(this);
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
	
	
	
	@Override
	public void onChangedSelected(String mode) {
		switch (mode) {
			case ItemListMode.MODE:
				itemColorFunction.setEnabled(false);
				break;
			case ItemTableMode.MODE:
				itemColorFunction.setEnabled(true);
				break;
			default:
				break;
		}
	}
	
}
