package grapher.ui;

import javax.swing.AbstractAction;
import javax.swing.JRadioButtonMenuItem;

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
