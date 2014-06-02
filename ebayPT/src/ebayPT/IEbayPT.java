package ebayPT;

import java.util.Iterator;

import ebayPT.exceptions.AnotherUserAlreadyLoggedIn;
import ebayPT.exceptions.BiddingClosedAuction;
import ebayPT.exceptions.BiddingOwnAuction;
import ebayPT.exceptions.InvalidAuction;
import ebayPT.exceptions.InvalidProduct;
import ebayPT.exceptions.InvalidUser;
import ebayPT.exceptions.InvalidUserType;
import ebayPT.exceptions.LowBidAmount;
import ebayPT.exceptions.NoBids;
import ebayPT.exceptions.NoUserLoggedIn;
import ebayPT.exceptions.NotSeller;
import ebayPT.exceptions.ProductAlreadyExists;
import ebayPT.exceptions.ProductNotAvailable;
import ebayPT.exceptions.UserAlreadyExists;
import ebayPT.exceptions.UserAlreadyLoggedIn;
import ebayPT.exceptions.UserDenied;


/**
 * Ebay@PT top application's class
 *
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */
public interface IEbayPT {
	
	/**
	 * Logs in user represented by <code>username</code>
	 * 
	 * @param username: username of user to login
	 * already logged in
	 * @throws InvalidUser: There is no user with given username
	 * @throws UserAlreadyLoggedIn: Given user is already logged in
	 * @throws AnotherUserAlreadyLoggedIn: Another user is already
	 * logged in
	 */
	void login(String username)
			throws InvalidUser, AnotherUserAlreadyLoggedIn,
			UserAlreadyLoggedIn;
	
	/**
	 * Returns and Logs out current user.
	 * @return Logged out user
	 * @throws NoUserLoggedIn: Trying to logout with no user logged in
	 */
	IUser logout() throws NoUserLoggedIn;
	
	/**
	 * Gets an iterator to all products of the user currently logged in.
	 * The products are sorted by alphabetical order.
	 * 
	 * @return iterator to all products of currently logged user or null if the
	 * user has no products in the database
	 * @throws UserDenied: Actual user cannot execute this task
	 */
	Iterator<IProduct> getProducts() throws UserDenied;
	
	/**
	 * Gets an iterator to all auctions in the database of a given category of
	 * the user currently logged in. The auctions are sorted by ascending
	 * order of the auction product's base price, and by alphabetical order in
	 * the case of ties.
	 * 
	 * @param category: one of IEbayPT.TABLET or IEBayPT.CAR
	 * @return iterator to all auctions of a given category or null if there are
	 * no auctions of that category in the database
	 * @throws UserDenied: Actual user cannot execute this task 
	 */
	Iterator<IAuction> getAuctions(EProductCategory category)
			throws UserDenied;
	
	/**
	 * Gets an iterator to all auctions in the database of Tablet products,
	 * up to a given maximum Tablet dimension. The auctions are sorted by
	 * the ascending order of the Tablet's sizes, and by alphabetical order in
	 * the case of ties.
	 * 
	 * @param size: maximum size of tablet display to include in result
	 * @return iterator to all auctions of Tablet products up to a maximum
	 * Tablet size or null if there are no such auctions in the database
	 * @throws UserDenied: Actual user cannot execute this task 
	 */
	Iterator<IAuction> getAuctionsTabletBySize(int size) throws UserDenied;
	
	/**
	 * Gets an iterator to all bids on a given auction. The bids are sorted by
	 * descending order of value, and by time in the system in the case of ties
	 * (i.e. the bid which was registered first is listed first).
	 * 
	 * @param sellerUsername: username of the seller of the product being auctioned
	 * @param productCode: product code of the product being auctioned
	 * @return iterator to all bids on a given auction
	 * @throws UserDenied: Actual user cannot execute this task
	 * @throws NotSeller: Auction is open and logged user isn't seller
	 * @throws InvalidAuction: There is no auction in the database identified
	 * with <code>sellerUsername</code> and <code>productCode</code>
	 */
	Iterator<IBid> getBiddings(String sellerUsername, String productCode)
			throws UserDenied, InvalidAuction,
			NotSeller;
	
	/**
	 * Gets an iterator to all the users in the database. Administrator accounts
	 * are listed first, followed by regular users. Users of the same type are
	 * sorted by username alphabetical order.
	 * 
	 * @return iterator to all the users in the database
	 * @throws UserDenied: Actual user cannot execute this task 
	 */
	Iterator<IUser> getUsers() throws UserDenied;
	
	/**
	 * Gets an iterator to all the regular users in the database, sorted by
	 * total value of sold products (including only auctions that are already
	 * closed). Alphabetical order of usernames are used in the case of ties.
	 * 
	 * @return iterator to regular users sorted by total amount of sales
	 * @throws UserDenied: Actual user cannot execute this task 
	 */
	Iterator<IUser> getUsersBySales() throws UserDenied;
	
