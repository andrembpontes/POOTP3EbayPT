package ebayPT;

import java.util.Iterator;

/**
 * Aims to represent an auction storing seller user, respective auction product
 * and all current bids.
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 *
 */
public interface IAuction {
	/**
	 * Returns seller user
	 * 
	 * @return Seller user
	 */
	IUser getSeller();
	
	/**
	 * Returns current auction product
	 * 
	 * @return Current auction product
	 */
	IProduct getProduct();
	
	/**
	 * Creates and returns an iterator for all bids
	 * Bids list is sort first by descending bid amount and then by ascending
	 * chronological order
	 * 
	 * @return Iterator for all bids
	 */
	Iterator<IBid> getBids();
	
	/**
	 * Verify if auction is open
	 * Returns True if yes, else False
	 * 
	 * @return True if auction is open, False if not
	 */
	boolean isOpen();
	
	/**
	 * Close current auction and returns winner bid
	 * 
	 * @return Winner bid
	 * @throws NoBidsException: Trying to close an auction that have no bids
	 */
	IBid close() throws NoBidsException;
	
	/**
	 * Bid on auction
	 * 
	 * @param user: Bidder user
	 * @param amount: Bid amount
	 * @return True if bid become closed by consequence of bid
	 * @throws LowBidAmountException: Bid amount is lower than base amount
	 * @throws BiddingClosedAuctionException: Bidding a closed auction
	 * @throws BiddingOwnAuctionException: Bidder is auction seller
	 */
	boolean bid(IUser user, int amount) throws LowBidAmountException,
		BiddingClosedAuctionException, BiddingOwnAuctionException;
	
	/**
	 * Returns winner bid, null if auction is running
	 * 
	 * @return Winner bid, Null if there is no winner yet
	 */
	IBid getWinnerBid();
	
	/**
	 * Returns bid with highest amount
	 * 
	 * @return Bid with highest amount
	 * @throws NoBidsException: There's no bids on auction
	 */
	IBid getHighestBid() throws NoBidsException;
	
	/**
	 * Return base amount value
	 * 
	 * @return base amount value
	 */
	int getBaseAmount();
}
