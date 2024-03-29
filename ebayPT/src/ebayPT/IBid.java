package ebayPT;

/**
 * A bid in an auction, in other words, a pair IUser (bidder) / bid amount.
 * Each auction bid contains respective chronological position
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 *
 */
public interface IBid extends Comparable<IBid>{
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
