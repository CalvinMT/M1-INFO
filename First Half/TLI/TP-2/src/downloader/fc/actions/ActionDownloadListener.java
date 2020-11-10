package downloader.fc.actions;

public interface ActionDownloadListener {

	public void onResumeDownload(int workerIndex);
	
	public void onPauseDownload(int workerIndex);
	
	public void onCancelDownload(int workerIndex);
	
	public void onRemoveDownload(int workerIndex);
	
	public void onClearAllDownloads();
	
}
