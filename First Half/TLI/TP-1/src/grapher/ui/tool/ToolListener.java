package grapher.ui.tool;

public interface ToolListener {
	
	void onFunctionAdd (String function);
	
	void onFunctionRemove ();
	
	void onFunctionEdit (int index, String function);
	
	void onFunctionSelection (int index);
	
}
