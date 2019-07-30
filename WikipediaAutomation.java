package com.GiveIndiaChallenge;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WikipediaAutomation {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\vnmeduri\\Downloads\\chrome_76\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		//Launch Wikipedia
		driver.get("https://en.wikipedia.org/wiki/Selenium");
		
		//Get all external links and verify navigation
		List<WebElement> alist = driver.findElements(By.xpath("//*[@aria-labelledby='sister-projects']/following-sibling::ul//li/a"));
		List<String> hrefList= new ArrayList<String>();
		for(WebElement we: alist){
			System.out.println(we.getAttribute("href"));
			hrefList.add(we.getAttribute("href"));
		}
		
		//Verifying links work by clicking on each of them
		for(String href: hrefList){
			driver.navigate().to(href);
			if(driver.getTitle()==null){
				System.out.println("Link is broken and not working");
			}else{
				System.out.println("Link works and the title is >> "+driver.getTitle());
			}
		}
		
		//Returning to main page and clicking on Oxygen element
		driver.get("https://en.wikipedia.org/wiki/Selenium");
		driver.findElement(By.xpath("//td[@title='Oxygen, 8 (reactive nonmetal)']")).click();
		String articleValue = driver.findElement(By.xpath("//a[text()='Article']")).getAttribute("href");
		if(articleValue.contains("Oxygen")){
			System.out.println("Yes..Oxygen is a featured article >> " + articleValue);
		}else{
			System.out.println("No..Oxygen is not a featured article");
		}
		
		//Taking screenshot of Oxygen properties
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File("D:/selenium/Oxygen.png"));
		
		//Count no of PDF elements in Citations
		int count = driver.findElements(By.xpath("//span[text()='(PDF)']")).size();
		System.out.println("Count of PDF elements >>> "+count);
		
		//Type Pluto in Search bar and check for suggestions
		driver.findElement(By.xpath("//input[@name='search']")).sendKeys("Pluto");
		
		//Get all Suggestions in list and verify second suggestion is Plutonium
		List<WebElement> suggestionsList = driver.findElements(By.xpath("//div[@class='suggestions-results']/a"));
		if(suggestionsList.get(1).getAttribute("title").equalsIgnoreCase("Plutonium")){
			System.out.println("Yes..Second suggestion is Plutonium and its attribute is "+suggestionsList.get(1).getAttribute("title"));
		}else{
			System.out.println("Yes..Second suggestion is not Plutonium ");
		}
		
		//Quit the driver
		driver.quit();
	}

}
