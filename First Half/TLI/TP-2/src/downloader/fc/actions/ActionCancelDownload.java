package downloader.fc.actions;

import java.awt.event.ActionEvent;

public class ActionCancelDownload extends ActionDownload {
	
	public ActionCancelDownload (String text) {
		super(text);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (ActionDownloadListener listener : listeners) {
			listener.onCancelDownload(workerIndex);
		}
	}
	
}
