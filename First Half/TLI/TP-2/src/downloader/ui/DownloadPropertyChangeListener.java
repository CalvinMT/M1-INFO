package downloader.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DownloadPropertyChangeListener implements PropertyChangeListener {
	
	private DownloadBox downloadBox;
	
	
	
	public DownloadPropertyChangeListener (DownloadBox dB) {
		downloadBox = dB;
	}
	
	
	
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals("progress")) {
			downloadBox.updateValue((int) e.getNewValue());
		}
	}
	
}
