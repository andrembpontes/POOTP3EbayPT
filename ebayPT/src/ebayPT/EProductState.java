package ebayPT;

/**
 * Enumeration containing all possible product states
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 *
 */
public enum EProductState {
	/**
	 * Product available for selling
	 */
	SALE,
	
	/**
	 * Auction running for this product
	 */
	AUCTION,
	
	/**
	 * Product has been sold
	 */
	SOLD;
}
