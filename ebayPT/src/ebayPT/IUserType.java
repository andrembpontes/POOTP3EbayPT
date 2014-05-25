package ebayPT;

/**
 * An IUserType represents a type of user account.
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */

public interface IUserType{
	
	/**
	 * To allow for complex access restrictions, this method is used to check
	 * if a given type of user can perform the same actions as another type.
	 * 
	 * @param userType: the IUserType against which this IUserType is to be tested
	 * @return true if this IUserType can perform the same actions as
	 * <code>userType</code>, false otherwise
	 */
	boolean hasAccessLevel(IUserType userType);
}
