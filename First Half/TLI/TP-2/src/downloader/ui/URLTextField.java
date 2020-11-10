package downloader.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import downloader.fc.URLListModel;
import downloader.fc.actions.ActionAddDownloadListener;

public class URLTextField extends JTextField implements ActionAddDownloadListener {
	
	public URLTextField () {
		super("https://...");
		
		// TODO - in focus, setText("");
		// TODO - out of focus, setText("https://...");
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER  &&  isFocusOwner()) {
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
