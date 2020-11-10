package downloader.fc;

import javax.swing.DefaultListModel;

public class URLListModel extends DefaultListModel <String> {
	
	private static URLListModel instance;
	
	
	
	private URLListModel () {
		
	}
	
	public static URLListModel getInstance () {
		if (instance == null) {
			instance = new URLListModel();
		}
		return instance;
	}
	
	
	
	public void addAllElements (String elements[]) {
		for (String element : elements) {
			addElement(element);
		}
	}
	
}
