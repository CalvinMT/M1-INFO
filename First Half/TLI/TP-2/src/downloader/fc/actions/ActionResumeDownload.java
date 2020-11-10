package downloader.fc.actions;

import java.awt.event.ActionEvent;

public class ActionResumeDownload extends ActionDownload {
	
	public ActionResumeDownload (String text) {
		super(text);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (ActionDownloadListener listener : listeners) {
			listener.onResumeDownload(workerIndex);
		}
	}
	
}
