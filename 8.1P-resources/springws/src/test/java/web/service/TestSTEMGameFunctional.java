package web.service;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.Assert.*;

public class TestSTEMGameFunctional {
	
	private WebDriver driver;
	private WebDriverWait wait;
	private static final String BASE_URL = "http://127.0.0.1:8083";
	private static final int TIMEOUT_SECONDS = 15;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:/Users/ADMIN/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, TIMEOUT_SECONDS);
	}
	
	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	private void login() {
		driver.get(BASE_URL + "/login");
		driver.findElement(By.name("username")).sendKeys("ahsan");
		driver.findElement(By.name("passwd")).sendKeys("ahsan_pass");
		driver.findElement(By.name("dob")).sendKeys("1990-01-01");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		wait.until(ExpectedConditions.urlContains("/q1"));
	}
	
	// ==================== LOGIN TESTS ====================
	
	@Test
	public void testLoginSuccess() {
		driver.get(BASE_URL + "/login");
		driver.findElement(By.name("username")).sendKeys("ahsan");
		driver.findElement(By.name("passwd")).sendKeys("ahsan_pass");
		driver.findElement(By.name("dob")).sendKeys("1990-01-01");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		wait.until(ExpectedConditions.urlContains("/q1"));
		assertTrue("Should redirect to Q1", driver.getCurrentUrl().contains("/q1"));
	}
	
	@Test
	public void testLoginFailure() {
		driver.get(BASE_URL + "/login");
		driver.findElement(By.name("username")).sendKeys("wronguser");
		driver.findElement(By.name("passwd")).sendKeys("wrongpass");
		driver.findElement(By.name("dob")).sendKeys("1990-01-01");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		wait.until(ExpectedConditions.urlContains("/login"));
		assertTrue("Should stay on login page", driver.getCurrentUrl().contains("/login"));
	}
	
	// ==================== Q1 ADDITION TESTS ====================
	
	@Test
	public void testQ1CorrectAnswer() {
		login();
		driver.findElement(By.name("number1")).sendKeys("5");
		driver.findElement(By.name("number2")).sendKeys("3");
		driver.findElement(By.name("result")).sendKeys("8");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		wait.until(ExpectedConditions.urlContains("/q2"));
		assertTrue("Should navigate to Q2", driver.getCurrentUrl().contains("/q2"));
	}
	
	@Test
	public void testQ1IncorrectAnswer() {
		login();
		driver.findElement(By.name("number1")).sendKeys("5");
		driver.findElement(By.name("number2")).sendKeys("3");
		driver.findElement(By.name("result")).sendKeys("10");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		wait.until(ExpectedConditions.urlContains("/q1"));
		assertTrue("Should stay on Q1", driver.getCurrentUrl().contains("/q1"));
		assertTrue("Should show error message", driver.getPageSource().contains("Wrong answer"));
	}
	
	// ==================== Q2 SUBTRACTION TESTS ====================
	
	@Test
	public void testQ2CorrectAnswer() {
		login();
		
		driver.findElement(By.name("number1")).sendKeys("5");
		driver.findElement(By.name("number2")).sendKeys("3");
		driver.findElement(By.name("result")).sendKeys("8");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		wait.until(ExpectedConditions.urlContains("/q2"));
		driver.findElement(By.name("number1")).sendKeys("10");
		driver.findElement(By.name("number2")).sendKeys("3");
		driver.findElement(By.name("result")).sendKeys("7");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		wait.until(ExpectedConditions.urlContains("/q3"));
		assertTrue("Should navigate to Q3", driver.getCurrentUrl().contains("/q3"));
	}
	
	@Test
	public void testQ2IncorrectAnswer() {
		login();
		
		driver.findElement(By.name("number1")).sendKeys("5");
		driver.findElement(By.name("number2")).sendKeys("3");
		driver.findElement(By.name("result")).sendKeys("8");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		wait.until(ExpectedConditions.urlContains("/q2"));
		driver.findElement(By.name("number1")).sendKeys("10");
		driver.findElement(By.name("number2")).sendKeys("3");
		driver.findElement(By.name("result")).sendKeys("20");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		wait.until(ExpectedConditions.urlContains("/q2"));
		assertTrue("Should stay on Q2", driver.getCurrentUrl().contains("/q2"));
		assertTrue("Should show error message", driver.getPageSource().contains("Wrong answer"));
	}
	
	// ==================== Q3 MULTIPLICATION TESTS ====================
	
	@Test
	public void testQ3CorrectAnswer() {
		login();
		
		driver.findElement(By.name("number1")).sendKeys("5");
		driver.findElement(By.name("number2")).sendKeys("3");
		driver.findElement(By.name("result")).sendKeys("8");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		wait.until(ExpectedConditions.urlContains("/q2"));
		driver.findElement(By.name("number1")).sendKeys("10");
		driver.findElement(By.name("number2")).sendKeys("3");
		driver.findElement(By.name("result")).sendKeys("7");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		wait.until(ExpectedConditions.urlContains("/q3"));
		driver.findElement(By.name("number1")).sendKeys("2");
		driver.findElement(By.name("number2")).sendKeys("3");
		driver.findElement(By.name("result")).sendKeys("6");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/q3")));
		assertFalse("Should leave Q3 page", driver.getCurrentUrl().contains("/q3"));
	}
	
	@Test
	public void testQ3IncorrectAnswer() {
		login();
		
		driver.findElement(By.name("number1")).sendKeys("5");
		driver.findElement(By.name("number2")).sendKeys("3");
		driver.findElement(By.name("result")).sendKeys("8");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		wait.until(ExpectedConditions.urlContains("/q2"));
		driver.findElement(By.name("number1")).sendKeys("10");
		driver.findElement(By.name("number2")).sendKeys("3");
		driver.findElement(By.name("result")).sendKeys("7");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		wait.until(ExpectedConditions.urlContains("/q3"));
		driver.findElement(By.name("number1")).sendKeys("2");
		driver.findElement(By.name("number2")).sendKeys("3");
		driver.findElement(By.name("result")).sendKeys("10");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		wait.until(ExpectedConditions.urlContains("/q3"));
		assertTrue("Should stay on Q3", driver.getCurrentUrl().contains("/q3"));
		assertTrue("Should show error message", driver.getPageSource().contains("Wrong answer"));
	}
	
	// ==================== COMPLETE FLOW TEST ====================
	
	@Test
	public void testCompleteGameFlow() {
		login();
		
		// Q1: 5 + 3 = 8
		driver.findElement(By.name("number1")).sendKeys("5");
		driver.findElement(By.name("number2")).sendKeys("3");
		driver.findElement(By.name("result")).sendKeys("8");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		// Q2: 10 - 3 = 7
		wait.until(ExpectedConditions.urlContains("/q2"));
		driver.findElement(By.name("number1")).sendKeys("10");
		driver.findElement(By.name("number2")).sendKeys("3");
		driver.findElement(By.name("result")).sendKeys("7");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		// Q3: 4 * 5 = 20
		wait.until(ExpectedConditions.urlContains("/q3"));
		driver.findElement(By.name("number1")).sendKeys("4");
		driver.findElement(By.name("number2")).sendKeys("5");
		driver.findElement(By.name("result")).sendKeys("20");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		// Should complete successfully
		wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/q3")));
		assertFalse("Should complete game", driver.getCurrentUrl().contains("/q3"));
	}
}