package downloader.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import downloader.fc.DownloadManager;
import downloader.fc.URLListModel;
import downloader.fc.actions.Actions;

public class Main extends JFrame {
	
	private Main (String title, String[] urls) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Actions.initialise(this);
		
		DownloadManager downloadManager = new DownloadManager();
		
		Actions.getInstance().actionResumeDownload.addListener(downloadManager);
		Actions.getInstance().actionPauseDownload.addListener(downloadManager);
		Actions.getInstance().actionCancelDownload.addListener(downloadManager);
		Actions.getInstance().actionRemoveDownload.addListener(downloadManager);
		Actions.getInstance().actionClearAllDownloads.addListener(downloadManager);
		
		BorderLayout borderLayout = new BorderLayout();
		PanelDownloads panelDownloads = new PanelDownloads(downloadManager);
		StackLayout stackLayoutDownloads = new StackLayout();
		SplitPaneAddURL splitPaneAddURL = new SplitPaneAddURL(JSplitPane.HORIZONTAL_SPLIT);
		
		panelDownloads.setLayout(stackLayoutDownloads);
		
		setLayout(borderLayout);
		getContentPane().add(panelDownloads, BorderLayout.CENTER);
		getContentPane().add(splitPaneAddURL, BorderLayout.SOUTH);
		
		URLListModel.getInstance().addListDataListener(panelDownloads);
		
		URLListModel.getInstance().addAllElements(urls);
		
		pack();
	}
	
	public static void main (String[] argv) {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		
		final String[] urls = argv;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				new Main("downloader", urls).setVisible(true); 
			}
		});
	}
	
}
