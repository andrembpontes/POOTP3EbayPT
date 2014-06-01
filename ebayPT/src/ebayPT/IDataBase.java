package ebayPT;

import java.util.Iterator;

import ebayPT.exceptions.NoUserLoggedIn;
import ebayPT.exceptions.NotAuctionSeller;
import ebayPT.exceptions.ProductAlreadyExists;
import ebayPT.exceptions.UserAlreadyExists;

public interface IDataBase {

	/**
	 * Add a new auction to database
	 * 
	 * @param auction: Auction to add
	 * @throws NoUserLoggedIn: Need an logged user to add auction
	 * @throws NotAuctionSeller : Trying to add an auction of an user
	 * in other user session
	 */
	void addAuction(IAuction auction)
			throws NotAuctionSeller, NoUserLoggedIn;
	
	/**
	 * Add a new user to database
	 * 
	 * @param user: User to add
	 * @throws UserAlreadyExists: username already exist
	 */
	void addUser(IUser user) throws UserAlreadyExists;
	
	/**
	 * Look for user with givens user name and return it
	 * 
	 * @param username: Username of user to look for
	 * @return User with givens username, null if there's no user'
	 */
	IUser getUser(String username);
	
	/**
	 * Create and returns a new iterator for all users sorted first by type then
	 * by username alphabetically
	 * 
	 * @return Iterator to all users
	 */
	Iterator<IUser> getUsers();
	
	/**
	 * Creates and returns a new iterator for all users sorted by sales amount
	 * 
	 * @return Iterator to all users sorted by amount of sales
	 */
	Iterator<IUser> getUsersBySales();
	
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
	 * @param product: Product to add
	 * @throws ProductAlreadyExists: Product already exist in database
	 * @throws NoUserLoggedIn: Need a logged user to add product to
	 */
	void addProduct(IProduct product)
			throws ProductAlreadyExists, NoUserLoggedIn;
	
	/**
	 * Remove auction for data base
	 * 
	 * @param auction: Auction to report that has been closed
	 */
	void reportClosedAuction(IAuction auction);
	
}

