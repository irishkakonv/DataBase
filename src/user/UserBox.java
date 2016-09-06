package user;

import server.Box;

/**
 * The instance of this class contains login, passwd and userType
 */
public class UserBox {
    private String login;
    private String passwd;
    private UserType userType;

    public UserBox(String login, String passwd, UserType userType) {
        setLogin(login);
        setPasswd(passwd);
        setUserType(userType);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
