package ebayPT;

/**
 * Enumeration containing all of actions (features) of ebay@Pt and which access
 * level is required to perform its actions.
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 *
 */
public enum EAction implements IAction{
	
	/**
	 * List all auctions
	 */
	LIST_AUCTIONS(null),
	
	/**
	 * Add a new user
	 */
	ADD_USER(EUserType.GUEST),
	
	/**
	 * List all users
	 */
	LIST_USERS(EUserType.ADMIN),
	
	/**
	 * Create a new product
	 */
	ADD_PRODUCT(EUserType.USER),
	
	/**
	 * Open a new auction
	 */
	CREATE_AUCTION(EUserType.USER),
	
	/**
	 * Bid on an open auction
	 */
	BID(EUserType.USER),
	
	/**
	 * Close auctions
	 */
	CLOSE_AUCTION(EUserType.USER),
	
	/**
	 * List own products
	 */
	LIST_PRODUCTS(EUserType.USER),
	
	/**
	 * List auction bids
	 */
	LIST_BIDS(EUserType.USER);
		
	/**
	 * Users that can execute action
	 * (null = no restrictions)
	 */
	private EUserType allowedUsers;
	
	/**
	 * Create a action condition with givens restrictions
	 * 
	 * @param allowedUsers: Type of users allowed to do this action,
	 * null if all allowed
	 */
	private EAction(EUserType allowedUsers){
		this.allowedUsers = allowedUsers;
	}
	
	@Override
	public EUserType getAllowedUserType() {
		return this.allowedUsers;
	}

}
