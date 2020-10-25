package grapher.ui;

public interface FunctionListListener {
	
	void onFunctionAdd (String function);
	
	void onFunctionRemove (int index);
	
	void onFunctionEdit (int index, String function);
	
	void onFunctionSelection (int index, String function);
	
}
