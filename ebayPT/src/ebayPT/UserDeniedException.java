package ebayPT;


public class UserDeniedException extends Exception {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private IUserType lowestType;
	
	public UserDeniedException(IUserType lowesType){
		this.lowestType = lowesType;
	}
	
	public IUserType getLowestType(){
		return this.lowestType;
	}
	
}
