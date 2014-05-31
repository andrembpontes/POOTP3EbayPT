package ebayPT;

/**
 * Exception used to signal that the logged user doesn't have permission
 * to execute an action.
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */

public class UserDeniedException extends Exception {
	
	private static final long	serialVersionUID	= 1L;
	
	private IUserType neededType;
	
	/**
	 * Constructor
	 * @param neededType the user type that is necessary to perform an action
	 */
	public UserDeniedException(IUserType neededType){
		this.neededType = neededType;
	}
	
	/**
	 * Gets the user type necessary to perform an action
	 * @return the user type necessary to perform an action
	 */
	public IUserType getNeededType(){
		return this.neededType;
	}
	
}
