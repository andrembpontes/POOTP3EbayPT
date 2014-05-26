package ebayPT;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class User implements IUser{
	private String username, email, name;
	
	private IUserType type;
	
	private Map<String, IProduct> products;
	
	private Map<String, IAuction> auctionsByProductCode;
	
	public User(IUserType type, String username, String email, String name){
		this.type = type;
		this.username = username;
		this.email = email;
		this.name = name;
		
		this.products = new TreeMap<String, IProduct>();
		
		//TODO for better implementation split auctions by states (closed/open)
		this.auctionsByProductCode = new HashMap<String, IAuction>();
	}
	
	@Override
	public String getEmail() {
		return this.email;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean equals(IUser user) {
		return user.getUsername().equals(this.username);
	}

	@Override
	public IUserType getUserType() {
		return this.type;
	}
	
	@Override
	public int hashCode(){
		return this.username.hashCode();
	}

	@Override
	public void addProduct(IProduct product) throws ProductAlreadyExistsException {
		
		if(this.products.containsValue(product))
			throw new ProductAlreadyExistsException();
		
		this.products.put(product.getCode(), product);
		
	}

	@Override
	public int getSales() {
		int sales = 0;
		
		for(IAuction auctionI: this.auctionsByProductCode.values()){
			try{
				sales += auctionI.getWinnerBid().getAmount();
			}
			catch(NullPointerException e){
				//Do nothing
			}
		}
		
		return sales;
	}

	@Override
	public Iterator<IProduct> getProducts() {
		return this.products.values().iterator();
	}

	@Override
	public void addAuction(IAuction auction) throws NotAuctionSellerException,
			AuctionAlreadyExists {
		
		if(!auction.getSeller().equals(this))
			throw new NotAuctionSellerException();
		
		if(this.auctionsByProductCode.containsKey(
				auction.getProduct().getCode()))
			throw new AuctionAlreadyExists();
		
		this.auctionsByProductCode.put(auction.getProduct().getCode(), auction);
	}

	@Override
	public IAuction getAuction(String productCode) {
		return this.auctionsByProductCode.get(productCode);
	}

	@Override
	public IProduct getProduct(String productCode) {
		return this.products.get(productCode);
	}

	@Override
	public int getClosedAuctionsCount() {
		//TODO think on auctions split by available to simplify searchs and other things
		
		int count = 0;
		
		for(IAuction auctionI : this.auctionsByProductCode.values()){
			if(!auctionI.isOpen()){
				count++;
			}
		}
		
		return count;
	}

	@Override
	public int compareTo(IUser user) {
		int typeDiff = this.getUserType().toString().
				compareTo(user.getUserType().toString());
		
		if(typeDiff == 0)
			return this.getUsername().compareTo(user.getUsername());
		
		return typeDiff;
	}

}
