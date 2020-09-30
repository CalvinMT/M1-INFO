package grapher.ui;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class FunctionPane extends JPanel implements ViewModeListener {
	
	JComponent components[];
	
	
	
	public FunctionPane (JComponent[] component) {
		super(new GridLayout(0, 1));
		components = component;
		add(components[0]);
	}
	
	
	
	@Override
	public void onChangedSelected (String mode) {
		removeAll();
		if (mode.equals(ItemListMode.MODE)) {
			add(components[0]);
		}
		else if (mode.equals(ItemTableMode.MODE)) {
			add(components[1]);
		}
		else {
			// TODO - throw exception
			System.out.println("ERROR: Mode " + mode + " was not found.");
		}
		revalidate();
	}
	
}
