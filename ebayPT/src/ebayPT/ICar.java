package ebayPT;

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
