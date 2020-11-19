package downloader.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import downloader.fc.DownloadManager;
import downloader.fc.URLListModel;
import downloader.fc.actions.Actions;

public class Main extends JFrame {

	private static final int DEFAULT_WIDTH = 1080;
	private static final int DEFAULT_HEIGHT = 720;
	
	
	
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
		JScrollPane scrollPane = new JScrollPane();
		PanelDownloads panelDownloads = new PanelDownloads(downloadManager);
		StackLayout stackLayoutDownloads = new StackLayout();
		SplitPaneAddURL splitPaneAddURL = new SplitPaneAddURL(JSplitPane.HORIZONTAL_SPLIT);
		
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(4);
		scrollPane.setViewportView(panelDownloads);
		
		panelDownloads.setLayout(stackLayoutDownloads);
		
		setLayout(borderLayout);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(splitPaneAddURL, BorderLayout.SOUTH);
		
		URLListModel.getInstance().addListDataListener(panelDownloads);
		
		URLListModel.getInstance().addAllElements(urls);
		
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
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
