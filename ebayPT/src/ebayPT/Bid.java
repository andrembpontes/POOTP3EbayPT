package ebayPT;

public class Bid implements IBid {

	private IUser bidder;
	private int amount;
	
	public Bid(IUser bidder, int amount){
		this.bidder = bidder;
		this.amount = amount;
	}
	
	@Override
	public IUser getBidder() {
		return this.bidder;
	}

	@Override
	public int getAmount() {
		return this.amount;
	}

}
