import net.sf.saxon.trans.SymbolicName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class ClientStudent implements ActionListener {
    private BufferedReader in;
    JFrame frame; private static String txt; private ClientTeacher ct;
    private JPanel p1,pp1,pp2,pp3; private JButton b1, b2;
    private JTextField t1; private JTextField t2;
    public ClientStudent(){
        frame = new JFrame("StudentApp");
        p1 = new JPanel(new GridLayout(3,1)); t1 = new JTextField(25); t1.setPreferredSize(new Dimension(25,25));
        pp1 = new JPanel(); pp1.setBackground(Color.black); pp2 = new JPanel(); pp2.setBackground(Color.black); pp3 = new JPanel(); pp3.setBackground(Color.black);
        b1 = new JButton("Enter"); b2 = new JButton("Start Server");

        pp1.add(b1); pp2.add(t1); pp3.add(b2);
        p1.add(pp3); p1.add(pp2); p1.add(pp1);
        p1.setBackground(Color.black);
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
                } catch (EOFException e) {
                    e.printStackTrace();
                } catch (Exception e) {
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