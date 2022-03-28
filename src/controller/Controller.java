package controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import model.ArrangeBy;
import model.Database;
import model.TaskExecutor;
import model.SearchItem;
import model.SearchItemsDatabase;
import model.SiteData;
import model.WebBot;
import view.FormEvent;
import view.MainPanelEvent;

public class Controller {

	//Database db = new Database();
	Database dbStorage = new Database(); 
	SearchItemsDatabase sidb = new SearchItemsDatabase();
	WebBot wb = new WebBot();
	LocalDateTime time = LocalDateTime.now();
	TaskExecutor executor = new TaskExecutor(this);
	
	public void setTimer(int hour, int minute, int second) {
		executor.startExecutionAt(hour, minute, second);
	}
	
	public List<SiteData> getSiteData() {
		List<SiteData> ls = new ArrayList();
		for(int i = 0;i<sidb.getSize();i++) {
			ls.addAll(sidb.getSearhList().get(i).getList());
		}
		return ls;
	}

	public Database getDatabase() {
		return dbStorage;
	}

	public void addSiteData(SiteData siteData) {
		dbStorage.addSiteDataToStorage(siteData);
	}

	public void addSiteDataString(String value, String URL, String text) {
		SiteData siteData = new SiteData(value, URL, text);
		dbStorage.addSiteDataToStorage(siteData);
	}

	public void saveToFile(File file) throws IOException {
		sidb.saveToFile(file);
	}

	public void loadFromFile(File file) throws IOException {
		sidb.loadFromFile(file);
	}
	
	public void saveSeachitemsToFile(File file) throws IOException {
		dbStorage.saveToFile(file);
	}

	public void loadSeachItemsFromFile(File file) throws IOException {
		dbStorage.loadFromFile(file);
	}

	public void removePerson(int row) {
		sidb.removeSearchItem(row);
	}

	public ArrayList<String> getCategories(FormEvent ev) {
		return wb.findCategory(ev.getSearchField());
	}

	public void getListings(FormEvent ev) {
		String name = ev.getSearchField();
		String searchCat = ev.getSearchCategory();
		Boolean region = ev.isRegionCheck();
		// String domain = ev.getDomainBox();
		Boolean defect = ev.isDefectCheck();
		String maxPrice = ev.getMaxPrice();
		String minPrice = ev.getMinPrice();
		ArrayList<SiteData> str = wb.searchDBA(name, searchCat, defect, region, minPrice, maxPrice);
		for (int i = 0; i < str.size(); i++) {
			dbStorage.addSiteDataToStorage(str.get(i));
		}
		// db.addSiteDataArray(wb.searchDBA("playstation 4 pro", "2", "n", "n", 1));

	}

	public void ClearDB() {
		int sizeDB = dbStorage.size();
		for (int i = 0; i < sizeDB; i++) {
			dbStorage.removeSiteData(0);
		}
		try {
			dbStorage.saveToStorageFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<SearchItem> getSearchItemsList() {
		return sidb.getSearhList();

	}
	
	public List<SiteData> getSiteDataNew() {
		return dbStorage.getSiteDataArray();

	}

	public SearchItemsDatabase getSearchItemsDatabase() {
		return sidb;
	}

	public List<SearchItem> addSearchItemgetList(FormEvent ev) {

		SearchItem is = new SearchItem(ev.getDomainBox() + " - " + ev.getSearchField(), ev);
		sidb.addSearchItem(is);
		
		try {
			sidb.saveToStorageFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sidb.getSearhList();
	}

	public void addSearchItemString(FormEvent formEvent, String text) {
		SearchItem searchItem = new SearchItem(text, formEvent);
		sidb.addSearchItem(searchItem);
	}

	public void removeSiteItem(int row) {
		sidb.removeSearchItem(row);
	}

	public void ClearSIDB() {
		int sizeDB = sidb.getSize();
		for (int i = 0; i < sizeDB; i++) {
			sidb.removeSearchItem(0);
		}

	}

	public void runSearchItems() {
		dbStorage.clearDB();
		ExecutorService executor = Executors.newFixedThreadPool(4);

		for (int y = 0; y < sidb.getSize(); y++) {
			final int num = y;
			executor.submit(new Runnable() {

				@Override
				public void run() {
					System.out.println("run search items for loop");
					if (sidb.getSearhList().get(num).getFormevent().getDomainBox().equalsIgnoreCase("www.dba.dk") == true) {
						System.out.println("Updating DBA.dk!");
						String searchField = sidb.getSearhList().get(num).getFormevent().getSearchField();
						String searchCat = sidb.getSearhList().get(num).getFormevent().getSearchCategory();
						boolean defect = sidb.getSearhList().get(num).getFormevent().isDefectCheck();
						boolean region = sidb.getSearhList().get(num).getFormevent().isRegionCheck();
						String minPrice = sidb.getSearhList().get(num).getFormevent().getMinPrice();
						String maxPrice = sidb.getSearhList().get(num).getFormevent().getMaxPrice();
						ArrayList<SiteData> updatedList = wb.searchDBA(searchField, searchCat, defect, region, minPrice, maxPrice);
						List<SiteData> historyList = sidb.getSearhList().get(num).getList();
						
						System.out.print("History List:");
						sidb.getSearhList().get(num).printStorageFile();
						
						System.out.println("UpdatedList:" + updatedList.size() + " HistoryList: " + historyList.size());
						for(int i =0;i<historyList.size();i++) {
							for(int x =0;x<updatedList.size();x++) {
								System.out.println("History index:" + i + " | Updated index:" + x);
								if(updatedList.get(x).getUrl().equalsIgnoreCase(historyList.get(i).getUrl()) || updatedList.get(x).getText().equalsIgnoreCase(historyList.get(i).getText())) {
									System.out.println("ls.size()<new results>: " + updatedList.size() + " ls2.size()<old list>: " + historyList.size()+ " Removing: " + updatedList.get(x).getUrl());
									updatedList.remove(x);
									
									x--;
									break;
								}
								//System.out.println("HistoryList: " + historyList.size());
							}
							System.out.println("ls.size()<new results>: " + updatedList.size() + " ls2.size()<old list>:" + historyList.size());
						}
						
						
						System.out.println("ls.size() after loop:" + updatedList.size());
						
						dbStorage.addList(updatedList);
						//dbStorage.clearDB();
							
							
							sidb.getSearhList().get(num).addToList(updatedList);
						try {
							sidb.saveToStorageFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				}
			});
		}
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public LocalDateTime getTimer() {
		System.out.println(time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());
		return time;
	}

	public void loadStorage() throws IOException {
		//dbStorage.saveToStorageFile();
		sidb.loadFromStorageFile();
		dbStorage.loadFromStorageFile();

		
		
	}

	public void listSeachItemsInStorageFile() {
		List<SearchItem> ls = sidb.getSearhList();
		for(int i = 0; i > ls.size();i++) {
			System.out.println(i + " : " + ls.get(i).getFormevent().getSearchField());
		}
		
	}
}
