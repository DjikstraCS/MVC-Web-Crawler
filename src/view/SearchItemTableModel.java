package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.SearchItem;

public class SearchItemTableModel extends AbstractTableModel {
		
		private List<SearchItem> db = new ArrayList<SearchItem>();
		private String[] colNames = {"Search list:", "domian"};

		public SearchItemTableModel() {
		
		}
		
		
		@Override
		public String getColumnName(int column) {
			
			return colNames[column];
		}

		public void setData(List<SearchItem> db) {
			this.db = db;  
		}
		
		@Override
		public int getColumnCount() {
			return 1;
		}

		@Override
		public int getRowCount() {
			return db.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			SearchItem searchItem = db.get(row);
		
			
			switch(col) {
			case 0:
				return searchItem.getName();
			case 1:
				return searchItem.getId();

			}
			return null;
		}

}
