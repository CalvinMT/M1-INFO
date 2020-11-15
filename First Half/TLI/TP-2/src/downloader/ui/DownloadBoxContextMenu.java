package downloader.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import downloader.fc.DownloadState;
import downloader.fc.actions.Actions;

public class DownloadBoxContextMenu extends JPopupMenu {
	
	private static DownloadBoxContextMenu instance;
	
	private JMenuItem itemResume;
	private JMenuItem itemPause;
	private JMenuItem itemCancel;
	private JMenuItem itemRemove;
	private JMenuItem itemClearAll;
	
	private int focusedIndex = 0;
	
	
	
	private DownloadBoxContextMenu () {
		itemResume = new JMenuItem(Actions.getInstance().actionResumeDownload);
		itemPause = new JMenuItem(Actions.getInstance().actionPauseDownload);
		itemCancel = new JMenuItem(Actions.getInstance().actionCancelDownload);
		itemRemove = new JMenuItem(Actions.getInstance().actionRemoveDownload);
		itemClearAll = new JMenuItem(clearAllDialog());
		
		itemClearAll.setText("Clear all...");

		add(itemResume);
		add(itemPause);
		addSeparator();
		add(itemCancel);
		add(itemRemove);
		addSeparator();
		add(itemClearAll);
	}
	
	public static DownloadBoxContextMenu getInstance () {
		if (instance == null) {
			instance = new DownloadBoxContextMenu();
		}
		return instance;
	}
	
	
	
	private AbstractAction clearAllDialog () {
		return new AbstractAction () {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirmation = JOptionPane.showConfirmDialog(
						null,
						"This action will clear all downloads, cancelling those running.\nClear all downloads?",
						"Clear all downloads",
						JOptionPane.YES_OPTION,
						JOptionPane.WARNING_MESSAGE,
						null);
				if (confirmation == JOptionPane.YES_OPTION) {
					Actions.getInstance().actionClearAllDownloads.actionPerformed(e);
				}
			}
		};
	}
	
	
	
	public void setDownloadBox (int index) {
		Actions.getInstance().actionResumeDownload.setWorkerIndex(index);
		Actions.getInstance().actionPauseDownload.setWorkerIndex(index);
		Actions.getInstance().actionCancelDownload.setWorkerIndex(index);
		Actions.getInstance().actionRemoveDownload.setWorkerIndex(index);
		Actions.getInstance().actionClearAllDownloads.setWorkerIndex(index);
		focusedIndex = index;
	}
	

	
	public void updateItemsEnableness (int index, DownloadState state) {
		if (index == focusedIndex) {
			switch (state) {
				case RUNNING:
					itemResume.setEnabled(false);
					itemPause.setEnabled(true);
					itemCancel.setEnabled(true);
					itemRemove.setEnabled(false);
					break;
				case PAUSED:
					itemResume.setEnabled(true);
					itemPause.setEnabled(false);
					itemCancel.setEnabled(true);
					itemRemove.setEnabled(false);
					break;
				case CANCELLED:
					itemResume.setEnabled(false);
					itemPause.setEnabled(false);
					itemCancel.setEnabled(false);
					itemRemove.setEnabled(true);
					break;
				case COMPLETED:
					itemResume.setEnabled(false);
					itemPause.setEnabled(false);
					itemCancel.setEnabled(false);
					itemRemove.setEnabled(true);
					break;
				default:
					break;
			}
		}
	}
	
}
