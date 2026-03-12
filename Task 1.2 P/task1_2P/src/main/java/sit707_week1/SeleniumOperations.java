package sit707_week1;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
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
	
	
	public static void open_chrome_and_close() {
		// Step 1: Locate chrome driver folder (chromium browser) in the local drive.
		System.setProperty("webdriver.chrome.driver", "/home/mahabib/java_lib/chromedriver-linux64/chromedriver");
		
		// Step 2: Use above chrome driver to open up a chromium browser.
		System.out.println("Fire up chrome browser.");
		WebDriver driver = new ChromeDriver();
		
		System.out.println("Driver info: " + driver);
		
		// Sleep a while
		sleep(5);
		
		/*
		 * We will use a lot of Selenium APIs here in future 
		 * including maximise window and open a URL in chromium browser.
		 */
		
		// close chrome driver. You can comment below line to keep the browser opened.
		driver.close();
		
	}
	

	public static void open_chrome_maximize_close() {
		// Step 1: Locate chrome driver folder in the local drive.
		System.setProperty("webdriver.chrome.driver", "/home/mahabib/java_lib/chromedriver-linux64/chromedriver");
		
		// Step 2: Use above chrome driver to open up a chromium browser.
		System.out.println("Fire up chrome browser.");
		WebDriver driver = new ChromeDriver();
		
		System.out.println("Driver info: " + driver);
		
		sleep(5);
		
		// Maximize chrome driver
		driver.manage().window().maximize();
		
		// Sleep a while
		sleep(5);
		
		// close chrome driver. You can comment below line to keep the browser opened.
		driver.close();	
	}

	
	public static void load_web_page_close() {
		// Step 1: Locate chrome driver folder in the local drive.
		System.setProperty("webdriver.chrome.driver", "/home/mahabib/java_lib/chromedriver-linux64/chromedriver");
		
		// Step 2: Use above chrome driver to open up a chromium browser.
		System.out.println("Fire up chrome browser.");
		WebDriver driver = new ChromeDriver();
		
		System.out.println("Driver info: " + driver);
		
		sleep(5);
		
		// Open the URL in chromium browser.
		driver.get("https://selenium.dev");
		
		// Sleep a while
		sleep(5);
		
		// close chrome driver. You can comment below line to keep the browser opened.
		driver.close();	
	}
	
	/*
	 * You have to complete this function.
	 */
	public static void open_chrome_loadpage_resize_close() {
		// Fill your personal information.
		System.out.println("Hello from 225287418, Aswin Soman");
		
		// Step 1: Locate chrome driver folder in the local drive.
		System.setProperty("webdriver.chrome.driver", "C:/Users/ADMIN/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
		
		// Step 2: Use above chrome driver to open up a chromium browser.
		System.out.println("Fire up chrome browser.");
		WebDriver driver = new ChromeDriver();
		
		System.out.println("Driver info: " + driver);
		
		sleep(2);
		
		/*
		 * Load google page.
		 */
		driver.get("https://www.google.com");
		
		/*
		 * Set window size manually to 640x480 and wait 2 second.
		 * Hint: Explore Selenium window functions to select one which allows to pass window size in WxH.
		 */
		driver.manage().window().setSize(new Dimension(640, 480));
		sleep(10);
		/*
		 * Double window size manually to 1280x960 and wait 2 second.		 
		 */
		driver.manage().window().setSize(new Dimension(1280, 960));
		
		// Sleep a while
		sleep(10);
		
		// close chrome driver. You can comment below line to keep the browser opened.
		driver.close();	
	}
}
