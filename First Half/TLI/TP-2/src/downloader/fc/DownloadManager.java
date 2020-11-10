package downloader.fc;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import downloader.ui.DownloadPropertyChangeListener;

public class DownloadManager {
	
	private List <DownloadWorker> workers = new ArrayList<>();
	
	
	
	public int addWorker (String url) {
		DownloadWorker downloadworker = new DownloadWorker(url);
		workers.add(downloadworker);
		return workers.size() - 1;
	}
	
	
	
	public void startWorker (int index) {
		workers.get(index).execute();
	}
	

	
	public void addDownloaderPropertyChangeListener (int workerIndex, DownloadPropertyChangeListener listener) {
		workers.get(workerIndex).addDownloaderPropertyChangeListener(listener);
	}
	
}
