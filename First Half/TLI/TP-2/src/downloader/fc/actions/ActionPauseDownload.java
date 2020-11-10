package downloader.fc.actions;

import java.awt.event.ActionEvent;

public class ActionPauseDownload extends ActionDownload {
	
	public ActionPauseDownload (String text) {
		super(text);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (ActionDownloadListener listener : listeners) {
			listener.onPauseDownload(workerIndex);
		}
	}
	
}
