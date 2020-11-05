package grapher.ui;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import grapher.ui.actions.Actions;
import grapher.ui.view.SplitPaneFunctionTool;
import grapher.ui.view.graph.Grapher;
import grapher.ui.view.list.FunctionList;
import grapher.ui.view.list.FunctionListModelFromTable;
import grapher.ui.view.menu.MenuBar;
import grapher.ui.view.table.FunctionTable;
import grapher.ui.view.tool.ToolBar;


public class Main extends JFrame {
	Main(String title, String[] expressions) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JSplitPane splitPaneFunctionGrapher = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		SplitPaneFunctionTool splitPaneFunctionTool = new SplitPaneFunctionTool(JSplitPane.VERTICAL_SPLIT);
		
		FunctionListModelFromTable <String> listModel = new FunctionListModelFromTable <> ();
		FunctionList functionList = new FunctionList(listModel);
		FunctionTable functionTable = new FunctionTable();
		
		Actions.initialise(functionTable, this);
		
		Inputs.getInstance(getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW), getRootPane().getActionMap());
		
		MenuBar menuBar = new MenuBar();
		
		ToolBar toolBar = new ToolBar();
		
		Grapher grapher = new Grapher();

		Actions.getInstance().actionAddFunction.addListener(functionTable);

		Actions.getInstance().actionEditFunction.addListener(functionTable);

		Actions.getInstance().actionColorFunction.addListener(functionTable);
		Actions.getInstance().actionColorFunction.addListener(grapher);

		Actions.getInstance().actionRemoveFunction.addListener(functionTable);
		
		Actions.getInstance().actionViewModeList.addListener(grapher);
		Actions.getInstance().actionViewModeList.addListener(splitPaneFunctionTool);

		Actions.getInstance().actionViewModeTable.addListener(grapher);
		Actions.getInstance().actionViewModeTable.addListener(splitPaneFunctionTool);

		functionList.addToolListener(functionTable);
		functionTable.addListener(menuBar);
		functionTable.addListener(grapher);
		functionTable.addListener(Actions.getInstance().actionEditFunction);
		functionTable.addListener(Actions.getInstance().actionColorFunction);
		functionTable.addListenerList(listModel);
		
		JComponent components[] = {functionList, functionTable};
		splitPaneFunctionTool.addFunctionComponents(components);
		splitPaneFunctionTool.setRightComponent(toolBar);
		
		splitPaneFunctionGrapher.add(splitPaneFunctionTool);
		splitPaneFunctionGrapher.add(grapher);
		splitPaneFunctionGrapher.getLeftComponent().setMinimumSize(new Dimension(FunctionTable.COLUMN_FUNCTION_MIN_WIDTH + FunctionTable.COLUMN_COLOUR_MIN_WIDTH, 0));

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
