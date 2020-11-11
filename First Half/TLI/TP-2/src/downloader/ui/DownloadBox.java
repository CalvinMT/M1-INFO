package downloader.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import downloader.fc.DownloadState;
import downloader.fc.URLListModel;
import downloader.fc.actions.ActionChangeDownloadStateListener;

public class DownloadBox extends JPanel implements ListDataListener {
	
	private int index;
	
	private DownloadState state;
	
	private JProgressBar progressBar;
	private JButton buttonChangeDownloadState;
	
	private List <ActionChangeDownloadStateListener> listeners = new ArrayList <ActionChangeDownloadStateListener> ();
	
	
	
	public DownloadBox (int boxIndex) {
		this.index = boxIndex;
		
		DownloadBoxMouseAdapter downloadBoxMouseAdapter = new DownloadBoxMouseAdapter(this);
		
		BorderLayout borderLayout = new BorderLayout();
		setLayout(borderLayout);
		
		JLabel url = new JLabel(URLListModel.getInstance().get(index), JLabel.LEFT);
		
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
		add(url, BorderLayout.NORTH);
		add(buttonChangeDownloadState, BorderLayout.EAST);
		
		addMouseListener(downloadBoxMouseAdapter);
		addMouseMotionListener(downloadBoxMouseAdapter);
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
		DownloadBoxContextMenu.getInstance().updateItemsEnableness(index, newState);
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
	
	
	
	@Override
	public void intervalAdded(ListDataEvent e) {
		int minIndex = Math.min(e.getIndex1(), e.getIndex0());
		int maxIndex = Math.max(e.getIndex1(), e.getIndex0()) + 1;
		for (int i=minIndex; i < maxIndex; i++) {
			if (i <= index) {
				index++;
			}
		}
	}
	
	@Override
	public void intervalRemoved(ListDataEvent e) {
		int minIndex = Math.min(e.getIndex1(), e.getIndex0());
		int maxIndex = Math.max(e.getIndex1(), e.getIndex0()) + 1;
		for (int i=minIndex; i < maxIndex; i++) {
			if (i <= index) {
				index--;
			}
		}
	}
	
	@Override
	public void contentsChanged(ListDataEvent e) {
		return;
	}
	
}
