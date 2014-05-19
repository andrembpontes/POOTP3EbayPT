package ebayPT;

public class UserControl implements IUserControl {

	public static final IUser GUEST_USER = null;
	public static final IUser DEFAULT_USER = GUEST_USER;
	
	private IUser loggedUser;
	
	public UserControl() {
		this.loggedUser = DEFAULT_USER;
	}
	
	public UserControl(IUser initialUser) {
		this.loggedUser = initialUser;
	}
	
	public boolean isAllowed(EAction action) {
		EUserType allowedType = action.getAllowedUserType();
		if(loggedUser == GUEST_USER) {
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
