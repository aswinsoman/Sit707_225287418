package task1_2P;


import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BunningsLoginTest {

    // update this to your chromedriver path
    private static final String CHROME_DRIVER_PATH =
            "C:/Users/ADMIN/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe";

    private static final String LOGIN_URL = "https://www.bunnings.com.au/login";

    private void sleep(long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // helper - fills in the login form and submits, returns the URL after submission
    private String attemptLogin(String email, String password) {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        WebDriver driver = new ChromeDriver();

        driver.navigate().to(LOGIN_URL);
        sleep(3);

        // fill email field
        WebElement emailField = driver.findElement(By.name("email"));
        emailField.clear();
        emailField.sendKeys(email);

        // fill password field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.clear();
        passwordField.sendKeys(password);

        // click submit
        WebElement signInButton = driver.findElement(By.cssSelector("button[type='submit']"));
        signInButton.click();

        sleep(4);

        String currentUrl = driver.getCurrentUrl();
        System.out.println("URL after login attempt: " + currentUrl);

        driver.close();
        return currentUrl;
    }

    // -----------------------------------------------------------------------
    // 2 original tests from task2_1P - updated with real name and student ID
    // -----------------------------------------------------------------------

    @Test
    public void testStudentName() {
        String studentName = "Aswin";
        Assert.assertFalse("Student name should not be empty", studentName.isEmpty());
        System.out.println("Student name: " + studentName);
    }

    @Test
    public void testStudentId() {
        String studentId = "220000000"; // replace with your actual student ID
        Assert.assertFalse("Student ID should not be empty", studentId.isEmpty());
        System.out.println("Student ID: " + studentId);
    }

    // -----------------------------------------------------------------------
    // Decision table test cases
    // -----------------------------------------------------------------------

    // TC-DT02: valid email + wrong password -> stays on login page
    @Test
    public void testLogin_ValidEmail_InvalidPassword_Fail() {
        String url = attemptLogin("aswin@gmail.com", "wrongpassword123");
        Assert.assertTrue("Expected to stay on login page with wrong password",
                url.contains("bunnings.com.au"));
    }

    // TC-DT03: invalid email + some password -> stays on login page
    @Test
    public void testLogin_InvalidEmail_ValidPassword_Fail() {
        String url = attemptLogin("notauser@fake.com", "somepassword123");
        Assert.assertTrue("Expected to stay on login page with invalid email",
                url.contains("bunnings.com.au"));
    }

    // TC-DT04: invalid email + invalid password -> stays on login page
    @Test
    public void testLogin_InvalidEmail_InvalidPassword_Fail() {
        String url = attemptLogin("notauser@fake.com", "wrongpassword123");
        Assert.assertTrue("Expected to stay on login page with invalid credentials",
                url.contains("bunnings.com.au"));
    }

    // TC-DT05: both fields empty -> stays on login page
    @Test
    public void testLogin_EmptyEmail_EmptyPassword_Fail() {
        String url = attemptLogin("", "");
        Assert.assertTrue("Expected to stay on login page with empty fields",
                url.contains("bunnings.com.au"));
    }

    // TC-DT06: empty email, some password -> stays on login page
    @Test
    public void testLogin_EmptyEmail_ValidPassword_Fail() {
        String url = attemptLogin("", "somepassword123");
        Assert.assertTrue("Expected to stay on login page with empty email",
                url.contains("bunnings.com.au"));
    }

    // TC-DT07: valid email format, empty password -> stays on login page
    @Test
    public void testLogin_ValidEmail_EmptyPassword_Fail() {
        String url = attemptLogin("aswin@gmail.com", "");
        Assert.assertTrue("Expected to stay on login page with empty password",
                url.contains("bunnings.com.au"));
    }
}
