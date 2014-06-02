package ebayPT;

/**
 * Car product
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */
public interface ICar extends IProduct {
	
	/**
	 *  Get car make
	 * 
	 * @return car make
	 * 
	 */
	String getMake();
	
	/**
	 * Get car model
	 * 
	 * @return car model
	 */
	String getModel();
	
	/**
	 * Get car year
	 * 
	 * @return car year
	 */
	int getYear();
}
