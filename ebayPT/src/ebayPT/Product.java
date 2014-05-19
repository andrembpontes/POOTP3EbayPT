package ebayPT;

public abstract class Product implements IProduct {

	private EProductCategory category;
	private String code, description;
	private EProductState state;

	Product (EProductCategory category, String code, String description, EProductState state){
		this.category = category;
		this.code = code;
		this.description = description;
		this.state = state;
	}
	
	@Override
	public EProductCategory getCategory() {
		return this.category;
	}

	@Override
	public final String getCode() {
		return this.code;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public EProductState getState() {
		return this.state;
	}

}
