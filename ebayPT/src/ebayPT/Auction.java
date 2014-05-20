package ebayPT;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Aims to represent an auction storing the seller, the product and all bids.
 * 
 * @author //TODO Specify authors
 * 
 */
public class Auction implements IAuction {

	private IUser seller;		//Seller user
	private IProduct product;	//Product's auction
	private Collection<IBid> bids;	//Bids list
	private boolean open;		//True if is open, False is is closed
	private int base;			//Minimum bid amount
	
	/**
	 * Creates and open a standard auction for givens seller, product and base
	 * amount.
	 * 
	 * @param seller: Seller user
	 * @param product: Auction product
	 * @param base: Minimum bid amount
	 */
	public Auction(IUser seller, IProduct product, int base){
		this.seller = seller;
		this.product = product;
		
		this.open = true;
		
		this.bids = new TreeSet<IBid>(); //TODO cant add duplicated entries
	}
	
	@Override
	public IUser getSeller() {
		return this.seller;
	}

	@Override
	public IProduct getProduct() {
		return this.product;
	}

	@Override
	public Iterator<IBid> getBids() {
		return this.bids.iterator();
	}

	@Override
	public boolean isOpen() {
		return this.open;
	}

	@Override
	public void close() throws NoBidsException{
		if(this.bids.size() == 0)
			throw new NoBidsException();
		
		this.open = false;
	}

	@Override
	public boolean bid(IUser user, int amount) throws LowBidAmountException,
			BiddingClosedAuctionException, BiddingOwnAuctionException {
		
		if(!this.open)
			throw new BiddingClosedAuctionException();
		
		if(this.base > amount)
			throw new LowBidAmountException();
		
		if(this.seller.equals(user))
			throw new BiddingOwnAuctionException();
		
		this.bids.add(new Bid(user, amount));
		
		return false;
	}

	@Override
	public IBid getWinnerBid() {
		if(this.open)
			return null;
		
		return this.bids.iterator().next();
	}

}
