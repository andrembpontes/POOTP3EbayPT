package ebayPT;

public interface IUserControl {
	
	/**
	 * Verify if current logged user can perform given action
	 * 
	 * @param action: Action to test
	 * @return True if action can be performed, False if not
	 */
	boolean isAllowed(EAction action);
	
	/**
	 * Login with given user
	 * 
	 * @param user user to log in
	 * @throws UserLoggedInException if there is another user currently logged in
	 */
	void login(IUser user) throws UserLoggedInException;
	
	/**
	 * Logout current user
	 * 
	 * @throws NoUserLoggedInException if there is no user currently logged in
	 */
	void logout() throws NoUserLoggedInException;
}
