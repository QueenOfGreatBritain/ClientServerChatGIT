/**
 * Created by Under on 19.02.2017.
 */
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConn {
    private static  Connection conn;
    private static  Statement statmnt;
    private static ResultSet resSet;

    public static void setConn() throws ClassNotFoundException, SQLException {
        String dbPath = "messagesDB.db";
        conn = null;
        Class.forName("org.sqlite.JDBC");
        Path path = Paths.get(dbPath);

        if(Files.notExists(path)){
            File f = new File(dbPath);
            createDB();
            writeDB();
        }

        conn = DriverManager.getConnection("jdbc:sqlite:messagesDB.db");
        System.out.println("Data base connected!");
        statmnt = conn.createStatement();

    }

    public static void createDB() throws SQLException {
        if(statmnt != null)
            System.out.println("statmnt != null");
        statmnt.execute("CREATE TABLE if not exist 'messages' ('id' INTEGER PRIMARY" +
                " KEY AUTOINCREMENT, 'username' TEXT, 'message' TEXT, 'time' INTEGER)");
        System.out.println("Table created or existing already!");
    }

    private static void writeDB() throws SQLException {
        statmnt.execute("INSERT INTO 'messages' ('username', 'message', 'time') " +
                "VALUES('Вася', 'Привет, Мир!'," + (int)(System.currentTimeMillis()/1000) + " )");
        statmnt.execute("INSERT INTO 'messages' ('username', 'message', 'time') " +
                "VALUES('Петя', 'Привет, Вася!'," + (int)(System.currentTimeMillis()/1000) + " )");
        statmnt.execute("INSERT INTO 'messages' ('username', 'message', 'time') " +
                "VALUES('Маша', 'Привет, мальчики!'," + (int)(System.currentTimeMillis()/1000) + " )");

        System.out.println("Table filled!");
    }

    public static void saveMessage(String name, String message, int time) throws SQLException {
        statmnt.execute("INSERT INTO 'messages' ('name', 'message', 'time')VALUES('" +
                name +"', '" + message + "'," + time + " )");
    }

    public static String readDB() throws SQLException {
        resSet = statmnt.executeQuery("SELECT * FROM messages ORDER BY time");
        String msgHistory = "";

        while(resSet.next()){
            String name = resSet.getString("name");
            String message = resSet.getString("message");
            String time = resSet.getString("time");

            String record = name + "~" + message + "~" + time + "\n";
            msgHistory += record;
        }

        return msgHistory;
    }

    public static void closeDB() throws SQLException {
        conn.close();
        statmnt.close();
        resSet.close();
    }


}
