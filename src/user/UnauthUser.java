package user;

import server.RequestType;

/**
 * Unauthorized user class
 */
public class UnauthUser extends User {

    public UnauthUser(String login) {
        this.login = login;
        this.passwd = null;
        this.userType = UserType.UNUSER;
        permissions = new RequestType[]{RequestType.FIND};
    }

    @Override
    public boolean checkPermissions(RequestType rType) {
        for (RequestType type: permissions) {
            if (type == rType) return true;
        }
        return false;
    }
}
