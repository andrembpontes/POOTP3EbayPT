package ebayPT;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

import ebayPT.exceptions.BiddingClosedAuction;
import ebayPT.exceptions.BiddingOwnAuction;
import ebayPT.exceptions.LowBidAmount;
import ebayPT.exceptions.NoBids;
import ebayPT.exceptions.ProductNotAvailable;

/**
 * Implementation of IAuction for a Standard Auction.
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 * 
 */
public class Auction implements IAuction, Comparable<IAuction> {

	private IUser seller;		//Seller user
	private IProduct product;	//Auctioned product
	private Collection<IBid> bids;	//List of bids
	private boolean open;		//Whether the auction is open
	private int base;			//Minimum starting bid amount
	
	/**
	 * Creates and opens a standard auction.
	 * 
	 * @param seller Seller user
	 * @param product Auction product
	 * @param base Minimum bid amount
	 * @throws ProductNotAvailable Trying to create an auction to an
	 * unavailable product
	 */
	public Auction(IUser seller, IProduct product, int base)
			throws ProductNotAvailable{
		
		if(!product.isAvaliable())
			throw new ProductNotAvailable();
		
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
	public IBid close() throws NoBids{
		this.open = false;
		
		this.product.setState(this.bids.size() == 0 ?
				EProductState.SALE : EProductState.SOLD);
		
		return this.getHighestBid();
	}

	@Override
	public IBid bid(IUser user, int amount) throws LowBidAmount,
			BiddingClosedAuction, BiddingOwnAuction {
		
		if(!this.open)
			throw new BiddingClosedAuction();
		
		if(this.seller.equals(user))
			throw new BiddingOwnAuction();
		
		if(this.base > amount)
			throw new LowBidAmount();
		
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
	public IBid getHighestBid() throws NoBids {
		if(this.bids.size() == 0)
			throw new NoBids();
		
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
