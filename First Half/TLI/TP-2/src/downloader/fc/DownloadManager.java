package downloader.fc;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import downloader.fc.actions.ActionChangeDownloadStateListener;
import downloader.ui.DownloadPropertyChangeListener;
import downloader.ui.DownloadStatePropertyChangeListener;

public class DownloadManager implements ActionChangeDownloadStateListener {
	
	private List <DownloadWorker> workers = new ArrayList<>();
	
	
	
	public int addWorker (String url) {
		DownloadWorker downloadworker = new DownloadWorker(url);
		workers.add(downloadworker);
		return workers.size() - 1;
	}
	
	
	
	public void startWorker (int index) {
		workers.get(index).setState(DownloadState.RUNNING);
		workers.get(index).execute();
	}
	
	public void pauseWorker (int index) {
		workers.get(index).setState(DownloadState.PAUSED);
	}
	
	public void resumeWorker (int index) {
		workers.get(index).setState(DownloadState.RUNNING);
	}
	
	
	
	public void startAllWorkers () {
		for (int i=0; i<workers.size(); i++) {
			startWorker(i);
		}
	}
	
	public void pauseAllWorkers () {
		for (int i=0; i<workers.size(); i++) {
			pauseWorker(i);
		}
	}
	
	public void resumeAllWorkers () {
		for (int i=0; i<workers.size(); i++) {
			resumeWorker(i);
		}
	}
	

	
	public void addDownloaderPropertyChangeListener (int workerIndex, DownloadPropertyChangeListener listener) {
		workers.get(workerIndex).addDownloaderPropertyChangeListener(listener);
	}
	
	public void addDownloadStatePropertyChangeListener (int workerIndex, DownloadStatePropertyChangeListener listener) {
		workers.get(workerIndex).addDownloadStatePropertyChangeListener(listener);
	}
	
	
	
	@Override
	public void onStateChange(int index) {
		DownloadState state = workers.get(index).getDownloadState();
		switch (state) {
			case RUNNING:
				pauseWorker(index);
				break;
			case PAUSED:
				resumeWorker(index);
				break;
			case CANCELLED:
				startWorker(index);
				break;
			case COMPLETED:
				break;
			default:
				break;
		}
	}
	
}
