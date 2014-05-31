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
	
	public UserDeniedException(IUserType neededType){
		this.neededType = neededType;
	}
	
	public IUserType getNeededType(){
		return this.neededType;
	}
	
}
