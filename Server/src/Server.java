import java.net.*;
import java.io.*;
/**
 * Created by Under on 01.02.2017.
 */
public class Server {
    public static void main(String[] args) {
        int port = 7777;
        try{
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Waiting for a client...");
            Socket socket = ss.accept();
            System.out.println("Someone connected=)");
            System.out.println();
            InputStream sin =  socket.getInputStream();
            OutputStream sout = socket.getOutputStream();
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            String line;
            while(true){
                line = in.readUTF();
                System.out.println("Connected essence send a message:   " + line);
                System.out.println("Server sending it back");
                out.writeUTF(line);
                out.flush();
                System.out.println("Server waiting for the next message...");
                System.out.println();
            }
        }
        catch(Exception x){
            x.printStackTrace();
        }
    }
}
