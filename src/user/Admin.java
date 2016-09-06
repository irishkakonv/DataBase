package user;

import server.RequestType;


/**
 * Admin class with additional methods
 */
public class Admin extends  AuthUser {

    public Admin(String login, String passwd) {
        super(login, passwd);
        this.userType = UserType.ADMIN;
        permissions = new RequestType[] {RequestType.FIND, RequestType.ADD,
                                         RequestType.DELETE, RequestType.RMALL};
    }

    /** Add new user with permissions as AuthUser */
    public void addUser(String l, String p) {

    }

    /** Return list of all unauthorized users */
    public String[] lsUsers() {
        return null;

    }

    public void rmUser(String login) {

    }

    public void rmUsers() {

    }


}
