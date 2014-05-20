package ebayPT;

/**
 * Represents an auction bid, in other words, a pair IUser (bidder) / bid amount
 * 
 * @author //TODO specify authors
 *
 */
public class Bid implements IBid, Comparable<IBid>{

	private IUser bidder;
	private int amount, count;
	
	/**
	 * Creates new bid
	 * 
	 * @param bidder: Bidder user
	 * @param amount: Bid amount
	 * @param count: Chronological position
	 */
	public Bid(IUser bidder, int amount, int count){
		this.bidder = bidder;
		this.amount = amount;
		this.count = count;
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
	public int compareTo(IBid bid) {
		int amountDiff = this.amount - bid.getAmount();
		
		if(amountDiff == 0)
			return bid.getCount() - this.count;
		
		return amountDiff;
	}

	@Override
	public int getCount() {
		return this.count;
	}

}
