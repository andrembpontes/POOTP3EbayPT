package ebayPT;

public interface IUserControl {
	
	/**
	 * Verify if current logged user can perform given action
	 * 
	 * @param action: Action to test
	 * @return True if action can be performed, False if not
	 */
	boolean isAllowed(IAction action);
	
	/**
	 * Simulates an action execution and throws UserDeniedException when actual
	 * logged user doesn't have needed permissions to execute it
	 * 
	 * @param action: action to execute
	 * @throws UserDeniedException: Actual user doesn't have permissions to
	 * execute givens action. Exception contains minimum user type needed to
	 * execute it
	 */
	void executeAction(IAction action) throws UserDeniedException;
	
	/**
	 * Login with given user
	 * 
	 * @param user user to log in
	 * @throws UserLoggedInException if there is another user currently logged in
	 * @throws AnotherUserAlreadyLoggedInException: Another user is already
	 * logged in 
	 * @throws UserAlreadyLoggedInException: Givens user is already logged in
	 */
	void login(IUser user) throws UserAlreadyLoggedInException,
		AnotherUserAlreadyLoggedInException;
	
	/**
	 * Returns and logout current user
	 * 
	 * @return Logged out user
	 * @throws NoUserLoggedInException if there is no user currently logged in
	 */
	IUser logout() throws NoUserLoggedInException;
	
	/**
	 * Returns actual logged user
	 * 
	 * @return actual logged user
	 * @throws NoUserLoggedInException: there is no user actual logged in
	 */
	IUser getLoggedUser() throws NoUserLoggedInException;
}
