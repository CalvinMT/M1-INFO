package grapher.ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FunctionTableMouseListener implements MouseListener {
	
	private enum States {IDLE,
						 PRESSED_RIGHT};
	
	private States state;
	
	private JTable table;
	
	private List <FunctionColorChooserListener> listeners = new ArrayList<>();
	
	
	
	public FunctionTableMouseListener (JTable t) {
		state = States.IDLE;
		table = t;
	}
	
	
	
	public void addListener (FunctionColorChooserListener l) {
		listeners.add(l);
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		return;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		return;
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		return;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (state == States.IDLE  &&  e.getButton() == 3) {
			state = States.PRESSED_RIGHT;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (state == States.PRESSED_RIGHT) {
			int row = table.rowAtPoint(e.getPoint());
			table.changeSelection(row, 0, false, false);
			Color newColor = JColorChooser.showDialog(null, "Choose a Color", (Color) table.getValueAt(table.getSelectedRow(), 1));
			if (newColor != null) {
				for (FunctionColorChooserListener listener : listeners) {
					listener.onColorChosen(table.getSelectedRow(), newColor);
				}
			}
		}
		state = States.IDLE;
	}
	
}
