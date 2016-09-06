package user;

import server.RequestType;

/**
 * Abstract class with common fields for inherit classes an authorized user, unauthorized user and admin.
 */
abstract public class User {
    String login;
    String passwd;
    UserType userType;
    RequestType[] permissions;

    abstract boolean checkPermissions(RequestType rType);
}
