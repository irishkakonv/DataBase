package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    private BufferedReader br;
    private BufferedWriter bw;

    /** Create socket and connect server */
    public Client (String addr, int port) {
        try {
            /** Connect to server */
            this.addr = InetAddress.getByName(addr);
            this.port = port;
            socket = new Socket(addr, port);
            /** Input socket */
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            /** Output socket */
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (UnknownHostException ex) {
            System.err.println("illegal" + addr);
        } catch (IOException e) {
            System.err.println();
        }

        getCommandFromConsole();
        getAnswer();
    }

    /** Get the command from the console and input the server buffer */
    private void getCommandFromConsole() {
        try {
            System.out.println("Enter the command(ADD/FIND/DELETE:key=value)...");
            /** Input the command from the console */
            BufferedReader bufCommandFromConsole = new BufferedReader(new InputStreamReader(System.in));
            String line = bufCommandFromConsole.readLine();
            /** Write the command to server buffer */
            bw.write(line);
            bw.flush();
        } catch (IOException ex) {
            System.err.println("Can't handle client request");
            ex.printStackTrace();
        }
    }

    private void getAnswer() {
        try {
            /** Read answer from socket buffer */
            String serverAnsw = br.readLine();
            System.out.println("Answer: " + serverAnsw);

        } catch (IOException ex) {
            System.err.println("Can't handle server answer");
            ex.printStackTrace();
        }
    }

    public static void main (String[] args) {
        Client client = new Client("127.0.0.1", 4749);
    }
}
