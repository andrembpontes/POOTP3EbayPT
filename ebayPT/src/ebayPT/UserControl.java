package ebayPT;

import ebayPT.exceptions.AnotherUserAlreadyLoggedIn;
import ebayPT.exceptions.NoUserLoggedIn;
import ebayPT.exceptions.UserAlreadyLoggedIn;
import ebayPT.exceptions.UserDenied;

/**
 * Implementation of IUserControl
 * 
 * @author n42540: Rodrigo Simoes; n42845: Andre Pontes
 */
public class UserControl implements IUserControl {

	/**
	 * Used when GUEST_USER == null
	 */
	public static final IUserType GUEST_USER_TYPE = EUserType.GUEST;
	
	/**
	 * User used by default or when no user
	 */
	public static final IUser GUEST_USER = null;
	
	/**
	 * Actual logged user
	 */
	private IUser loggedUser;
	
	/**
	 * Same as UserControl(UserControl.GUEST_USER)
	 */
	public UserControl() {
		this.loggedUser = GUEST_USER;
	}
	
	/**
	 * Creates a new UserControl
	 * 
	 * @param initialUser initial user logged in
	 */
	public UserControl(IUser initialUser) {
		this.loggedUser = initialUser;
	}
	
	@Override
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

	@Override
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

	@Override
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
