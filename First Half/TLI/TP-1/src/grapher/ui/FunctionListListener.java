package grapher.ui;

public interface FunctionListListener {
	
	void onFunctionAdd (String function);
	
	void onFunctionRemove (int function);
	
	void onFunctionSelection (int selected, String name);
	
}