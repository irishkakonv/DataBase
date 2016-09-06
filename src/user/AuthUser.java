package user;

import server.RequestType;

/**
 * Authorized user class
 */
public class AuthUser extends UnauthUser {

    public AuthUser(String login, String passwd) {
        super(login);
        this.passwd = passwd;
        this.userType = UserType.USER;
        permissions = new RequestType[]{RequestType.FIND, RequestType.ADD, RequestType.DELETE};
    }
}
