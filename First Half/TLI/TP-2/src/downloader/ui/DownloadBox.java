package downloader.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class DownloadBox extends JPanel {
	
	private final int index;
	
	private JProgressBar progressBar;
	private JButton buttonChangeDownloadState;
	
	
	
	public DownloadBox (int boxIndex) {
		this.index = boxIndex;
		
		BorderLayout borderLayout = new BorderLayout();
		setLayout(borderLayout);
		
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		
		buttonChangeDownloadState = new JButton();
		buttonChangeDownloadState.setText("❙❙");
		
		add(progressBar, BorderLayout.CENTER);
		add(buttonChangeDownloadState, BorderLayout.EAST);
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
