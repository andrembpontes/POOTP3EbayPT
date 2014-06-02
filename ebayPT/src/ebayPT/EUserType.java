package ebayPT;

/**
 * Enumeration of different user types and correspondent access level
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 *
 */
public enum EUserType implements IUserType {
	/**
	 * Guest User
	 */
	GUEST,
	
	/**
	 * Registered
	 */
	USER,
	
	/**
	 * Administrator
	 */
	ADMIN;
		
	@Override
	public boolean hasAccessLevel(IUserType userType){
		return userType == null || this.equals(userType);
	}
}
