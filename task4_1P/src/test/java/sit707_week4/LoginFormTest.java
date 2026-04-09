package sit707_week4;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests functions in LoginForm.
 * @author Ahsan Habib
 */
public class LoginFormTest 
{

	@Test
	public void testStudentIdentity() {
		String studentId = "225287418";
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Aswin Soman";
		Assert.assertNotNull("Student name is null", studentName);
	}
	
	@Test
    public void testFailEmptyUsernameAndEmptyPasswordAndDontCareValCode()
    {
		LoginStatus status = LoginForm.login(null, null);
		Assert.assertTrue( status.isLoginSuccess() == false );
    }
	
	 // TC2: Empty username, wrong password
    @Test
    public void test_login_emptyUsername_wrongPassword() {
        LoginStatus status = LoginForm.login("", "wrongpass");
        assertFalse(status.isLoginSuccess());
        assertEquals("Empty Username", status.getErrorMsg());
    }

    // TC3: Empty username, correct password
    @Test
    public void test_login_emptyUsername_correctPassword() {
        LoginStatus status = LoginForm.login("", "ahsan_pass");
        assertFalse(status.isLoginSuccess());
        assertEquals("Empty Username", status.getErrorMsg());
    }

    // TC4: Wrong username, empty password
    @Test
    public void test_login_wrongUsername_emptyPassword() {
        LoginStatus status = LoginForm.login("wronguser", "");
        assertFalse(status.isLoginSuccess());
        assertEquals("Empty Password", status.getErrorMsg());
    }

    // TC5: Wrong username, wrong password
    @Test
    public void test_login_wrongUsername_wrongPassword() {
        LoginStatus status = LoginForm.login("wronguser", "wrongpass");
        assertFalse(status.isLoginSuccess());
        assertEquals("Credential mismatch", status.getErrorMsg());
    }

    // TC6: Wrong username, correct password
    @Test
    public void test_login_wrongUsername_correctPassword() {
        LoginStatus status = LoginForm.login("wronguser", "ahsan_pass");
        assertFalse(status.isLoginSuccess());
        assertEquals("Credential mismatch", status.getErrorMsg());
    }

    // TC7: Correct username, empty password
    @Test
    public void test_login_correctUsername_emptyPassword() {
        LoginStatus status = LoginForm.login("ahsan", "");
        assertFalse(status.isLoginSuccess());
        assertEquals("Empty Password", status.getErrorMsg());
    }

    // TC8: Correct username, wrong password
    @Test
    public void test_login_correctUsername_wrongPassword() {
        LoginStatus status = LoginForm.login("ahsan", "wrongpass");
        assertFalse(status.isLoginSuccess());
        assertEquals("Credential mismatch", status.getErrorMsg());
    }

    // TC9: Correct username, correct password
    @Test
    public void test_login_correctUsername_correctPassword() {
        LoginStatus status = LoginForm.login("ahsan", "ahsan_pass");
        assertTrue(status.isLoginSuccess());
        assertEquals("123456", status.getErrorMsg());
    }

    // --- VALIDATE CODE TESTS

    // TC10: Correct login, then empty validation code -> false
    @Test
    public void test_validateCode_empty() {
        LoginForm.login("ahsan", "ahsan_pass");
        boolean result = LoginForm.validateCode("");
        assertFalse(result);
    }

    // TC11: Correct login, then wrong validation code -> false
    @Test
    public void test_validateCode_wrong() {
        LoginForm.login("ahsan", "ahsan_pass");
        boolean result = LoginForm.validateCode("abcd");
        assertFalse(result);
    }

    // TC12: Correct login, then correct validation code -> true
    @Test
    public void test_validateCode_correct() {
        LoginForm.login("ahsan", "ahsan_pass");
        boolean result = LoginForm.validateCode("123456");
        assertTrue(result);
    }
}
