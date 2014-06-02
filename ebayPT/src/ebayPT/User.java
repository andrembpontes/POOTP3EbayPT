package ebayPT;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import ebayPT.exceptions.InvalidAuction;
import ebayPT.exceptions.NotAuctionSeller;
import ebayPT.exceptions.ProductAlreadyExists;

/**
 * Implementation of IUser
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */

public class User implements IUser{
	private String username, email, name;
	
	private IUserType type;
	
	private Map<String, IProduct> products; // products indexed by product code
	
	private Map<String, IAuction> auctionsByProductCode; // open auctions indexed
														// by product code
	
	private Map<String, IAuction> closedAuctions; // closed auctions indexed by product code
	
	private int closedAuctionSales;	// total amount earned in auctions
	
	public User(IUserType type, String username, String email, String name){
		this.type = type;
		this.username = username;
		this.email = email;
		this.name = name;
		
		this.closedAuctionSales = 0;
		
		this.products = new TreeMap<String, IProduct>();
		
		this.auctionsByProductCode = new HashMap<String, IAuction>();
		
		this.closedAuctions = new HashMap<String, IAuction>();
		
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
	public void addProduct(IProduct product) throws ProductAlreadyExists {
		
		if(this.products.containsKey(product.getCode()))
			throw new ProductAlreadyExists();
		
		this.products.put(product.getCode(), product);
		
	}

	@Override
	public int getSales() {
		int sales = this.closedAuctionSales;
		
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
	public void addAuction(IAuction auction) throws NotAuctionSeller {
		
		//TODO think in remove this
		if(!auction.getSeller().equals(this))
			throw new NotAuctionSeller();
		
		this.auctionsByProductCode.put(auction.getProduct().getCode(), auction);
	}

	@Override
	public IAuction getAuction(String productCode) {
		IAuction auction = this.auctionsByProductCode.get(productCode);
		
		if(auction == null)
			return this.closedAuctions.get(productCode);
		
		return auction;
	}

	@Override
	public IProduct getProduct(String productCode) {
		return this.products.get(productCode);
	}

	@Override
	public int getClosedAuctionsCount() {
		return this.closedAuctions.size();
	}

	@Override
	public int compareTo(IUser user) {
		int typeDiff = this.getUserType().toString().
				compareTo(user.getUserType().toString());
		
		if(typeDiff == 0)
			return this.getUsername().compareTo(user.getUsername());
		
		return typeDiff;
	}

	@Override
	public void reportClosedAuction(String productCode)
			throws InvalidAuction {
		
		IBid winnerBid = null;
		
		try{
			winnerBid =
					this.auctionsByProductCode.get(productCode).getWinnerBid();
		}
		catch(NullPointerException e){
			throw new InvalidAuction();
		}
		
		this.closedAuctions.put(productCode,
				this.auctionsByProductCode.remove(productCode));
				
		try{
			this.closedAuctionSales += winnerBid.getAmount();			
		}
		catch(NullPointerException e){
			//There is no winner bid //Do nothing
		}
	}
}
