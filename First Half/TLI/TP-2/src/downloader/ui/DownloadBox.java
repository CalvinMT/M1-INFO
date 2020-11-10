package downloader.ui;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class DownloadBox extends JPanel {
	
	private final int index;
	
	private JProgressBar progressBar;
	
	
	
	public DownloadBox (int boxIndex) {
		this.index = boxIndex;
		
		progressBar = new JProgressBar(0, 100);
		progressBar.setBounds(0, 0, 160, 30);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		
		add(progressBar);
	}
	

	
	public int getIndex () {
		return index;
	}
	
	
	
	/*
	 * Called by DownloadPropertyChangeListener
	 */
	protected void updateValue (int value) {
		progressBar.setValue(value);
	}
	
}
