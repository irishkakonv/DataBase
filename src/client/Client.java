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


    public Client (String addr, int port) {
        try {
            /** connect to server */
            this.addr = InetAddress.getByName(addr);
            this.port = port;
            socket = new Socket(addr, port);
            /** input */
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            /** output */
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (UnknownHostException ex) {
            System.err.println("illegal" + addr);
        } catch (IOException e) {
            System.err.println();
        }

        getStr();
    }

    private void getStr() {
        try {
            bw.write(String.valueOf(System.in));
            bw.flush();

            String svrAnsw = br.readLine();
            System.out.println("Answer: " + svrAnsw);

        } catch (IOException ex) {
            System.err.println("Can't handle client request");
            ex.printStackTrace();
        }
    }

    public static void main (String[] args) {
        Client client = new Client("127.0.0.1", 4749);
    }
}
