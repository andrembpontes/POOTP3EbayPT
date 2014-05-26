package ebayPT;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.sun.org.apache.regexp.internal.recompile;


public class DataBase implements IDataBase {

	private IUserControl userControl;
	
	private Map <String, IUser> users;

	private Set<IUser> sortedUsers;
	
	private MapOfSets<EProductCategory, IAuction> auctionsByProductCategory;

	private MapOfSets<Integer, IAuction> tabletAuctionsBySize; 
	
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
			throws ProductAlreadyExistsException, NoUserLoggedInException {
		this.userControl.getLoggedUser().addProduct(product);
	}

	@Override
	public void addAuction(IAuction auction)
			throws NotAuctionSellerException, NoUserLoggedInException {
	
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
	public void addUser(IUser user) throws UserAlreadyExistException {
		//Add to users
		if(this.users.put(user.getUsername(), user) != null)
			throw new UserAlreadyExistException();
		
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
		return this.users.values().iterator();
	}

	@Override
	public Iterator<IUser> getUsersBySales() {
		//TODO improve
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
	public Iterator<IBid> getBiddings(IAuction auction) {
		try{

			IUser seller = this.users.get(sellerUsername);
			IAuction auction = seller.getAuction(productCode);

			IUser loggedUser;

			try{
				loggedUser = this.userControl.getLoggedUser();
			}
			catch(NoUserLoggedInException e){
				//At this point this exception in not acceptable
				loggedUser = null;
			}

			if(auction.isOpen()
					&& !loggedUser.equals(seller))
				throw new NotSellerException();

			return auction.getBids();
		}
		catch(NullPointerException e){
			throw new InvalidAuctionException();
		}
	}

}
