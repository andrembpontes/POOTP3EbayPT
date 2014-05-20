package ebayPT;

public class Tablet extends Product implements ITablet {

	private String brand;
	private int size, weight;
	
	public Tablet(EProductCategory category, String code, String description,
			String brand, int size, int weight) {
		super(category, code, description);
		
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
