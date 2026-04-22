package web.service;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for LoginService.
 *
 * I used equivalence class testing, boundary value analysis and a decision table
 * to try to cover as many cases as I could. Goal was to get above 90% code coverage.
 */
public class LoginServiceUnitTest {

    // --- Equivalence Class Tests for login() ---
    // testing the main valid and invalid partitions for each field

    // TC-U01: all three fields correct, should return true
    @Test
    public void testLogin_AllValid_ReturnsTrue() {
        assertTrue(LoginService.login("ahsan", "ahsan_pass", "1990-01-15"));
    }

    // TC-U02: wrong username, everything else right
    @Test
    public void testLogin_WrongUsername_ReturnsFalse() {
        assertFalse(LoginService.login("wronguser", "ahsan_pass", "1990-01-15"));
    }

    // TC-U03: wrong password
    @Test
    public void testLogin_WrongPassword_ReturnsFalse() {
        assertFalse(LoginService.login("ahsan", "wrongpass", "1990-01-15"));
    }

    // TC-U04: wrong dob
    @Test
    public void testLogin_WrongDob_ReturnsFalse() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", "2000-06-20"));
    }

    // TC-U05: everything wrong
    @Test
    public void testLogin_AllWrong_ReturnsFalse() {
        assertFalse(LoginService.login("bad", "bad", "2000-01-01"));
    }

    // --- Null and Empty input tests ---
    // these should all fail gracefully without throwing exceptions

    // TC-U06: null username
    @Test
    public void testLogin_NullUsername_ReturnsFalse() {
        assertFalse(LoginService.login(null, "ahsan_pass", "1990-01-15"));
    }

    // TC-U07: null password
    @Test
    public void testLogin_NullPassword_ReturnsFalse() {
        assertFalse(LoginService.login("ahsan", null, "1990-01-15"));
    }

    // TC-U08: null dob
    @Test
    public void testLogin_NullDob_ReturnsFalse() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", null));
    }

    // TC-U09: empty username
    @Test
    public void testLogin_EmptyUsername_ReturnsFalse() {
        assertFalse(LoginService.login("", "ahsan_pass", "1990-01-15"));
    }

    // TC-U10: empty password
    @Test
    public void testLogin_EmptyPassword_ReturnsFalse() {
        assertFalse(LoginService.login("ahsan", "", "1990-01-15"));
    }

    // TC-U11: empty dob
    @Test
    public void testLogin_EmptyDob_ReturnsFalse() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", ""));
    }

    // TC-U12: whitespace only username - trim() should catch this
    @Test
    public void testLogin_WhitespaceUsername_ReturnsFalse() {
        assertFalse(LoginService.login("   ", "ahsan_pass", "1990-01-15"));
    }

    // --- Decision Table Tests ---
    // covering combinations of valid/invalid fields

    // TC-U13: correct username and dob, wrong password
    @Test
    public void testLogin_DT_ValidU_InvalidP_ValidD() {
        assertFalse(LoginService.login("ahsan", "wrong", "1990-01-15"));
    }

    // TC-U14: wrong username, correct password and dob
    @Test
    public void testLogin_DT_InvalidU_ValidP_ValidD() {
        assertFalse(LoginService.login("nobody", "ahsan_pass", "1990-01-15"));
    }

    // TC-U15: correct username + password, but invalid dob format
    @Test
    public void testLogin_DT_ValidU_ValidP_InvalidD() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", "1990-13-01"));
    }

    // TC-U16: correct username, wrong password and invalid dob
    @Test
    public void testLogin_DT_ValidU_InvalidP_InvalidD() {
        assertFalse(LoginService.login("ahsan", "wrong", "9999-99-99"));
    }

    // --- BVA on year ---

    // TC-U17: year = 1900, should be accepted (lower boundary)
    @Test
    public void testDobFormat_YearLowerBoundary_Valid() {
        assertTrue(LoginService.isValidDobFormat("1900-01-01"));
    }

    // TC-U18: year = 1899, just below lower boundary
    @Test
    public void testDobFormat_YearBelowLower_Invalid() {
        assertFalse(LoginService.isValidDobFormat("1899-01-01"));
    }

    // TC-U19: year = 2100, upper boundary
    @Test
    public void testDobFormat_YearUpperBoundary_Valid() {
        assertTrue(LoginService.isValidDobFormat("2100-12-31"));
    }

    // TC-U20: year = 2101, just above upper boundary
    @Test
    public void testDobFormat_YearAboveUpper_Invalid() {
        assertFalse(LoginService.isValidDobFormat("2101-01-01"));
    }

    // --- BVA on month ---

    // TC-U21: month = 01 (minimum valid month)
    @Test
    public void testDobFormat_MonthLowerBoundary_Valid() {
        assertTrue(LoginService.isValidDobFormat("1990-01-15"));
    }

    // TC-U22: month = 00, just below minimum
    @Test
    public void testDobFormat_MonthBelowLower_Invalid() {
        assertFalse(LoginService.isValidDobFormat("1990-00-15"));
    }

    // TC-U23: month = 12, maximum valid month
    @Test
    public void testDobFormat_MonthUpperBoundary_Valid() {
        assertTrue(LoginService.isValidDobFormat("1990-12-15"));
    }

    // TC-U24: month = 13, just above maximum
    @Test
    public void testDobFormat_MonthAboveUpper_Invalid() {
        assertFalse(LoginService.isValidDobFormat("1990-13-15"));
    }

    // --- BVA on day ---

    // TC-U25: day = 01 (minimum valid day)
    @Test
    public void testDobFormat_DayLowerBoundary_Valid() {
        assertTrue(LoginService.isValidDobFormat("1990-06-01"));
    }

    // TC-U26: day = 00, just below minimum
    @Test
    public void testDobFormat_DayBelowLower_Invalid() {
        assertFalse(LoginService.isValidDobFormat("1990-06-00"));
    }

    // TC-U27: day = 31, maximum we allow
    @Test
    public void testDobFormat_DayUpperBoundary_Valid() {
        assertTrue(LoginService.isValidDobFormat("1990-01-31"));
    }

    // TC-U28: day = 32, just above max
    @Test
    public void testDobFormat_DayAboveUpper_Invalid() {
        assertFalse(LoginService.isValidDobFormat("1990-01-32"));
    }

    // --- Format validation tests ---

    // TC-U29: slashes instead of dashes
    @Test
    public void testDobFormat_SlashSeparator_Invalid() {
        assertFalse(LoginService.isValidDobFormat("1990/01/15"));
    }

    // TC-U30: date in dd-mm-yyyy order instead of yyyy-mm-dd
    @Test
    public void testDobFormat_WrongOrder_Invalid() {
        assertFalse(LoginService.isValidDobFormat("15-01-1990"));
    }

    // TC-U31: letters in the dob string
    @Test
    public void testDobFormat_Letters_Invalid() {
        assertFalse(LoginService.isValidDobFormat("YYYY-MM-DD"));
    }

    // TC-U32: null passed directly to isValidDobFormat
    @Test
    public void testDobFormat_Null_Invalid() {
        assertFalse(LoginService.isValidDobFormat(null));
    }

    // TC-U33: empty string
    @Test
    public void testDobFormat_Empty_Invalid() {
        assertFalse(LoginService.isValidDobFormat(""));
    }

    // TC-U34: incomplete date, missing day part
    @Test
    public void testDobFormat_Partial_Invalid() {
        assertFalse(LoginService.isValidDobFormat("1990-01"));
    }
}