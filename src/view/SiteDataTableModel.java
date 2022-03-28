package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.SiteData;

public class SiteDataTableModel extends AbstractTableModel{
	
	private List<SiteData> db;
	private String[] colNames = {"id", "Domain", "Search text", "Category", "Defect", "Arrange by"};

	public SiteDataTableModel() {
		
	}
	
	
	
	@Override
	public String getColumnName(int column) {
		
		return colNames[column];
	}



	public void setData(List<SiteData> db) {
		this.db = db;  
	}
	
	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return db.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		SiteData siteData = db.get(row);
		
		switch(col) {
		case 0:
			return siteData.getId();
		case 1:
			return siteData.getPrice();
		case 2:
			return siteData.getUrl();
		case 3:
			return siteData.getText();
		}
		return null;
	}

}
