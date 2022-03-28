package model;

import java.awt.List;
import java.util.ArrayList;
//Dependencies
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import controller.Controller;


public class WebBot{
	
	private Controller controller;

	public static void main(String[] args) {
		//Start program
		terminalInterface();
		
	}
public WebBot() {
	//Driver for Selenium v3 support
	System.out.println("Initializing gecko driver for Selenium v3");
	System.setProperty("webdriver.gecko.driver", "/home/linux/eclipse-workspace/webBot/lib/geckodriver/geckodriver");
}
	
	

public static void terminalInterface() { 
	WebBot wb = new WebBot();
	//Driver for Selenium v3 support
	//System.out.println("Initializing gecko driver for Selenium v3");
	//System.setProperty("webdriver.gecko.driver", "/home/linux/eclipse-workspace/webBot/lib/geckodriver/geckodriver");
	//Terminal interface
	Scanner scanner = new Scanner(System.in);
	System.out.println("Welcome to DBAwebBot alfa!");
	System.out.println("...........................");
	System.out.println("What are you looking to buy? fx: 'Playstation 4' 'fjernsyn' or 'i7 16gb Thinkpad'");
	String regex = "^\\s+";
	String searchText = scanner.nextLine().replaceFirst(regex, "");;
	System.out.println("Search text: '" + searchText + "'");
	System.out.println("What is the number of the row with the correct category? Don't know? Type: 'h'");
	String searchNum = scanner.next();
	if(searchNum.equalsIgnoreCase("h") == true) {
		wb.findCategory(searchText);
		System.out.println("What is the number of the row with the correct category?");
		searchNum = scanner.next();
	}
	System.out.println("Only looking for defecs? (Y/n)");
	String searchDefect = scanner.next().toString();
	System.out.println("Only looking for listings in the Copenhagen area? (Y/n)");
	String searchCopenhagen = scanner.next().toString();
	System.out.println("Arrange after price(0) or date(1)");
	int searchOrder = Integer.parseInt(scanner.next().toString());
	//wb.searchDBA(searchText, searchNum, searchDefect, searchCopenhagen, searchOrder);
		
}	

public ArrayList<String> findCategory(String searchText) {
	//Driver for Selenium v3 support
	//System.out.println("Initializing gecko driver for Selenium v3");
	//System.setProperty("webdriver.gecko.driver", "/home/linux/eclipse-workspace/webBot/lib/geckodriver/geckodriver");
	System.out.println("Opening Firefox...");
	WebDriver driver = new FirefoxDriver();
	driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	//Open website
	System.out.println("Opening DBA.dk...");
	driver.get("http://dba.dk/");
	System.out.println("Inserting '" + searchText + "' in search filed...");
    driver.findElement(By.id("searchField")).clear();
    driver.findElement(By.id("searchField")).sendKeys(searchText);
    //Clicks on the chosen element in drop down menu
    System.out.println("Loading Results...");
    ArrayList<String> categories = new ArrayList<String>(); 
    try {
	    for (int i = 1 ; i < 10; i++) {
	    	categories.add(i-1, driver.findElement(By.xpath("/html/body/div[4]/header/div/div/form/div/ul/li[" + i + "]/a/strong")).getText());
	    	System.out.println(categories.get(i-1));
	    }
    }catch(NoSuchElementException e) {
    	
    }
    System.out.println("Data recieved with succes! Exiting window...");
    driver.quit();
	return categories;
}
	
public ArrayList<SiteData> searchDBA(String searchText, String searchCat, Boolean searchDefect, Boolean searchCopenhagen, String minPrice, String maxPrice) {
	WebBot wb = new WebBot();
	System.out.println("Opening Firefox...");
	WebDriver driver = new FirefoxDriver();
	//Open website
	System.out.println("Opening DBA.dk...");
	driver.get("http://dba.dk/");
	searchText(driver, searchText, searchCat);
	if(searchDefect == true) {
		System.out.println("Finding defects...");
		wb.defekt(driver);
	}
	if(searchCopenhagen == true) {
		wb.copenhagenArea(driver);
	}
	if(!minPrice.isEmpty() || !maxPrice.isEmpty()) {
		wb.limitPrice(driver, minPrice, maxPrice);
	}
	wb.arrangeAfterDate(driver);
    return wb.printListings(driver);
}

public static void limitPrice(WebDriver driver, String minPrice, String maxPrice) {
	int temp = 0;
	for(int i = 1; i < 9; i++) {
    	if(driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/aside/div/div/div/div["+ i +"]/h4")).getText().equalsIgnoreCase("Pris")) {
    		driver.findElement(By.cssSelector("div.navigator:nth-child("+i+")")).click();;
    		temp = i;
    		break;
    	}
    }
	
	System.out.println(minPrice + " : " + maxPrice);
	try{
		driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/aside/div/div/div/div["+temp+"]/div/form/p/input[1]")).clear();
	    driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/aside/div/div/div/div["+temp+"]/div/form/p/input[1]")).sendKeys(minPrice);
	    driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/aside/div/div/div/div["+temp+"]/div/form/p/input[2]")).clear();
	    driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/aside/div/div/div/div["+temp+"]/div/form/p/input[2]")).sendKeys(maxPrice);
	    driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/aside/div/div/div/div["+temp+"]/div/form/p/input[3]")).click();;
	}catch(NoSuchElementException e){
		//driver.findElement(By.xpath("./html/body/div[4]/div[1]/div[1]/section/table/thead/tr/th[3]/span")).click();
	}
}

public static void searchText(WebDriver driver, String searchText, String searchCat) {
    //Only Defect show defects
	System.out.println("Inserting text in search filed...");
    driver.findElement(By.id("searchField")).clear();
    driver.findElement(By.id("searchField")).sendKeys(searchText);
    //Clicks on the chosen element in drop down menu
	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    System.out.println("Loading Results...");
    for(int i = 1; i < 9; i++) {
    	System.out.println(searchCat + " : " + driver.findElement(By.xpath("/html/body/div[4]/header/div/div/form/div/ul/li[" + i + "]/a/strong")).getText());
    	if(driver.findElement(By.xpath("/html/body/div[4]/header/div/div/form/div/ul/li[" + i + "]/a/strong")).getText().equalsIgnoreCase(searchCat)) {
    		driver.findElement(By.xpath("/html/body/div[4]/header/div/div/form/div/ul/li[" + i + "]/a")).click();
    		break;
    	}
    }
	driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
}

public static void defekt(WebDriver driver) {
    //Only Defect show defects
	try {
	    driver.findElement(By.cssSelector("html.js.no-touch.history.rgba.multiplebgs.borderradius.boxshadow.textshadow.opacity.cssgradients.csstransitions.generatedcontent.localstorage.boxsizing.filereader.fileinput.placeholder body.fixed div#page div#content.container div.sidebar-layout aside.sidebar.sidebar-left div.module.optionsModule div.bd div#srpNavigators div.navigator.checkboxNavigator.modulePanel.collapsedNavigator.n-stand h4")).click();
	    try {
	    	driver.findElement(By.cssSelector("html.js.no-touch.history.rgba.multiplebgs.borderradius.boxshadow.textshadow.opacity.cssgradients.csstransitions.generatedcontent.localstorage.boxsizing.filereader.fileinput.placeholder body.fixed div#page div#content.container div.sidebar-layout aside.sidebar.sidebar-left div.module.optionsModule div.bd div#srpNavigators div.navigator.checkboxNavigator.modulePanel.n-stand div div.collapsibleContent ul li.no-defekt span.human-ref")).click();
	    }catch(NoSuchElementException e){
	    	System.out.println("Error: No defect items in this category at the moment. Exiting...");
	    	driver.quit();
	    }
	}catch(NoSuchElementException e){
		System.out.println("Error: Defect option not available. Exiting...");
		driver.quit();
	}
}
	

public static void copenhagenArea(WebDriver driver) {
    //Copenhagen area
	System.out.println("Finding listings in copenhagen region...");
	try {
		driver.findElement(By.cssSelector("html.js.no-touch.history.rgba.multiplebgs.borderradius.boxshadow.textshadow.opacity.cssgradients.csstransitions.generatedcontent.localstorage.boxsizing.filereader.fileinput.placeholder body.fixed div#page div#content.container div.sidebar-layout aside.sidebar.sidebar-left div.module.optionsModule div.bd div#srpNavigators div.navigator.checkboxNavigator.modulePanel.locationNavigator.n-dummy-isGroupedNavigator div#regionNavigatorDiv.collapsibleContent.bootstrap ul.navigation-tree.no-checkbox.border-below-list li.no-koebenhavn-og-omegn span a")).click();
	}catch(NoSuchElementException e) {
		System.out.println("Error: No listings in Copenhagen region. Exiting...");
		driver.quit();
	}
}

public static void arrangeAfterDate(WebDriver driver) {
	//Arrange after date, newest first
	System.out.println("Checking if listings are ascending...");
	try{
		driver.findElement(By.cssSelector(".ascendingCurrentOrder"));
		System.out.println("They are... Continuing...");
	}catch(NoSuchElementException e){
		System.out.println("Listings are not ascending... taking action...");
		driver.findElement(By.xpath("./html/body/div[4]/div[1]/div[1]/section/table/thead/tr/th[3]/span")).click();
	}finally {
	}
}

public static void arrangeAfterPrice(WebDriver driver) {
	//Arrange after price
	System.out.println("Checking if listings are ordered by price...");
	try{
		driver.findElement(By.cssSelector(".sorting > tr:nth-child(1) > th:nth-child(6) > span:nth-child(1)"));
		System.out.println("They are... Continuing...");
	}catch(NoSuchElementException e){
		System.out.println("Listings are not ordered by price... taking action...");
		driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/section/table/thead/tr/th[4]/span")).click();
	}finally {
	}
}

public ArrayList<SiteData> printListings(WebDriver driver) {
	System.out.println("Listings:");
	SiteData siteData = new SiteData();
	ArrayList<SiteData> siteDataList = new ArrayList<SiteData>();
		for(int j = 3; j < 256; j++) {
			try {
				for (int i = 4 ; i<35 ; i++) {
			    	if(i == 14) {
			    		i++;
			    	}
				    String text = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/section/table/tbody/tr[" + i + "]/td[2]/div")).getText();
				    String URL = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/section/table/tbody/tr[" + i + "]/td[2]/a")).getAttribute("href");
				    String value = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/section/table/tbody/tr[" + i + "]/td[4]/a")).getText();
				    siteData = new SiteData(value, URL, text);
				    siteDataList.add(siteData);
				    System.out.println(value + " - " + URL  + " - " + text);
			    }
			driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/section/div[8]/ul/li[" + j + "]/span")).click();
			}catch(NoSuchElementException e) {
				break;
			}
		}
	
	System.out.println("No more pages");
	System.out.println("Data recieved with succes! Exiting...");
	System.out.println("Goodbye!");
	driver.quit();
	return siteDataList;
	
}
}