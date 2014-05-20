package ebayPT;

public interface IBid {
	/**
	 * Returns bidder user
	 * 
	 * @return Bidder user
	 */
	IUser getBidder();
	
	/**
	 * Returns bid amount
	 * 
	 * @return Bid amount
	 */
	int getAmount();
}
