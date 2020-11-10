package downloader.fc;

import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.locks.ReentrantLock;
import java.net.MalformedURLException;

import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;


public class Downloader {
	
	public static final int CHUNK_SIZE = 1024;
	
	URL url;
	long content_length;
	BufferedInputStream in;
	
	String filename;
	File rootFolder;
	File temp;
	FileOutputStream out;
	
	private int _progress;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	private ReentrantLock lock = new ReentrantLock();
	
	
	
	public Downloader(String uri) {
		try {
			url = new URL(uri);
			
			URLConnection connection = url.openConnection();
			content_length = connection.getContentLength();
			
			in = new BufferedInputStream(connection.getInputStream());
			
			rootFolder = new File("downloads");
			rootFolder.mkdir();
			
			String[] path = url.getFile().split("/");
			filename = path[path.length-1];
			temp = File.createTempFile(filename, ".part");
			out = new FileOutputStream(temp);
		}
		catch(MalformedURLException e) { throw new RuntimeException(e); }
		catch(IOException e) { throw new RuntimeException(e); }
	}
	
	
	
	public String download() throws InterruptedException {
		byte buffer[] = new byte[CHUNK_SIZE];
		long size = 0;
		long count = 0;
		
		while (true) {
			lock.lock();
			try {
				try { count = Long.valueOf(in.read(buffer, 0, CHUNK_SIZE)); }
				catch(IOException e) { continue; }
	
				if(count < 0L) {
					break;
				}
				
				try { out.write(buffer); }
				catch(IOException e) { continue; }
				
				size += count;
				setProgress((int) (100L*size/content_length));
			}
			finally {
				lock.unlock();
			}
		}
		
		if(size < content_length) {
			temp.delete();
			throw new InterruptedException();
		}
		
		temp.renameTo(new File(rootFolder, filename));
		return filename;
	}
	
	public ReentrantLock getLock () {
		return lock;
	}
	
	
	
	public void setProgress(int progress) {
		int old_progress = _progress;
		_progress = progress;
		pcs.firePropertyChange("progress", old_progress, progress);
	}
	
	
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
	
	public PropertyChangeListener[] getAllPropertyChangeListeners() {
		return pcs.getPropertyChangeListeners();
	}
	
	
	
	@Override
	public String toString() {
		return url.toString();
	}
	
}
