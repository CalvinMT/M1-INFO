package downloader.fc.actions;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

public abstract class ActionDownload extends AbstractAction {
	
	protected List <ActionDownloadListener> listeners = new ArrayList <ActionDownloadListener> ();
	
	protected int workerIndex = -1;
	
	
	
	public ActionDownload (String text) {
		super(text);
	}
	
	
	
	public void setWorkerIndex (int index) {
		workerIndex = index;
	}
	
	
	
	public void addListener (ActionDownloadListener l) {
		listeners.add(l);
	}
	
}
