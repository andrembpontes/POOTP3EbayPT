package ebayPT;

/**
 * An IUser represents a user in the ebayPT system.
 *
 * @author n42540: Rodrigo Simões; n42845: André Pontes
 */
public interface IUser {
	/**
	 * Gets the email of this user.
	 * @return user email
	 */
	String getEmail();
	
	/**
	 * Gets the name of this user.
	 * @return user's name
	 */
	String getName();
	
	/**
	 * Gets this user's username.
	 * @return username
	 */
	String getUsername();
	
	/**
	 * Gets this user's type
	 * @return user's type
	 */
	IUserType getUserType();
	
	/**
	 * Returns whether this IUser is the same as <code>user</code>.
	 * @param user an IUser against which this IUser will be compared
	 * @return true if this IUser equals <code>user</code>, false otherwise
	 */
	boolean equals(IUser user);
}
