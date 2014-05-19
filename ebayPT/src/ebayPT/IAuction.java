package ebayPT;

import java.util.Iterator;

public interface IAuction {
	IUser getSeller();
	IProduct getProduct();
	Iterator<IBid> getBids();
	
	boolean isOpen();
	
	void close() throws NoBidsException;
	
	boolean bid(IUser user, int amount) throws LowBidAmountException;
}
