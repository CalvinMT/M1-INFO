package grapher.ui.view;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import grapher.ui.menu.FunctionViewModes;

public class SplitPaneFunctionTool extends JSplitPane implements ViewModeListener {
	
	JComponent components[];
	
	public SplitPaneFunctionTool (int newOrientation) {
		super(newOrientation);
		
		setResizeWeight(0.9);
		setDividerSize(0);
	}
	
	
	
	public void addFunctionComponents (JComponent[] c) {
		components = c;
		add(components[0]);
		setLeftComponent(components[0]);
	}
	
	
	
	@Override
	public void onChangedSelected (FunctionViewModes mode) {
		if (mode.equals(FunctionViewModes.LIST)) {
			setLeftComponent(components[0]);
		}
		else if (mode.equals(FunctionViewModes.TABLE)) {
			setLeftComponent(components[1]);
		}
		else {
			// TODO - throw exception
			System.out.println("ERROR: Mode " + mode + " was not found.");
		}
		repaint();
		revalidate();
	}
	
}
