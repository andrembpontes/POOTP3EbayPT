package ebayPT;

/**
 * Supertype for all types of products that can be auctioned in ebayPT.
 *
 * @author n42540: Rodrigo Simões; n42845: André Pontes
 */

public interface IProduct extends Comparable<IProduct>{
	/**
	 * Initial state of a product.
	 */
	static final EProductState START_STATE = EProductState.SALE; 
	
	/**
	 * Available state to create a new auction with
	 */
	static final EProductState AVAILABLE_STATE = EProductState.SALE;
	
	/**
	 * Gets the category of this product.
	 * @return category of this product
	 */
	EProductCategory getCategory();
	
	/**
	 * Gets the code of this product.
	 * @return code of this product
	 */
	String getCode();
	
	/**
	 * Gets the description of this product.
	 * @return description of this product
	 */
	String getDescription();
	
	/**
	 * Gets state of this product.
	 * @return state of this product
	 */
	EProductState getState();

	/**
	 * Verify if product is available to create a new auction with.
	 * Return True if yes
	 * 
	 * @return True if product is available to create new auction, else False
	 */
	boolean isAvaliable();

	/**
	 * Set product state
	 * 
	 * @param state new Product state
	 */
	void setState(EProductState state);
}
