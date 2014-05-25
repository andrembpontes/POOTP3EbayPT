import java.io.PrintStream;

public enum EMessage{
	TABLETS_ALL_TITLE("All tablets:"),
	SALES_TITLE("Sales by users:"),
	PRODUCTS_TITLE("All products:"),
	NOTHING_TO_LIST("Nothing to list."),
	USER_DENIED("No @arg is logged in."),
	WELLCOME("Welcome @arg."),
	GOODBYE("Goodbye @arg."),
	NO_USER_LOGGED("No user is logged in."),
	ANOTHER_USER_ALREADY_LOGGED("User already logged in."),
	INVALID_USER("User does not exist."),
	USER_ALREADY_LOGGED("Another user is logged in."),
	USERS_ALL_TITLE("All users:"),
	EXIT("Exiting..."),
	WINNING_BID("Winning bid: @arg @arg"),
	NO_BIDS("Product not sold."),
	INVALID_AUCTION("Auction does not exist."),
	CARS_ALL_TITLE("All cars:"),
	BIDDINGS_TITLE("All bids:"),
	NOT_SELLER("Cannot view bids."),
	INVALID_PRODUCT("Product does not exist."),
	PRODUCT_NOT_AVALIABLE("Product not available."),
	PRODUCT_ALREADY_EXIST("Product code already exists."),
	PRODUCT_ADDED("New product added."),
	BID("Bid accepted."),
	BIDDING_OWN_AUCTION("Cannot bid in auction."),
	BID_LOWER_THAN_BASE("Bid not accepted.");
	
	private static final PrintStream PRINT_STREAM = System.out;
	private static final String ARG_IDENTIFIER = "@arg";
	
	private String message;
	
	private EMessage(String message) {
		this.message = message;
	}
	
	public void print() {
		PRINT_STREAM.println(this.message);
	}
	
	public void print(String arg){
		PRINT_STREAM.println(this.message.replaceFirst(ARG_IDENTIFIER, arg));
	}
	
	public void print(String[] args){
		String toPrint = this.message;
		
		for(String argI : args){
			toPrint.replaceFirst(ARG_IDENTIFIER, argI);
		}
		
		PRINT_STREAM.println(toPrint);
	}
	
	@Override
	public String toString(){
		return this.message;
	}
}
