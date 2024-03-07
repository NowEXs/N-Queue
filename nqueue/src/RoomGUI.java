import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoomGUI extends JFrame {

    private int roomNumber;

    public RoomGUI() {
        initComponents();
        setSize(275, 200);
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Room Selector");

        JButton first_button = new JButton("1");
        JButton second_button = new JButton("2");
        JButton third_button = new JButton("3");
        first_button.addActionListener(e -> {
            int selectedRoom = Integer.parseInt(first_button.getText());
            setRoomNumber(selectedRoom);
            dispose();
            openDemoForStudentDB();
        });
        second_button.addActionListener(e -> {
            int selectedRoom = Integer.parseInt(second_button.getText());
            setRoomNumber(selectedRoom);
            dispose();
            openDemoForStudentDB();
        });
        third_button.addActionListener(e -> {
            int selectedRoom = Integer.parseInt(third_button.getText());
            setRoomNumber(selectedRoom);
            dispose();
            openDemoForStudentDB();
        });


        JLabel label = new JLabel("Room Selector");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        getContentPane().add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(first_button);
        buttonPanel.add(second_button);
        buttonPanel.add(third_button);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(label, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void openDemoForStudentDB() {
        Demo_for_student_db demo = new Demo_for_student_db();
        demo.setSelectedRoom(getRoomNumber());
        demo.setVisible(true);
    }

    public int getRoomNumber() {
        return this.roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RoomGUI().setVisible(true));
    }
}
