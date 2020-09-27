package grapher.ui;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;


public class Main extends JFrame {
	Main(String title, String[] expressions) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JSplitPane splitPaneListGrapher = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JSplitPane splitPaneListToolbar = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		MenuBar menuBar = new MenuBar();
		
		FunctionList functionList = new FunctionList();
		functionList.addFunctions(expressions);
		
		ToolBar toolBar = new ToolBar();
		
		Grapher grapher = new Grapher();
		for(String expression : expressions) {
			grapher.add(expression);
		}
		
		menuBar.addListener(functionList);

		functionList.addListener(grapher);
		functionList.addListener(toolBar.getActionRemoveFunction());
		functionList.addListener(menuBar.getActionRemoveFunction());
		
		toolBar.addListener(functionList);

		splitPaneListToolbar.add(functionList);
		splitPaneListToolbar.add(toolBar);
		splitPaneListToolbar.setResizeWeight(0.9);
		splitPaneListToolbar.setDividerSize(0);
		
		splitPaneListGrapher.add(splitPaneListToolbar);
		splitPaneListGrapher.add(grapher);
		
		setJMenuBar(menuBar);
		add(splitPaneListGrapher);
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
