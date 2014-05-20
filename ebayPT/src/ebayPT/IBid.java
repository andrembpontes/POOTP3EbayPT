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
		
	/**
	 * Returns chronological bid order on correspondent auction.
	 * 
	 * Note: Counter is 1-based
	 * 
	 * @return Chronological bid order on auction
	 */
	int getCount();
}
