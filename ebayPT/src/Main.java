import java.util.Scanner;

import ebayPT.AnotherUserAlreadyLoggedInException;
import ebayPT.BiddingClosedAuctionException;
import ebayPT.BiddingOwnAuctionException;
import ebayPT.EProductCategory;
import ebayPT.EbayPT;
import ebayPT.IAuction;
import ebayPT.IBid;
import ebayPT.ICar;
import ebayPT.IEbayPT;
import ebayPT.IProduct;
import ebayPT.ITablet;
import ebayPT.IUser;
import ebayPT.InvalidAuctionException;
import ebayPT.InvalidProductException;
import ebayPT.InvalidUserException;
import ebayPT.InvalidUserTypeException;
import ebayPT.LowBidAmountException;
import ebayPT.NoBidsException;
import ebayPT.NoUserLoggedInException;
import ebayPT.NotSellerException;
import ebayPT.ProductAlreadyExistsException;
import ebayPT.ProductNotAvaliableException;
import ebayPT.UserAlreadyLoggedInException;
import ebayPT.UserDeniedException;

import java.util.Iterator;


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
				
				//TODO delete scan.nextLine(); //Go to next line
				System.out.println(); //Print blank line
			}
			catch(IllegalArgumentException e){
				invalidCommand(scan);
			}
			catch(InvalidCommandException e){
				invalidCommand(scan);
			}
			catch (UserDeniedException e) {
				UserDenied(e);
			}
		}
	}

	private static void execTablets(Scanner scan, IEbayPT ebayPT)
			throws UserDeniedException {
		
		switch (ECommand.Tablets.valueOf(scan.next())) {
			case ALL: execTabletsAll(scan, ebayPT); break;
			case DIMENSION: execTabletsDimension(scan, ebayPT);
		}
	}

	private static void execTabletsDimension(Scanner scan, IEbayPT ebayPT)
			throws UserDeniedException {
		
		//Iterator<IAuction> tablets =
		//		ebayPT.getAuctionsTabletBySize(scan.nextInt());
		//
		//TODO implement
	}

	private static void execTabletsAll(Scanner scan, IEbayPT ebayPT)
			throws UserDeniedException {

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
			catch(NoBidsException e){
				highestBid = NO_BIDS_HIGHEST_BID_VALUE;
			}


			System.out.print(seller + " " + tabletI.getCode() + " "
					+ tabletI.getBrand() + " " + tabletI.getSize() + " "
					+ tabletI.getWeight() + " " + auctionI.getBaseAmount()
					+ " " + highestBid);
		}
	}

	private static void UserDenied(UserDeniedException e) {
		EMessage.USER_DENIED.print(e.getLowestType().toString());
	}

	private static void execSales(Scanner scan, IEbayPT ebayPT)
			throws UserDeniedException {
		
		Iterator<IUser> users = ebayPT.getUsersBySales();
		
		EMessage.SALES_TITLE.print();
		
		while(users.hasNext()){
			IUser userI = users.next();
			
			System.out.println(userI.getUsername() + " " + userI.getName() + " "
					+ userI.getEmail() + " " + userI.getAuctionsCount() + " "
					+ userI.getSales());
		}
		
	}

	private static void execRegister(Scanner scan, IEbayPT ebayPT)
			throws UserDeniedException, InvalidCommandException {
		
		String userType = scan.next();
		String username = scan.next();
		String name = scan.nextLine();
		String email = scan.nextLine();		
		
		try {
			ebayPT.addUser(email, name, username, userType);
		}
		catch (InvalidUserTypeException e) {
			throw new InvalidCommandException();
		}
	}

	private static void execProducts(Scanner scan, IEbayPT ebayPT)
			throws UserDeniedException {

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
		catch (NoUserLoggedInException e) {
			EMessage.NO_USER_LOGGED.print();
		}
	}

	private static void execLogin(Scanner scan, IEbayPT ebayPT) {
		try {
			String username = scan.next();
			ebayPT.login(username);
			
			EMessage.WELLCOME.print(username);
		}
		catch (AnotherUserAlreadyLoggedInException e) {
			EMessage.ANOTHER_USER_ALREADY_LOGGED.print();
		}
		catch(UserAlreadyLoggedInException e){
			EMessage.USER_ALREADY_LOGGED.print();
		}
		catch (InvalidUserException e) {
			EMessage.INVALID_USER.print();
		}
	}

	private static void execList(Scanner scan, IEbayPT ebayPT)
			throws InvalidCommandException, UserDeniedException {
		
		switch (ECommand.List.valueOf(scan.next())) {
			case ALL: execListAll(scan, ebayPT); break;
			default: throw new InvalidCommandException();
		}
	}

	private static void execListAll(Scanner scan, IEbayPT ebayPT)
			throws UserDeniedException {
		
		Iterator<IUser> users = ebayPT.getUsers();
		
		EMessage.USERS_ALL_TITLE.print();
		
		while(users.hasNext()){
			IUser userI = users.next();
		
			System.out.println(userI.getUsername() + " " + userI.getName() + " "
					+ userI.getEmail() + " " + userI.getUserType());
		}
	}

	private static void execExit() {
		EMessage.EXIT.print();
		
		System.exit(0);
	}

	private static void execClose(Scanner scan, IEbayPT ebayPT)
			throws UserDeniedException {
		
		try {
			IBid winnerBid = ebayPT.closeAuction(scan.next());
			
			String[] args = { winnerBid.getBidder().getUsername(),
					Integer.toString(winnerBid.getAmount()) };
			
			EMessage.WINNING_BID.print(args);
		}
		catch (NoBidsException e) {
			EMessage.NO_BIDS.print();
		}
		catch (InvalidAuctionException e) {
			EMessage.INVALID_AUCTION.print();
		}
	}

	private static void execCars(Scanner scan, IEbayPT ebayPT)
			throws InvalidCommandException, UserDeniedException {
		
		switch(ECommand.Cars.valueOf(scan.next())){
			case ALL: execCarsAll(scan, ebayPT); break;
			default: throw new InvalidCommandException();
		}
	}

	private static void execCarsAll(Scanner scan, IEbayPT ebayPT)
			throws UserDeniedException {
		
		Iterator <IAuction> auctions = ebayPT.getAuctions(EProductCategory.CAR);
		
		if(auctions.hasNext()){
			EMessage.CARS_ALL_TITLE.print();

			while(auctions.hasNext()){
				IAuction auctionI = auctions.next();
				ICar carI = (ICar) auctionI.getProduct();

				String seller = auctionI.getSeller().getUsername();

				int highestBid;

				try{
					highestBid = auctionI.getHighestBid().getAmount();
				}
				catch (NoBidsException e){
					highestBid = NO_BIDS_HIGHEST_BID_VALUE;
				}

				System.out.println(seller + " " + carI.getCode() + " " 
						+ carI.getMake() + " " + carI.getModel() + " "
						+ carI.getYear() + " " + auctionI.getBaseAmount() + " "
						+ highestBid);

			}
		}
		else{
			EMessage.NOTHING_TO_LIST.print();
		}
	}

	private static void execBiddinds(Scanner scan, IEbayPT ebayPT)
			throws UserDeniedException {
		
		try {
			Iterator <IBid> bids = ebayPT.getBiddings(scan.next(), scan.next());
		
			if(bids.hasNext()){
				
				EMessage.BIDDINGS_TITLE.print();
				
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
		catch (InvalidAuctionException e){
			EMessage.INVALID_AUCTION.print();
		}
		catch (NotSellerException e) {
			EMessage.NOT_SELLER.print();
		}
		
		
	}

	private static void execAuction(Scanner scan, IEbayPT ebayPT)
			throws InvalidCommandException, UserDeniedException {
		
		switch (ECommand.Auction.valueOf(scan.next())) {
			case STANDARD: execAuctionStandard(scan, ebayPT); break;
			case PLAFOND: execAuctionPlafond(scan, ebayPT); break;
			default: throw new InvalidCommandException();
		}
		
	}

	private static void execAuctionStandard(Scanner scan, IEbayPT ebayPT)
			throws UserDeniedException {
		
		try {
			ebayPT.createAuctionStandard(scan.next(), scan.nextInt());
		}
		catch (InvalidProductException e) {
			EMessage.INVALID_PRODUCT.print();
		}
		catch (ProductNotAvaliableException e) {
			EMessage.PRODUCT_NOT_AVALIABLE.print();
		}
	}

	private static void execAuctionPlafond(Scanner scan, IEbayPT ebayPT)
			throws UserDeniedException {
		
		try {
			ebayPT.createAuctionPlafond(scan.next(), scan.nextInt(),
					scan.nextInt());
		}
		catch (InvalidProductException e) {
			EMessage.INVALID_PRODUCT.print();
		}
		catch (ProductNotAvaliableException e) {
			EMessage.PRODUCT_NOT_AVALIABLE.print();
		}
	}

	private static void execAdd(Scanner scan, IEbayPT ebayPT)
			throws InvalidCommandException, UserDeniedException {
		
		switch(ECommand.Add.valueOf(scan.next())){
			case BID: execAddBid(scan, ebayPT);	break;
			case CAR: execAddCar(scan, ebayPT); break;
			case TABLET: execAddTablet(scan, ebayPT); break;
			default: throw new InvalidCommandException();
		}
		
	}

	private static void execAddTablet(Scanner scan, IEbayPT ebayPT)
			throws UserDeniedException {
		
		try {
			ebayPT.createTablet(scan.next(), scan.next(), scan.next(),
					scan.nextInt(), scan.nextInt());
			
			EMessage.PRODUCT_ADDED.print();
		}
		catch (ProductAlreadyExistsException e) {
			EMessage.PRODUCT_ALREADY_EXIST.print();
		}
	}

	private static void execAddCar(Scanner scan, IEbayPT ebayPT)
			throws UserDeniedException {
		
		try {
			ebayPT.createCar(scan.next(), scan.next(), scan.next(), scan.next(),
					scan.nextInt());
			
			EMessage.PRODUCT_ADDED.print();
		}
		catch (ProductAlreadyExistsException e) {
			EMessage.PRODUCT_ALREADY_EXIST.print();
		}
	}

	private static void execAddBid(Scanner scan, IEbayPT ebayPT)
			throws UserDeniedException {
		
		try {
			ebayPT.bid(scan.next(), scan.next(), scan.nextInt());
			
			EMessage.BID.print();
			
		}
		catch (LowBidAmountException e) {
			EMessage.BID_LOWER_THAN_BASE.print();
		}
		catch (BiddingClosedAuctionException e) {
			EMessage.INVALID_AUCTION.print();
		}
		catch (BiddingOwnAuctionException e) {
			EMessage.BIDDING_OWN_AUCTION.print();
		}
		catch (InvalidAuctionException e) {
			EMessage.INVALID_AUCTION.print();
		}
		
	}

	private static void invalidCommand(Scanner scan) {
		//TODO remove
		
		System.out.println("Damn peace... command is wrong ( again... -.-'' )");
	}
}
