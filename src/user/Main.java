package user;

import server.Box;

import java.io.*;
import java.util.LinkedList;

import static server.Server.exists;

/**
 * Main class manage user packages
 */
public class Main {
    private String login;
    private String passwd;
    private UserType userType;
    private String userFilePath;
    private LinkedList<UserBox> usersData = new LinkedList<UserBox>();

    public Main() {
        userFilePath = "/home/stratopedarx/Java/Projects/DataBase/src/dbfiles/users";
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

    public LinkedList<UserBox> getUsersData() {
        return usersData;
    }

    public void setUsersData(LinkedList<UserBox> usersData) {
        this.usersData = usersData;
    }

    /** Return UserType */
    public UserType mapUserType(String type) {
        if(type == "user"){
            return UserType.USER;
        }
        return UserType.ADMIN;
    }

    /** Return string with user type */
    public String mapStrUserType(UserType userType) {
        if (userType == UserType.USER) {
            return "user";
        }
        return "admin";
    }

    /** Open file and fill the users data */
    public void readFile() throws FileNotFoundException {
        /** The special object for building strings */
        File file = new File(userFilePath);
        try {
            exists(file);     // check the file path
        } catch (FileNotFoundException ex) {
            System.out.println("The file doesn't exist!");
            throw new RuntimeException(ex);
        }

        try {
            /** The object for reading in buffer*/
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            try {
                /** Read the file by a string */
                String str;
                while((str = in.readLine()) != null) {
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

    /** Writing to the file from the data */
    public void writeFile() throws IOException {
        File file = new File(userFilePath);

        try {
            /** If file doesn't exist, create the file */
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                for (UserBox box: usersData) {
                    out.print(box.getLogin() + ":" + box.getPasswd() + ":"
                            + mapStrUserType(box.getUserType()) +'\n');
                }
            } finally {
                out.close();
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("The file was written successfully");
    }

    public void parseCommand(String loginPasswd) throws IOException{
        System.out.println("Handle server command");
        if (loginPasswd == "guest" || loginPasswd == "Guest" || loginPasswd == "GUEST") {
            this.setUserType(UserType.UNUSER);
        }

        if (loginPasswd.contains(":")) {
            try {
                String[] strings = loginPasswd.split(":");
                this.setLogin(strings[0]);
                this.setPasswd(strings[1]);
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("ERROR: incorrect server's command");
                throw new IOException("ERROR: incorrect server's command");
            }
        }
        else {
            System.out.println("ERROR: incorrect server's command");
            throw new IOException("ERROR: incorrect server's command");
        }
    }

    /** check user. If user exist and the password is correct set userType */
    public void checkUser() throws IOException {
        for (UserBox box: usersData) {
            if (box.getLogin() == this.getLogin() && box.getPasswd() == this.getPasswd()) {
                setUserType(box.getUserType());
                return;
            }
            throw new IOException("Incorrect login or password");
        }
    }

    public String handleAdminCommand(String command) {
        Admin admin = new Admin(this.getLogin(), this.getPasswd());

    }

    /** main method */
    public UserType main(String args) throws IOException{
        try {
            parseCommand(args);
            checkUser();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return UserType.UNKNOWN;
        }
        return this.getUserType();
    }
}
