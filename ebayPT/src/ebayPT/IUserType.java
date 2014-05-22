package ebayPT;
//TODO general comments

public interface IUserType {
	
	/**
	 * Verify if has access level of givens user type
	 * 
	 * @param userType: user access level to test
	 * @return True if has access level, False if not 
	 */
	boolean hasAccessLevel(EUserType userType);
}
