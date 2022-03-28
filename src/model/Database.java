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

public class Database {
	
	private List<SiteData> db;
	
	public Database() {
		db = new LinkedList<SiteData>();
		File f = new File("SiteDataStorage.txt.txt");
		if(!f.exists() && f.isDirectory()) { 
			try {
				saveToStorageFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		
	
	
	public void addSiteDataToStorage(SiteData siteData) {
		db.add(siteData);
		try {
			saveToStorageFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setList(List<SiteData> siteData) {
		this.db = siteData;
		try {
			saveToStorageFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public  void addList(List<SiteData> list) {
		db.addAll(list);
		try {
			saveToStorageFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<SiteData> getSiteDataArray(){
		List<SiteData> ls = Collections.unmodifiableList(db);
		return ls;
		
	}
	
	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		SiteData[] siteData = db.toArray(new SiteData[db.size()]);
		
		oos.writeObject(siteData);
		
		oos.close();
		
	}
	
	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		try {
			SiteData[] siteData =  (SiteData[])ois.readObject();
			db.clear();
			db.addAll(Arrays.asList(siteData ));
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
		ois.close();
		
	}
	
	public void saveToStorageFile() throws IOException {
		FileOutputStream fos = new FileOutputStream(new File("SiteDataStorage.txt"));
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		SiteData[] siteData = db.toArray(new SiteData[db.size()]);
		
		oos.writeObject(siteData);
		
		System.out.println("Site Data Saved to Storage!");
		
		oos.close();
		
	}
	
	public void loadFromStorageFile() throws IOException {
		FileInputStream fis = new FileInputStream("SiteDataStorage.txt");
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		try {
			SiteData[] siteData =  (SiteData[])ois.readObject();
			db.clear();
			db.addAll(Arrays.asList(siteData ));
			System.out.println("Site Data Loaded!");
			
		} catch (EOFException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		ois.close();
		
	}

	public void removeSiteData(int row) {
		db.remove(row);
		
	}
	
	public int size() {
		return db.size();
		
	}
	public void clearDB() {
	db.removeAll(db);
	try {
		saveToStorageFile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
