package ebayPT;

public interface IUserControl {
	
	/**
	 * Verify if current logged user can perform givens action
	 * 
	 * @param action: Action to test
	 * @return True if action can be performed, False if not
	 */
	boolean isAllowed(EAction action);
	
	//TODO add exception to when is already someone logged in
	/**
	 * Login with givensuser
	 */
	void login(IUser user);
	
	/**
	 * Logout current user
	 */
	void logout();
}
