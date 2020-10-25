package grapher.ui;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.event.MouseInputAdapter;

import grapher.ui.Grapher.States;

public class GrapherMouseInputAdapter extends MouseInputAdapter {
	
	private static final double START_DRAG_BOUNDARY = 5;
	private static final Cursor CURSOR_DEFAULT = new Cursor(Cursor.DEFAULT_CURSOR);
	private static final Cursor CURSOR_HAND = new Cursor(Cursor.MOVE_CURSOR);
	
	Grapher grapher;
	
	private Point mousePosition;
	private Point rightClickFrameBegin;
	private Point rightClickFrameEnd;
	
	
	
	public GrapherMouseInputAdapter (Grapher g) {
		grapher = g;
		grapher.setState(States.IDLE);
		mousePosition = new Point(0, 0);
		rightClickFrameBegin = new Point(0, 0);
		rightClickFrameEnd = new Point(0, 0);
	}
	
	
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mousePosition.x = e.getX();
		mousePosition.y = e.getY();
	}
    
	@Override
    public void mouseDragged(MouseEvent e){
		if (grapher.getState() == States.PRESSED_LEFT) {
			int dx = Math.abs(e.getX() - mousePosition.x);
			int dy = Math.abs(e.getY() - mousePosition.y);
			if (dx >= START_DRAG_BOUNDARY  ||  dy >= START_DRAG_BOUNDARY) {
				grapher.setState(States.DRAGGED_LEFT);
			}
		}
		if (grapher.getState() == States.DRAGGED_LEFT) {
			grapher.setState(States.DRAGGED_LEFT);
			grapher.setCursor(CURSOR_HAND);
			int dx = e.getX() - mousePosition.x;
			int dy = e.getY() - mousePosition.y;
			grapher.translate(dx, dy);
			mousePosition.x = e.getX();
			mousePosition.y = e.getY();
		}
		else if (grapher.getState() == States.PRESSED_RIGHT  ||  grapher.getState() == States.DRAGGED_RIGHT) {
			grapher.setState(States.DRAGGED_RIGHT);
			rightClickFrameEnd.x = e.getX();
			rightClickFrameEnd.y = e.getY();
			grapher.repaint();
		}
	};
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			grapher.setState(States.PRESSED_LEFT);
		}
		else if (e.getButton() == MouseEvent.BUTTON3) {
			grapher.setState(States.PRESSED_RIGHT);
			rightClickFrameBegin = mousePosition;
		}
    };
	
	@Override
    public void mouseReleased(MouseEvent e) {
		if (grapher.getState() == States.PRESSED_LEFT) {
			grapher.zoom(mousePosition, 5);
		}
		else if (grapher.getState() == States.PRESSED_RIGHT) {
			grapher.zoom(mousePosition, -5);
		}
		else if (grapher.getState() == States.DRAGGED_LEFT) {
			grapher.setCursor(CURSOR_DEFAULT);
		}
		else if (grapher.getState() == States.DRAGGED_RIGHT) {
			grapher.zoom(rightClickFrameBegin, rightClickFrameEnd);
		}
		grapher.setState(States.IDLE);
    };
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
		if (grapher.getState() == States.IDLE) {
			if (e.getWheelRotation() < 0) {
				grapher.zoom(mousePosition, 5);
			}
			else {
				grapher.zoom(mousePosition, -5);
			}
		}
    }
    
    
    
    public Point getMousePosition () {
    	return mousePosition;
    }
    
    public Point getRightClickFrameBegin () {
    	return rightClickFrameBegin;
    }
    
    public Point getRightClickFrameEnd () {
    	return rightClickFrameEnd;
    }
	
}
