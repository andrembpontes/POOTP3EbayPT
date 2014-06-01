package ebayPT;

import ebayPT.exceptions.BiddingClosedAuction;
import ebayPT.exceptions.BiddingOwnAuction;
import ebayPT.exceptions.LowBidAmount;
import ebayPT.exceptions.NoBids;
import ebayPT.exceptions.ProductNotAvailable;

/**
 * Implementation of IAuction for a Plafond Auction. A Plafond Auction is
 * different than a Standard Auction in that it is closed automatically when a
 * bid is made that is equal to or greater than the specified plafond amount.
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 *
 */
public class AuctionPlafond extends Auction {

	private int plafond;	//Auction plafond
	
	/**
	 * Creates and opens a plafond auction.
	 * 
	 * @param seller Seller user
	 * @param product Auction product
	 * @param base Minimum bid amount
	 * @param plafond Auction's plafond
	 * @throws ProductNotAvailable Trying to create an auction for an
	 * unavailable product
	 */
	public AuctionPlafond(IUser seller, IProduct product, int base,
			int plafond) throws ProductNotAvailable  {
		
		super(seller, product, base);
		
		this.plafond = plafond;
	}
	
	@Override
	public IBid bid(IUser bidder, int amount) throws LowBidAmount,
			BiddingClosedAuction, BiddingOwnAuction{
		
		super.bid(bidder, amount);
		
		if(amount > this.plafond){
			try {
				this.close();
				
			} catch (NoBids e) {
				//Do nothing
				//At this point this exceptions are not acceptable
			}
			
			return this.getWinnerBid();
		}
		
		return null;
	}

}
