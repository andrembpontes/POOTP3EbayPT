package ebayPT;

/**
 * Represents a product of car type
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 *
 */
public class Car extends Product implements ICar {

	private String 	make,	//Car make
					model; 	//Car model
	
	private int year;		//Car year
	
	/**
	 * Creates a new Car product with givens data
	 * 
	 * @param code: Car code
	 * @param description: Description
	 * @param make: Car make
	 * @param model: Car model
	 * @param year: Car year
	 */
	public Car(String code, String description, String make, String model,
			int year) {
		
		super(EProductCategory.CAR, code, description);
		
		this.make = make;
		this.model = model;
		this.year = year;
	}
	
	@Override
	public String getMake() {
		return this.make;
	}

	@Override
	public String getModel() {
		return this.model;
	}

	@Override
	public int getYear() {
		return this.year;
	}

}
