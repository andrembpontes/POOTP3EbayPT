package ebayPT;


/**
 * Represents an action that can be performed in ebay@Pt and respective user
 * type required to perform its
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 *
 */
public interface IAction {
	/**
	 * Returns minimum user type required to perform action
	 * 
	 * @return Minimum user type required to perform action
	 */
	EUserType getAllowedUserType();
}
