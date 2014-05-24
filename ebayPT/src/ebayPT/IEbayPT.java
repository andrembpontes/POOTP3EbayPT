package ebayPT;

import java.util.Iterator;


/**
 * Ebay application.
 *
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */
public interface IEbayPT {
	
	
	//TODO com as enumerações é mesmo necessario especificalas aqui?
	
	/**
	 * Constant representing Tablet product category.
	 */
	static final String TABLET = EProductCategory.TABLET.toString();
	
	/**
	 * Constant representing Car product category.
	 */
	static final String CAR = EProductCategory.CAR.toString();
	
	/**
	 * Constant representing regular user type.
	 */
	static final String REGISTERED = EUserType.REGISTERED.toString();
	
	/**
	 * Constant representing admin user type.
	 */
	static final String ADMIN = EUserType.ADMIN.toString();
	
	/**
	 * Logs in user represented by <code>username</code>
	 * 
	 * @param username: username of user to login
	 */
	void login(String username);
	
	/**
	 * Logs out current user.
	 */
	void logout();
	
	/**
	 * Gets an iterator to all products of the user currently logged in.
	 * The products are sorted by alphabetical order.
	 * 
	 * @return iterator to all products of currently logged user
	 */
	Iterator<IProduct> getProducts();
	
	/**
	 * Gets an iterator to all auctions in the database of a given category of
	 * the user currently logged in. The auctions are sorted by ascending
	 * order of the auction product's base price, and by alphabetical order in
	 * the case of ties.
	 * 
	 * @param category: one of IEbayPT.TABLET or IEBayPT.CAR
	 * @return iterator to all auctions of a given category
	 */
	Iterator<IAuction> getAuctions(String category);
	
	/**
	 * Gets an iterator to all auctions in the database of Tablet products,
	 * up to a given maximum Tablet dimension. The auctions are sorted by
	 * the ascending order of the Tablet's sizes, and by alphabetical order in
	 * the case of ties.
	 * 
	 * @param size: maximum size of tablet display to include in result
	 * @return iterator to all auctions of Tablet products up to a maximum Tablet size
	 */
	Iterator<IAuction> getAuctionsTabletBySize(int size);
	
	/**
	 * Gets an iterator to all bids on a given auction. The bids are sorted by
	 * descending order of value, and by time in the system in the case of ties
	 * (i.e. the bid which was registered first is listed first).
	 * 
	 * @param sellerUsername: username of the seller of the product being auctioned
	 * @param productCode: product code of the product being auctioned
	 * @return iterator to all bids on a given auction
	 */
	Iterator<IBid> getBiddings(String sellerUsername, String productCode);
	
	/**
	 * Gets an iterator to all the users in the database. Administrator accounts
	 * are listed first, followed by regular users. Users of the same type are
	 * sorted by username alphabetical order.
	 * 
	 * @return iterator to all the users in the database
	 */
	Iterator<IUser> getUsers();
	
	/**
	 * Gets an iterator to all the regular users in the database, sorted by
	 * total value of sold products (including only auctions that are already
	 * closed). Alphabetical order of usernames are used in the case of ties.
	 * 
	 * @return iterator to regular users sorted by total amount of sales
	 */
	Iterator<IUser> getUsersBySales();
	
	/**
	 * Adds a new user to the database.
	 * 
	 * @param email: email of user
	 * @param name: real name of user
	 * @param username: unique name used to identify user in the database
	 * @param userType: one of IEbayPT.REGISTERED or IEbayPT.ADMIN
	 */
	void addUser(String email, String name, String username, String userType);
	
	/**
	 * Create a standard auction.
	 * 
	 * @param productCode: code of the product to auction
	 * @param basePrice: base price of the auction
	 */
	void createAuctionStandard(String productCode, int basePrice);
	
	/**
	 * Create a plafond auction.
	 * 
	 * @param productCode: code of the product to auction
	 * @param basePrice: base price of the auction
	 * @param plafond: plafond value
	 */
	void createAuctionPlafond(String productCode, int basePrice, int plafond);
	
	/**
	 * Bid on the given auction. This method returns true if the auction is
	 * a plafond auction and this bid wins it by matching the value of the plafond.
	 * It returns false in any other case.
	 * 
	 * @param sellerUsername: auction seller
	 * @param productCode: product code
	 * @param amount: bid amount
	 * @return true if bid matches plafond, false in any other case
	 */
	boolean bid(String sellerUsername, String productCode, int amount);
	
	/**
	 * Closes the auction of the current logged user identified by
	 * <code>productCode</code> and returns the winning bid.
	 * 
	 * @param productCode: code of the product whose auction is to be closed
	 * @return winning bid
	 */
	IBid closeAuction(String productCode);
	
	/**
	 * Adds a Car product to the database.
	 * 
	 * @param code: code used to identify the car -- a user can't have more
	 * than one product with the same code
	 * @param description: description of the car
	 * @param make: make of the car
	 * @param model: model of the car
	 * @param year: year of the car
	 */
	void createCar(String code, String description, String make,
			String model, int year);
	
	/**
	 * Adds a Tablet product to the database.
	 * 
	 * @param code code used to identify the tablet -- a user can't have more
	 * than one product with the same code
	 * @param description: description of the tablet
	 * @param brand: brand of the tablet
	 * @param size: size of the tablet
	 * @param weight: weight of the tablet
	 */
	void createTablet(String code, String description, String brand,
			int size, int weight);
}
