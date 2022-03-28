package model;

import java.io.Serializable;
import java.util.List;

import model.SiteData;

public class SiteData implements Serializable {
	

	private static final long serialVersionUID = -7873581769366622617L;
	
	private static int count = 1;
	private int id;
	private String price;
	private String url;
	private String text;
	
	public SiteData() {
	}

	
	public SiteData( String price, String url, String text) {
		this.price = price;
		this.url = url;
		this.text = text;
		id = count++;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public boolean isInList(Database db) {
		List<SiteData> ls = db.getSiteDataArray();
		for(int i = 0; i < db.size() ; i++) {
			if(this.getPrice() == ls.get(i).getPrice() && this.getText() == ls.get(i).getText() && this.getUrl() == ls.get(i).getUrl()){
				return true;
			}
		}	
		return false;
	}
	
	@Override
	public String toString() {
		return price + " : " + url + " : " + text;
	}
}
