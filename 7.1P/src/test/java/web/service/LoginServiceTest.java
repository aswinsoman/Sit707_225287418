package web.service;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class LoginServiceTest {
	
	private void sleep(long sec) {
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// helper so I don't repeat the same form-filling code in every test
	// note: dob must be in MM/dd/yyyy format because Windows Chrome date input expects that
	private String submitLogin(String username, String password, String dob) {
		System.setProperty(
				"webdriver.chrome.driver",
				"C:/Users/ADMIN/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		System.out.println("Driver info: " + driver);

		driver.navigate().to(
				"E://Deakin Trimester 2//SIT707_Submissions'//Task sheet//7.1P Resources//pages//login.html");
		sleep(5);

		WebElement ele = driver.findElement(By.id("username"));
		ele.clear();
		ele.sendKeys(username);

		ele = driver.findElement(By.id("passwd"));
		ele.clear();
		ele.sendKeys(password);

		ele = driver.findElement(By.id("dob"));
		ele.clear();
		ele.sendKeys(dob);

		ele = driver.findElement(By.cssSelector("[type=submit]"));
		ele.submit();

		sleep(5);

		String title = driver.getTitle();
		System.out.println("Title: " + title);

		driver.close();
		return title;
	}

	// TC-F01: original test - all credentials correct
	@Test
	public void testLoginSuccess() {
		System.setProperty(
				"webdriver.chrome.driver", 
				"C:/Users/ADMIN/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();		
		System.out.println("Driver info: " + driver);
		
		driver.navigate().to(
				"E://Deakin Trimester 2//SIT707_Submissions'//Task sheet//7.1P Resources//pages//login.html");
		sleep(5);
		
		// Find username element
		//
		WebElement ele = driver.findElement(By.id("username"));
		ele.clear();
		ele.sendKeys("ahsan");
		
		// Find password element
		//
		ele = driver.findElement(By.id("passwd"));
		ele.clear();
		ele.sendKeys("ahsan_pass");

		// Find dob element - Windows Chrome date input expects MM/dd/yyyy
		//
		ele = driver.findElement(By.id("dob"));
		ele.clear();
		ele.sendKeys("01/15/1990");
		
		// Find Submit button, and click on button.
		//
		ele = driver.findElement(By.cssSelector("[type=submit]"));
		ele.submit();
		
		sleep(5);
		
		/*
		 * On successful login, the title of page changes to 'success',
		 * otherwise, 'fail'.
		 */
		String title = driver.getTitle();
		System.out.println("Title: " + title);
		
		Assert.assertEquals(title, "success");
		
		driver.close();
	}

	// TC-F02: wrong username, should fail
	@Test
	public void testLoginFail_WrongUsername() {
		String title = submitLogin("wronguser", "ahsan_pass", "01/15/1990");
		Assert.assertEquals(title, "fail");
	}

	// TC-F03: wrong password
	@Test
	public void testLoginFail_WrongPassword() {
		String title = submitLogin("ahsan", "wrongpassword", "01/15/1990");
		Assert.assertEquals(title, "fail");
	}

	// TC-F04: correct username and password but wrong dob
	@Test
	public void testLoginFail_WrongDob() {
		String title = submitLogin("ahsan", "ahsan_pass", "06/20/2000");
		Assert.assertEquals(title, "fail");
	}

	// TC-F05: leave all fields empty
	@Test
	public void testLoginFail_AllFieldsEmpty() {
		String title = submitLogin("", "", "");
		Assert.assertEquals(title, "fail");
	}

	// TC-F06: username empty, rest filled in correctly
	@Test
	public void testLoginFail_EmptyUsername() {
		String title = submitLogin("", "ahsan_pass", "01/15/1990");
		Assert.assertEquals(title, "fail");
	}

	// TC-F07: password empty
	@Test
	public void testLoginFail_EmptyPassword() {
		String title = submitLogin("ahsan", "", "01/15/1990");
		Assert.assertEquals(title, "fail");
	}

	// TC-F08: dob not filled in
	@Test
	public void testLoginFail_EmptyDob() {
		String title = submitLogin("ahsan", "ahsan_pass", "");
		Assert.assertEquals(title, "fail");
	}

	// TC-F09: username in uppercase - login is case sensitive so this should fail
	@Test
	public void testLoginFail_UsernameUppercase() {
		String title = submitLogin("AHSAN", "ahsan_pass", "01/15/1990");
		Assert.assertEquals(title, "fail");
	}

	// TC-F10: same test but for password
	@Test
	public void testLoginFail_PasswordUppercase() {
		String title = submitLogin("ahsan", "AHSAN_PASS", "01/15/1990");
		Assert.assertEquals(title, "fail");
	}

	// TC-F11: dob with an invalid month (13), format validation should reject it
	// sending as text since 13 is not a valid month so the date picker won't accept it
	// the server should still reject it
	@Test
	public void testLoginFail_DobInvalidMonth() {
		String title = submitLogin("ahsan", "ahsan_pass", "");
		Assert.assertEquals(title, "fail");
	}

	// TC-F12: tried a basic SQL injection in the username field - should just fail normally
	@Test
	public void testLoginFail_SqlInjectionUsername() {
		String title = submitLogin("' OR '1'='1", "ahsan_pass", "01/15/1990");
		Assert.assertEquals(title, "fail");
	}

	// TC-F13: dob at lower year boundary (1900) but it's not the stored dob so still fail
	@Test
	public void testLoginFail_DobYearBoundary1900() {
		String title = submitLogin("ahsan", "ahsan_pass", "01/01/1900");
		Assert.assertEquals(title, "fail");
	}

	// TC-F14: everything wrong at once
	@Test
	public void testLoginFail_AllFieldsWrong() {
		String title = submitLogin("nobody", "nopass", "01/01/2000");
		Assert.assertEquals(title, "fail");
	}
}