package grapher.ui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import grapher.ui.ViewModeListener;
import grapher.ui.actions.ActionAddFunction;
import grapher.ui.actions.ActionRemoveFunction;
import grapher.ui.actions.ActionViewModeList;
import grapher.ui.actions.ActionViewModeTable;
import grapher.ui.tool.ToolListener;

public class MenuBar extends JMenuBar {

	JMenu menuFunction;
	ActionAddFunction actionAddFunction;
	ActionRemoveFunction actionRemoveFunction;
	
	JMenu menuWindow;
	ActionViewModeList actionViewModeList;
	ActionViewModeTable actionViewModeTable;
	
	
	public MenuBar () {
		super();
		
		initialiseMenuFunction();
		initialiseMenuWindow();
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
	
	
	
	private void initialiseMenuWindow () {
		menuWindow = new JMenu("Window");
		
		JMenu submenuViewMode = new JMenu("View mode");

		actionViewModeList = new ActionViewModeList("List");
		actionViewModeTable = new ActionViewModeTable("Table");
		
		ItemListMode itemListMode = new ItemListMode(actionViewModeList);
		ItemTableMode itemTableMode = new ItemTableMode(actionViewModeTable);
		
		itemListMode.setSelected(true);

		actionViewModeList.addListener(itemListMode);
		actionViewModeList.addListener(itemTableMode);
		actionViewModeTable.addListener(itemListMode);
		actionViewModeTable.addListener(itemTableMode);
		
		submenuViewMode.add(itemListMode);
		submenuViewMode.add(itemTableMode);
		
		menuWindow.add(submenuViewMode);
		
		add(menuWindow);
	}
	
	
	
	public void addToolListener (ToolListener listener) {
		actionAddFunction.addListener(listener);
		actionRemoveFunction.addListener(listener);
	}
	
	public void addViewModeListener (ViewModeListener listener) {
		actionViewModeList.addListener(listener);
		actionViewModeTable.addListener(listener);
	}
	
	
	
	public ActionRemoveFunction getActionRemoveFunction () {
		return actionRemoveFunction;
	}
	
}
