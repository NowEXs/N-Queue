import java.awt.*;
import javax.swing.*;

public class GUI {
    private JFrame frame;
    private JPanel panel1, panel2;
    private JLabel text;
    private JButton std, ta, pfs;
    public GUI(){
        frame = new JFrame();
        frame.setTitle("N-Queue");
        frame.setBounds(170, 80, 1200, 700);

        panel1 = new JPanel();
        panel2 = new JPanel();

        text = new JLabel("Select your role");

        std = new JButton("Student");
        ta = new JButton("TA");
        pfs = new JButton("Professor");

        panel2.add(text);
        panel2.add(std);
        panel2.add(ta);
        panel2.add(pfs);
        panel1.add(panel2);
        frame.add(panel1);

        panel2.setLayout(new GridLayout(4, 1));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
