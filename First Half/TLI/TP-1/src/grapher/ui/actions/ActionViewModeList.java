package grapher.ui.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

import grapher.ui.ViewModeListener;
import grapher.ui.menu.ItemListMode;

public class ActionViewModeList extends AbstractAction {
	
	private List <ViewModeListener> listeners = new ArrayList<>();
	
	
	
	public ActionViewModeList (String text) {
		super(text);
	}
	
	
	
	public void addListener (ViewModeListener listener) {
		listeners.add(listener);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (ViewModeListener listener : listeners) {
			listener.onChangedSelected(ItemListMode.MODE);
		}
	}
	
}