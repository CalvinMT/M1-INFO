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
		downloadBox.updateValue((int) e.getNewValue());
	}
	
}
