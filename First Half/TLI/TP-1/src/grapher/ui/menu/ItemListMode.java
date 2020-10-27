package grapher.ui.menu;

import javax.swing.AbstractAction;
import javax.swing.JRadioButtonMenuItem;

import grapher.ui.view.ViewModeListener;

public class ItemListMode extends JRadioButtonMenuItem implements ViewModeListener {
	
	public static final String MODE = "LIST";
	
	
	
	public ItemListMode (AbstractAction a) {
		super(a);
	}
	
	
	
	@Override
	public void onChangedSelected(String mode) {
		if (! mode.equals(MODE)) {
			setSelected(false);
		}
		else {
			setSelected(true);
		}
	}
	
}
