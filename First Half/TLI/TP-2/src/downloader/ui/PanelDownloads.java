package downloader.ui;

import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import downloader.fc.DownloadManager;
import downloader.fc.URLListModel;
import downloader.fc.actions.ActionRestartDownloadListener;

public class PanelDownloads extends JPanel implements ListDataListener, ActionRestartDownloadListener {
	
	DownloadManager downloadManager;
	
	
	
	public PanelDownloads (DownloadManager dM) {
		downloadManager = dM;
		downloadManager.addActionRestartDownloadListener(this);
	}
	
	
	
	public void addDownload (String url) {
		addDownload(url, getComponentCount());
	}
	
	public void addDownload (String url, int index) {
		DownloadBox downloadBox = new DownloadBox(index);
		downloadBox.addActionChangeDownloadStateListener(downloadManager);
		add(downloadBox, index);
		downloadManager.addWorker(url, index);
		DownloadPropertyChangeListener downloadPropertyChangeListener = new DownloadPropertyChangeListener(downloadBox);
		DownloadStatePropertyChangeListener downloadStatePropertyChangeListener = new DownloadStatePropertyChangeListener(downloadBox);
		downloadManager.addDownloaderPropertyChangeListener(index, downloadPropertyChangeListener);
		downloadManager.addDownloadStatePropertyChangeListener(index, downloadStatePropertyChangeListener);
		downloadManager.startWorker(index);
		revalidate();
		repaint();
	}
	
	public void restartDownload (int index) {
		removeDownload(index);
		addDownload(URLListModel.getInstance().get(index), index);
	}
	
	public void removeDownload (int index) {
		remove(index);
		revalidate();
		repaint();
	}
	
	
	
	public void addAllDownloads (String urls[]) {
		for (String url : urls) {
			addDownload(url);
		}
	}
	
	public void removeDownloadsInterval (int index0, int index1) {
		int nbIndeces = Math.abs(index1 - index0) + 1;
		for (int i=0; i < nbIndeces; i++) {
			removeDownload(index0);
		}
	}
	
	public void clearDownloads () {
		removeAll();
		revalidate();
		repaint();
	}
	
	
	
	@Override
	public void intervalAdded(ListDataEvent e) {
		addDownload(URLListModel.getInstance().get(e.getIndex0()));
	}
	
	@Override
	public void intervalRemoved(ListDataEvent e) {
		removeDownloadsInterval(e.getIndex0(), e.getIndex1());
	}
	
	@Override
	public void contentsChanged(ListDataEvent e) {
		return;
	}
	
	
	
	@Override
	public void onRestartDownload(int index) {
		restartDownload(index);
	}
	
}
