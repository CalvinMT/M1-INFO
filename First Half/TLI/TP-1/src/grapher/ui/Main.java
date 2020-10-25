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
		
		ListModelFromTable <String> listModel = new ListModelFromTable <> ();
		FunctionList functionList = new FunctionList(listModel);
		FunctionTable functionTable = new FunctionTable();
		
		ToolBar toolBar = new ToolBar();
		
		Grapher grapher = new Grapher();
		
		menuBar.addToolListener(functionTable);

		functionList.addToolListener(functionTable);
		functionTable.addListener(grapher);
		functionTable.addListener(listModel);
		functionTable.addListener(menuBar.getActionEditFunction());
		functionTable.addFunctionColorChooserListener(grapher);
		
		toolBar.addListener(functionTable);

		menuBar.addViewModeListener(grapher);
		menuBar.addViewModeListener(splitPaneFunctionTool);
		
		JComponent components[] = {functionList, functionTable};
		splitPaneFunctionTool.addFunctionComponents(components);
		splitPaneFunctionTool.setRightComponent(toolBar);
		
		splitPaneFunctionGrapher.add(splitPaneFunctionTool);
		splitPaneFunctionGrapher.add(grapher);

		functionTable.addFunctions(expressions);
		
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
