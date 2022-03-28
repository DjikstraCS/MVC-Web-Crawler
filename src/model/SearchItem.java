package model;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import view.FormEvent;

public class SearchItem implements Serializable {

	private static final long serialVersionUID = 1607911535521677456L;
	
	private static int count = 1;
	private int id;
	private String name;
	private FormEvent formEvent;
	private List<SiteData> list;

	public SearchItem(String name, FormEvent formEvent)  {
		this.formEvent = formEvent;
		this.name = name;
		list = new ArrayList<SiteData>();
		id = count++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FormEvent getFormevent() {
		return formEvent;
	}

	public void setFormevent(FormEvent formEvent) {
		this.formEvent = formEvent;
	}

	public List<SiteData> getList() {

		return list;
	}

	public void addToList(List<SiteData> ls3) {
		this.list.addAll(ls3);
		

	}

	
	
	
	
	public void printStorageFile() {

		for(int i = 0 ;i<list.size();i++) {
			System.out.println("Price:" + list.get(i).getPrice());
		}
		if(list.size() == 0) {
			System.out.println("List is empty.");
		}
	}
}
