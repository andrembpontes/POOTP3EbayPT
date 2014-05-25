package ebayPT;

import java.util.Iterator;


/**
 * Ebay@PT top application's class
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
	 * @throws UserLoggedInException: Trying to log in when there's a user
	 * already logged in
	 */
	void login(String username) throws UserLoggedInException;
	
	/**
	 * Logs out current user.
	 * @throws NoUserLoggedInException: Trying to logout with no user logged in
	 */
	void logout() throws NoUserLoggedInException;
	
	/**
	 * Gets an iterator to all products of the user currently logged in.
	 * The products are sorted by alphabetical order.
	 * 
	 * @return iterator to all products of currently logged user
	 * @throws UserDeniedException: Actual user cannot execute this task: Actual user cannot execute this task
	 */
	Iterator<IProduct> getProducts() throws UserDeniedException;
	
	/**
	 * Gets an iterator to all auctions in the database of a given category of
	 * the user currently logged in. The auctions are sorted by ascending
	 * order of the auction product's base price, and by alphabetical order in
	 * the case of ties.
	 * 
	 * @param category: one of IEbayPT.TABLET or IEBayPT.CAR
	 * @return iterator to all auctions of a given category
	 * @throws UserDeniedException: Actual user cannot execute this task 
	 */
	Iterator<IAuction> getAuctions(EProductCategory tablet) throws UserDeniedException;
	
	/**
	 * Gets an iterator to all auctions in the database of Tablet products,
	 * up to a given maximum Tablet dimension. The auctions are sorted by
	 * the ascending order of the Tablet's sizes, and by alphabetical order in
	 * the case of ties.
	 * 
	 * @param size: maximum size of tablet display to include in result
	 * @return iterator to all auctions of Tablet products up to a maximum Tablet size
	 * @throws UserDeniedException: Actual user cannot execute this task 
	 */
	Iterator<IAuction> getAuctionsTabletBySize(int size) throws UserDeniedException;
	
	/**
	 * Gets an iterator to all bids on a given auction. The bids are sorted by
	 * descending order of value, and by time in the system in the case of ties
	 * (i.e. the bid which was registered first is listed first).
	 * 
	 * @param sellerUsername: username of the seller of the product being auctioned
	 * @param productCode: product code of the product being auctioned
	 * @return iterator to all bids on a given auction
	 * @throws UserDeniedException: Actual user cannot execute this task 
	 */
	Iterator<IBid> getBiddings(String sellerUsername, String productCode) throws UserDeniedException;
	
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
	 * @throws InvalidUserTypeException: Givens userType isn't a valid user type
	 * @throws UserDeniedException: Actual user cannot execute this task 
	 */
	void addUser(String email, String name, String username, String userType) throws InvalidUserTypeException, UserDeniedException;
	
	/**
	 * Create a standard auction with givens data with actual logged user as
	 * auction's seller
	 * 
	 * @param productCode: code of the product to auction
	 * @param basePrice: base price of the auction
	 * @throws InvalidProductException: Actual user doesn't has givens product 
	 * @throws UserDeniedException: Actual user cannot execute this task 
	 */
	void createAuctionStandard(String productCode, int basePrice)
			throws UserDeniedException, InvalidProductException;
	
	/**
	 * Create a plafond auction with givens data with actual logged user as
	 * auction's seller
	 *  
	 * @param productCode: code of the product to auction
	 * @param basePrice: base price of the auction
	 * @param plafond: plafond value
	 * @throws UserDeniedException: Actual user cannot execute this task 
	 * @throws InvalidProductException: Actual user doesn't has givens product
	 */
	void createAuctionPlafond(String productCode, int basePrice, int plafond)
			throws InvalidProductException, UserDeniedException;
	
	/**
	 * Bid on the given auction. This method returns true if the auction is
	 * a plafond auction and this bid wins it by matching the value of the plafond.
	 * It returns false in any other case.
	 * 
	 * @param sellerUsername: auction seller
	 * @param productCode: product code
	 * @param amount: bid amount
	 * @return true if bid matches plafond, false in any other case
	 * @throws BiddingOwnAuctionException: Seller can't bid own auction
	 * @throws BiddingClosedAuctionException: Trying to bid a closed auction 
	 * @throws LowBidAmountException: Bid amount is lower than auction's base 
	 * @throws UserDeniedException: Actual user cannot execute this task 
	 */
	boolean bid(String sellerUsername, String productCode, int amount)
			throws UserDeniedException, LowBidAmountException,
			BiddingClosedAuctionException, BiddingOwnAuctionException;
	
	/**
	 * Closes the auction of the current logged user identified by
	 * <code>productCode</code> and returns the winning bid.
	 * 
	 * @param productCode: code of the product whose auction is to be closed
	 * @return winning bid
	 * @throws NoBidsException: Trying to close an auction without bids 
	 * @throws UserDeniedException: Actual user cannot execute this task 
	 */
	IBid closeAuction(String productCode)
			throws NoBidsException, UserDeniedException;
	
	/**
	 * Adds a Car product to the database.
	 * 
	 * @param code: code used to identify the car -- a user can't have more
	 * than one product with the same code
	 * @param description: description of the car
	 * @param make: make of the car
	 * @param model: model of the car
	 * @param year: year of the car
	 * @throws ProductAlreadyExists: Already exists another product with same
	 * code for actual logged user 
	 * @throws UserDeniedException: Actual user cannot execute this task 
	 */
	void createCar(String code, String description, String make,
			String model, int year)
					throws UserDeniedException, ProductAlreadyExists;
	
	/**
	 * Adds a Tablet product to the database.
	 * 
	 * @param code code used to identify the tablet -- a user can't have more
	 * than one product with the same code
	 * @param description: description of the tablet
	 * @param brand: brand of the tablet
	 * @param size: size of the tablet
	 * @param weight: weight of the tablet
	 * @throws ProductAlreadyExists: Already exists another produc with same
	 * code for actual logged user 
	 * @throws UserDeniedException: Actual user cannot execute this task 
	 */
	void createTablet(String code, String description, String brand,
			int size, int weight) throws UserDeniedException, ProductAlreadyExists;
}
