package ebayPT;

import java.util.Iterator;

public class EbayPT implements IEbayPT {

	private IUserControl userControl;
	private IDataBase db;
	
	public EbayPT() {
		this.userControl = new UserControl();
		this.db = new DataBase(userControl);
	}
	
	@Override
	public void login(String username)
			throws AnotherUserAlreadyLoggedInException,
			UserAlreadyLoggedInException, InvalidUserException {
		
		try{
			this.userControl.login(this.db.getUser(username));
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
		
		//TODO auctions not removed when closed
		
		this.userControl.executeAction(EAction.LIST_AUCTIONS);
		
		return this.db.getAuctions(category);
	}

	@Override
	public Iterator<IAuction> getAuctionsTabletBySize(int size)
			throws UserDeniedException {
		
		this.userControl.executeAction(EAction.LIST_AUCTIONS);
		
		return this.db.getAuctionsTabletBySize(size);
	}

	@Override
	public Iterator<IBid> getBiddings(String sellerUsername, String productCode)
			throws UserDeniedException, InvalidAuctionException,
			NotSellerException{
		
		//TODO Verify user access level needed
		this.userControl.executeAction(EAction.LIST_BIDS);
		
		try{
			
			IUser seller = this.db.getUser(sellerUsername);
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
		
		return this.db.getUsers();
	}

	@Override
	public Iterator<IUser> getUsersBySales() throws UserDeniedException {
		this.userControl.executeAction(EAction.LIST_USERS);
		
		return this.db.getUsersBySales();
	}

	@Override
	public void addUser(String email, String name, String username,
			String userType) throws InvalidUserTypeException,
			UserDeniedException, UserAlreadyExistException{
		
		this.userControl.executeAction(EAction.ADD_USER);
		
		try {
			IUser newUser = new User(EUserType.valueOf(userType), username, email,
					name);
			
			this.db.addUser(newUser);
		}
		catch (IllegalArgumentException e) {
			throw new InvalidUserTypeException();
		}
	}
	
	@Override
	public void createAuctionStandard(String productCode, int basePrice) 
			throws UserDeniedException, InvalidProductException,
			ProductNotAvailableException {
		
		this.userControl.executeAction(EAction.CREATE_AUCTION);
		
		try {
			IUser loggedUser = this.userControl.getLoggedUser();
			IProduct product = loggedUser.getProduct(productCode);			
			this.db.addAuction(new Auction(loggedUser, product, basePrice));
		}
		catch (NoUserLoggedInException e) {
			//Not applicable at this point
		}
		catch (NotAuctionSellerException e) {
			//Not applicable at this point
		}
		catch (NullPointerException e){
			throw new InvalidProductException();
		}
	}

	@Override
	public void createAuctionPlafond(String productCode, int basePrice,
			int plafond) throws InvalidProductException, UserDeniedException,
			ProductNotAvailableException {
		
		this.userControl.executeAction(EAction.CREATE_AUCTION);
		
		try {
			IUser loggedUser = this.userControl.getLoggedUser();
			IProduct product = loggedUser.getProduct(productCode);
			
			this.db.addAuction(new AuctionPlafond(loggedUser, product, basePrice,
					plafond));
		}
		catch (NoUserLoggedInException e) {
			//Not applicable at this point
		}
		catch (NotAuctionSellerException e) {
			//Not applicable at this point
		}
		catch (NullPointerException e){
			throw new InvalidProductException();
		}

	}

	@Override
	public IBid bid(String sellerUsername, String productCode, int amount)
			throws UserDeniedException, LowBidAmountException,
			BiddingClosedAuctionException, BiddingOwnAuctionException,
			InvalidAuctionException {
		
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
		catch (NoUserLoggedInException e) {
			//At this point NoUserLoggedInException in not acceptable
			return null;
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
			IAuction auction =
					this.userControl.getLoggedUser().getAuction(productCode);
			
			IBid winnerBid = auction.close();
		
			this.db.reportClosedAuction(auction);
			
			return winnerBid;
		}
		catch (NoUserLoggedInException e) {
			//At this point NoUserLoggedInException in not acceptable
			return null;
		}
		catch (NullPointerException e){
			throw new InvalidAuctionException();
		}
	}
	
	@Override
	public void createCar(String code, String description, String make,
			String model, int year) throws UserDeniedException,
			ProductAlreadyExistsException {
		
		this.userControl.executeAction(EAction.ADD_PRODUCT);
		
		try {
			this.db.addProduct(new Car(code, description, make, model, year));
		} 
		catch (NoUserLoggedInException e) {
			//Not applicable at this point
		}
		
	}

	@Override
	public void createTablet(String code, String description, String brand,
			int size, int weight) throws UserDeniedException,
			ProductAlreadyExistsException {
		
		this.userControl.executeAction(EAction.ADD_PRODUCT);
		
		try {
			this.db.addProduct(
					new Tablet(code, description, brand, size, weight));
		} 
		catch (NoUserLoggedInException e) {
			//Not applicable at this point
		}
	}

}
