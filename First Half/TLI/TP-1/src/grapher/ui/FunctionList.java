package grapher.ui;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class FunctionList extends JList <String> {
	
	DefaultListModel <String> model = new DefaultListModel<>();
	
	public FunctionList () {
		super();
		setModel(model);
	}
	
	
	
	public void addFunctions (String[] f) {
		for (String s : f) {
			model.addElement(s);
		}
	}
	
}
