package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Create the client and send the command
 */
public class Client {
    private InetAddress addr;
    private int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader inu;

    /** Create socket and connect server */
    public Client (String addr, int port) {
        System.out.println("Welcome to Client side");
        welcomeMessage();
            try {
                /** Connect to server */
                this.addr = InetAddress.getByName(addr);
                this.port = port;
                socket = new Socket(addr, port);
                /** Input socket */
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                /** Output socket */
                out = new PrintWriter(socket.getOutputStream(), true);
                inu = new BufferedReader(new InputStreamReader(System.in));

                String fuser, fserver;

                while ((fuser = inu.readLine()) != null) {
                    if (fuser.equalsIgnoreCase("close")) break;
                    if (fuser.equalsIgnoreCase("exit")) break;
                    if (fuser.equalsIgnoreCase("--help") || fuser.equalsIgnoreCase("-h")) {
                        callHelp();
                        continue;
                    }
                    out.println(fuser);
                    out.flush();
                    fserver = in.readLine();
                    System.out.println(fserver);
                    welcomeMessage();
                }
                out.close();
                in.close();
                inu.close();
                socket.close();
            } catch (UnknownHostException ex) {
                System.err.println("illegal" + addr);
            } catch (IOException e) {
                System.err.println();
            }
        }

     private void welcomeMessage() {
         System.out.println("Enter please the command in format ");
         System.out.println("COMMAND:<KEY>=[VALUE] ");
         System.out.println("Type 'close' or 'exit' for exit");
         System.out.println("Use --help or -h for the help information: ");
     }

     private void callHelp() {
         System.out.println("1. ADD:<KEY>=[VALUE]");
         System.out.println("2. FIND:<KEY>");
         System.out.println("3. DELETE:<KEY>");
         System.out.println("Enter please the command: ");
     }

    public static void main (String[] args) {
        Client client = new Client("127.0.0.1", 4749);
    }
}