	/**
	 * Adds a new user to the database.
	 * 
	 * @param email: email of user
	 * @param name: real name of user
	 * @param username: unique name used to identify user in the database
	 * @param userType: either EUserType.USER.toString() or EUserType.ADMIN.toString()
	 * @throws InvalidUserType: Given userType isn't a valid user type
	 * @throws UserDenied: Actual user cannot execute this task 
	 * @throws UserAlreadyExists: Specified username already exist
	 */
	void addUser(String email, String name, String username, String userType)
			throws InvalidUserType, UserDenied,
			UserAlreadyExists;
	
	/**
	 * Create a standard auction with given data with actual logged user as
	 * the auction's seller
	 * 
	 * @param productCode: code of the product to auction
	 * @param basePrice: base price of the auction
	 * @throws InvalidProduct: Actual user doesn't have a product whose code is
	 * equal to <code>productCode</code>
	 * @throws UserDenied: Actual user cannot execute this task
	 * @throws ProductNotAvaliableException: Product is already sold or is
	 * currently being auctioned
	 */
	void createAuctionStandard(String productCode, int basePrice)
			throws UserDenied, InvalidProduct,
			ProductNotAvailable;
	
	/**
	 * Create a plafond auction with given data with actual logged user as
	 * the auction's seller
	 *  
	 * @param productCode: code of the product to auction
	 * @param basePrice: base price of the auction
	 * @param plafond: plafond value
	 * @throws UserDenied: Actual user cannot execute this task 
	 * @throws InvalidProduct: Actual user doesn't have a product whose code is
	 * equal to <code>productCode</code>
	 * @throws ProductNotAvaliableException: Product is already sold or is
	 * currently being auctioned
	 */
	void createAuctionPlafond(String productCode, int basePrice, int plafond)
			throws InvalidProduct, UserDenied,
			ProductNotAvailable;
	
	/**
	 * Bid on the given auction. This method returns true if the auction is
	 * a plafond auction and this bid wins it by matching the value of the plafond.
	 * It returns false in any other case.
	 * 
	 * @param sellerUsername: auction seller
	 * @param productCode: product code
	 * @param amount: bid amount
	 * @return Winner bid if the auction closes after this bid is made, null otherwise
	 * @throws BiddingOwnAuction: Seller trying to bid on own auction
	 * @throws BiddingClosedAuction: Trying to bid on a closed auction
	 * @throws LowBidAmount: Bid amount is lower than the auction's base amount 
	 * @throws UserDenied: Actual user cannot execute this task
	 * @throws InvalidAuction: There is no auction with seller whose user name is
	 * equal to <code>sellerUsername</code> and whose product's code is equal to
	 * <code>productCode</code>
	 */
	IBid bid(String sellerUsername, String productCode, int amount)
			throws UserDenied, LowBidAmount,
			BiddingClosedAuction, BiddingOwnAuction,
			InvalidAuction;
	
	/**
	 * Closes the auction of the current logged user identified by
	 * <code>productCode</code> and returns the winning bid. If there are no
	 * bids on the auction then a <code>NoBids</code> exception is thrown.
	 * 
	 * @param productCode: code of the product whose auction is to be closed
	 * @return winning bid
	 * @throws NoBids: Closing an auction with no bids
	 * @throws UserDenied: Actual user cannot execute this task
	 * @throws InvalidAuction: There is no auction in the database for product
	 * identified with <code>productCode</code>
	 */
	IBid closeAuction(String productCode)
			throws NoBids, UserDenied,
			InvalidAuction;
	
	/**
	 * Adds a Car product to the database.
	 * 
	 * @param code: code used to identify the car -- a user can't have more
	 * than one product with the same code
	 * @param description: description of the car
	 * @param make: make of the car
	 * @param model: model of the car
	 * @param year: year of the car
	 * @throws ProductAlreadyExists: Actual user already has a product whose
	 * product code is equal to <code>code</code>
	 * @throws UserDenied: Actual user cannot execute this task 
	 */
	void createCar(String code, String description, String make,
			String model, int year)
					throws UserDenied, ProductAlreadyExists;
	
	/**
	 * Adds a Tablet product to the database.
	 * 
	 * @param code code used to identify the tablet -- a user can't have more
	 * than one product with the same code
	 * @param description: description of the tablet
	 * @param brand: brand of the tablet
	 * @param size: size of the tablet
	 * @param weight: weight of the tablet
	 * @throws ProductAlreadyExists: Actual user already has a product whose
	 * product code is equal to <code>code</code>
	 * @throws UserDenied: Actual user cannot execute this task 
	 */
	void createTablet(String code, String description, String brand,
			int size, int weight) throws UserDenied, ProductAlreadyExists;
}
