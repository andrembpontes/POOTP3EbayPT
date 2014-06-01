package ebayPT;

/**
 * Abstract implementation of IProduct. For each concrete type of product
 * that can be auctioned in Ebay@PT there is a corresponding subclass of Product.
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */

public abstract class Product implements IProduct {
	private EProductCategory category;
	private String code, description;
	private EProductState state;

	Product (EProductCategory category, String code, String description){
		this.category = category;
		this.code = code;
		this.description = description;
		
		this.state = IProduct.START_STATE;
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
	
	@Override
	public int compareTo(IProduct product){
		return this.getCode().compareTo(product.getCode());
	}
	
	@Override
	public boolean isAvaliable(){
		return this.state.equals(IProduct.AVAILABLE_STATE);
	}
	
	@Override
	public void setState(EProductState state){
		this.state = state;
	}

}
