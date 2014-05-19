package ebayPT;

import java.util.Iterator;

public interface IAuction {
	IUser getSeller();
	IProduct getProduct();
	Iterator<IBid> getBids();
	
	boolean isOpen();
	
	void close();
	void bid(IUser user, int amount);
}
