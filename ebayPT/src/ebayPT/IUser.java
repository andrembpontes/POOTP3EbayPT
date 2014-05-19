package ebayPT;

public interface IUser {
	
	String getEmail();
	
	String getName();
	
	String getUsername();
	
	IUserType getUserType();
	
	boolean equals(IUser user);
}
