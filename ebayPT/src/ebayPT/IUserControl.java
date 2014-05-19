package ebayPT;

public interface IUserControl {
	boolean isAllowed(EAction action);
	
	void login(IUser user);
	void logout();
}
