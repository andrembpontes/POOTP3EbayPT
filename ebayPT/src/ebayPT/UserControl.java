package ebayPT;

public class UserControl implements IUserControl {

	public static final IUserType GUEST_USER_TYPE = EUserType.GUEST;
	public static final IUser GUEST_USER = null;
	
	private IUser loggedUser;
	
	public UserControl() {
		this.loggedUser = GUEST_USER;
	}
	
	public UserControl(IUser initialUser) {
		this.loggedUser = initialUser;
	}
	
	public boolean isAllowed(IAction action) {
		IUserType allowedType = action.getAllowedUserType();
		
		if(loggedUser == GUEST_USER) {
			try{
				return loggedUser.getUserType().hasAccessLevel(allowedType);
			}
			catch(NullPointerException e){
				return GUEST_USER_TYPE.hasAccessLevel(allowedType);
			}
			
		} else {
			return this.loggedUser.getUserType().hasAccessLevel(allowedType);
		}
	}

	public void login(IUser user) throws UserAlreadyLoggedInException,
		AnotherUserAlreadyLoggedInException {

		if(user == null)
			throw new NullPointerException();
		
		if(this.loggedUser == GUEST_USER){
			this.loggedUser = user;
		}
		else{
			if(this.loggedUser.equals(user))
				throw new UserAlreadyLoggedInException();
		
			if(this.loggedUser != GUEST_USER)
				throw new AnotherUserAlreadyLoggedInException();
		}
	}

	public IUser logout() throws NoUserLoggedInException {
		if(this.loggedUser == GUEST_USER) {
			throw new NoUserLoggedInException();
		} else {
			IUser userLoggedout = this.loggedUser;
			this.loggedUser = GUEST_USER;
			
			return userLoggedout;
		}
	}
	
	@Override
	public IUser getLoggedUser() throws NoUserLoggedInException {
		if(this.loggedUser == GUEST_USER)
			throw new NoUserLoggedInException();
		
		return this.loggedUser;
	}

	@Override
	public void executeAction(IAction action) throws UserDeniedException {
		if(!this.isAllowed(action))
			throw new UserDeniedException(action.getAllowedUserType());
	}
	

}
