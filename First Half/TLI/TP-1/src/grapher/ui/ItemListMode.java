package grapher.ui;

import javax.swing.AbstractAction;
import javax.swing.JRadioButtonMenuItem;

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
