package grapher.ui.view;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import grapher.ui.view.menu.FunctionViewModes;

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
		switch (mode) {
			case LIST:
				setLeftComponent(components[0]);
				break;
			case TABLE:
				setLeftComponent(components[1]);
				break;
			default:
				throw new IllegalArgumentException("invalid viewMode : " + mode.toString());
		}
		repaint();
		revalidate();
	}
	
}
