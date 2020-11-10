package downloader.fc.actions;

import java.awt.Component;

public class Actions {
	
	private static Actions instance;

	public final ActionAddDownload actionAddDownload;
	public final ActionResumeDownload actionResumeDownload;
	public final ActionPauseDownload actionPauseDownload;
	public final ActionCancelDownload actionCancelDownload;
	public final ActionRemoveDownload actionRemoveDownload;
	public final ActionClearAllDownloads actionClearAllDownloads;
	
	
	
	private Actions (Component parent) {
		actionAddDownload = new ActionAddDownload("Add");
		actionResumeDownload = new ActionResumeDownload("Resume");
		actionPauseDownload = new ActionPauseDownload("Pause");
		actionCancelDownload = new ActionCancelDownload("Cancel");
		actionRemoveDownload = new ActionRemoveDownload("Remove");
		actionClearAllDownloads = new ActionClearAllDownloads("Clear all...");
	}
	
	
	
	public static Actions initialise (Component parent) {
		if (instance == null) {
			instance = new Actions(parent);
		}
		return instance;
	}
	
	public static Actions getInstance () {
		return instance;
	}
	
}
