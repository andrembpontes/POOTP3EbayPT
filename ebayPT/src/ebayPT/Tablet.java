package ebayPT;

public class Tablet extends Product implements ITablet {

	private String brand;
	private int size, weight;
	
	Tablet(EProductCategory category, String code, String description,
			EProductState state, String brand, int size, int weight) {
		super(category, code, description, state);
		
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
