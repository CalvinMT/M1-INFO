package downloader.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import downloader.fc.DownloadState;
import downloader.fc.actions.ActionChangeDownloadStateListener;

public class DownloadBox extends JPanel {
	
	private final int index;
	
	private DownloadState state;
	
	private JProgressBar progressBar;
	private JButton buttonChangeDownloadState;
	
	private List <ActionChangeDownloadStateListener> listeners = new ArrayList <ActionChangeDownloadStateListener> ();
	
	
	
	public DownloadBox (int boxIndex) {
		this.index = boxIndex;
		
		BorderLayout borderLayout = new BorderLayout();
		setLayout(borderLayout);
		
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		
		buttonChangeDownloadState = new JButton();
		buttonChangeDownloadState.setText("❙❙");
		buttonChangeDownloadState.addActionListener(e -> {
			for (ActionChangeDownloadStateListener listener : listeners) {
				listener.onStateChange(index);
			}
		});
		
		add(progressBar, BorderLayout.CENTER);
		add(buttonChangeDownloadState, BorderLayout.EAST);
	}
	

	
	public int getIndex () {
		return index;
	}
	
	public DownloadState getState () {
		return state;
	}
	
	
	
	/*
	 * Called by DownloadPropertyChangeListener
	 */
	protected void updateValue (int value) {
		progressBar.setValue(value);
	}

	/*
	 * Called by DownloadStatePropertyChangeListener
	 */
	protected void updateButtonState (DownloadState newState) {
		switch (newState) {
			case RUNNING:
				buttonChangeDownloadState.setEnabled(true);
				buttonChangeDownloadState.setVisible(true);
				buttonChangeDownloadState.setText("❙❙");
				break;
			case PAUSED:
				buttonChangeDownloadState.setEnabled(true);
				buttonChangeDownloadState.setVisible(true);
				buttonChangeDownloadState.setText("▶");
				break;
			case CANCELLED:
				buttonChangeDownloadState.setEnabled(true);
				buttonChangeDownloadState.setVisible(true);
				buttonChangeDownloadState.setText("⟲");
				break;
			case COMPLETED:
				buttonChangeDownloadState.setEnabled(false);
				buttonChangeDownloadState.setVisible(false);
				break;
			default:
				break;
		}
		state = newState;
	}
	
	
	
	public void addActionChangeDownloadStateListener (ActionChangeDownloadStateListener l) {
		listeners.add(l);
	}
	
}