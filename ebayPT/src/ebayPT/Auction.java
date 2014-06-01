package ebayPT;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

import ebayPT.exceptions.BiddingClosedAuctionException;
import ebayPT.exceptions.BiddingOwnAuctionException;
import ebayPT.exceptions.InvalidAuctionException;
import ebayPT.exceptions.LowBidAmountException;
import ebayPT.exceptions.NoBidsException;
import ebayPT.exceptions.ProductNotAvailableException;

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
	 * @throws ProductNotAvailableException Trying to create an auction to an
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
		
		this.product.setState(this.bids.size() == 0 ?
				EProductState.SALE : EProductState.SOLD);
		
		return this.getHighestBid();
	}

	@Override
	public IBid bid(IUser user, int amount) throws LowBidAmountException,
			BiddingClosedAuctionException, BiddingOwnAuctionException {
		
		if(!this.open)
			throw new BiddingClosedAuctionException();
		
		if(this.seller.equals(user))
			throw new BiddingOwnAuctionException();
		
		if(this.base > amount)
			throw new LowBidAmountException();
		
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
