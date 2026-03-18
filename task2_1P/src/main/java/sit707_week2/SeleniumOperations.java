package sit707_week2;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;

/**
 * This class demonstrates Selenium locator APIs to identify HTML elements.
 * 
 * Details in Selenium documentation https://www.selenium.dev/documentation/webdriver/elements/locators/
 * 
 * @author Ahsan Habib
 */
public class SeleniumOperations {

	public static void sleep(int sec) {
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void officeworks_registration_page(String url) {
		// Step 1: Locate chrome driver folder in the local drive.
		System.setProperty("webdriver.chrome.driver", "C:/Users/ADMIN/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
		
		// Step 2: Use above chrome driver to open up a chromium browser.
		System.out.println("Fire up chrome browser.");
		WebDriver driver = new ChromeDriver();
		
		System.out.println("Driver info: " + driver);
		
		sleep(2);
	
		// Load a webpage in chromium browser.
		driver.get(url);
		
		/*
		 * How to identify a HTML input field -
		 * Step 1: Inspect the webpage, 
		 * Step 2: locate the input field, 
		 * Step 3: Find out how to identify it, by id/name/...
		 */
		
		// Find first input field which is firstname
		WebElement element = driver.findElement(By.id("firstname"));
		System.out.println("Found element: " + element);
		// Send first name
		element.sendKeys("Aswin");
		
		/*
		 * Find following input fields and populate with values
		 */
         driver.findElement(By.id("lastname")).sendKeys("Soman");
         driver.findElement(By.id("email")).sendKeys("test@gmail.com");
         
         //fill password with 123
         driver.findElement(By.id("password")).sendKeys("123");
         driver.findElement(By.id("confirmPassword")).sendKeys("123");
		
		
		/*
		 * Identify button 'Create account' and click to submit using Selenium API.
		 */
         driver.findElement(By.xpath("//button[contains(text(),'Create account')]")).click();
		
		/*
		 * Take screenshot using selenium API.
		 */
         
         try {
        	// Scroll down to show errors
        	 ((org.openqa.selenium.JavascriptExecutor) driver)
        	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
        	 
        	 ((org.openqa.selenium.JavascriptExecutor) driver)
        	    .executeScript("document.body.style.zoom='60%'");
        	 
        	 sleep(2);
        	 
             TakesScreenshot ts = (TakesScreenshot) driver;
             File src = ts.getScreenshotAs(OutputType.FILE);

             Files.copy(src.toPath(),
                     new File("officeworks.png").toPath(),
                     StandardCopyOption.REPLACE_EXISTING);

             System.out.println("Screenshot saved!");
         } catch (Exception e) {
             e.printStackTrace();
         }
		
		
		// Sleep a while
		sleep(2);
		
		// close chrome driver
		driver.close();	
	}
	
	public static void instagram_registration_page(String url) {

	    System.setProperty("webdriver.chrome.driver", "C:/Users/ADMIN/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
	    WebDriver driver = new ChromeDriver();

	    driver.get(url);
	    driver.manage().window().maximize();

	    sleep(5);

	    try {
	        // Fill mobile/email
	        driver.findElement(By.name("emailOrPhone"))
	              .sendKeys("test@gmail.com");

	        // Full name
	        driver.findElement(By.name("fullName"))
	              .sendKeys("Aswin Soman");

	        // Username
	        driver.findElement(By.name("username"))
	              .sendKeys("aswin_test123");

	        // Weak password (intentional)
	        driver.findElement(By.name("password"))
	              .sendKeys("123");

	        sleep(2);

	        // Click Sign Up button
	        driver.findElement(By.xpath("//button[@type='submit']"))
	              .click();

	        sleep(5);

	        // Screenshot
	        TakesScreenshot ts = (TakesScreenshot) driver;
	        File src = ts.getScreenshotAs(OutputType.FILE);

	        Files.copy(src.toPath(),
	                new File("instagram.png").toPath(),
	                StandardCopyOption.REPLACE_EXISTING);

	        System.out.println("Instagram screenshot saved!");

	    } catch (Exception e) {
	        System.out.println("Instagram failed!");
	        e.printStackTrace();
	    }

	    sleep(3);
	    driver.quit();
	}
	
}
