package grapher.ui.view.menu;

import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import grapher.ui.actions.Actions;
import grapher.ui.view.ViewModeListener;
import grapher.ui.view.table.FunctionTableListener;

public class MenuBar extends JMenuBar implements FunctionTableListener, ViewModeListener {

	private JMenu menuFunction;
	
	private JMenu menuWindow;
	
	private JMenuItem itemColorFunction;
	
	private FunctionViewModes functionViewModes;
	private boolean isUniqueFunctionSelected = false;
	
	
	
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

		Actions.getInstance().actionViewModeList.addListener(this);
		Actions.getInstance().actionViewModeTable.addListener(this);
		
		itemListMode.setSelected(true);
		
		groupFunctionViewMode.add(itemListMode);
		groupFunctionViewMode.add(itemTableMode);
		
		submenuViewMode.add(itemListMode);
		submenuViewMode.add(itemTableMode);
		
		menuWindow.add(submenuViewMode);
		
		add(menuWindow);
	}
	
	
	
	private void changeMenuItemsStates () {
		switch (functionViewModes) {
			case LIST:
				itemColorFunction.setEnabled(false);
				break;
			case TABLE:
				if (isUniqueFunctionSelected) {
					itemColorFunction.setEnabled(true);
				}
				else {
					itemColorFunction.setEnabled(false);
				}
				break;
			default:
				itemColorFunction.setEnabled(false);
				break;
		}
	}
	
	
	
	@Override
	public void onChangedSelected(FunctionViewModes mode) {
		functionViewModes = mode;
		changeMenuItemsStates();
	}



	@Override
	public void onFunctionAdd(String function) {
		return;
	}
	
	@Override
	public void onFunctionRemove(int[] indices) {
		return;
	}
	
	@Override
	public void onFunctionEdit(int index, String function) {
		return;
	}
	
	@Override
	public void onFunctionSelection(int[] indices, String function, Color color) {
		if (indices.length == 1) {
			isUniqueFunctionSelected = true;
		}
		else {
			isUniqueFunctionSelected = false;
		}
		changeMenuItemsStates();
	}
	
}
