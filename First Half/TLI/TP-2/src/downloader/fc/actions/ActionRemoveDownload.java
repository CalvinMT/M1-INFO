package downloader.fc.actions;

import java.awt.event.ActionEvent;

public class ActionRemoveDownload extends ActionDownload {
	
	public ActionRemoveDownload (String text) {
		super(text);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (ActionDownloadListener listener : listeners) {
			listener.onRemoveDownload(workerIndex);
		}
	}
	
}
