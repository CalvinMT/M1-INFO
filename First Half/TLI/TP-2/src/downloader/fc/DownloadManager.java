package downloader.fc;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import downloader.fc.actions.ActionChangeDownloadStateListener;
import downloader.fc.actions.ActionDownloadListener;
import downloader.ui.DownloadPropertyChangeListener;
import downloader.ui.DownloadStatePropertyChangeListener;

public class DownloadManager implements ActionChangeDownloadStateListener, ActionDownloadListener {
	
	private List <DownloadWorker> workers = new ArrayList<>();
	
	
	
	public int addWorker (String url) {
		DownloadWorker downloadworker = new DownloadWorker(url);
		workers.add(downloadworker);
		return workers.size() - 1;
	}
	
	
	
	public void startWorker (int index) {
		workers.get(index).setState(DownloadState.RUNNING);
		if (workers.get(index).isDone()) {
			String url = workers.get(index).getUrl();
			PropertyChangeListener dPCL[] = workers.get(index).getAllDownloaderPropertyChangeListeners();
			PropertyChangeListener dSPCL[] = workers.get(index).getAllDownloadStatePropertyChangeListeners();
			workers.set(index, new DownloadWorker(url));
			workers.get(index).addAllDownloaderPropertyChangeListeners(dPCL);
			workers.get(index).addAllDownloadStatePropertyChangeListeners(dSPCL);
		}
		workers.get(index).execute();
	}
	
	public void pauseWorker (int index) {
		workers.get(index).setState(DownloadState.PAUSED);
		workers.get(index).getLock().lock();
	}
	
	public void resumeWorker (int index) {
		workers.get(index).setState(DownloadState.RUNNING);
		workers.get(index).getLock().unlock();
	}
	
	public void cancelWorker (int index) {
		workers.get(index).setState(DownloadState.CANCELLED);
		workers.get(index).cancel(true);
	}
	
	public void removeWorker (int index) {
		cancelWorker(index);
		workers.remove(index);
		URLListModel.getInstance().remove(index);
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
	
	public void cancelAllWorkers () {
		for (int i=0; i<workers.size(); i++) {
			cancelWorker(i);
		}
	}
	
	public void removeAllWorkers () {
		cancelAllWorkers();
		workers.clear();
		URLListModel.getInstance().removeAllElements();
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
	
	
	
	@Override
	public void onResumeDownload(int workerIndex) {
		resumeWorker(workerIndex);
	}
	
	@Override
	public void onPauseDownload(int workerIndex) {
		pauseWorker(workerIndex);
	}
	
	@Override
	public void onCancelDownload(int workerIndex) {
		cancelWorker(workerIndex);
	}
	
	@Override
	public void onRemoveDownload(int workerIndex) {
		removeWorker(workerIndex);
	}
	
	@Override
	public void onClearAllDownloads() {
		removeAllWorkers();
	}
	
}
