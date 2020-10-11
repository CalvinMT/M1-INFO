package grapher.ui;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import grapher.ui.menu.MenuBar;
import grapher.ui.tool.ToolBar;


public class Main extends JFrame {
	Main(String title, String[] expressions) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JSplitPane splitPaneFunctionGrapher = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		SplitPaneFunctionTool splitPaneFunctionTool = new SplitPaneFunctionTool(JSplitPane.VERTICAL_SPLIT);
		
		MenuBar menuBar = new MenuBar();
		
		FunctionList functionList = new FunctionList();
		functionList.addFunctions(expressions);
		FunctionTable functionTable = new FunctionTable();
		functionTable.addFunctions(expressions);
		
		ToolBar toolBar = new ToolBar();
		
		Grapher grapher = new Grapher();
		for(String expression : expressions) {
			grapher.add(expression);
		}
		
		menuBar.addToolListener(functionList);
		menuBar.addToolListener(functionTable);

		functionList.addListener(grapher);
		functionList.addListener(toolBar.getActionRemoveFunction());
		functionList.addListener(menuBar.getActionRemoveFunction());
		functionTable.addListener(grapher);
		functionTable.addListener(toolBar.getActionRemoveFunction());
		functionTable.addListener(menuBar.getActionRemoveFunction());
		functionTable.addFunctionColorChooserListener(grapher);
		
		toolBar.addListener(functionList);
		toolBar.addListener(functionTable);

		menuBar.addViewModeListener(grapher);
		menuBar.addViewModeListener(splitPaneFunctionTool);
		
		JComponent components[] = {functionList, functionTable};
		splitPaneFunctionTool.addFunctionComponents(components);
		
		splitPaneFunctionTool.setRightComponent(toolBar);
		splitPaneFunctionTool.setResizeWeight(0.9);
		splitPaneFunctionTool.setDividerSize(0);
		
		splitPaneFunctionGrapher.add(splitPaneFunctionTool);
		splitPaneFunctionGrapher.add(grapher);
		
		setJMenuBar(menuBar);
		add(splitPaneFunctionGrapher);
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
