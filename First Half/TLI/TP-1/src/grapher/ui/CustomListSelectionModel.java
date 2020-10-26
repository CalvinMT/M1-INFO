package grapher.ui;

import javax.swing.DefaultListSelectionModel;

public class CustomListSelectionModel extends DefaultListSelectionModel {
	
	public CustomListSelectionModel () {
		setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
	}
	
	
	
	@Override
    public void setSelectionInterval(int index0, int index1) {
		if (index0 == index1  &&  isSelectedIndex(index0)) {
			clearSelection();
		}
		else {
			super.setSelectionInterval(index0, index1);
		}
    }
	
}
