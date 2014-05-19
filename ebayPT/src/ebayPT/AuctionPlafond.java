package ebayPT;

public class AuctionPlafond extends Auction {

	private int plafond;
	
	public AuctionPlafond(IUser seller, IProduct product, int base, int plafond) {
		super(seller, product, base);
		
		this.plafond = plafond;
	}
	
	@Override
	public boolean bid(IUser bidder, int amount) throws LowBidAmountException{
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
