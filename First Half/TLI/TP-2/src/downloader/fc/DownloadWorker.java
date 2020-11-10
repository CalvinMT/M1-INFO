package downloader.fc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.SwingWorker;

public class DownloadWorker extends SwingWorker <Void, Void> {
	
	private DownloadState state = DownloadState.PAUSED;
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	private Downloader downloader;
	
	private String url;
	
	
	
	public DownloadWorker (String url) {
		this.url = url;
		
		initialiseDownloader();
	}
	
	
	
	private void initialiseDownloader () {
		downloader = null;
		try {
			downloader = new Downloader(url);
		}
		catch(RuntimeException e) {
			System.err.format("skipping %s %s\n", url, e);
		}
		System.out.format("Downloading %s:", downloader);
		
		downloader.addPropertyChangeListener(new  PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				System.out.print(".");
				System.out.flush();
			}
		});
	}
	

	
	public void addDownloaderPropertyChangeListener(PropertyChangeListener listener) {
		downloader.addPropertyChangeListener(listener);
	}
	
	public void addDownloadStatePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void addAllDownloaderPropertyChangeListeners(PropertyChangeListener listeners[]) {
		for (PropertyChangeListener listener : listeners) {
			addDownloaderPropertyChangeListener(listener);
		}
	}
	
	public void addAllDownloadStatePropertyChangeListeners(PropertyChangeListener listeners[]) {
		for (PropertyChangeListener listener : listeners) {
			addDownloadStatePropertyChangeListener(listener);
		}
	}
	
	
	
	private void runDownloader () {
		String filename = "";
		try {
			filename = downloader.download();
		}
		catch(InterruptedException e) {
			System.err.println("failed!");
		}
		System.out.format("into %s\n", filename);
	}
	
	
	
	public void setState (DownloadState newState) {
		DownloadState oldState = state;
		state = newState;
		propertyChangeSupport.firePropertyChange("download_state", oldState, state);
	}
	
	
	
	public DownloadState getDownloadState () {
		return state;
	}
	
	public ReentrantLock getLock () {
		return downloader.getLock();
	}
	
	public String getUrl () {
		return url;
	}
	
	public Downloader getDownloader () {
		return downloader;
	}
	
	public PropertyChangeListener[] getAllDownloaderPropertyChangeListeners () {
		return downloader.getAllPropertyChangeListeners();
	}
	
	public PropertyChangeListener[] getAllDownloadStatePropertyChangeListeners () {
		return propertyChangeSupport.getPropertyChangeListeners();
	}
	
	
	
	@Override
	protected Void doInBackground () throws Exception {
		runDownloader();
		return null;
	}
	
	@Override
	protected void done () {
		setState(DownloadState.COMPLETED);
	}
	
}
