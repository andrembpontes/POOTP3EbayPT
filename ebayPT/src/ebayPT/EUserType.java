package ebayPT;

public enum EUserType implements IUserType {
	GUEST(1),
	REGISTERED(2),
	ADMIN(3);
	
	private int accessLevel;
	
	EUserType(int accessLevel){
		this.accessLevel = accessLevel;
	}
	
	// TODO esta cena devia receber um IUserType, mas implicaria um downcasting
	// ou perda de encapsulamento
	public boolean hasAccessLevel(EUserType userType){
		return this.accessLevel >= userType.accessLevel;
	}
}
