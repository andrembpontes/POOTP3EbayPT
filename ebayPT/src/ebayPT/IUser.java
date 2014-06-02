package ebayPT;

import java.util.Iterator;

import ebayPT.exceptions.InvalidAuction;
import ebayPT.exceptions.NotAuctionSeller;
import ebayPT.exceptions.ProductAlreadyExists;

/**
 * An IUser represents a user in the Ebay@PT system, being identified by a
 * unique username, a real name, an email, and a user type. In addition to
 * that, an IUser also manages the user's auctions and products.
 *
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */
public interface IUser extends Comparable<IUser>{
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
	void addProduct(IProduct product) throws ProductAlreadyExists;
	
	/**
	 * Get total amount received by this user in sales
	 * 
	 * @return Total sales amount
	 */
	int getSales();

	/**
	 * Get iterator to this user's products (sorted alphabetically)
	 * 
	 * @return Iterator to products sorted alphabetically
	 */
	Iterator<IProduct> getProducts();

	/**
	 * Add an auction to this user
	 * 
	 * @param auction: auction to add
	 * @throws NotAuctionSeller: <code>auction</code> was created by different user
	 */
	void addAuction(IAuction auction)
			throws NotAuctionSeller;
	
	/**
	 * Get auction of product with the given product code
	 * 
	 * @param productCode: Code of auctioned product
	 * @return Auction of product with the given code, null if code not found
	 */
	IAuction getAuction(String productCode);
	
	/**
	 * Get product identified with <code>productCode</code>
	 * 
	 * @param productCode: Code of product
	 * @return Product identified with given code, null if not found
	 */
	IProduct getProduct(String productCode);

	/**
	 * Return total number of auctions closed by this user
	 * 
	 * @return Total number of auctions closed by this user
	 */
	int getClosedAuctionsCount();
	
	/**
	 * Report that given auctions has been closed
	 * 
	 * @param productCode: Code of product whose auction to close
	 * @throws InvalidAuction: No auction of product identified with <code>productCode</code>
	 */
	void reportClosedAuction(String productCode) throws InvalidAuction;
}
