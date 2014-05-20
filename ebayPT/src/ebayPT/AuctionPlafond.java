package ebayPT;

/**
 * This class represents a Plafond auction. A Plafond Auction is equals to a
 * Standard Auction but close automatically when receive a bid amount higher
 * than specified plafond
 * 
 * @author //TODO specify authors
 *
 */
public class AuctionPlafond extends Auction {

	private int plafond;	//Auction plafond
	
	/**
	 * Creates and open a plafond auction for givens seller, product and base
	 * amount with givens plafond.
	 * 
	 * @param seller: Seller user
	 * @param product: Auction product
	 * @param base: Minimum bid amount
	 * @param plafond: Auction's plafond
	 */
	public AuctionPlafond(IUser seller, IProduct product, int base,
			int plafond) {
		
		super(seller, product, base);
		
		this.plafond = plafond;
	}
	
	@Override
	public boolean bid(IUser bidder, int amount) throws LowBidAmountException,
			BiddingClosedAuctionException, BiddingOwnAuctionException{
		
		super.bid(bidder, amount);
		
		if(amount > this.plafond){
			try {
				this.close();
			} catch (NoBidsException e) {
				//Do nothing
				//At this point close() will never throws NoBidsException
				
			}
			
			return true;
		}
		
		return false;
	}

}
