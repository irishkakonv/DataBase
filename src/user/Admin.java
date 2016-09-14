package user;

import server.RequestType;


/**
 * Admin class with additional methods
 */
public class Admin extends AuthUser {
    private RequestType adminCommand;
    private String paramsCommand;

    public Admin(String login, String passwd) {
        super(login, passwd);
        this.userType = UserType.ADMIN;
        permissions = new RequestType[]{RequestType.FIND, RequestType.ADD,
                RequestType.DELETE, RequestType.RMALL};
    }
}
