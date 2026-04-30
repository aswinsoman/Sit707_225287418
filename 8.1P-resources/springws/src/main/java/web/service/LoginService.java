package web.service;

/**
 * Business logic to handle login functions.
 *
 * @author __Ahsan__.
 */
public class LoginService {

	/**
	 * Static method returns true for successful login, false otherwise.
	 * @param username
	 * @param password
	 * @param dob
	 * @return
	 */
	public static boolean login(String username, String password, String dob) {
		// Match a fixed user name and password.
		//
		if ("ahsan".equals(username) && "ahsan_pass".equals(password)) {
			return true;
		}
		return false;
	}
}