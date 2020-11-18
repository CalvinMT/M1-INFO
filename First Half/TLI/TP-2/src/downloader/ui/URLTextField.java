package downloader.ui;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import downloader.fc.URLListModel;
import downloader.fc.actions.ActionAddDownloadListener;
import downloader.fc.actions.Actions;

public class URLTextField extends JTextField implements ActionAddDownloadListener {
	
	private static final String DEFAULT_GHOST_TEXT = "https://...";
	
	private static Color DEFAULT_TEXT_COLOR;
	private static final Color DEFAULT_GHOST_TEXT_COLOR = new Color(0, 0, 0, 100);
	
	
	
	public URLTextField () {
		DEFAULT_TEXT_COLOR = getSelectedTextColor();
		
		addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if (getText().trim().equals("")) {
					setForeground(DEFAULT_GHOST_TEXT_COLOR);
					setText(DEFAULT_GHOST_TEXT);
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if (getText().trim().equals(DEFAULT_GHOST_TEXT)) {
					setForeground(DEFAULT_TEXT_COLOR);
					setText("");
				}
			}
			
		});
		
		addActionListener(Actions.getInstance().actionAddDownload);
	}
	
	
	
	private void addURL () {
		URLListModel.getInstance().addElement(getText());
		setText("");
	}
	
	
	
	@Override
	public void onAddURL() {
		addURL();
	}
	
}
