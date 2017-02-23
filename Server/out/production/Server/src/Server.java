import java.net.*;
import java.io.*;
import java.sql.SQLException;

/**
 * Created by Under on 01.02.2017.
 */
public class Server {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        int port = 7777;
        DataBaseConn.setConn();
        String msgHistosy = DataBaseConn.readDB();
        printOnServer(msgHistosy);

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
                String[] splitedLine = line.split("~");
                switch (splitedLine.length){
                    case 1: sendMsgHistory(out);
                        break;
                    case 3: sendMsg(line, out);
                    default : break;
                }


            }
        }
        catch(Exception x){
            x.printStackTrace();
        }
    }

    private static void printOnServer(String messages) throws SQLException {
        String records = messages;
        String[] splitedRecords = records.split("\n");

        for(String line : splitedRecords){
            String[] splitedLine = line.split("~");
            //Date time = new Date((long)Integer.parseInt(splitedLine[3]) * 1000);

            System.out.println(splitedLine[0] + ": " + splitedLine[1] + "\t at: " + splitedLine[2]);
        }
    }

    private static void sendMsgHistory(DataOutputStream out) throws SQLException, IOException {
        String msgHistory = DataBaseConn.readDB();
        String[] splitedMsgHistory = msgHistory.split("\n");
        for (String msg: splitedMsgHistory) {
            out.writeUTF(msg);
            out.flush();
        }
    }

    private static void sendMsg(String line, DataOutputStream out) throws IOException {
        out.writeUTF(line);
        out.flush();
        System.out.println("Server waiting for the next message...");
        System.out.println();
    }
}
