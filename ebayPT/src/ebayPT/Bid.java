package ebayPT;

/**
 * Represents an auction bid, in other words, a pair IUser (bidder) / bid amount
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
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
		int amountDiff = bid.getAmount() - this.amount;
		
		if(amountDiff == 0)
			return this.count - bid.getCount();
		
		return amountDiff;
	}

	@Override
	public int getCount() {
		return this.count;
	}

}
