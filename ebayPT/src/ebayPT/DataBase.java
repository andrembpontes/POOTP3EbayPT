package ebayPT;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import ebayPT.exceptions.NoUserLoggedIn;
import ebayPT.exceptions.NotAuctionSeller;
import ebayPT.exceptions.ProductAlreadyExists;
import ebayPT.exceptions.UserAlreadyExists;

/**
 * Implementation of IDataBase
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */
public class DataBase implements IDataBase {

	/**
	 * User control module
	 */
	private IUserControl userControl;
	
	/**
	 * Users map by username
	 */
	private Map <String, IUser> users;

	/**
	 * Users sorted by User Type then by username
	 */
	private Set<IUser> sortedUsers;
	
	/**
	 * Auctions maps by product category
	 */
	private MapOfSets<EProductCategory, IAuction> auctionsByProductCategory;

	/**
	 * Tablets maps by size
	 */
	private MapOfSets<Integer, IAuction> tabletAuctionsBySize; 
	
	/**
	 * Constructor: initialize database and use <code>userControl</code> as
	 * this database's UserControl
	 * 
	 * @param userControl: IUserControl to be used by the database
	 */
	public DataBase(IUserControl userControl) {
		this.userControl = userControl;
		
		this.users = new HashMap<String, IUser>();
			
		this.auctionsByProductCategory =
				new HashMapOfTreeSet<EProductCategory, IAuction>();
		
		this.tabletAuctionsBySize = new HashMapOfTreeSet<Integer, IAuction>();
		
		this.sortedUsers = new TreeSet<IUser>();
	}
	
	@Override
	public void addProduct(IProduct product)
			throws ProductAlreadyExists, NoUserLoggedIn {
		
		this.userControl.getLoggedUser().addProduct(product);
	}

	@Override
	public void addAuction(IAuction auction)
			throws NotAuctionSeller, NoUserLoggedIn {
	
		IProduct product = auction.getProduct();
		
		//Try to add to user
		this.userControl.getLoggedUser().addAuction(auction);
		
		//Add auction to auctionsByProductCategory
		this.auctionsByProductCategory.putElemt(product.getCategory(), auction);
		
		//Verify if is an tablet to add it to tabletAuctionsBySize
		if(product.getCategory().equals(EProductCategory.TABLET)){
			this.tabletAuctionsBySize.
				putElemt(((ITablet) product).getSize(), auction);
		}
		
	}

	@Override
	public void addUser(IUser user) throws UserAlreadyExists {
		//Add to users
		if(this.users.put(user.getUsername(), user) != null)
			throw new UserAlreadyExists();
		
		//Add to sortedUsers
		this.sortedUsers.add(user);
	}

	@Override
	public IUser getUser(String username) {
		return this.users.get(username);
	}

	@Override
	public Iterator<IAuction> getAuctions(EProductCategory category) {
		return this.auctionsByProductCategory.get(category).iterator();
	}

	@Override
	public Iterator<IUser> getUsers() {
		return this.sortedUsers.iterator();
	}

	@Override
	public Iterator<IUser> getUsersBySales() {
		Set<IUser> usersBySales = new TreeSet<IUser>(new ComparatorUserBySales());
		
		for(IUser userI : this.users.values()){
			if(userI.getUserType().equals(EUserType.USER))
				usersBySales.add(userI);
		}
		
		return usersBySales.iterator();

	}


	@Override
	public Iterator<IAuction> getAuctionsTabletBySize(int size) {
		
		Collection<Collection<IAuction>> lessThanSizeAuctions =
				new LinkedList<Collection<IAuction>>();
		
		for(int sizeI : this.tabletAuctionsBySize.keySet()){
			if(sizeI <= size)
				lessThanSizeAuctions.add(
						this.tabletAuctionsBySize.get(sizeI));
		}
		
		return new DoubleIterator<IAuction>(lessThanSizeAuctions);
	}

	@Override
	public void reportClosedAuction(IAuction auction) {		
		IProduct auctionProduct = auction.getProduct();
		
		this.auctionsByProductCategory.removeElemt(
				auctionProduct.getCategory(), auction);
		
		if(auctionProduct.getCategory().equals(EProductCategory.TABLET)){
			ITablet tablet = (ITablet) auctionProduct;
			
			this.tabletAuctionsBySize.removeElemt(tablet.getSize(), auction);
		}
	}

}
