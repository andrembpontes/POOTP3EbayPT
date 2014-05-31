package ebayPT;


public class UserDeniedException extends Exception {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private IUserType neededType;
	
	public UserDeniedException(IUserType neededType){
		this.neededType = neededType;
	}
	
	public IUserType getNeededType(){
		return this.neededType;
	}
	
}
