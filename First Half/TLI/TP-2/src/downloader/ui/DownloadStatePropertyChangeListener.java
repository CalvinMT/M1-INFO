package downloader.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import downloader.fc.DownloadState;

public class DownloadStatePropertyChangeListener implements PropertyChangeListener {
	
	private DownloadBox downloadBox;
	
	
	
	public DownloadStatePropertyChangeListener (DownloadBox dB) {
		downloadBox = dB;
	}
	
	
	
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals("download_state")) {
			downloadBox.updateButtonState(DownloadState.valueOf((String) e.getNewValue().toString()));
		}
	}
	
}
