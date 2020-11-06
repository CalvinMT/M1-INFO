package downloader.fc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingWorker;

public class DownloadWorker extends SwingWorker <Void, Void> {
	
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
	
	
	
	public void runDownloader () {
		String filename = "";
		try {
			filename = downloader.download();
		}
		catch(InterruptedException e) {
			System.err.println("failed!");
		}
		System.out.format("into %s\n", filename);
	}



	@Override
	protected Void doInBackground () throws Exception {
		runDownloader();
		return null;
	}
	
	@Override
	protected void done () {
		// TODO
	}
	
}
