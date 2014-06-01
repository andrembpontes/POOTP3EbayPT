package ebayPT;

import ebayPT.exceptions.AnotherUserAlreadyLoggedIn;
import ebayPT.exceptions.NoUserLoggedIn;
import ebayPT.exceptions.UserAlreadyLoggedIn;
import ebayPT.exceptions.UserDenied;

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

	public void login(IUser user) throws UserAlreadyLoggedIn,
		AnotherUserAlreadyLoggedIn {

		if(user == null)
			throw new NullPointerException();
		
		if(this.loggedUser == GUEST_USER){
			this.loggedUser = user;
		}
		else{
			if(this.loggedUser.equals(user))
				throw new UserAlreadyLoggedIn();
		
			if(this.loggedUser != GUEST_USER)
				throw new AnotherUserAlreadyLoggedIn();
		}
	}

	public IUser logout() throws NoUserLoggedIn {
		if(this.loggedUser == GUEST_USER) {
			throw new NoUserLoggedIn();
		} else {
			IUser userLoggedout = this.loggedUser;
			this.loggedUser = GUEST_USER;
			
			return userLoggedout;
		}
	}
	
	@Override
	public IUser getLoggedUser() throws NoUserLoggedIn {
		if(this.loggedUser == GUEST_USER)
			throw new NoUserLoggedIn();
		
		return this.loggedUser;
	}

	@Override
	public void executeAction(IAction action) throws UserDenied {
		if(!this.isAllowed(action))
			throw new UserDenied(action.getAllowedUserType());
	}
	

}
