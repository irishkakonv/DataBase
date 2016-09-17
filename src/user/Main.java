package user;

import server.Request;

import java.io.*;
import java.util.LinkedList;

import static server.Server.exists;
import static user.UserType.USER;

/**
 * Main class manage user packages
 */
public class Main {
    private String login;
    private String passwd;
    private UserType userType;
    private String userFilePath;
    public LinkedList<UserBox> usersData;


    public Main(String userFilePath) {
        setUserFilePath(userFilePath);
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

    public void setUserFilePath(String userFilePath) {
        this.userFilePath = userFilePath;
    }

    /**
     * Return UserType
     */
    public UserType mapUserType(String type) {
        if (type.equals("user")) {
            return UserType.USER;
        }
        return UserType.ADMIN;
    }

    /**
     * Return string with user type
     */
    public String mapStrUserType(UserType userType) {
        if (userType == USER) {
            return "user";
        }
        return "admin";
    }

    /**
     * Open file and fill the users data
     */
    public void readFile() throws FileNotFoundException {
        /** The special object for building strings */
        File file = new File(userFilePath);
        this.usersData = new LinkedList<UserBox>();
        try {
            exists(file);     // check the file path
        } catch (FileNotFoundException ex) {
            System.out.println("The file doesn't exist!");
            throw new RuntimeException(ex);
        }

        try {
            /** The object for reading in buffer*/
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                /** Read the file by a string */
                String str;
                while ((str = in.readLine()) != null) {
                    String[] strings = str.split(":");
                    try {
                        UserBox userBox = new UserBox(strings[0], strings[1], mapUserType(strings[2]));
                        usersData.add(userBox);

                    } catch (IndexOutOfBoundsException ex) {
                        System.out.println("ERROR: incorrect data in the file");
                        throw new IOException(ex);
                    }
                }
            } finally {
                /** Close the file */
                in.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("The file was read successfully");
    }

    /**
     * Writing to the file from the data
     */
    public void writeFile() throws IOException {
        File file = new File(userFilePath);

        try {
            /** If file doesn't exist, create the file */
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                for (UserBox box : usersData) {
                    out.print(box.getLogin() + ":" + box.getPasswd() + ":"
                            + mapStrUserType(box.getUserType()) + '\n');
                }
            } finally {
                out.close();
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("The file was written successfully");
    }

    /**
     * check user. If user exist and the password is correct set userType
     */
    public void checkUser(String login, String passwd) throws IOException {
        for (UserBox box : usersData) {
            if (box.getLogin().equals(login) && box.getPasswd().equals(passwd)) {
                setUserType(box.getUserType());
                setLogin(login);
                setPasswd(passwd);
                return;
            }
        }
        throw new IOException("Incorrect login or password");
    }

    /**
     * check user's login
     */
    public boolean userExists(String login) {
        for (UserBox box : usersData) {
            if (box.getLogin().equals(login)) return true;
        }
        return false;
    }

    /**
     * return UserType for user who try logging or raise error
     */
    public UserType handleLoginRequest(Request req) {

        try {
            readFile();
            checkUser(req.getKey(), req.getValue());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return UserType.UNKNOWN;
        }
        return this.getUserType();
    }

    /**
     * return an answer to server
     */
    public String handleAdminCommand(Request req) throws IOException {
        String answer = "";

        switch (req.getType()) {
            case ADDUSER:
                System.out.println("Handle ADDUSER");
                if (!userExists(req.getKey())) {
                    addUser(req.getKey(), req.getValue());
                    answer = "ADDUSER OK";
                    writeFile();    // save and close the file
                } else {
                    System.out.println("User already exists");
                    answer = "ADDUSER FAIL";
                }
                break;

            case RMUSER:
                System.out.println("Handle RMUSER");
                if (userExists(req.getKey())) {
                    rmUser(req.getKey());
                    writeFile();    // save and close the file
                    answer = "RMUSER OK";
                } else {
                    System.out.println("User doesn't exists");
                    answer = "RMUSER FAIL";
                }
                break;

            case RMUSERS:
                System.out.println("Handle RMUSERS");
                rmUsers();
                writeFile();    // save and close the file
                answer = "RMUSERS OK";
                break;

            case LSUSER:
                System.out.println("Handle LSUSER");
                answer = "LSUSER OK " + lsUsers();
                break;

            default:
                System.out.println("The command is not correct. Can't parse the command");
                throw new IOException("The command is not correct. Can't parse the command");
        }

        return answer;
    }

    /**
     * Add new user with permissions as user
     */
    public void addUser(String login, String passwd) {
        usersData.add(new UserBox(login, passwd, USER));
    }

    /**
     * Return list of all unauthorized users
     */
    public String lsUsers() {
        String results = "";
        for (UserBox box : usersData) {
            results += box.getLogin() + " " + box.getPasswd() + "; ";
        }
        return results;
    }

    /**
     * Delete user by login
     */
    public void rmUser(String login) {
        for (UserBox box : usersData) {
            if (box.getLogin().equals(login)) {
                usersData.remove(box);
                return;
            }
        }
    }

    /**
     * Delete all users except admin
     */
    public void rmUsers() {
        usersData.clear();
        usersData.add(new UserBox(this.getLogin(), this.getPasswd(), UserType.ADMIN));
    }
}
