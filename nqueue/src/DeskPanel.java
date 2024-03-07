import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class DeskPanel extends JPanel {
    private int selectedRoom;
    private JPanel deskPanel = new JPanel();
    private ArrayList<Integer> deskNumbers = new ArrayList();

    public DeskPanel() {
        this.setLayout(new BorderLayout());
        this.deskPanel.setLayout(new GridLayout(0, 4, 10, 15));
        this.deskNumbers.clear();
        this.fetchDeskNumbers();
        this.add(this.deskPanel, BorderLayout.CENTER);
        setSize(200, 200);
    }

    public void setSelectedRoom(int selectedRoom) {
        this.selectedRoom = selectedRoom;
        this.deskPanel.removeAll(); // Clear existing desk buttons
        fetchDeskNumbers(); // Fetch desk numbers for the new selected room
    }

    private void fetchDeskNumbers() {
        // Database connection parameters
        String url = "jdbc:mysql://192.168.1.112:3306/NQueue_db";
        String user = "root";
        String password = "1234";

        String sql = "SELECT SeatID FROM SeatManager WHERE RoomNumber = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, selectedRoom); // Select the seatID from the room that user selected
            ResultSet resultSet = statement.executeQuery();

            int counter = 0;
            while (resultSet.next()) {
                int seatID = resultSet.getInt("SeatID");
                JButton deskButton = new JButton(selectedRoom + " - " + seatID);
                this.deskPanel.add(deskButton);
                counter++;
                if (counter >= 20) {
                    break;
                }
            }
            JButton addingButton = new JButton("+"); // ปุ่มเพิ่ม
            JButton deletingButton = new JButton("-"); // ปุ่มลด
            this.deskPanel.add(addingButton);
            this.deskPanel.add(deletingButton);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeskPanel deskPanel = new DeskPanel();
            deskPanel.setVisible(true);
        });
    }
}
