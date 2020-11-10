package downloader.fc.actions;

import java.awt.event.ActionEvent;

public class ActionClearAllDownloads extends ActionDownload {
	
	public ActionClearAllDownloads (String text) {
		super(text);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (ActionDownloadListener listener : listeners) {
			listener.onClearAllDownloads();
		}
	}
	
}
