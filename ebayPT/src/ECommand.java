/**
 * Possible user commands.
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */
public enum ECommand {
	CARS,
	TABLETS,
	REGISTER,
	EXIT,
	LOGIN,
	LOGOUT,
	LIST,
	SALES,
	ADD,
	AUCTION,
	CLOSE,
	PRODUCTS,
	BIDDINGS;
	
	enum Cars{
		ALL;
	}
	
	enum Tablets{
		ALL,
		DIMENSION;
	}
	
	enum List{
		ALL;
	}
	
	enum Add{
		CAR,
		TABLET,
		BID;
	}
	
	enum Auction{
		STANDARD,
		PLAFOND;
	}
}
