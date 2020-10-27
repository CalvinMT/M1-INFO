package grapher.ui.view.table;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import grapher.ui.actions.Actions;

public class FunctionTableMouseAdapter extends MouseAdapter {
	
	private enum States {IDLE,
						 PRESSED_RIGHT};
	
	private States state;
	
	private JTable table;
	
	
	
	public FunctionTableMouseAdapter (JTable t) {
		state = States.IDLE;
		table = t;
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
				Actions.getInstance().actionColorFunction.actionPerformed(null);
			}
		}
		state = States.IDLE;
	}
	
}
