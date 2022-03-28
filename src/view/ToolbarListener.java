package view;

import java.util.EventListener;

public interface ToolbarListener extends EventListener {
	public void toolbarClearEventOccurred(ToolbarEvent e);
	public void refreshEventOccurred(ToolbarEvent ev);
	public void toolbarTestEventOccurred(ToolbarEvent ev);

}
