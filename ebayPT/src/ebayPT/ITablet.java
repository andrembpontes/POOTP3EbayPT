package ebayPT;

/**
 * Tablet product
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */
public interface ITablet extends IProduct {
	
	/**
	 * Return Tablet's brand'
	 * 
	 * @return Tablet brand 
	 */
	String getBrand();
	
	/**
	 * Return Tablet's size'
	 * 
	 * @return Tablet size 
	 */
	int getSize();
	
	/**
	 * Return Tablet's weight'
	 * 
	 * @return Tablet weight
	 */
	int getWeight();
}
