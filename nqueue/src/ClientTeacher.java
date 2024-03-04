import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientTeacher implements ActionListener {
    public ServerSocket soc; private BufferedReader in;
    JFrame frame; private static String txt;
    private JPanel p1; private JButton b1;
    private JTextField t2;
    public ClientTeacher(){
        frame = new JFrame("TeacherApp");
        p1 = new JPanel(); t2 = new JTextField(10);
        t2.setEditable(false); b1 = new JButton("View Comment"); b1.addActionListener(this);

        p1.add(t2); p1.add(b1);
        p1.setBackground(Color.black);

        frame.add(p1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(500,250,400,400);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new ClientTeacher().startServer(1111);

    }
    public void startServer(int port){
                try {
                    while (true) {
                        System.out.println("...");
                        soc = new ServerSocket(port);
                        Socket sev = soc.accept();
                        System.out.println("Input Receive!");
                        in = new BufferedReader(new InputStreamReader(sev.getInputStream()));
                        ClientTeacher.txt = in.readLine();
                        PrintWriter output = new PrintWriter(sev.getOutputStream(), true);
                        t2.setText(txt);
                        soc.close();
                        output.println("Complete!!");
                    }
                }
                catch (BindException e) {
                    throw new RuntimeException(e);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1){
            ClientTeacher.this.startServer(1111);
        }
    }
}