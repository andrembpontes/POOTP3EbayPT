package ebayPT;

public class Car extends Product implements ICar {

	private String 	make,	//Car make
					model; 	//Car model
	
	private int year;		//Car year
	
	public Car(String code, String description, String make, String model, int year) {
		
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
