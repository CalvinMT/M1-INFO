package downloader.ui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JSplitPane;

import downloader.fc.actions.Actions;

public class SplitPaneAddURL extends JSplitPane {
	
	private static final int MAX_BUTTON_HEIGHT = 25;
	private static final int MAX_BUTTON_WIDTH = 50;
	
	
	
	public SplitPaneAddURL (int orientation) {
		super(orientation);
		
		URLTextField urlTextField = new URLTextField();
		JButton buttonAddURL = new JButton(Actions.getInstance().actionAddDownload);
		
		buttonAddURL.setMaximumSize(new Dimension(MAX_BUTTON_WIDTH, MAX_BUTTON_HEIGHT));
		buttonAddURL.setMinimumSize(new Dimension(MAX_BUTTON_WIDTH, MAX_BUTTON_HEIGHT));
		
		Actions.getInstance().actionAddDownload.addListener(urlTextField);
		
		add(urlTextField);
		add(buttonAddURL);
		
		setDividerSize(0);
		setResizeWeight(1.0);
	}
	
}
