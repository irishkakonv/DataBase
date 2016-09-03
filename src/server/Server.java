package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;


/** The instance of this class contains key and value */
class Box {
    private String key;
    private String value;

    public Box(String k, String v){
        this.key = k;
        this.value = v;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}


/**
 * This class create the instance of server and run client's command.
 */
public class Server {
    private int port;
    private ServerSocket ss;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    String dbFilePath;
    LinkedList<Box> data;
    Request request;
    Answer answer;

    /** The constructor*/
    public Server(int port){
        this.port = port;
    }

    /** Creating the server socket, waiting for the connection*/
    public void start(){
//        try{
//            ss = new ServerSocket(this.port);
//            while(true){
//                System.out.println("Waiting for a client...");
//                /** accept connection */
//                socket = ss.accept();
//                System.out.println("The client ip is " + socket.getInetAddress());
//                /** input */
//                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                /** output */
//                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                exec();

//                try {
//                    bufferedWriter.write();
//                }
            }
//        } catch (IOException ex){
//            System.err.println("Can't start the server!");
//            ex.printStackTrace();
//        }
//    }

    /** This method control the sequence of the execution command */
    public void exec() {
        try {
            parseClientCommand();
            handleRequest();
        } catch (IOException ex) {
            System.err.println("Can't parse the command from user");
            ex.printStackTrace();
        }

    }

    private void parseClientCommand() throws IOException {
        RequestType type = RequestType.UNKNOUWN;    // default value
        String key = "<key>";                       // default value
        String value = "[value]";                   // default value
        String command = "ADD:<key>=[value]";       // for test
//        String command;
//        try {
//            command = bufferedReader.readLine();
//        } catch (IOException ex) {} // ?????????????????

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
        Request req = new Request(type, key, value);
        request = req;
    }

    private void handleRequest() throws IOException{
        /** handle request and fill the answer*/
        Answer ans = new Answer(request.getType(), AnswerType.NON, "");
        /** Fill the data*/
        readFile();
        switch (request.getType()) {
            case ADD:
                break;
            case FIND:
                break;
            case DELETE:
                break;
            default:
                System.out.println("Error: unknown error");
                throw new IOException();
        }
        data.clear();
        answer = ans;
    }


    /** Create the new thread for the new sever*/
    public static void main(String[] args) throws InterruptedIOException {
        Server server = new Server(4749);
//        server.start();
        server.start();
    }
}
