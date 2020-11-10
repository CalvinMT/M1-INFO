package downloader.fc.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

public class ActionAddDownload extends AbstractAction {
	
	private List <ActionAddDownloadListener> listeners = new ArrayList <ActionAddDownloadListener> ();
	
	
	
	public ActionAddDownload (String text) {
		super(text);
	}
	
	
	
	public void addListener (ActionAddDownloadListener l) {
		listeners.add(l);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (ActionAddDownloadListener listener : listeners) {
			listener.onAddURL();
		}
	}
	
}
