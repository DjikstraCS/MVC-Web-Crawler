package view;

public interface MainPanelListener {
	public void mainPanelEventOccurred(MainPanelEvent e);

	public void mainPanelRunEventOccurred(MainPanelEvent ev);

	public void rowDeleted(int row);
}
