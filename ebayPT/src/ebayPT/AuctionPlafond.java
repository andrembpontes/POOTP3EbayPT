package ebayPT;

import ebayPT.exceptions.BiddingClosedAuction;
import ebayPT.exceptions.BiddingOwnAuction;
import ebayPT.exceptions.InvalidAuction;
import ebayPT.exceptions.LowBidAmount;
import ebayPT.exceptions.NoBids;
import ebayPT.exceptions.ProductNotAvailable;

/**
 * Implementation of IAuction for a Plafond Auction. A Plafond Auction is
 * different than a Standard Auction in that it is closed automatically when a
 * bid is made that surpasses the specified plafond amount.
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 *
 */
public class AuctionPlafond extends Auction {

	/**
	 * Auction plafond
	 */
	private int plafond;
	
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
				this.getSeller().closeAuction(this.getProduct().getCode());
				
			} catch (NoBids|InvalidAuction e) {
				//Unreachable code
			}
			
			return this.getWinnerBid();
		}
		
		return null;
	}

}
