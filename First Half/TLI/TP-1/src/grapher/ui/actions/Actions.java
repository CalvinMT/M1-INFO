package grapher.ui.actions;

import java.awt.Component;

import javax.swing.JTable;

public class Actions {
	
	private static Actions instance;
	
	public final ActionAddFunction actionAddFunction;
	public final ActionEditFunction actionEditFunction;
	public final ActionRemoveFunction actionRemoveFunction;
	
	public final ActionViewModeList actionViewModeList;
	public final ActionViewModeTable actionViewModeTable;
	
	
	
	private Actions (JTable table, Component parent) {
		actionAddFunction = new ActionAddFunction(table, parent, "Add...");
		actionEditFunction = new ActionEditFunction(table, parent, "Edit...");
		actionRemoveFunction = new ActionRemoveFunction(table, parent, "Remove...");
		
		actionViewModeList = new ActionViewModeList("List");
		actionViewModeTable = new ActionViewModeTable("Table");
	}
	
	public static Actions initialise (JTable table, Component parent) {
		if (instance == null) {
			instance = new Actions(table, parent);
		}
		return instance;
	}
	
	public static Actions getInstance () {
		return instance;
	}
	
}
