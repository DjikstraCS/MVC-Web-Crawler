package model;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SearchItemsDatabase {
	
	private List<SearchItem> db;

	public SearchItemsDatabase() {
		db = new LinkedList<SearchItem>();
		
		File f = new File("SearchItemsStorage.txt");
		if(!f.exists() && f.isDirectory()) { 
			try {
				saveToStorageFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void addSearchItem(SearchItem searchItem) {
		db.add(searchItem);
		try {
			saveToStorageFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<SearchItem> getSearhList(){
		return db; 
		
	}
	
	public int getSize() {
		return db.size();
		
	}
	
	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		SearchItem[] siteData = db.toArray(new SearchItem[db.size()]);
		
		oos.writeObject(siteData);
		
		oos.close();
		
	}
	
	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		try {
			SearchItem[] siteData =  (SearchItem[])ois.readObject();
			//db.clear();
			db.addAll(Arrays.asList(siteData ));
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		System.out.println("Search Items loaded from selected file");
		ois.close();
		
		saveToStorageFile();
		
	}
	public synchronized void saveToStorageFile() throws IOException {
		//System.out.println("Saved!");
		//File yourFile = new File("SearchItemsStorage.txt");
		//yourFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(new File("SearchItemsStorage.txt"));
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		SearchItem[] siteData = db.toArray(new SearchItem[db.size()]);
		
		oos.writeObject(siteData);
		
		oos.close();
		System.out.println("Saved!");
		
	}
	
	public void loadFromStorageFile() throws IOException {
		

		FileInputStream fis = new FileInputStream("SearchItemsStorage.txt");
		
		
		try {
			ObjectInputStream ois = new ObjectInputStream(fis);
				SearchItem[] siteData =  (SearchItem[])ois.readObject();
				db.clear();
				db.addAll(Arrays.asList(siteData ));
				System.out.println("Search Items Loaded!");	
				ois.close();
			
		} catch (EOFException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	public void removeSearchItem(int row) {
		db.remove(row);
		try {
			saveToStorageFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
