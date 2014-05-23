package ebayPT;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


public class EbayPT implements IEbayPT {

	private IUserControl userControl;
	
	private Map <String, IUser> users;

	private Set<IUser> usersBySales;
	
	private Map<EProductCategory, Set<IAuction>> auctionsByProductCategory;

	private Map<Integer, Set<ITablet>> tabletAuctionsBySize; 
	
	public EbayPT() {
		this.userControl = new UserControl();
		this.users = new TreeMap<String, IUser>();
			
		this.auctionsByProductCategory = new HashMap<EProductCategory,
				Set<IAuction>>();
		
		this.tabletAuctionsBySize = new HashMap<Integer, Set<ITablet>>();
		
		this.usersBySales = new TreeSet<IUser>(new ComparatorUserBySales());
	}
	
	@Override
	public void login(String username) throws UserLoggedInException {
		this.userControl.login(this.users.get(username));
	}

	@Override
	public void logout() throws NoUserLoggedInException {
		this.userControl.logout();
	}

	@Override
	public Iterator<IProduct> getProducts() throws UserDeniedException {
		this.userControl.executeAction(EAction.LIST_PRODUCTS);
		
		this.userControl.getLoggedUser().getProducts();
	}

	@Override
	public Iterator<IAuction> getAuctions(String category)
			throws UserDeniedException {
		this.userControl.executeAction(EAction.LIST_AUCTIONS);
		
		return this.auctionsByProductCategory.get(category).iterator();
	}

	@Override
	public Iterator<IAuction> getAuctionsTabletBySize(int size)
			throws UserDeniedException {
		
		this.userControl.executeAction(EAction.LIST_AUCTIONS);
		
		this.tabletAuctionsBySize.get(size).iterator();
	}

	@Override
	public Iterator<IBid> getBiddings(String sellerUsername, String productCode)
			throws UserDeniedException {
		
		//TODO Verify user access level needed
		this.userControl.executeAction(EAction.LIST_BIDS);
		
		return this.users.get(sellerUsername).getAuction(productCode).getBids();
	}

	@Override
	public Iterator<IUser> getUsers() {
		return this.users.values().iterator();
	}

	@Override
	public Iterator<IUser> getUsersBySales() {
		return this.usersBySales.iterator();
	}

	@Override
	public void addUser(String email, String name, String username,
			String userType) throws InvalidUserTypeException{
		this.userControl.executeAction(EAction.ADD_USER);
		
		IUser newUser = new User(EUserType.valueOf(userType), username, email,
				name);
		
		this.users.put(username, newUser);
		this.usersBySales.add(newUser);
	}

	//TODO comment this
	private void addAuction(IAuction auction, IProduct product)
			throws InvalidProductException, UserDeniedException{
		
		this.userControl.executeAction(EAction.CREATE_AUCTION);
		
		try {
			if(!this.auctionsByProductCategory.
					containsKey(product.getCategory())){
				
				Set<IAuction> newSet = new TreeSet<IAuction>();
				newSet.add(auction);
				this.auctionsByProductCategory.
					put(product.getCategory(), newSet);
			}
			
			this.auctionsByProductCategory.get(product.getCategory()).
				add(auction);
		}
		catch (NullPointerException e2) {
			throw new InvalidProductException();
		}
	}
	
	@Override
	public void createAuctionStandard(String productCode, int basePrice) 
			throws UserDeniedException, InvalidProductException {
		
		IUser loggedUser = this.userControl.getLoggedUser();
		IProduct product = loggedUser.getProduct(productCode);
		
		this.addAuction(new Auction(loggedUser, product, basePrice), product);

	}

	@Override
	public void createAuctionPlafond(String productCode, int basePrice,
			int plafond) throws InvalidProductException, UserDeniedException {
		
		IUser loggedUser = this.userControl.getLoggedUser();
		IProduct product = loggedUser.getProduct(productCode);
		
		this.addAuction(new AuctionPlafond(loggedUser, product, basePrice,
				plafond), product);

	}

	@Override
	public boolean bid(String sellerUsername, String productCode, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IBid closeAuction(String productCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createCar(String code, String description, String make,
			String model, int year) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createTablet(String code, String description, String brand,
			int size, int weight) {
		// TODO Auto-generated method stub

	}

}
