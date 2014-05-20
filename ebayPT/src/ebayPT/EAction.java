package ebayPT;

/**
 * Enumeration containing all of actions (features) of ebay@Pt and which access
 * level is required to perform its actions.
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 *
 */
public enum EAction implements IAction{
											//Permissions list:
	LIST_AUCTIONS(EUserType.GUEST),			//List all auctions
	ADD_USER(EUserType.GUEST),				//Add a new user
	LIST_USERS(EUserType.ADMIN),			//List all users
	ADD_PRODUCT(EUserType.REGISTERED),		//Create a new product
	CREATE_AUCTION(EUserType.REGISTERED),	//Open a new auction
	BID(EUserType.REGISTERED),				//Bid on an open auction
	CLOSE_AUCTION(EUserType.REGISTERED),	//Close auctions
	LIST_PRODUCTS(EUserType.REGISTERED),	//List own products
	LIST_BIDS(EUserType.REGISTERED);		//List auction bids
		
	private EUserType allowedUsers;		//Minimun user access level required
	
	private EAction(EUserType allowedUsers){
		this.allowedUsers = allowedUsers;
	}
	
	@Override
	public EUserType getAllowedUserType() {
		return this.allowedUsers;
	}

}
