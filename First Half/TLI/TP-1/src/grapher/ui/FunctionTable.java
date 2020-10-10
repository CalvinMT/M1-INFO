package grapher.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import grapher.ui.tool.ToolListener;

public class FunctionTable extends JTable implements ToolListener, FunctionColorChooserListener {
	
	private String[] columnNames = new String[] {"Function", "Color"};
	public static DefaultTableModel model;
	
	private FunctionTableMouseListener functionTableMouseListener;
	
	private List <FunctionListListener> listeners = new ArrayList<>();
	
	
	
	public FunctionTable () {
		super();
		model = new DefaultTableModel(columnNames, 0);
		setModel(model);
		
		getColumnModel().getColumn(1).setCellRenderer(new FunctionTableCellRenderer());
		
		getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					for (FunctionListListener listener : listeners) {
						listener.onFunctionSelection(getSelectedRow(), getSelectedFunction());
					}
				}
			}
		});
		
		functionTableMouseListener = new FunctionTableMouseListener(this);
		functionTableMouseListener.addListener(this);
		addMouseListener(functionTableMouseListener);
	}
	
	
	
	public void addFunctions (String[] f) {
		for (String s : f) {
			model.addRow(new Object[] {s, Color.RED});
		}
	}
	
	
	
	public void addListener (FunctionListListener l) {
		listeners.add(l);
	}
	
	public void addFunctionColorChooserListener (FunctionColorChooserListener l) {
		functionTableMouseListener.addListener(l);
	}
	
	
	
	private String getSelectedFunction () {
		return (String) getValueAt(getSelectedRow(), 0);
	}



	@Override
	public void onFunctionAdd(String function) {
		model.addRow(new Object[] {function, Color.RED});
		for (FunctionListListener listener : listeners) {
			listener.onFunctionAdd(function);
		}
	}
	
	@Override
	public void onFunctionRemove(int function) {
		model.removeRow(function);
		for (FunctionListListener listener : listeners) {
			listener.onFunctionRemove(function);
		}
	}
	
	
	
	@Override
	public void onColorChosen(int selectedRow, Color newColor) {
		model.setValueAt(newColor, selectedRow, 1);
	}
	
}
