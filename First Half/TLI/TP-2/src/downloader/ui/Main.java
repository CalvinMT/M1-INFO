package downloader.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import downloader.fc.DownloadWorker;

public class Main extends JFrame {
	private Main (String title, String[] urls) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		BorderLayout borderLayout = new BorderLayout();
		JPanel panelDownloads = new JPanel();
		StackLayout stackLayoutDownloads = new StackLayout();
		
		panelDownloads.setLayout(stackLayoutDownloads);
		setLayout(borderLayout);
		getContentPane().add(panelDownloads, BorderLayout.CENTER);
		
		List <DownloadWorker> threads = new ArrayList<>();
		
		for (String url : urls) {
			DownloadWorker downloadworker = new DownloadWorker(url);
			DownloadBox downloadBox = new DownloadBox();
			DownloadPropertyChangeListener downloadPropertyChangeListener = new DownloadPropertyChangeListener(downloadBox);
			downloadworker.addDownloaderPropertyChangeListener(downloadPropertyChangeListener);
			panelDownloads.add(downloadBox);
			threads.add(downloadworker);
		}
		
		pack();
		
		for (DownloadWorker thread : threads) {
			thread.execute();
		}
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
