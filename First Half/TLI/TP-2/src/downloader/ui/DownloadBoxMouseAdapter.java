package downloader.ui;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DownloadBoxMouseAdapter extends MouseAdapter {
	
	private static final double CLICK_MARGIN_OF_ERROR = 5;
	
	private enum MouseState {
		IDLE,	
		PRESSED_RIGHT
	}
	
	private MouseState mouseState = MouseState.IDLE;
	
	private DownloadBox downloadBox;

	private Point mousePosition;
	
	
	
	public DownloadBoxMouseAdapter (DownloadBox dB) {
		downloadBox = dB;
		mousePosition = new Point(0, 0);
	}
	
	
	
	@Override
	public void mouseMoved (MouseEvent e) {
		mousePosition.x = e.getX();
		mousePosition.y = e.getY();
	}
	
	@Override
	public void mouseDragged (MouseEvent e) {
		if (mouseState == MouseState.PRESSED_RIGHT) {
			int dx = Math.abs(e.getX() - mousePosition.x);
			int dy = Math.abs(e.getY() - mousePosition.y);
			if (dx >= CLICK_MARGIN_OF_ERROR  ||  dy >= CLICK_MARGIN_OF_ERROR) {
				mouseState = MouseState.IDLE;
			}
		}
	}
	
	@Override
	public void mousePressed (MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			mouseState = MouseState.PRESSED_RIGHT;
		}
	}
	
	@Override
	public void mouseReleased (MouseEvent e) {
		if (mouseState == MouseState.PRESSED_RIGHT) {
			DownloadBoxContextMenu.getInstance().setDownloadBox(downloadBox.getIndex());
			DownloadBoxContextMenu.getInstance().updateItemsEnableness(downloadBox.getIndex(), downloadBox.getState());
			DownloadBoxContextMenu.getInstance().show(e.getComponent(), mousePosition.x, mousePosition.y);
		}
		mouseState = MouseState.IDLE;
	}
	
}
