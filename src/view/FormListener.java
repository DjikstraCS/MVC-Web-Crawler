package view;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import model.SearchItem;

public interface FormListener extends EventListener {
	public void formEventOccurred(FormEvent e);
	
	public void formDomainSelectionEventOccurred(FormEvent e);

	public void formCategorySearchEventOccurred(FormEvent e);

	public void formSearchEventOccurred(FormEvent ev);

	public List<SearchItem> formSaveEventOccurred(FormEvent ev);

	public void formCancelEventOccurred();
}
