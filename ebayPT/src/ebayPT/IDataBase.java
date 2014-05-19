package ebayPT;

import java.util.Iterator;

public interface IDataBase {

	void addProduct(IProduct product);
	
	void addAuction(IAuction auction);
	
	void addUser(IUser user);
	
	IUser getUser(String username);
	
	IAuction getAuction(IUser seller, IProduct product);

	IProduct getProduct(IUser owner, String productCode);
	
	Iterator<IUser> getUsers();
	
	Iterator<IUser> getUsersBySales();
	
	Iterator<IProduct> getProducts(IUser owner);
	
	Iterator<IAuction> getAuctions(EProductCategory category);
	
	Iterator<IAuction> getAuctionsTabletBySize(int size);
	
	Iterator<IBid> getBiddings(IUser seller, String productCode);
}
