package ebayPT;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Auction implements IAuction {

	private IUser seller;
	private IProduct product;
	private List<IBid> bids;
	private boolean open;
	
	public Auction(IUser seller, IProduct product){
		this.seller = seller;
		this.product = product;
		
		this.open = true;
		
		this.bids = new LinkedList<IBid>();
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
	public boolean bid(IUser user, int amount) {
		return this.bids.add(new Bid(user, amount));
	}

}
