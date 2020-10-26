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
	private CustomListSelectionModel customListSelectionModel;
	
	private FunctionTableMouseAdapter functionTableMouseAdapter;
	
	private List <FunctionTableListener> listeners = new ArrayList<>();
	private FunctionTableListener listenerList;
	
	
	
	public FunctionTable () {
		super();
		
		model = new DefaultTableModel(columnNames, 0);
		setModel(model);
		
		customListSelectionModel = new CustomListSelectionModel();
		setSelectionModel(customListSelectionModel);
		
		getColumnModel().getColumn(0).setMinWidth(COLUMN_FUNCTION_MIN_WIDTH);
		getColumnModel().getColumn(1).setMinWidth(COLUMN_COLOUR_MIN_WIDTH);
		getColumnModel().getColumn(1).setMaxWidth(COLUMN_COLOUR_MAX_WIDTH);
		getColumnModel().getColumn(1).setCellRenderer(new FunctionTableCellRenderer());
		
		getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					for (FunctionTableListener listener : listeners) {
						listener.onFunctionSelection(getSelectedRows(), getSelectedFunctionName());
					}
					listenerList.onFunctionSelection(getSelectedRows(), getSelectedFunctionName());
				}
			}
		});
		
		functionTableMouseAdapter = new FunctionTableMouseAdapter(this);
		functionTableMouseAdapter.addListener(this);
		addMouseListener(functionTableMouseAdapter);
	}
	
	
	
	public void addFunction (String f, Color c) {
		for (FunctionTableListener listener : listeners) {
			listener.onFunctionAdd(f);
		}
		listenerList.onFunctionAdd(f);
		model.addRow(new Object[] {f, c});
	}
	
	public void addFunction (String f) {
		addFunction(f, Color.RED);
	}
	
	public void addFunctions (String[] f) {
		for (String s : f) {
			addFunction(s);
		}
	}
	
	public void removeFunction () {
		if (getSelectedRows().length >= 0  &&  getSelectedRows().length <= getRowCount()) {
			for (FunctionTableListener listener : listeners) {
				listener.onFunctionRemove(getSelectedRows());
			}
			listenerList.onFunctionRemove(getSelectedRows());
			for (int i=getSelectedRows().length - 1; i>=0; i--) {
				model.removeRow(getSelectedRows()[i]);
			}
			clearSelection();
		}
	}
	
	public void removeAllFunctions () {
		for (int i=0; i<getRowCount(); i++) {
			addRowSelectionInterval(i, i);
		}
		removeFunction();
	}
	
	public void editFunction (int index, String f) {
		for (FunctionTableListener listener : listeners) {
			listener.onFunctionEdit(index, f);
		}
		listenerList.onFunctionEdit(index, f);
		model.setValueAt(f, index, 0);
	}
	
	
	
	public void addListener (FunctionTableListener l) {
		listeners.add(l);
	}
	
	public void addListenerList (FunctionTableListener lList) {
		listenerList = lList;
	}
	
	public void addFunctionColorChooserListener (FunctionColorChooserListener l) {
		functionTableMouseAdapter.addListener(l);
	}
	
	
	
	private String getSelectedFunctionName () {
		if (getSelectedRow() < 0  ||  getSelectedRow() > 0) {
			return "";
		}
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
	public void onFunctionSelection(int indices[]) {
		clearSelection();
		for (int index : indices) {
			addRowSelectionInterval(index, index);
		}
		for (FunctionTableListener listener : listeners) {
			listener.onFunctionSelection(indices, getSelectedFunctionName());
		}
	}
	
	
	
	@Override
	public void onColorChosen(int selectedRow, Color newColor) {
		model.setValueAt(newColor, selectedRow, 1);
	}
	
}
