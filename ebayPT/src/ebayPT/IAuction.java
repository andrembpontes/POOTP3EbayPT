package ebayPT;

import java.util.Iterator;

import ebayPT.exceptions.BiddingClosedAuction;
import ebayPT.exceptions.BiddingOwnAuction;
import ebayPT.exceptions.LowBidAmount;
import ebayPT.exceptions.NoBids;

/**
 * An IAuction type object represents an auction and manages the bids that were
 * made in said auction. In addition to that it keeps track of the product being
 * auctioned, its seller, the current state of the auction (open or close) and its
 * minimum bid amount.
 * 
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 *
 */
public interface IAuction extends Comparable<IAuction>{
	/**
	 * Returns seller user
	 * 
	 * @return Seller user
	 */
	IUser getSeller();
	
	/**
	 * Returns auctioned product
	 * 
	 * @return Auctioned product
	 */
	IProduct getProduct();
	
	/**
	 * Get iterator to bids on this auction, listed by descending bid amount
	 * and then by ascending chronological order
	 * 
	 * @return Iterator for all bids
	 */
	Iterator<IBid> getBids();
	
	/**
	 * Verify if auction is open
	 * 
	 * @return True if auction is open, False if not
	 */
	boolean isOpen();
	
	/**
	 * Close current auction and return winner bid. If there are no bids, the
	 * auctioned product returns to its Sale state and a NoBidsException is thrown.
	 * 
	 * @return Winner bid
	 * @throws NoBids When the auction has no bids
	 */
	IBid close() throws NoBids;
	
	/**
	 * Bid on auction
	 * 
	 * @param user Bidding user
	 * @param amount Bid amount
	 * @return Bid if actual bid won, null otherwise
	 * @throws LowBidAmount Bid amount is lower than base amount
	 * @throws BiddingClosedAuction This auction is closed
	 * @throws BiddingOwnAuction <code>user</code> is this auction's seller
	 */
	IBid bid(IUser user, int amount) throws LowBidAmount,
		BiddingClosedAuction, BiddingOwnAuction;
	
	/**
	 * Returns winner bid or null if auction is open
	 * 
	 * @return Winner bid, null if there is no winner yet
	 */
	IBid getWinnerBid();
	
	/**
	 * Gets highest bid. If there are several bids with a winning amount, then
	 * the first of those to be made is considered highest.
	 * 
	 * @return highest bid
	 * @throws NoBids No bids were made on this auction
	 */
	IBid getHighestBid() throws NoBids;
	
	/**
	 * Return base bid amount
	 * 
	 * @return base bid amount
	 */
	int getBaseAmount();
}
