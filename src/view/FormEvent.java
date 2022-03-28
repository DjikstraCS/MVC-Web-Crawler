package view;

import java.util.EventObject;

public class FormEvent extends EventObject {
	
	private String searchField;
	private String searchCategory;
	private String domainBox;
	private boolean defectCheck;
	private Boolean region;
	private String maxPrice;
	private String minPrice;
	
	public FormEvent(Object source) {
		super(source);
	}
	
	public FormEvent(Object source, String searchField) {
		super(source);
		this.searchField = searchField;
	}
	
	public FormEvent(Object source, String domainBox, String searchField, String searchCat, boolean defectCheck, boolean region, String minPrice, String maxPrice) {
		super(source);
		this.domainBox = domainBox;
		this.searchField = searchField;
		this.searchCategory = searchCat;
		this.region = region;
		this.defectCheck = defectCheck;
		this.minPrice = minPrice;
		this.maxPrice =maxPrice;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	
	public String getSearchCategory() {
		return searchCategory;
	}

	public void setSearchCategory(String searchCategory) {
		this.searchCategory = searchCategory;
	}
	
	public boolean isDefectCheck() {
		return defectCheck;
	}

	public void setDefectCheck(boolean defectCheck) {
		this.defectCheck = defectCheck;
	}

	public Boolean isRegionCheck() {
		return region;
	}

	public void setRegionCheck(Boolean region) {
		this.region = region;
	}

	public String getDomainBox() {
		return domainBox;
	}

	public void setDomainBox(String domainBox) {
		this.domainBox = domainBox;
	}

	public Boolean getRegion() {
		return region;
	}

	public void setRegion(Boolean region) {
		this.region = region;
	}

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}
	
	
	
}
