import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientTeacher implements ActionListener {
    private ServerSocket soc;
    private BufferedReader in;
    private JFrame frame;
    private JPanel p1;
    private JButton b1;
    private JTextField t2;
    private JTextArea ta;

    public ClientTeacher() {
        frame = new JFrame("TeacherApp");
        ta = new JTextArea(50, 50);
        ta.setEditable(false);
        p1 = new JPanel();
        t2 = new JTextField(10);
        t2.setEditable(false);
        b1 = new JButton("View Comment History");
        b1.addActionListener(this);

        p1.add(t2);
        p1.add(b1);
        p1.setBackground(Color.black);

        frame.add(p1, BorderLayout.NORTH);
        frame.add(new JScrollPane(ta)); // Use JScrollPane to enable scrolling
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(500, 250, 400, 400);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ClientTeacher().startServer(1111);
    }

    public void HistoryInput() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Comment.txt"), "UTF-8"))) {
            StringBuilder historyText = new StringBuilder();
            String line;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            while ((line = br.readLine()) != null) {
                historyText.append(line).append("\n");
            }
            ta.setText(dtf.format(LocalDateTime.now())+historyText.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer(int port) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Comment.txt", true), "UTF-8")))) {
            soc = new ServerSocket(port);
            while (true) {
                System.out.println("Waiting for input...");
                Socket sev = soc.accept();
                System.out.println("Input Received!");
                in = new BufferedReader(new InputStreamReader(sev.getInputStream(), "UTF-8"));
                String txt = in.readLine();
                pw.println(txt);
                pw.flush();
                PrintWriter output = new PrintWriter(sev.getOutputStream(), true);
                t2.setText(txt);
                output.println("Complete!!");
                sev.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            HistoryInput();
        }
    }
}