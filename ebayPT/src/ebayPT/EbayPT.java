package ebayPT;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;


public class EbayPT implements IEbayPT {

	private IUserControl userControl;
	
	private Map <String, IUser> usersMap;

	private Map<IUser, TreeSet<IProduct>>	productsByUser;
	
	private Map<EProductCategory, TreeSet<IAuction>> auctionsByProductCategoryMap;

	private Map<Integer, TreeSet<ITablet>> tabletAuctionsBySize; 
	
	public EbayPT() {
		this.userControl = new UserControl();
		this.usersMap = new HashMap<String, IUser>();
		
		//TODO change String with IUser?
		this.productsByUser = new HashMap<IUser, TreeSet<IProduct>>();
		
		this.auctionsByProductCategoryMap = new HashMap<EProductCategory,
				TreeSet<IAuction>>();
		
		this.tabletAuctionsBySize = new HashMap<Integer, TreeSet<ITablet>>();
		
	}
	
	@Override
	public void login(String username) throws UserLoggedInException {
		this.userControl.login(this.usersMap.get(username));
	}

	@Override
	public void logout() throws NoUserLoggedInException {
		this.userControl.logout();
	}

	@Override
	public Iterator<IProduct> getProducts() throws UserDeniedException {
		this.userControl.executeAction(EAction.LIST_PRODUCTS);
		
		this.productsByUser.get(this.userControl.getLoggedUser()).iterator();
	}

	@Override
	public Iterator<IAuction> getAuctions(String category) {
		this.userControl.executeAction(EAction.LIST_AUCTIONS);
		
		this.auctionsByProductCategoryMap.get(category).iterator();
	}

	@Override
	public Iterator<IAuction> getAuctionsTabletBySize(int size)
			throws UserDeniedException {
		
		this.userControl.executeAction(EAction.LIST_AUCTIONS);
		
		this.tabletAuctionsBySize.get(size).iterator();
	}

	@Override
	public Iterator<IBid>
			getBiddings(String sellerUsername, String productCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<IUser> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<IUser> getUsersBySales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUser(String email, String name, String username,
			String userType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createAuctionStandard(String productCode, int basePrice) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createAuctionPlafond(String productCode, int basePrice,
			int plafond) {
		// TODO Auto-generated method stub

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
