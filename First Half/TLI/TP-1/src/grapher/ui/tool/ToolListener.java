package grapher.ui.tool;

public interface ToolListener {
	
	void onFunctionAdd (String function);
	
	void onFunctionRemove (int function);
	
	void onFunctionEdit (int index, String function);
	
}
