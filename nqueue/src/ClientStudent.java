import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class ClientStudent implements ActionListener {
    private BufferedReader in;
    JFrame frame; private static String txt; private ClientTeacher ct;
    private JPanel p1; private JButton b1, b2;
    private JTextField t1; private JTextField t2;
    public ClientStudent(){
        frame = new JFrame("StudentApp");
        p1 = new JPanel(); t1 = new JTextField(10); t2 = new JTextField(10);
        b1 = new JButton("Enter"); b2 = new JButton("Start Server"); t2.setEditable(false);

        p1.add(t1); p1.add(t2);
        p1.setBackground(Color.black);
        p1.add(b1); p1.add(b2);
        b1.addActionListener(this); b2.addActionListener(this);

        frame.add(p1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(500,250,400,400);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new ClientStudent();
    }
    public void startServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try(Socket clientSocket = new Socket("localhost", 1111);) {
                    System.out.println("Client Start...");
                    System.out.println("Enter: ");
                    PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                    output.println(txt);
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    System.out.println(in.readLine());
                }
                catch (ConnectException e){
                    System.out.println("Not Have Server!!");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.b1){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ClientStudent.txt = t1.getText();
                    ClientStudent.this.t2.setText(ClientStudent.txt);
                    ClientStudent.this.startServer();
                }
            });
            new DemoServer();
        }
        else if (e.getSource() == b2){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new ClientTeacher().startServer(1111);
                }
            });
        }
    }
}