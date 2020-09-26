package grapher.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar implements FunctionListListener {
	
	private List <ToolListener> listeners = new ArrayList<>();
	
	private JButton buttonAdd;
	private JButton buttonRemove;

	private int selectedFunction = -1;
	private String selectedFunctionName = "";
	
	
	
	public ToolBar () {
		super();
		setFloatable(false);

		buttonAdd = new JButton("+");
		buttonRemove = new JButton("-");
		add(buttonAdd);
		add(buttonRemove);
		
		initialiseButtonEvents();
	}
	
	
	
	private void initialiseButtonEvents () {
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newExpression = (String)JOptionPane.showInputDialog(
	                    ToolBar.this,
	                    "New expression:",
	                    "Add expression",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "");
				if (newExpression != null  &&  newExpression.length() > 0) {
					for (ToolListener listener : listeners) {
						listener.onFunctionAdd(newExpression);
					}
				}
			}
		});
		
		buttonRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedFunction >= 0) {
					Object[] options = {"Remove", "Cancel"};
					int answer = JOptionPane.showOptionDialog(
							ToolBar.this,
							"Do you want to remove " + selectedFunctionName + "? This action cannot be undone.",
							"Remove expression",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE,
							null,
							options,
							options[0]);
					if (answer == 0) {
						for (ToolListener listener : listeners) {
							listener.onFunctionRemove(selectedFunction);
						}
					}
				}
			}
		});
	}
	
	
	
	public void addListener (ToolListener listener) {
		listeners.add(listener);
	}
	
	
	
	@Override
	public void onFunctionSelection(int selected, String name) {
		selectedFunction = selected;
		selectedFunctionName = name;
	}
	
	@Override
	public void onFunctionAdd(String function) {
		return;
	}
	
	@Override
	public void onFunctionRemove(int function) {
		return;
	}
	
}
