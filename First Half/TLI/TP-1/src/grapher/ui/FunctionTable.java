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

	public static final int COLUMN_FUNCTION_MIN_WIDTH = 50;
	public static final int COLUMN_COLOUR_MIN_WIDTH = 20;
	public static final int COLUMN_COLOUR_MAX_WIDTH = 20;
	
	private String[] columnNames = new String[] {"Function", "Color"};
	public static DefaultTableModel model;
	
	private FunctionTableMouseListener functionTableMouseListener;
	
	private List <FunctionListListener> listeners = new ArrayList<>();
	
	
	
	public FunctionTable () {
		super();
		model = new DefaultTableModel(columnNames, 0);
		setModel(model);
		
		getColumnModel().getColumn(0).setMinWidth(COLUMN_FUNCTION_MIN_WIDTH);
		getColumnModel().getColumn(1).setMinWidth(COLUMN_COLOUR_MIN_WIDTH);
		getColumnModel().getColumn(1).setMaxWidth(COLUMN_COLOUR_MAX_WIDTH);
		getColumnModel().getColumn(1).setCellRenderer(new FunctionTableCellRenderer());
		
		getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					for (FunctionListListener listener : listeners) {
						listener.onFunctionSelection(getSelectedRow(), getSelectedFunctionName());
					}
				}
			}
		});
		
		functionTableMouseListener = new FunctionTableMouseListener(this);
		functionTableMouseListener.addListener(this);
		addMouseListener(functionTableMouseListener);
	}
	
	
	
	public void addFunction (String f) {
		for (FunctionListListener listener : listeners) {
			listener.onFunctionAdd(f);
		}
		model.addRow(new Object[] {f, Color.RED});
	}
	
	public void addFunctions (String[] f) {
		for (String s : f) {
			addFunction(s);
		}
	}
	
	public void removeFunction () {
		if (getSelectedRow() >= 0  &&  getSelectedRow() < getRowCount()) {
			for (FunctionListListener listener : listeners) {
				listener.onFunctionRemove(getSelectedRow());
			}
			model.removeRow(getSelectedRow());
		}
	}
	
	public void editFunction (int index, String f) {
		for (FunctionListListener listener : listeners) {
			listener.onFunctionEdit(index, f);
		}
		model.setValueAt(f, index, 0);
	}
	
	
	
	public void addListener (FunctionListListener l) {
		listeners.add(l);
	}
	
	public void addFunctionColorChooserListener (FunctionColorChooserListener l) {
		functionTableMouseListener.addListener(l);
	}
	
	
	
	private String getSelectedFunctionName () {
		return (String) getValueAt(getSelectedRow(), 0);
	}



	@Override
	public void onFunctionAdd(String function) {
		addFunction(function);
	}
	
	@Override
	public void onFunctionRemove() {
		removeFunction();
	}
	
	@Override
	public void onFunctionEdit(int index, String function) {
		editFunction(index, function);
	}
	
	@Override
	public void onFunctionSelection(int index) {
		changeSelection(index, 0, false, false);
		for (FunctionListListener listener : listeners) {
			listener.onFunctionSelection(getSelectedRow(), getSelectedFunctionName());
		}
	}
	
	
	
	@Override
	public void onColorChosen(int selectedRow, Color newColor) {
		model.setValueAt(newColor, selectedRow, 1);
	}
	
}
