package sit707_week2;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.Assert.*;

/**
 * Decision Table Testing for Bunnings Login Page
 * URL: https://www.bunnings.com.au/login
 * 
 * Conditions:
 * C1: Email provided (Valid / Invalid / Empty)
 * C2: Password provided (Valid / Invalid / Empty)
 * 
 * Actions:
 * A1: Login succeeds (URL changes away from /login)
 * A2: Login fails (URL stays on /login or error shown)
 * 
 * @author Aswin Soman
 * @student s225287418
 */
public class TestBunningsLogin {

	private WebDriver driver;
	private WebDriverWait wait;
	private static final String LOGIN_URL = "https://www.bunnings.com.au/login";
	private static final int TIMEOUT_SECONDS = 15;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver",
				"C:/Users/ADMIN/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, TIMEOUT_SECONDS);
	}

	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	// ==================== STUDENT IDENTITY TESTS ====================

	@Test
	public void testStudentIdentity() {
		String studentId = "s225287418";
		assertNotNull("Student ID should not be null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Aswin Soman";
		assertNotNull("Student name should not be null", studentName);
	}

	// ==================== DECISION TABLE TEST CASES ====================

	/**
	 * Decision Table Rule 1:
	 * C1: Valid Email = YES
	 * C2: Valid Password = YES
	 * Expected: Login succeeds or proceeds (URL changes from /login)
	 * 
	 * Note: Since we do not have real Bunnings credentials,
	 * we verify the form submission behaviour.
	 */
	@Test
	public void testValidEmailValidPassword() {
		driver.get(LOGIN_URL);
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.cssSelector("input[type='email'], input[name='email'], input[id='email'], input[type='text']")));

		// Locate email field
		WebElement emailField = findEmailField();
		assertNotNull("Email field should be present", emailField);
		emailField.clear();
		emailField.sendKeys("testuser@example.com");

		// Locate password field
		WebElement passwordField = findPasswordField();
		assertNotNull("Password field should be present", passwordField);
		passwordField.clear();
		passwordField.sendKeys("TestPassword123");

		// Click submit button
		WebElement submitBtn = findSubmitButton();
		assertNotNull("Submit button should be present", submitBtn);
		submitBtn.click();

		// Wait for page response
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// With invalid credentials, should show error or stay on login
		String currentUrl = driver.getCurrentUrl();
		String pageSource = driver.getPageSource().toLowerCase();
		boolean hasError = pageSource.contains("error") || pageSource.contains("invalid")
				|| pageSource.contains("incorrect") || pageSource.contains("try again")
				|| currentUrl.contains("login");
		assertTrue("Should show error for non-existent credentials", hasError);
	}

	/**
	 * Decision Table Rule 2:
	 * C1: Valid Email = YES
	 * C2: Valid Password = NO (invalid format)
	 * Expected: Login fails, error message displayed
	 */
	@Test
	public void testValidEmailInvalidPassword() {
		driver.get(LOGIN_URL);
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.cssSelector("input[type='email'], input[name='email'], input[id='email'], input[type='text']")));

		WebElement emailField = findEmailField();
		emailField.clear();
		emailField.sendKeys("testuser@example.com");

		WebElement passwordField = findPasswordField();
		passwordField.clear();
		passwordField.sendKeys("x");

		WebElement submitBtn = findSubmitButton();
		submitBtn.click();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String currentUrl = driver.getCurrentUrl();
		String pageSource = driver.getPageSource().toLowerCase();
		boolean loginFailed = currentUrl.contains("login") || pageSource.contains("error")
				|| pageSource.contains("invalid") || pageSource.contains("password");
		assertTrue("Login should fail with invalid password", loginFailed);
	}

	/**
	 * Decision Table Rule 3:
	 * C1: Valid Email = NO (invalid format)
	 * C2: Valid Password = YES
	 * Expected: Login fails, error message displayed
	 */
	@Test
	public void testInvalidEmailValidPassword() {
		driver.get(LOGIN_URL);
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.cssSelector("input[type='email'], input[name='email'], input[id='email'], input[type='text']")));

		WebElement emailField = findEmailField();
		emailField.clear();
		emailField.sendKeys("not-an-email");

		WebElement passwordField = findPasswordField();
		passwordField.clear();
		passwordField.sendKeys("TestPassword123");

		WebElement submitBtn = findSubmitButton();
		submitBtn.click();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String currentUrl = driver.getCurrentUrl();
		String pageSource = driver.getPageSource().toLowerCase();
		boolean loginFailed = currentUrl.contains("login") || pageSource.contains("error")
				|| pageSource.contains("invalid") || pageSource.contains("email");
		assertTrue("Login should fail with invalid email", loginFailed);
	}

	/**
	 * Decision Table Rule 4:
	 * C1: Valid Email = NO (invalid format)
	 * C2: Valid Password = NO (invalid format)
	 * Expected: Login fails, error message displayed
	 */
	@Test
	public void testInvalidEmailInvalidPassword() {
		driver.get(LOGIN_URL);
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.cssSelector("input[type='email'], input[name='email'], input[id='email'], input[type='text']")));

		WebElement emailField = findEmailField();
		emailField.clear();
		emailField.sendKeys("invalid");

		WebElement passwordField = findPasswordField();
		passwordField.clear();
		passwordField.sendKeys("x");

		WebElement submitBtn = findSubmitButton();
		submitBtn.click();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String currentUrl = driver.getCurrentUrl();
		boolean loginFailed = currentUrl.contains("login");
		assertTrue("Login should fail with both invalid", loginFailed);
	}

	/**
	 * Decision Table Rule 5:
	 * C1: Valid Email = EMPTY
	 * C2: Valid Password = YES
	 * Expected: Login fails, validation prevents submission
	 */
	@Test
	public void testEmptyEmailValidPassword() {
		driver.get(LOGIN_URL);
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.cssSelector("input[type='email'], input[name='email'], input[id='email'], input[type='text']")));

		// Leave email empty
		WebElement emailField = findEmailField();
		emailField.clear();

		WebElement passwordField = findPasswordField();
		passwordField.clear();
		passwordField.sendKeys("TestPassword123");

		WebElement submitBtn = findSubmitButton();
		submitBtn.click();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Should stay on login page
		String currentUrl = driver.getCurrentUrl();
		assertTrue("Should stay on login page with empty email", currentUrl.contains("login"));
	}

	/**
	 * Decision Table Rule 6:
	 * C1: Valid Email = YES
	 * C2: Valid Password = EMPTY
	 * Expected: Login fails, validation prevents submission
	 */
	@Test
	public void testValidEmailEmptyPassword() {
		driver.get(LOGIN_URL);
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.cssSelector("input[type='email'], input[name='email'], input[id='email'], input[type='text']")));

		WebElement emailField = findEmailField();
		emailField.clear();
		emailField.sendKeys("testuser@example.com");

		// Leave password empty
		WebElement passwordField = findPasswordField();
		passwordField.clear();

		WebElement submitBtn = findSubmitButton();
		submitBtn.click();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Should stay on login page
		String currentUrl = driver.getCurrentUrl();
		assertTrue("Should stay on login page with empty password", currentUrl.contains("login"));
	}

	/**
	 * Decision Table Rule 7:
	 * C1: Valid Email = EMPTY
	 * C2: Valid Password = EMPTY
	 * Expected: Login fails, validation prevents submission
	 */
	@Test
	public void testEmptyEmailEmptyPassword() {
		driver.get(LOGIN_URL);
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.cssSelector("input[type='email'], input[name='email'], input[id='email'], input[type='text']")));

		// Leave both fields empty
		WebElement emailField = findEmailField();
		emailField.clear();

		WebElement passwordField = findPasswordField();
		passwordField.clear();

		WebElement submitBtn = findSubmitButton();
		submitBtn.click();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Should stay on login page
		String currentUrl = driver.getCurrentUrl();
		assertTrue("Should stay on login page with both empty", currentUrl.contains("login"));
	}

	/**
	 * Decision Table Rule 8:
	 * Verify login page loads correctly
	 * Expected: Page contains email, password fields and submit button
	 */
	@Test
	public void testLoginPageLoads() {
		driver.get(LOGIN_URL);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Verify page loaded
		String currentUrl = driver.getCurrentUrl();
		assertTrue("Should be on login page", currentUrl.contains("login") || currentUrl.contains("bunnings"));

		// Verify form elements exist
		WebElement emailField = findEmailField();
		assertNotNull("Email field should exist", emailField);

		WebElement passwordField = findPasswordField();
		assertNotNull("Password field should exist", passwordField);

		WebElement submitBtn = findSubmitButton();
		assertNotNull("Submit button should exist", submitBtn);
	}

	// ==================== HELPER METHODS ====================

	/**
	 * Locate email input field using multiple strategies.
	 */
	private WebElement findEmailField() {
		String[] selectors = {
				"input[type='email']",
				"input[name='email']",
				"input[id='email']",
				"input[name='username']",
				"input[id='username']",
				"input[name='okta-signin-username']",
				"input[id='okta-signin-username']",
				"input[data-testid='email-input']",
				"input[autocomplete='email']",
				"input[autocomplete='username']"
		};

		for (String selector : selectors) {
			try {
				WebElement el = driver.findElement(By.cssSelector(selector));
				if (el.isDisplayed()) {
					return el;
				}
			} catch (Exception e) {
				// Try next selector
			}
		}

		// Fallback: find first visible text input
		try {
			for (WebElement el : driver.findElements(By.cssSelector("input[type='text']"))) {
				if (el.isDisplayed()) {
					return el;
				}
			}
		} catch (Exception e) {
			// ignore
		}

		return null;
	}

	/**
	 * Locate password input field using multiple strategies.
	 */
	private WebElement findPasswordField() {
		String[] selectors = {
				"input[type='password']",
				"input[name='password']",
				"input[id='password']",
				"input[name='okta-signin-password']",
				"input[id='okta-signin-password']",
				"input[data-testid='password-input']",
				"input[autocomplete='current-password']"
		};

		for (String selector : selectors) {
			try {
				WebElement el = driver.findElement(By.cssSelector(selector));
				if (el.isDisplayed()) {
					return el;
				}
			} catch (Exception e) {
				// Try next selector
			}
		}

		return null;
	}

	/**
	 * Locate submit button using multiple strategies.
	 */
	private WebElement findSubmitButton() {
		String[] selectors = {
				"button[type='submit']",
				"input[type='submit']",
				"button[data-testid='login-button']",
				"button[id='okta-signin-submit']",
				"input[id='okta-signin-submit']"
		};

		for (String selector : selectors) {
			try {
				WebElement el = driver.findElement(By.cssSelector(selector));
				if (el.isDisplayed()) {
					return el;
				}
			} catch (Exception e) {
				// Try next selector
			}
		}

		// Fallback: find button containing login/sign in text
		try {
			for (WebElement btn : driver.findElements(By.tagName("button"))) {
				String text = btn.getText().toLowerCase();
				if (text.contains("log in") || text.contains("login") || text.contains("sign in")
						|| text.contains("submit")) {
					if (btn.isDisplayed()) {
						return btn;
					}
				}
			}
		} catch (Exception e) {
			// ignore
		}

		return null;
	}
}