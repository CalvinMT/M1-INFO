package grapher.ui.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

import grapher.ui.view.ViewModeListener;
import grapher.ui.view.menu.FunctionViewModes;

public class ActionViewModeTable extends AbstractAction {
	
	private List <ViewModeListener> listeners = new ArrayList<>();
	
	
	
	public ActionViewModeTable (String text) {
		super(text);
	}
	
	
	
	public void addListener (ViewModeListener listener) {
		listeners.add(listener);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (ViewModeListener listener : listeners) {
			listener.onChangedSelected(FunctionViewModes.TABLE);
		}
	}
	
}
