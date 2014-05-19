package ebayPT;

public enum EAction implements IAction{
	LIST_AUCTIONS(EUserType.GUEST),
	ADD_USER(EUserType.GUEST),
	LIST_USERS(EUserType.ADMIN),
	ADD_PRODUCT(EUserType.REGISTERED),
	CREATE_AUCTION(EUserType.REGISTERED),
	BID(EUserType.REGISTERED),
	CLOSE_AUCTION(EUserType.REGISTERED),
	LIST_PRODUCTS(EUserType.REGISTERED),
	LIST_BIDS(EUserType.REGISTERED);
		
	private EUserType allowedUsers;
	
	private EAction(EUserType allowedUsers){
		this.allowedUsers = allowedUsers;
	}
	
	@Override
	public EUserType getAllowedUserType() {
		return this.allowedUsers;
	}

}
