import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Under on 02.02.2017.
 */
public class ChatClient extends JFrame
    implements WindowListener, MouseListener, KeyListener{

    private TextArea messageArea;
    private TextField sendArea;
    private String userName;
    int serverPort = 7777;
    String address = "127.0.0.1";
    private InetAddress ipAddress;
    private Socket socket;

    public ChatClient(String s){

        super(s);

        this.addWindowListener(this);
        this.setSize(800, 600);
        this.setResizable(true);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        messageArea = new TextArea();
        messageArea.setEditable(false);
        this.add(messageArea, "Center");
        messageArea.setFont(new Font("Arial", Font.PLAIN, 14));

        Panel p = new Panel();
        p.setLayout(new FlowLayout());

        sendArea = new TextField(50);
        sendArea.addKeyListener(this);
        sendArea.setFont(new Font("Arial", Font.PLAIN, 14));

        p.add(sendArea);
        p.setBackground(new Color(222, 222, 222));

        Button send = new Button("Send");
        send.addMouseListener(this);
        p.add(send);

        Button clear = new Button("Clear");
        clear.addMouseListener(this);

        p.add(clear);
        this.add(p, "South");
        this.setVisible(true);

        try{
            ipAddress = InetAddress.getByName(address);
            socket = new Socket(ipAddress, serverPort);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        receiveMsg();
    }




    public static void main(String[] args) {
        ChatClient c = new ChatClient("CHATTT");


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void receiveMsg(){
        try{
            InputStream sin = socket.getInputStream();
            DataInputStream in = new DataInputStream(sin);
            String line;


            while(true){
                line = in.readUTF();
                line+="\n";
                messageArea.setText(messageArea.getText() + line);

            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void sendMsg(){

        System.out.println(sendArea.getText().length());
        if(sendArea.getText().length() == 0){
            return;
        }
        else{
            String line = sendArea.getText();
            try{
                OutputStream sout = socket.getOutputStream();
                DataOutputStream out = new DataOutputStream(sout);
                out.writeUTF(line);
                out.flush();
                clearMsg();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }

    }

    private void clearMsg(){
        sendArea.setText(" ");
        sendArea.setText("");
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)){
            switch (((Button)e.getSource()).getLabel()){
                case "Clear": clearMsg();
                    break;
                case "Send" : sendMsg();
                    break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
