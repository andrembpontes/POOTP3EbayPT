package ebayPT;

import java.util.Iterator;

import ebayPT.exceptions.*;

/**
 * Implementation of IEbayPT
 *
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */

public class EbayPT implements IEbayPT {

	private IUserControl userControl;
	private IDataBase db;
	
	/**
	 * Constructor
	 */
	public EbayPT() {
		this.userControl = new UserControl();
		this.db = new DataBase(userControl);
	}
	
	@Override
	public void login(String username)
			throws AnotherUserAlreadyLoggedIn,
			UserAlreadyLoggedIn, InvalidUser {
		
		try{
			this.userControl.login(this.db.getUser(username));
		}
		catch(NullPointerException e){
			throw new InvalidUser();
		}
	}

	@Override
	public IUser logout() throws NoUserLoggedIn {
		return this.userControl.logout();
	}

	@Override
	public Iterator<IProduct> getProducts() throws UserDenied {
		this.userControl.executeAction(EAction.LIST_PRODUCTS);
		
		try {
			return this.userControl.getLoggedUser().getProducts();
		}
		catch (NoUserLoggedIn e) {
			return null; //At this point this exception is not acceptable
		}
	}

	@Override
	public Iterator<IAuction> getAuctions(EProductCategory category)
			throws UserDenied {
		
		this.userControl.executeAction(EAction.LIST_AUCTIONS);
		
		return this.db.getAuctions(category);
	}

	@Override
	public Iterator<IAuction> getAuctionsTabletBySize(int size)
			throws UserDenied {
		
		this.userControl.executeAction(EAction.LIST_AUCTIONS);
		
		return this.db.getAuctionsTabletBySize(size);
	}

	@Override
	public Iterator<IBid> getBiddings(String sellerUsername, String productCode)
			throws UserDenied, InvalidAuction,
			NotAuctionSeller{
		
		this.userControl.executeAction(EAction.LIST_BIDS);
		
		try{
			
			IUser seller = this.db.getUser(sellerUsername);
			IAuction auction = seller.getAuction(productCode);
			
			IUser loggedUser;
			
			try{
				loggedUser = this.userControl.getLoggedUser();
			}
			catch(NoUserLoggedIn e){
				//At this point this exception in not acceptable
				loggedUser = null;
			}
			
			if(auction.isOpen()
					&& !loggedUser.equals(seller))
				throw new NotAuctionSeller();
			
			return auction.getBids();
		}
		catch(NullPointerException e){
			throw new InvalidAuction();
		}
	}

	@Override
	public Iterator<IUser> getUsers() throws UserDenied {
		this.userControl.executeAction(EAction.LIST_USERS);
		
		return this.db.getUsers();
	}

	@Override
	public Iterator<IUser> getUsersBySales() throws UserDenied {
		this.userControl.executeAction(EAction.LIST_USERS);
		
		return this.db.getUsersBySales();
	}

	@Override
	public void addUser(String email, String name, String username,
			String userType) throws InvalidUserType,
			UserDenied, UserAlreadyExists{
		
		this.userControl.executeAction(EAction.ADD_USER);
		
		try {
			IUser newUser = new User(EUserType.valueOf(userType), username, email,
					name);
			
			this.db.addUser(newUser);
		}
		catch (IllegalArgumentException e) {
			throw new InvalidUserType();
		}
	}
	
	@Override
	public void createAuctionStandard(String productCode, int basePrice) 
			throws UserDenied, InvalidProduct,
			ProductNotAvailable {
		
		this.userControl.executeAction(EAction.CREATE_AUCTION);
		
		try {
			IUser loggedUser = this.userControl.getLoggedUser();
			IProduct product = loggedUser.getProduct(productCode);			
			this.db.addAuction(new Auction(loggedUser, product, basePrice));
		}
		catch (NoUserLoggedIn e) {
			//Not applicable at this point
		}
		catch (NotAuctionSeller e) {
			//Not applicable at this point
		}
		catch (NullPointerException e){
			throw new InvalidProduct();
		}
	}

	@Override
	public void createAuctionPlafond(String productCode, int basePrice,
			int plafond) throws InvalidProduct, UserDenied,
			ProductNotAvailable {
		
		this.userControl.executeAction(EAction.CREATE_AUCTION);
		
		try {
			IUser loggedUser = this.userControl.getLoggedUser();
			IProduct product = loggedUser.getProduct(productCode);
			
			this.db.addAuction(new AuctionPlafond(loggedUser, product, basePrice,
					plafond));
		}
		catch (NoUserLoggedIn e) {
			//Not applicable at this point
		}
		catch (NotAuctionSeller e) {
			//Not applicable at this point
		}
		catch (NullPointerException e){
			throw new InvalidProduct();
		}

	}

	@Override
	public IBid bid(String sellerUsername, String productCode, int amount)
			throws UserDenied, LowBidAmount,
			BiddingClosedAuction, BiddingOwnAuction,
			InvalidAuction {
		
		this.userControl.executeAction(EAction.BID);
		
		try {
			IAuction auction =
					this.db.getUser(sellerUsername).getAuction(productCode);
			
			IBid winnerBid =
					auction.bid(this.userControl.getLoggedUser(), amount);
			
			if(winnerBid != null)
				this.db.reportClosedAuction(auction);
			
			return winnerBid;
		}
		catch (NoUserLoggedIn e) {
			//At this point NoUserLoggedInException in not acceptable
			return null;
		}
		catch (NullPointerException e){
			throw new InvalidAuction();
		}
		
	}

	@Override
	public IBid closeAuction(String productCode)
			throws NoBids, UserDenied,
			InvalidAuction {
		
		this.userControl.executeAction(EAction.CLOSE_AUCTION);
		
		try {
			IAuction auction =
					this.userControl.getLoggedUser().getAuction(productCode);
			
			IBid winnerBid = auction.getSeller().closeAuction(productCode);
		
			this.db.reportClosedAuction(auction);
			
			return winnerBid;
		}
		catch (NoUserLoggedIn e) {
			//At this point NoUserLoggedInException in not acceptable
			return null;
		}
		catch (NullPointerException e){
			throw new InvalidAuction();
		}
	}
	
	@Override
	public void createCar(String code, String description, String make,
			String model, int year) throws UserDenied,
			ProductAlreadyExists {
		
		this.userControl.executeAction(EAction.ADD_PRODUCT);
		
		try {
			this.db.addProduct(new Car(code, description, make, model, year));
		} 
		catch (NoUserLoggedIn e) {
			//Not applicable at this point
		}
		
	}

	@Override
	public void createTablet(String code, String description, String brand,
			int size, int weight) throws UserDenied,
			ProductAlreadyExists {
		
		this.userControl.executeAction(EAction.ADD_PRODUCT);
		
		try {
			this.db.addProduct(
					new Tablet(code, description, brand, size, weight));
		} 
		catch (NoUserLoggedIn e) {
			//Not applicable at this point
		}
	}

}
