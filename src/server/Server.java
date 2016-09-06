package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;


/**
 * This class create the instance of server and run client's command.
 */
public class Server {
    private int port;
    private ServerSocket ss;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    String dbFilePath;
    public LinkedList<Box> data;
    public Request request;
    public Answer answer;

    /** The constructor with port*/
    public Server(int port){
        this.port = port;
        this.dbFilePath = "C:\\Java\\MyProjects\\DataBase\\out\\production\\DataBase\\dbfiles\\db.txt";
        data = new LinkedList<Box>();
    }

    /** The constructor with port and dbPath */
    public Server(int port, String filePath){
        this.port = port;
        this.dbFilePath = filePath;
        data = new LinkedList<Box>();
    }

    /** Creating the server socket, waiting for the connection*/
    public void start(){
        try{
            ss = new ServerSocket(this.port);
            while(true) {
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
                    if (input.equalsIgnoreCase("exit")) break;
                    /** The common function */
                    exec(input);

                    if (answer == null) {
                        answer.setRequest(RequestType.ADD);
                        answer.setAnswer(AnswerType.NON);
                        answer.setValue("");
                        answer.setMessage("The value is required for this command!");
                    }
                    /** Send the answer to a client */
                    out.write("The answer: The command " + answer.getRequest() + " with status "
                            + answer.getAnswer() + ". The value is " + answer.getValue()
                            + " and the message: " + answer.getMessage() + '\n');
                    out.flush();
                }
            }
        } catch (IOException ex){
            System.err.println("Can't start the server!");
            ex.printStackTrace();
        }
    }

    /** This method control the sequence of the execution command */
    public void exec(String command) {
        try {
            parseClientCommand(command);
            handleRequest();
        } catch (IOException ex) {
            System.err.println("Can't parse the command from user");
            ex.printStackTrace();
            return;
        }

    }

    public void parseClientCommand(String command) throws IOException {
        RequestType type = RequestType.UNKNOUWN;    // default value
        String key = "<key>";                       // default required value
        String value = null;                        // default value

        if (!command.contains(":")) {
            throw new IOException();
        }

        String[] temp = command.split(":");
        switch (temp[0]) {
            case "ADD":
                type = RequestType.ADD;
                break;
            case "FIND":
                type = RequestType.FIND;
                break;
            case "DELETE":
                type = RequestType.DELETE;
                break;
            case "UNKNOUWN":
                type = RequestType.UNKNOUWN;
                break;
            default:
                System.out.println("The command is not correct. Can't parse the command");
                throw new IOException();
        }

        if (temp[1].contains("=")) {
            String[] keyAndValue = temp[1].split("=");
            key = keyAndValue[0];
            value = keyAndValue[1];
        } else {
            key = temp[1];
        }

        if (type == RequestType.ADD && value == null) {
            System.out.println("The value is required for this command!");
            throw new IOException();
        }
        Request req = new Request(type, key, value);
        request = req;
    }

    /** handle request and fill the instance of the answer*/
    public void handleRequest() throws IOException{
        Answer ans = new Answer(request.getType(), AnswerType.NON, "", "");     // the default answer
        /** Fill the data*/
        readFile();
        switch (request.getType()) {
            case ADD:
                System.out.println("Handle ADD...");
                if (keyExists(request.getKey())) {
                    ans.setAnswer(AnswerType.FAIL);
                    ans.setMessage("The key " + request.getKey() + " already exists");
                } else {
                    System.out.println("Adding key-value");
                    data.add(new Box(request.getKey(), request.getValue()));
                    writeFile();
                    ans.setAnswer(AnswerType.OK);
                    ans.setMessage("The key " + request.getKey()
                            + " and the value " + request.getValue() + " were added successfully");
                }
                break;

            case FIND:
                System.out.println("Handle FIND...");
                try {
                    ans.setValue(findBox(request.getKey()));
                    ans.setAnswer(AnswerType.OK);
                    ans.setMessage("The value was found");
                } catch (Exception ex) {
                    ans.setAnswer(AnswerType.FAIL);
                    ans.setMessage("The key " + request.getKey() +" was not found");
                }
                break;

            case DELETE:
                System.out.println("Handle DELETE...");
                if (keyExists(request.getKey())) {
                    removeBox(request.getKey());
                    writeFile();

                    ans.setAnswer(AnswerType.OK);
                    ans.setMessage("The key " + request.getKey() + " was deleted");
                } else {
                    ans.setAnswer(AnswerType.FAIL);
                    ans.setMessage("The key " + request.getKey() + " was not found");
                }
                break;

            default:
                System.out.println("Error: unknown error");
                throw new IOException();
        }
        data.clear();   // clean the data
        answer = ans;
    }

    /** Open file and fill the data */
    public void readFile() throws FileNotFoundException{
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
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            try {
                /** Read the file by a string */
                String str;
                while((str = in.readLine()) != null) {
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
    }

    /** Writing to the file from the data */
    public void writeFile() throws IOException {
        File file = new File(dbFilePath);

        try {
            /** If file doesn't exist, create the file */
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                for (Box box: data) {
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
        for (Box box: data) {
            if (box.getKey().equals(key)) {
                System.out.println("The value was found");
                return box.getValue();
            }
        }
        System.out.println("The value wasn't found");
        throw new Exception();
    }

    public void removeBox(String key) {
        for (Box box: data) {
            if (box.getKey().equals(key)) {
                data.remove(box);
                break;
            }
        }
    }

    /** Check the key */
    public boolean keyExists(String key) {
        for (Box box: data) {
            if (box.getKey().equals(key)) {
                System.out.println("The key exists");
                return true;
            }
        }
        System.out.println("The key doesn't exist");
        return false;
    }

    /** Check the file */
    public static void exists(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }
    }

    /** Create the new thread for the new sever*/
    public static void main(String[] args) throws InterruptedIOException {
        Server server = new Server(4749);
        server.start();
    }
}
