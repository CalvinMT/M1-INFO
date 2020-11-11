package downloader.ui;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import downloader.fc.URLListModel;
import downloader.fc.actions.ActionAddDownloadListener;

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
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
					addURL();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				return;
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				return;
			}
			
		});
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
