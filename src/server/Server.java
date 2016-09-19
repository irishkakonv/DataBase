package server;

import user.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class create the instance of server and run client's command.
 */
public class Server {
    private int port;
    private ServerSocket ss;
    public Socket socket;
    public BufferedReader in;
    private PrintWriter out;

    // Public only for test
    public String dbFilePath;
    public LinkedList<Box> data;
    public Request request;
    public Answer answer;
    public UserType userType;
    public UnauthUser user;
    /** Path to users file*/
    public String userFilePath;

    /**
     * The constructor with port and dbPath
     */
    public Server(int port, String filePath, String userFilePath) {
        this.port = port;
        this.dbFilePath = filePath;
        this.userFilePath = userFilePath;
        data = new LinkedList<Box>();
    }

    /**
     * Creating the server socket, waiting for the connection
     */
    public void start() {
        try {
            /** 0 - if the first message with login and password from user,
             * 1 - if the second message with command */
            int flag = 0;
            this.userType = UserType.UNKNOWN;   // default value
            Main main = new Main(userFilePath);             // The main class of user package
            ss = new ServerSocket(this.port);
            while (true) {
                System.out.println("Waiting for a client...");
                /** accept connection */
                socket = ss.accept();
                System.out.println("The client ip is " + socket.getInetAddress());
                /** input */
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                /** output */
                out = new PrintWriter(socket.getOutputStream(), true);

                String input;
                while ((input = in.readLine()) != null) {
                    try {
                        parseClientCommand(input);
                    } catch (IOException ex) {
                        System.err.println("Can't parse the command from user: " + ex.getMessage());
                        ex.printStackTrace();
                        out.write("UNKNOWN NON" + '\n');
                        out.flush();
                        continue;
                    }

                    // if the client is guest
                    if (flag == 0 && this.userType.equals(UserType.UNUSER)) {
                        flag = 1;
                        out.write("Hello the guest" + '\n');
                        out.flush();
                        continue;
                    }

                    if (request.getType().equals(RequestType.LOGOFF)) {
                        flag = 0;
                        this.userType = UserType.UNKNOWN;
                        out.write("LOGOFF OK" + '\n');
                        out.flush();
                        continue;
                    }

                    // initialise userType
                    if (flag == 0) {
                        this.userType = main.handleLoginRequest(this.request);

                        if (this.userType.equals(UserType.UNKNOWN)) {
                            out.write("LOGIN FAIL" + '\n');
                            out.flush();
                            continue;
                        }

                        flag = 1;
                        out.write("LOGIN OK" + '\n');
                        out.flush();
                        continue;
                    }

                    // Only for admin
                    if (flag == 1 && this.userType.equals(UserType.ADMIN) &&
                            (this.request.getType().equals(RequestType.ADDUSER) ||
                                    this.request.getType().equals(RequestType.LSUSER) ||
                                    this.request.getType().equals(RequestType.RMUSER) ||
                                    this.request.getType().equals(RequestType.RMUSERS))) {
                        String adminAnswer = main.handleAdminCommand(this.request);
                        out.write(adminAnswer + '\n');
                        out.flush();
                        continue;
                    }

                    /** The common function - check permissions and handle request  */
                    exec();

                    if (answer == null) {
                        answer = new Answer(request.getType(), AnswerType.FAIL, "", "");
                    }

                    /** Send the answer to a client */
                    out.write(answer.getRequest() + " " + answer.getAnswer() + " " + answer.getValue() + '\n');
                    out.flush();
                    this.request = null;    // erase the filed
                    this.answer = null;     // erase the filed
                }
            }
        } catch (IOException ex) {
            System.err.println("Can't start the server!");
            ex.printStackTrace();
        }
    }

    public void checkPermissions() throws IOException {
        if (this.userType.equals(UserType.UNUSER)) {
            user = new UnauthUser("guest");
            if (user.checkPermissions(this.request.getType())) return;
        } else if (this.userType.equals(UserType.USER)) {
            AuthUser user = new AuthUser("", "");
            if (user.checkPermissions(this.request.getType())) return;
        } else if (this.userType.equals(UserType.ADMIN)) {
            Admin admin = new Admin("", "");
            if (admin.checkPermissions(this.request.getType())) return;
        }
        throw new IOException("Permission denied");
    }

    /**
     * This method control the sequence of the execution command
     */
    public void exec() {
        try {
            checkPermissions();
            handleRequest();
        } catch (IOException ex) {
            System.err.println("Can't parse the command from user: " + ex.getMessage());
            ex.printStackTrace();
            return; // try to do next command
        }
    }

