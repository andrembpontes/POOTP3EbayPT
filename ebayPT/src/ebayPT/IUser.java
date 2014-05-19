package ebayPT;

public interface IUser {
	
	String getEmail();
	
	String getName();
	
	String getUsername();
	
	boolean equals(IUser user);
}
