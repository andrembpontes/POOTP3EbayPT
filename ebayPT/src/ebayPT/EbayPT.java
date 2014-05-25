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

	private Map<Integer, Set<IAuction>> tabletAuctionsBySize; 
	
	public EbayPT() {
		this.userControl = new UserControl();
		this.users = new TreeMap<String, IUser>();
			
		this.auctionsByProductCategory = new HashMap<EProductCategory,
				Set<IAuction>>();
		
		this.tabletAuctionsBySize = new HashMap<Integer, Set<IAuction>>();
		
		this.usersBySales = new TreeSet<IUser>(new ComparatorUserBySales());
	}
	
	@Override
	public void login(String username)
			throws AnotherUserAlreadyLoggedInException,
			UserAlreadyLoggedInException, InvalidUserException {
		
		try{
			this.userControl.login(this.users.get(username));
		}
		catch(NullPointerException e){
			throw new InvalidUserException();
		}
	}

	@Override
	public IUser logout() throws NoUserLoggedInException {
		return this.userControl.logout();
	}

	@Override
	public Iterator<IProduct> getProducts() throws UserDeniedException {
		this.userControl.executeAction(EAction.LIST_PRODUCTS);
		
		try {
			return this.userControl.getLoggedUser().getProducts();
		}
		catch (NoUserLoggedInException e) {
			return null; //At this point this exception is not acceptable
		}
	}

	@Override
	public Iterator<IAuction> getAuctions(EProductCategory category)
			throws UserDeniedException {
		this.userControl.executeAction(EAction.LIST_AUCTIONS);
		
		return this.auctionsByProductCategory.get(category).iterator();
	}

	@Override
	public Iterator<IAuction> getAuctionsTabletBySize(int size)
			throws UserDeniedException {
		
		this.userControl.executeAction(EAction.LIST_AUCTIONS);
		
		return this.tabletAuctionsBySize.get(size).iterator();
	}

	@Override
	public Iterator<IBid> getBiddings(String sellerUsername, String productCode)
			throws UserDeniedException, InvalidAuctionException,
			NotSellerException{
		
		//TODO Verify user access level needed
		this.userControl.executeAction(EAction.LIST_BIDS);
		
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

	@Override
	public Iterator<IUser> getUsers() throws UserDeniedException {
		this.userControl.executeAction(EAction.LIST_USERS);
		
		return this.users.values().iterator();
	}

	@Override
	public Iterator<IUser> getUsersBySales() throws UserDeniedException {
		this.userControl.executeAction(EAction.LIST_USERS);
		
		return this.usersBySales.iterator();
	}

	@Override
	public void addUser(String email, String name, String username,
			String userType)
					throws InvalidUserTypeException, UserDeniedException{
		
		this.userControl.executeAction(EAction.ADD_USER);
		
		try {
			IUser newUser = new User(EUserType.valueOf(userType), username, email,
					name);
			
			this.users.put(username, newUser);
			
			if(newUser.getUserType().equals(EUserType.USER))
				this.usersBySales.add(newUser);
		}
		catch (IllegalArgumentException e) {
			throw new InvalidUserTypeException();
		}
	}

	//TODO comment this
	private void addAuction(IAuction auction, IProduct product)
			throws InvalidProductException, UserDeniedException,
			ProductNotAvaliableException{
		
		this.userControl.executeAction(EAction.CREATE_AUCTION);
		
		if(!product.isAvaliable())
			throw new ProductNotAvaliableException();
			
		try {
			EProductCategory category = product.getCategory();
			
			//Add to auctions by product category
			if(!this.auctionsByProductCategory.containsKey(category)){
				Set<IAuction> newSet = new TreeSet<IAuction>();
				newSet.add(auction);
				this.auctionsByProductCategory.put(category, newSet);
			}
			else{
				this.auctionsByProductCategory.get(category).add(auction);
			}
			
			//Add to tablet auctions by size
			
			if(category.equals(EProductCategory.TABLET)){
				ITablet tablet = (ITablet) product;
				
				if(!this.tabletAuctionsBySize.containsKey(tablet.getSize())){
					Set<IAuction> newSet = new TreeSet<IAuction>();
					newSet.add(auction);
					this.tabletAuctionsBySize.
						put(tablet.getSize(), newSet);
				}
				else{
					this.tabletAuctionsBySize.
						get(tablet.getSize()).add(auction);
				}
			}
		}
		catch (NullPointerException e2) {
			throw new InvalidProductException();
		}
	}
	
	@Override
	public void createAuctionStandard(String productCode, int basePrice) 
			throws UserDeniedException, InvalidProductException,
			ProductNotAvaliableException {
		
		try {
			IUser loggedUser = this.userControl.getLoggedUser();
			IProduct product = loggedUser.getProduct(productCode);			
			this.addAuction(new Auction(loggedUser, product, basePrice), product);
		}
		catch (NoUserLoggedInException e) {
			//If there's no user logged in throws UserDeniedException
			
			this.userControl.executeAction(EAction.CREATE_AUCTION);
		}
	}

	@Override
	public void createAuctionPlafond(String productCode, int basePrice,
			int plafond) throws InvalidProductException, UserDeniedException,
			ProductNotAvaliableException {
		
		try {
			IUser loggedUser = this.userControl.getLoggedUser();
			IProduct product = loggedUser.getProduct(productCode);
			
			this.addAuction(new AuctionPlafond(loggedUser, product, basePrice,
					plafond), product);
		}
		catch (NoUserLoggedInException e) {
			//If there's no user logged in throws UserDeniedException
			
			this.userControl.executeAction(EAction.CREATE_AUCTION);
		}

	}

	@Override
	public boolean bid(String sellerUsername, String productCode, int amount)
			throws UserDeniedException, LowBidAmountException,
			BiddingClosedAuctionException, BiddingOwnAuctionException,
			InvalidAuctionException {
		
		this.userControl.executeAction(EAction.BID);
		
		try {
			return this.users.get(sellerUsername).getAuction(productCode).
				bid(this.userControl.getLoggedUser(), amount);
		}
		catch (NoUserLoggedInException e) {
			//At this point NoUserLoggedInException in not acceptable
			return false;
		}
		catch (NullPointerException e){
			throw new InvalidAuctionException();
		}
		
	}

	@Override
	public IBid closeAuction(String productCode)
			throws NoBidsException, UserDeniedException,
			InvalidAuctionException {
		
		this.userControl.executeAction(EAction.CLOSE_AUCTION);
		
		try {
			return this.userControl.getLoggedUser().getAuction(productCode).close();
		}
		catch (NoUserLoggedInException e) {
			//At this point NoUserLoggedInException in not acceptable
			return null;
		}
		catch (NullPointerException e){
			throw new InvalidAuctionException();
		}
	}
	
	//TODO comment
	private void createProduct(IProduct product)
			throws UserDeniedException, ProductAlreadyExistsException {
		
		this.userControl.executeAction(EAction.ADD_PRODUCT);
		
		try {
			this.userControl.getLoggedUser().addProduct(product);
		}
		catch (NoUserLoggedInException e) {
			//At this point NoUserLoggedInException is not acceptable
		}
	}

	@Override
	public void createCar(String code, String description, String make,
			String model, int year) throws UserDeniedException,
			ProductAlreadyExistsException {
		
		ICar newCar = new Car(code, description, make, model, year);
		
		this.createProduct(newCar);
		
	}

	@Override
	public void createTablet(String code, String description, String brand,
			int size, int weight) throws UserDeniedException,
			ProductAlreadyExistsException {
		
		ITablet newTablet = new Tablet(code, description, brand, size, weight);
		
		this.createProduct(newTablet);
	}

}
