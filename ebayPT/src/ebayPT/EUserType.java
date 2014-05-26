package ebayPT;

/**
 * Enumeration of different user types and correspondent access level
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 *
 */
public enum EUserType implements IUserType {
					//User types enumeration
	GUEST,		//Guest	User
	USER,		//Registered
	ADMIN;		//Administrator
		
	@Override
	public boolean hasAccessLevel(IUserType userType){
		return this.equals(userType) || userType.equals(GUEST);
	}
}
