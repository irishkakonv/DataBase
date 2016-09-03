package server;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import jdk.jfr.internal.BufferWriter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class create the instance of server and run client's command.
 */
public class Server {
    private int port;
    private ServerSocket ss;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    /** The constructor*/
    public Server(int port){
        this.port = port;
    }

    /** Creating the server socket, waiting for the connection*/
    public void start(){
        try{
            ss = new ServerSocket(this.port);
            while(true){
                System.out.println("Waiting for a client...");
                /** accept connection */
                socket = ss.accept();
                System.out.println("The client ip is " + socket.getInetAddress());
                /** input */
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                /** output */
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            }
        } catch (IOException ex){
            System.err.println("Can't start the server!");
            ex.printStackTrace();
        }
    }

    /** Create the new thread for the new sever*/
    public static void main(String[] args) throws InterruptedIOException {
        Thread serverThread = new Thread(() -> {
            Server server = new Server(4749);
            server.start();
        });
    }



}
