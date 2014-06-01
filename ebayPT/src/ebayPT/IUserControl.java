package ebayPT;

import ebayPT.exceptions.AnotherUserAlreadyLoggedIn;
import ebayPT.exceptions.NoUserLoggedIn;
import ebayPT.exceptions.UserAlreadyLoggedIn;
import ebayPT.exceptions.UserDenied;

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
	 * @throws UserDenied: Actual user doesn't have permissions to
	 * execute givens action. Exception contains minimum user type needed to
	 * execute it
	 */
	void executeAction(IAction action) throws UserDenied;
	
	/**
	 * Login with given user
	 * 
	 * @param user user to log in
	 * @throws UserLoggedInException if there is another user currently logged in
	 * @throws AnotherUserAlreadyLoggedIn: Another user is already
	 * logged in 
	 * @throws UserAlreadyLoggedIn: Givens user is already logged in
	 */
	void login(IUser user) throws UserAlreadyLoggedIn,
		AnotherUserAlreadyLoggedIn;
	
	/**
	 * Returns and logout current user
	 * 
	 * @return Logged out user
	 * @throws NoUserLoggedIn if there is no user currently logged in
	 */
	IUser logout() throws NoUserLoggedIn;
	
	/**
	 * Returns actual logged user
	 * 
	 * @return actual logged user
	 * @throws NoUserLoggedIn: there is no user actual logged in
	 */
	IUser getLoggedUser() throws NoUserLoggedIn;
}
