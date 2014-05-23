package ebayPT;

import java.util.Iterator;

/**
 * An IUser represents a user in the ebayPT system.
 *
 * @author n42540: Rodrigo Simões; n42845: André Pontes
 */
public interface IUser {
	/**
	 * Gets the email of this user.
	 * @return user email
	 */
	String getEmail();
	
	/**
	 * Gets the name of this user.
	 * @return user's name
	 */
	String getName();
	
	/**
	 * Gets this user's username.
	 * @return username
	 */
	String getUsername();
	
	/**
	 * Gets this user's type
	 * @return user's type
	 */
	IUserType getUserType();
	
	/**
	 * Returns whether this IUser is the same as <code>user</code>.
	 * @param user an IUser against which this IUser will be compared
	 * @return true if this IUser equals <code>user</code>, false otherwise
	 */
	boolean equals(IUser user);

	/**
	 * Add a new product to user
	 * 
	 * @param product: Product to add
	 * @throws NotProductOwnerException: Trying to add a product of other user
	 * @throws ProductAlreadyExists: Trying to add a product that already exists
	 */
	void addProduct(IProduct product)
			throws NotProductOwnerException, ProductAlreadyExists;
	
	/**
	 * Sum all sales and return total of sales amount
	 * 
	 * @return Total sales amount
	 */
	int getSales();

	/**
	 * Create and returns a new iterator for products sort alphabetical
	 * 
	 * @return Iterator to products sort alphabetical
	 */
	Iterator<IProduct> getProducts();

	/**
	 * Add an auction to user
	 * 
	 * @param auction: auction to add
	 * @throws NotAuctionSellerException: User is not auction seller
	 * @throws AuctionAlreadyExists: Trying to add an auction that already exists
	 */
	void addAuction(IAuction auction)
			throws NotAuctionSellerException, AuctionAlreadyExists;
	
	/**
	 * Look for and returns auction of product with givens productCode
	 * 
	 * @param productCode: Code of auction product
	 * @return Auction, null if not found
	 */
	IAuction getAuction(String productCode);
	
	/**
	 * Look for and returns product with givens code
	 * 
	 * @param productCode: Code of product
	 * @return Product, null if not found
	 */
	IProduct getProduct(String productCode);
}
