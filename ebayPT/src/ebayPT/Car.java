package ebayPT;

public class Car extends Product implements ICar {

	private String make, model;
	private int year;
	
	public Car(EProductCategory category, String code, String description,
			EProductState state, String make, String model, int year) {
		super(category, code, description, state);
		
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
