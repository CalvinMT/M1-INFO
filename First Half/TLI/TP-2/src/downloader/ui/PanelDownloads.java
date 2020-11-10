package downloader.ui;

import javax.swing.JPanel;

import downloader.fc.DownloadManager;
import downloader.fc.URLListModel;

public class PanelDownloads extends JPanel implements ListDataListener {
	
	DownloadManager downloadManager;
	
	
	
	public PanelDownloads (DownloadManager dM) {
		downloadManager = dM;
	}
	
	
	
	public void addDownload (String url) {
		DownloadBox downloadBox = new DownloadBox(getComponentCount());
		downloadBox.addActionChangeDownloadStateListener(downloadManager);
		add(downloadBox);
		int workerIndex = downloadManager.addWorker(url);
		DownloadPropertyChangeListener downloadPropertyChangeListener = new DownloadPropertyChangeListener(downloadBox);
		DownloadStatePropertyChangeListener downloadStatePropertyChangeListener = new DownloadStatePropertyChangeListener(downloadBox);
		downloadManager.addDownloaderPropertyChangeListener(workerIndex, downloadPropertyChangeListener);
		downloadManager.addDownloadStatePropertyChangeListener(workerIndex, downloadStatePropertyChangeListener);
		downloadManager.startWorker(workerIndex);
		revalidate();
		repaint();
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
	
}
