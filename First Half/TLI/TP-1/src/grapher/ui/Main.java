package grapher.ui;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;


public class Main extends JFrame {
	Main(String title, String[] expressions) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		FunctionList functionList = new FunctionList();
		functionList.addFunctions(expressions);
		
		Grapher grapher = new Grapher();
		for(String expression : expressions) {
			grapher.add(expression);
		}
		
		functionList.addListener(grapher);
		
		splitPane.add(functionList);
		splitPane.add(grapher);
		
		add(splitPane);
		pack();
	}

	public static void main(String[] argv) {
		final String[] expressions = argv;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				new Main("grapher", expressions).setVisible(true); 
			}
		});
	}
}
