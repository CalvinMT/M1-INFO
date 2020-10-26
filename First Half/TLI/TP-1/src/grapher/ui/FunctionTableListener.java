package grapher.ui;

public interface FunctionTableListener {
	
	void onFunctionAdd (String function);
	
	void onFunctionRemove (int index);
	
	void onFunctionEdit (int index, String function);
	
	void onFunctionSelection (int indices[], String function);
	
}
