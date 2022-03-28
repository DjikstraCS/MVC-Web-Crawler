package view;

import java.util.EventObject;

public class ToolbarEvent extends EventObject {
	
	private String selectedSite;
	
	public ToolbarEvent(Object source) {
		super(source);
	}
	
	public ToolbarEvent(Object source, String selectedSite) {
		super(source);
		this.selectedSite = selectedSite;
	}

	public String getSelectedSite() {
		return selectedSite;
	}

	public void setSelectedSite(String selectedSite) {
		this.selectedSite = selectedSite;
	}

	
}
