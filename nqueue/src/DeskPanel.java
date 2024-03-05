//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;
import javax.swing.*;

public class DeskPanel extends JPanel {
    private JPanel deskPanel = new JPanel();
    private ArrayList<Integer> deskNumbers = new ArrayList();

    public DeskPanel() {
        this.deskPanel.setLayout(new GridLayout(0, 4, 10, 10));
        this.deskNumbers.clear();
        this.fetchDeskNumbers();
        Iterator var1 = this.deskNumbers.iterator();

        while(var1.hasNext()) {
            int deskNumber = (Integer)var1.next();
            JButton deskButton = new JButton("Desk " + deskNumber);
            this.deskPanel.add(deskButton);
        }

        this.add(this.deskPanel);
    }

    private void fetchDeskNumbers() {
        // Database connection parameters
        String url = "jdbc:mysql://192.168.1.112:3306/NQueue_db";
        String user = "root";
        String password = "1234";

        // SQL query to retrieve desk numbers
        String sql = "SELECT SeatID FROM SeatManager";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            int counter = 0;
            while (resultSet.next()) {
                int seatID = resultSet.getInt("SeatID");
                deskNumbers.add(seatID);
                counter++;


                // Check if the value of SeatID is not null
                if (!resultSet.wasNull()) {
                    String updateSql = "UPDATE SeatManager SET ConfigSeatID = ? WHERE SeatID = ?";
                    try (PreparedStatement updateStatement = conn.prepareStatement(updateSql)) {
                        updateStatement.setInt(1, seatID);
                        updateStatement.setInt(2, seatID);
                        updateStatement.executeUpdate();
                    }
                }
                if (counter >= 21) {
                    break;
                }
            }

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
