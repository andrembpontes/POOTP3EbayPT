package ebayPT;

import java.util.Iterator;

public interface IEbayPT {
	void login(String username);
	void logout();
	
	Iterator<IProduct> getProducts();
	Iterator<IAuction> getAuctions(String category);
	Iterator<IAuction> getAuctionsTabletBySize(int size);
	Iterator<IBid> getBiddings(String sellerUsername, String productCode);
	Iterator<IUser> getUsers();
	Iterator<IUser> getUsersBySales();
	
	void addUser(String email, String name, String userType);
	
	void createAuctionStandard(String productCode, int basePrice);
	void createAuctionPlafond(String productCode, int basePrice, int plafond);
	
	/**
	 * Bid on givens auction.
	 * 
	 * @param sellerUsername: auction seller
	 * @param productCode: product code
	 * @param amount: bid amount
	 * @return if bid wins auction
	 */
	boolean bid(String sellerUsername, String productCode, int amount);
	
	IBid closeAuction(String productCode);
	
	void createCar(String code, String description, String make,
			String model, int year);
	
	void createTablet(String code, String description, String brand,
			int size, int weight);
}
