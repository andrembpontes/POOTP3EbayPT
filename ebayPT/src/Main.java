import java.util.Scanner;
import java.util.Iterator;

import ebayPT.EProductCategory;
import ebayPT.EUserType;
import ebayPT.EbayPT;
import ebayPT.IAuction;
import ebayPT.IBid;
import ebayPT.ICar;
import ebayPT.IEbayPT;
import ebayPT.IProduct;
import ebayPT.ITablet;
import ebayPT.IUser;
import ebayPT.exceptions.*;

public class Main {

	public static final int NO_BIDS_HIGHEST_BID_VALUE = 0;
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		IEbayPT ebayPT = new EbayPT();
		
		while(true){
			try{
				switch(ECommand.valueOf(scan.next())){
					case ADD: execAdd(scan, ebayPT); break;
					case AUCTION: execAuction(scan, ebayPT); break;
					case BIDDINGS: execBiddinds(scan, ebayPT); break;
					case CARS: execCars(scan, ebayPT); break;
					case CLOSE: execClose(scan, ebayPT); break;
					case EXIT: execExit(); break;
					case LIST: execList(scan, ebayPT); break;
					case LOGIN: execLogin(scan, ebayPT); break;
					case LOGOUT: execLogout(scan, ebayPT); break;
					case PRODUCTS: execProducts(scan, ebayPT); break;
					case REGISTER: execRegister(scan, ebayPT); break;
					case SALES: execSales(scan, ebayPT); break;
					case TABLETS: execTablets(scan, ebayPT); break;
					default: throw new InvalidCommandException();
				}
			}
			catch(IllegalArgumentException e){
				invalidCommand(scan);
			}
			catch(InvalidCommandException e){
				invalidCommand(scan);
			}
			catch (UserDenied e) {
				UserDenied(e);
			}
			
			System.out.println(); //Print blank line
		}
	}

	private static void execTablets(Scanner scan, IEbayPT ebayPT)
			throws UserDenied {
		
		switch (ECommand.Tablets.valueOf(scan.next())) {
			case ALL: execTabletsAll(scan, ebayPT); break;
			case DIMENSION: execTabletsDimension(scan, ebayPT);
		}
	}

	private static void execTabletsDimension(Scanner scan, IEbayPT ebayPT)
			throws UserDenied {

		int size = scan.nextInt();



		Iterator<IAuction> tablets =
				ebayPT.getAuctionsTabletBySize(size);

		if(tablets.hasNext()){

			EMessage.TABLETS_DIMENSION_TITLE.print();

			System.out.println(size);

			do{
				IAuction auctionI = tablets.next();
				ITablet tabletI	= (ITablet) auctionI.getProduct();

				String seller = auctionI.getSeller().getUsername();

				int higestBid;

				try {
					higestBid = auctionI.getHighestBid().getAmount();
				}
				catch (NoBids e) {
					higestBid = 0;
				}

				System.out.println(seller + " " + tabletI.getCode() + " "
						+ tabletI.getBrand() + " " + tabletI.getSize() + " "
						+ tabletI.getWeight() + " " + auctionI.getBaseAmount()
						+ " " + higestBid);
			}
			while(tablets.hasNext());
		}
		else{
			EMessage.NOTHING_TO_LIST.print();
		}
	}

	private static void execTabletsAll(Scanner scan, IEbayPT ebayPT)
			throws UserDenied {

		try {
			Iterator<IAuction> iterator =
					ebayPT.getAuctions(EProductCategory.TABLET);

			EMessage.TABLETS_ALL_TITLE.print();

			while(iterator.hasNext()) {
				IAuction auctionI = iterator.next();
				ITablet tabletI = (ITablet) auctionI.getProduct();

				String seller = auctionI.getSeller().getUsername();

				int highestBid;

				try{
					highestBid = auctionI.getHighestBid().getAmount();
				}
				catch(NoBids e){
					highestBid = NO_BIDS_HIGHEST_BID_VALUE;
				}


				System.out.println(seller + " " + tabletI.getCode() + " "
						+ tabletI.getBrand() + " " + tabletI.getSize() + " "
						+ tabletI.getWeight() + " " + auctionI.getBaseAmount()
						+ " " + highestBid);
			}
		}
		catch (NullPointerException e) {
			EMessage.NOTHING_TO_LIST.print();
		}
	}

	private static void UserDenied(UserDenied e) {
		if(e.getNeededType().equals(EUserType.GUEST)){
			EMessage.THERE_IS_A_USER_LOGGED_IN.print();
		}
		else{
			EMessage.USER_DENIED.
				print(e.getNeededType().toString().toLowerCase());
		}
	}

	private static void execSales(Scanner scan, IEbayPT ebayPT)
			throws UserDenied {
		
		Iterator<IUser> users = ebayPT.getUsersBySales();
		
		EMessage.SALES_TITLE.print();
		
		while(users.hasNext()){
			IUser userI = users.next();
			
			System.out.println(userI.getUsername() + " " + userI.getName() + " "
					+ userI.getEmail() + " " + userI.getClosedAuctionsCount() + " "
					+ userI.getSales());
		}
		
	}

	private static void execRegister(Scanner scan, IEbayPT ebayPT)
			throws UserDenied, InvalidCommandException {
		
		String userType = scan.next();
		String username = scan.next();
		scan.nextLine();
		String name = scan.nextLine();
		String email = scan.nextLine();		
		
		try {
			ebayPT.addUser(email, name, username, userType);
			
			EMessage.NEW_USER.print(userType.toLowerCase());
		}
		catch (InvalidUserType e) {
			throw new InvalidCommandException();
		}
		catch (UserAlreadyExists e) {
			EMessage.USER_ALREADY_EXIST.print();
		}
	}

	private static void execProducts(Scanner scan, IEbayPT ebayPT)
			throws UserDenied {

		Iterator<IProduct> products = ebayPT.getProducts();

		if(products.hasNext()){
			EMessage.PRODUCTS_TITLE.print();

			while(products.hasNext()){
				IProduct productI = products.next();

				System.out.println(productI.getCode() + " "
						+ productI.getDescription() + " "
						+ productI.getState());
			}
		}
		else{
			EMessage.NOTHING_TO_LIST.print();
		}
	}

	private static void execLogout(Scanner scan, IEbayPT ebayPT) {
		try {
			EMessage.GOODBYE.print(ebayPT.logout().getUsername());
		}
		catch (NoUserLoggedIn e) {
			EMessage.NO_USER_LOGGED.print();
		}
	}

	private static void execLogin(Scanner scan, IEbayPT ebayPT) {
		try {
			String username = scan.next();
			ebayPT.login(username);
			
			EMessage.WELLCOME.print(username);
		}
		catch (AnotherUserAlreadyLoggedIn e) {
			EMessage.ANOTHER_USER_ALREADY_LOGGED.print();
		}
		catch(UserAlreadyLoggedIn e){
			EMessage.USER_ALREADY_LOGGED.print();
		}
		catch (InvalidUser e) {
			EMessage.INVALID_USER.print();
		}
	}

	private static void execList(Scanner scan, IEbayPT ebayPT)
			throws InvalidCommandException, UserDenied {
		
		switch (ECommand.List.valueOf(scan.next())) {
			case ALL: execListAll(scan, ebayPT); break;
			default: throw new InvalidCommandException();
		}
	}

	private static void execListAll(Scanner scan, IEbayPT ebayPT)
			throws UserDenied {
		
		Iterator<IUser> users = ebayPT.getUsers();
		
		EMessage.USERS_ALL_TITLE.print();
		
		while(users.hasNext()){
			IUser userI = users.next();
		
			System.out.println(userI.getUsername() + " " + userI.getName() + " "
					+ userI.getEmail() + " "
					+ userI.getUserType().toString());
		}
	}

	private static void execExit() {
		EMessage.EXIT.print();
		
		System.out.println();
		
		System.exit(0);
	}

	private static void execClose(Scanner scan, IEbayPT ebayPT)
			throws UserDenied {
		
		try {
			printWinningBid(ebayPT.closeAuction(scan.next()));
		}
		catch (NoBids e) {
			EMessage.NO_BIDS.print();
		}
		catch (InvalidAuction e) {
			EMessage.INVALID_AUCTION.print();
		}
	}
	
	private static void printWinningBid(String winner, int amount) {
		String[] args = { winner, Integer.toString(amount) };
		
		EMessage.WINNING_BID.print(args);
	}
	
	private static void printWinningBid(IBid winningBid) {
		printWinningBid(winningBid.getBidder().getUsername(),
				winningBid.getAmount());
	}

	private static void execCars(Scanner scan, IEbayPT ebayPT)
			throws InvalidCommandException, UserDenied {
		
		switch(ECommand.Cars.valueOf(scan.next())){
			case ALL: execCarsAll(scan, ebayPT); break;
			default: throw new InvalidCommandException();
		}
	}

	private static void execCarsAll(Scanner scan, IEbayPT ebayPT)
			throws UserDenied {
		
		try{
			
			Iterator <IAuction> auctions =
					ebayPT.getAuctions(EProductCategory.CAR);
			
			EMessage.CARS_ALL_TITLE.print();

			while(auctions.hasNext()){
				IAuction auctionI = auctions.next();
				ICar carI = (ICar) auctionI.getProduct();

				String seller = auctionI.getSeller().getUsername();

				int highestBid;

				try{
					highestBid = auctionI.getHighestBid().getAmount();
				}
				catch (NoBids e){
					highestBid = NO_BIDS_HIGHEST_BID_VALUE;
				}

				System.out.println(seller + " " + carI.getCode() + " " 
						+ carI.getMake() + " " + carI.getModel() + " "
						+ carI.getYear() + " " + auctionI.getBaseAmount() + " "
						+ highestBid);

			}
		}
		catch(NullPointerException e){
			EMessage.NOTHING_TO_LIST.print();
		}
	}

	private static void execBiddinds(Scanner scan, IEbayPT ebayPT)
			throws UserDenied {
		
		try {
			String seller = scan.next();
			String productCode = scan.next();
			
			Iterator <IBid> bids = ebayPT.getBiddings(seller, productCode);
		
			if(bids.hasNext()){
				
				EMessage.BIDDINGS_TITLE.print();
				
				System.out.println(seller + " " + productCode);
				
				while(bids.hasNext()){
					IBid bidI = bids.next();
					
					System.out.println(bidI.getBidder().getUsername() + " " 
					+ bidI.getAmount());
				}
				
				
			}
			else{
				EMessage.NOTHING_TO_LIST.print();
			}
		}
		catch (InvalidAuction e){
			EMessage.INVALID_AUCTION.print();
		}
		catch (NotAuctionSeller e) {
			EMessage.NOT_SELLER.print();
		}
		
		
	}

	private static void execAuction(Scanner scan, IEbayPT ebayPT)
			throws InvalidCommandException, UserDenied {
		
		switch (ECommand.Auction.valueOf(scan.next())) {
			case STANDARD: execAuctionStandard(scan, ebayPT); break;
			case PLAFOND: execAuctionPlafond(scan, ebayPT); break;
			default: throw new InvalidCommandException();
		}
		
	}

	private static void execAuctionStandard(Scanner scan, IEbayPT ebayPT)
			throws UserDenied {
		
		try {
			ebayPT.createAuctionStandard(scan.next(), scan.nextInt());
			
			EMessage.AUCTION_ADDED.print();
		}
		catch (InvalidProduct e) {
			EMessage.INVALID_PRODUCT.print();
		}
		catch (ProductNotAvailable e) {
			EMessage.PRODUCT_NOT_AVALIABLE.print();
		}
	}

	private static void execAuctionPlafond(Scanner scan, IEbayPT ebayPT)
			throws UserDenied {
		
		try {
			ebayPT.createAuctionPlafond(scan.next(), scan.nextInt(),
					scan.nextInt());
			
			EMessage.AUCTION_ADDED.print();
			
		}
		catch (InvalidProduct e) {
			EMessage.INVALID_PRODUCT.print();
		}
		catch (ProductNotAvailable e) {
			EMessage.PRODUCT_NOT_AVALIABLE.print();
		}
	}

	private static void execAdd(Scanner scan, IEbayPT ebayPT)
			throws InvalidCommandException, UserDenied {
		
		switch(ECommand.Add.valueOf(scan.next())){
			case BID: execAddBid(scan, ebayPT);	break;
			case CAR: execAddCar(scan, ebayPT); break;
			case TABLET: execAddTablet(scan, ebayPT); break;
			default: throw new InvalidCommandException();
		}
		
	}

	private static void execAddTablet(Scanner scan, IEbayPT ebayPT)
			throws UserDenied {
		
		try {
			ebayPT.createTablet(scan.nextLine().trim(), scan.nextLine(),
					scan.next(), scan.nextInt(), scan.nextInt());
			
			EMessage.PRODUCT_ADDED.print();
		}
		catch (ProductAlreadyExists e) {
			EMessage.PRODUCT_ALREADY_EXIST.print();
		}
	}

	private static void execAddCar(Scanner scan, IEbayPT ebayPT)
			throws UserDenied {
		
		try {
			ebayPT.createCar(scan.nextLine().trim(), scan.nextLine(),
					scan.next(), scan.next(), scan.nextInt());
			
			EMessage.PRODUCT_ADDED.print();
		}
		catch (ProductAlreadyExists e) {
			EMessage.PRODUCT_ALREADY_EXIST.print();
		}
	}

	private static void execAddBid(Scanner scan, IEbayPT ebayPT)
			throws UserDenied {
		
		try {
			printWinningBid(
					ebayPT.bid(scan.next(), scan.next(), scan.nextInt()));
		}
		catch (NullPointerException e){
			//Auction not closed with bid
			EMessage.BID.print();
		}
		catch (LowBidAmount e) {
			EMessage.BID_LOWER_THAN_BASE.print();
		}
		catch (BiddingClosedAuction e) {
			EMessage.INVALID_AUCTION.print();
		}
		catch (BiddingOwnAuction e) {
			EMessage.BIDDING_OWN_AUCTION.print();
		}
		catch (InvalidAuction e) {
			EMessage.INVALID_AUCTION.print();
		}
		
	}

	private static void invalidCommand(Scanner scan) {
		EMessage.INVALID_COMMAND.print();
	}
}
