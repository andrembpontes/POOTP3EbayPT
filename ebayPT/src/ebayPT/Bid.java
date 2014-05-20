package ebayPT;

/**
 * Represents an auction bid, in other words, a pair IUser (bidder) / bid amount
 * 
 * @author //TODO specify authors
 *
 */
public class Bid implements IBid, Comparable<IBid>{

	private IUser bidder;
	private int amount;
	
	/**
	 * Creates new bid
	 * 
	 * @param bidder: Bidder user
	 * @param amount: Bid amount
	 */
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

	@Override
	public int compareTo(IBid arg0) {
		return this.amount - arg0.getAmount();
	}

}
