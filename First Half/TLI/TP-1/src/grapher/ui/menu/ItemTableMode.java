package grapher.ui.menu;

import javax.swing.AbstractAction;
import javax.swing.JRadioButtonMenuItem;

import grapher.ui.ViewModeListener;

public class ItemTableMode extends JRadioButtonMenuItem implements ViewModeListener {
	
	public static final String MODE = "TABLE";
	
	
	
	public ItemTableMode (AbstractAction a) {
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
