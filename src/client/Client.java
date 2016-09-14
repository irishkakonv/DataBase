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
                    if (fuser.equalsIgnoreCase("close") || fuser.equalsIgnoreCase("exit")) break;
                    if (fuser.equalsIgnoreCase("--help") || fuser.equalsIgnoreCase("-h")) {
                        callHelp();
                        continue;
                    }
                    out.println(fuser);
                    out.flush();
                    fserver = in.readLine();
                    System.out.println(fserver);
                    callMessage();
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

     private void callMessage() {
         System.out.println("Enter please the command in format: ");
         System.out.println("COMMAND:<KEY>=[VALUE] ");
         System.out.println("If you want logoff enter please the command: LOGOFF");
         System.out.println("Type 'close' or 'exit' for exit");
         System.out.println("Use --help or -h for the help information: ");
     }

    private void welcomeMessage() {
        System.out.println("You are welcome.");
        System.out.println("Enter please login and password in format:");
        System.out.println("LOGIN:<login>:<password>");
        System.out.println("If you want login as the guest enter please: guest or GUEST");
        System.out.println("Type 'close' or 'exit' for exit");
        System.out.println("Use --help or -h for the help information: ");
    }

     private void callHelp() {
         System.out.println("1. LOGIN:<login>=<password> - Log in the service");
         System.out.println("2. LOGOFF - Finish the user session.");
         System.out.println("3. ADD:<KEY>=[VALUE] - Available for user and admin. Action - add the key and the value.");
         System.out.println("4. FIND:<KEY> - Available for guest, user and admin. Action - find the value by the key.");
         System.out.println("5. FIND:PATTERN - Available for guest, user and admin. Action - find the value by the pattern.");
         System.out.println("6. DELETE:<KEY> - Available for user and admin. Action - delete the pair by the key.");
         System.out.println("7. RMALL - Available for admim. Action - clears the database.");
         System.out.println("8. RMUSER - Available for admim. Action - delete user by login.");
         System.out.println("9. RMUSERS - Available for admim. Action - delete all users except admin.");
         System.out.println("10. ADDUSER - Available for admim. Action - add new user if doesn't exist.");
         System.out.println("11. LSUSER - Available for admim. Action - print list of available users.");
         System.out.println("Enter please the command: ");
     }

    public static void main (String[] args) {
        Client client = new Client("127.0.0.1", 4749);
    }
}
