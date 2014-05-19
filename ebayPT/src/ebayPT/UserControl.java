package ebayPT;

public class UserControl implements IUserControl {

	private IUser loggedUser;
	
	public boolean isAllowed(EAction action) {
		EUserType allowedType = action.getAllowedUserType();
		if(loggedUser == null) {
			return EUserType.GUEST.hasAccessLevel(allowedType);
		} else {
			return this.loggedUser.getUserType().hasAccessLevel(allowedType);
		}
	}

	public void login(IUser user) {
		this.loggedUser = user;
	}

	public void logout() {
		this.loggedUser = null;
	}

}
