package web.service;

public class LoginService {

    // hardcoded user for now, would normally come from a DB
    private static final String VALID_USERNAME = "ahsan";
    private static final String VALID_PASSWORD = "ahsan_pass";
    private static final String VALID_DOB = "1990-12-01";

    /**
     * Checks if the given username, password and dob match our stored user.
     * dob needs to be in yyyy-MM-dd format.
     */
    public static boolean login(String username, String password, String dob) {

        // reject anything null or blank straight away
        if (username == null || username.trim().isEmpty()) return false;
        if (password == null || password.trim().isEmpty()) return false;
        if (dob      == null || dob.trim().isEmpty())      return false;

        // also check dob is actually a valid date string before comparing
        if (!isValidDobFormat(dob)) return false;

        return VALID_USERNAME.equals(username)
            && VALID_PASSWORD.equals(password)
            && VALID_DOB.equals(dob);
    }

    /**
     * Quick check that dob looks like yyyy-MM-dd and the numbers are in range.
     * Not doing full calendar validation (like feb 30) but covers the obvious cases.
     */
    public static boolean isValidDobFormat(String dob) {
        if (dob == null) return false;

        // must match exactly 4 digits, dash, 2 digits, dash, 2 digits
        if (!dob.matches("\\d{4}-\\d{2}-\\d{2}")) return false;

        String[] parts = dob.split("-");
        int year  = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day   = Integer.parseInt(parts[2]);

        // reasonable year range
        if (year < 1900 || year > 2100) return false;
        // month 1-12
        if (month < 1 || month > 12)    return false;
        // day 1-31 basic check
        if (day < 1 || day > 31)        return false;

        return true;
    }
}