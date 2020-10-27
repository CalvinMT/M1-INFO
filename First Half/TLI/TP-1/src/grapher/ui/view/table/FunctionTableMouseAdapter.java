package grapher.ui.view.table;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JTable;

import grapher.ui.view.FunctionColorChooserListener;

public class FunctionTableMouseAdapter extends MouseAdapter {
	
	private enum States {IDLE,
						 PRESSED_RIGHT};
	
	private States state;
	
	private JTable table;
	
	private List <FunctionColorChooserListener> listeners = new ArrayList<>();
	
	
	
	public FunctionTableMouseAdapter (JTable t) {
		state = States.IDLE;
		table = t;
	}
	
	
	
	public void addListener (FunctionColorChooserListener l) {
		listeners.add(l);
	}
	
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (state == States.IDLE  &&  e.getButton() == MouseEvent.BUTTON3) {
			state = States.PRESSED_RIGHT;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (state == States.PRESSED_RIGHT) {
			int row = table.rowAtPoint(e.getPoint());
			if (row == table.getSelectedRow()) {
				Color newColor = JColorChooser.showDialog(null, "Choose a Color", (Color) table.getValueAt(table.getSelectedRow(), 1));
				if (newColor != null) {
					for (FunctionColorChooserListener listener : listeners) {
						listener.onColorChosen(table.getSelectedRow(), newColor);
					}
				}
			}
		}
		state = States.IDLE;
	}
	
}
