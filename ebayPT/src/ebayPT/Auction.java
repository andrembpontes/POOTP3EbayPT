package ebayPT;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

/**
 * Aims to represent an auction storing the seller, the product and all bids.
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 * 
 */
public class Auction implements IAuction, Comparable<IAuction> {

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
	 * @throws ProductNotAvailableException: Trying to create an auction to an
	 * unavailable product  
	 */
	public Auction(IUser seller, IProduct product, int base)
			throws ProductNotAvailableException{
		
		if(!product.isAvaliable())
			throw new ProductNotAvailableException();
		
		this.seller = seller;
		this.product = product;
		
		product.setState(EProductState.AUCTION);
		
		this.base = base;
		
		this.open = true;
		
		this.bids = new TreeSet<IBid>();
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
	public IBid close() throws NoBidsException{
		this.open = false;
		
		try {
			this.getSeller().reportClosedAuction(this.getProduct().getCode());
		}
		catch (InvalidAuctionException e) {
			//Not acceptable at this point
		}
		
		if(this.bids.size() == 0){
			this.product.setState(EProductState.SALE);
			throw new NoBidsException();
		}
		else{
			this.product.setState(EProductState.SOLD);
		}
		
		return this.getHighestBid();
	}

	@Override
	public IBid bid(IUser user, int amount) throws LowBidAmountException,
			BiddingClosedAuctionException, BiddingOwnAuctionException {
		
		if(!this.open)
			throw new BiddingClosedAuctionException();
		
		if(this.base > amount)
			throw new LowBidAmountException();
		
		if(this.seller.equals(user))
			throw new BiddingOwnAuctionException();
		
		this.bids.add(new Bid(user, amount, this.bids.size()));
		
		return null;
	}

	@Override
	public IBid getWinnerBid() {
		if(this.open)
			return null;
		
		try{
			return this.bids.iterator().next();
		}
		catch(NoSuchElementException e){
			return null;
		}
	}

	@Override
	public IBid getHighestBid() throws NoBidsException {
		if(this.bids.size() == 0)
			throw new NoBidsException();
		
		return this.bids.iterator().next();
	}

	@Override
	public int compareTo(IAuction auction) {
		int baseDiff = this.getBaseAmount() - auction.getBaseAmount();
		
		if(baseDiff == 0)
			return this.getProduct().getCode().
					compareTo(auction.getProduct().getCode());
		
		return baseDiff;
	}

	@Override
	public int getBaseAmount() {
		return this.base;
	}

}
