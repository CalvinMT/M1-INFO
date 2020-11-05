package grapher.ui.actions;

public interface FunctionActionListener {
	
	void onFunctionAdd (String function);
	
	void onFunctionRemove ();
	
	void onFunctionEdit (int index, String function);
	
	void onFunctionSelection (int indices[]);
	
}