    /**
     * Parse the client command and fill the object of request
     */
    public void parseClientCommand(String command) throws IOException {
        RequestType type;
        String key;
        String value = null;                        // default value

        if (command.equals("RMALL")) {
            request = new Request(RequestType.RMALL, "", "");
            return;
        }

        if (command.equals("LOGOFF")) {
            request = new Request(RequestType.LOGOFF, "", "");
            return;
        }

        if (command.equals("LSUSER")) {
            request = new Request(RequestType.LSUSER, "", "");
            return;
        }

        if (command.equals("RMUSERS")) {
            request = new Request(RequestType.RMUSERS, "", "");
            return;
        }

        if (command.equals("GUEST") || command.equals("guest")) {
            this.userType = UserType.UNUSER;
            return;
        }

        /** handle the empty command */
        if (!command.contains(":")) {
            throw new IOException("The command was not entered");
        }

        String[] temp = command.split(":");
        switch (temp[0]) {
            case "LOGIN":
                type = RequestType.LOGIN;
                break;
            case "ADD":
                type = RequestType.ADD;
                break;
            case "FIND":
                type = RequestType.FIND;
                break;
            case "DELETE":
                type = RequestType.DELETE;
                break;
            case "RMALL":
                type = RequestType.RMALL;
                break;
            case "UNKNOUWN":
                type = RequestType.UNKNOUWN;
                break;
            // for admin
            case "ADDUSER":
                type = RequestType.ADDUSER;
                break;
            case "RMUSER":
                type = RequestType.RMUSER;
                break;

            default:
                System.out.println("The command is not correct. Can't parse the command");
                throw new IOException("The command is not correct. Can't parse the command");
        }

        /** handle the incorrect command */
        try {
            if (temp[1].contains("=")) {
                String[] keyAndValue = temp[1].split("=");
                key = keyAndValue[0];
                value = keyAndValue[1];
            } else {
                key = temp[1];
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("ERROR: The key is required for this command!");
            throw new IOException("ERROR: The key is required for this command!");
        }

        if ((type == RequestType.ADD || type == RequestType.LOGIN) && value == null) {
            System.err.println("ERROR: The value is required for this command!");
            throw new IOException("ERROR: The value is required for this command!");
        }
        Request req = new Request(type, key, value);
        request = req;
    }

    /**
     * handle request and fill the instance of the answer
     */
    public void handleRequest() throws IOException {
        Answer ans = new Answer(request.getType(), AnswerType.NON, "", "");     // the default answer
        /** Fill the data*/
        readFile();
        switch (request.getType()) {
            case ADD:
                System.out.println("Handle ADD...");
                if (keyExists(request.getKey())) {
                    ans.setAnswer(AnswerType.FAIL);
                    //ans.setMessage("The key " + request.getKey() + " already exists");
                } else {
                    System.out.println("Adding key-value");
                    data.add(new Box(request.getKey(), request.getValue()));
                    writeFile();
                    ans.setAnswer(AnswerType.OK);
//                    ans.setMessage("The key " + request.getKey() + " and the value " + request.getValue() + " were added successfully");
                }
                break;

            case FIND:
                System.out.println("Handle FIND...");
                try {
                    ans.setValue(findBox(request.getKey()));
                    ans.setAnswer(AnswerType.OK);
//                    ans.setMessage("The value was found");
                } catch (Exception ex) {
                    ans.setAnswer(AnswerType.FAIL);
//                    ans.setMessage("The key " + request.getKey() +" was not found");
                }
                break;

            case DELETE:
                System.out.println("Handle DELETE...");
                if (keyExists(request.getKey())) {
                    removeBox(request.getKey());
                    writeFile();

                    ans.setAnswer(AnswerType.OK);
//                    ans.setMessage("The key " + request.getKey() + " was deleted");
                } else {
                    ans.setAnswer(AnswerType.FAIL);
//                    ans.setMessage("The key " + request.getKey() + " was not found");
                }
                break;

            case RMALL:
                System.out.println("Handle RMALL...");
                data.clear();
                writeFile();
                ans.setAnswer(AnswerType.OK);
//                ans.setMessage("Everything was removed");
                break;
            default:
                System.out.println("Error: unknown error");
                throw new IOException();
        }
        data.clear();   // clean the data
        answer = ans;
    }

    /**
     * Open file and fill the data
     */
    public void readFile() throws FileNotFoundException {
        /** The special object for building strings */
        File file = new File(dbFilePath);
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
                    if (str.contains(" ")) {
                        String[] keyAndValue = str.split(" ");
                        try {
                            Box box = new Box(keyAndValue[0], keyAndValue[1]);
                            data.add(box);
                        } catch (IndexOutOfBoundsException ex) {
                            System.out.println("ERROR: incorrect data in the file");
                            throw new IOException(ex);
                        }
                    } else {
                        System.out.println("ERROR: incorrect data in the file");
                        throw new IOException();
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
        File file = new File(dbFilePath);

        try {
            /** If file doesn't exist, create the file */
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                for (Box box : data) {
                    out.print(box.getKey() + " " + box.getValue() + '\n');
                }
            } finally {
                out.close();
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("The file was written successfully");
    }

    public String findBox(String key) throws Exception {
        /** for text format */
        for (Box box : data) {
            if (box.getKey().equals(key)) {
                System.out.println("The value was found");
                return box.getValue();
            }
        }
        /** for regex format */
        String results = "";
        Pattern regexp = Pattern.compile(key);
        for (Box box : data) {
            Matcher matcher = regexp.matcher(box.getKey());
            if (matcher.find()) {
                results += box.getKey() + "=" + box.getValue() + "; ";
            }
        }
        if (results != "") return results;

        System.out.println("The value wasn't found");
        throw new Exception();
    }

    public void removeBox(String key) {
        for (Box box : data) {
            if (box.getKey().equals(key)) {
                data.remove(box);
                break;
            }
        }
    }

    /**
     * Check the key
     */
    public boolean keyExists(String key) {
        for (Box box : data) {
            if (box.getKey().equals(key)) {
                System.out.println("The key exists");
                return true;
            }
        }
        System.out.println("The key doesn't exist");
        return false;
    }

    /**
     * Check the file
     */
    public static void exists(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }
    }

    /**
     * Create the new sever
     */
    public static void main(String[] args) throws InterruptedIOException {
        Server server = new Server(4749, "/home/stratopedarx/Java/Projects/DataBase/src/dbfiles/db.txt",
                                         "/home/stratopedarx/Java/Projects/DataBase/src/dbfiles/users");

        server.start();
    }
}
