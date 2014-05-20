package ebayPT;

/**
 * Enumeration of different user types and correspondent access level
 * Implementation based on unique path level possibilities
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 *
 */
public enum EUserType implements IUserType {
					//User types enumeration
	GUEST(1),		//Guest	User		| 1
	REGISTERED(2),	//Registered		| 2
	ADMIN(3);		//Administrator		| 3
	
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
