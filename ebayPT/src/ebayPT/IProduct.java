package ebayPT;

public interface IProduct {
	static final EProductState START_STATE = EProductState.SALE; 
	
	EProductCategory getCategory();
	
	String getCode();
	
	String getDescription();
	
	EProductState getState();
}
