package ebayPT;

/**
 * Represents a tablet type product
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 *
 */
public class Tablet extends Product implements ITablet {

	/**
	 * Brand
	 */
	private String brand;
	
	/**
	 * Size
	 */
	private int size;
	
	/**
	 * Weight
	 */
	private int weight;
	
	/**
	 * Creates a new Tablet product with givens data
	 * 
	 * @param code: Tablet code
	 * @param description: Description
	 * @param brand: Tablet brand
	 * @param size: Tablet size
	 * @param weight: Tablet weight
	 */
	public Tablet(String code, String description, String brand, int size,
			int weight) {
		
		super(EProductCategory.TABLET, code, description);
		
		this.brand = brand;
		this.size = size;
		this.weight = weight;
		
	}

	@Override
	public String getBrand() {
		return this.brand;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public int getWeight() {
		return this.weight;
	}

}
