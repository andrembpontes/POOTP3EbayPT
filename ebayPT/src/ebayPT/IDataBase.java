package ebayPT;

import java.util.Iterator;

public interface IDataBase {

	/**
	 * Add a new product to data base
	 * 
	 * @param product: Product to add
	 */
	void addProduct(IProduct product);
	
	/**
	 * Add a new auction to database
	 * 
	 * @param auction: Auction to add
	 */
	void addAuction(IAuction auction);
	
	/**
	 * Add a new user to database
	 * 
	 * @param user: User to add
	 */
	void addUser(IUser user);
	
	/**
	 * Look for user with givens user name and return it
	 * 
	 * @param username: Username of user to look for
	 * @return User with givens username, null if there's no user'
	 */
	IUser getUser(String username);
	
	/**
	 * Look for auction with givens data and returns it
	 * 
	 * @param seller: Seller user of auction to look for
	 * @param product: Product of auction to look for
	 * @return Auction corresponding to givens seller and product
	 */
	IAuction getAuction(IUser seller, IProduct product);

	/**
	 * Look for product with givens data and returns it
	 * 
	 * @param owner: Product owner user
	 * @param productCode: Code of product to look for
	 * @return Product according to givens data, null if not found
	 */
	IProduct getProduct(IUser owner, String productCode);
	
	//TODO verify sort settings
	/**
	 * Create and returns a new iterator for all users alphabetical sorted
	 * 
	 * @return Iterator to all users
	 */
	Iterator<IUser> getUsers();
	
	//TODO verify sort settings
	/**
	 * Creates and returns a new iterator for all users sorted by sales amount
	 * 
	 * @return Iterator to all users sorted by amount of sales
	 */
	Iterator<IUser> getUsersBySales();
	
	/**
	 * Create and returns a new iterator for all products of givens user
	 * 
	 * @param owner: User to get products' iterator'
	 * @return Iterator for user's products'
	 */
	Iterator<IProduct> getProducts(IUser owner);
	
	/**
	 * Create and returns a new iterator for all auctions of givens product category
	 * 
	 * @param category: Product category to look for
	 * @return Auctions selling products of givens category
	 */
	Iterator<IAuction> getAuctions(EProductCategory category);
	
	/**
	 * Create and returns a new iterator for auctions of Tablets with givens size
	 * 
	 * @param size: size of Tablet to look for
	 * @return Iterator for Tablets' auctions
	 */
	Iterator<IAuction> getAuctionsTabletBySize(int size);
	
	/**
	 * Create and return a new Iterator for all bids of givens auction
	 * 
	 * @param auction: auction to get bids iterator
	 * @returns Iterator for auction bids
	 */
	Iterator<IBid> getBiddings(IAuction auction);
}

