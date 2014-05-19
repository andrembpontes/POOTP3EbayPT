package ebayPT;

public enum EUserType {
	GUEST(1),
	REGISTERED(2),
	ADMIN(3);
	
	private int accessLevel;
	
	EUserType(int accessLevel){
		this.accessLevel = accessLevel;
	}
	
	public boolean hasAccessLevel(EUserType userType){
		return this.accessLevel >= userType.accessLevel;
	}
}
