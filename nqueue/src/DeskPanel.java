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
        JButton addingButton = new JButton("+"); // ปุ่มเพิ่ม
        JButton deletingButton = new JButton("-"); // ปุ่มลด
        this.deskPanel.add(addingButton);
        this.deskPanel.add(deletingButton);

        this.add(this.deskPanel);
    }

    private void fetchDeskNumbers() {
        // Database connection parameters
        String url = "jdbc:mysql://192.168.1.112:3306/NQueue_db";
        String user = "root";
        String password = "1234";

        // SQL queries to retrieve desk numbers
        String config_check = "SELECT ConfigSeatID FROM SeatManager";
        String seat_check = "SELECT SeatID FROM SeatManager";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement statement = conn.createStatement()) {

            // Execute the query to check the ConfigSeatID
            try (ResultSet resultSet_config = statement.executeQuery(config_check)) {
                boolean isFirstValueZero = true;

                if (resultSet_config.next()) {
                    int firstConfigSeatID = resultSet_config.getInt("ConfigSeatID");
                    if (firstConfigSeatID != 0) {
                        isFirstValueZero = false;
                    }
                }

                // If the first value of ConfigSeatID is zero, fetch desk numbers from SeatID
                if (isFirstValueZero) {
                    try (ResultSet resultSet_seat = statement.executeQuery(seat_check)) {
                        int counter = 0;
                        while (resultSet_seat.next() && counter < 19) {
                            int seatID = resultSet_seat.getInt("SeatID");
                            deskNumbers.add(seatID);

                            if (!resultSet_seat.wasNull()) {
                                // Perform update if necessary
                                String updateSql = "UPDATE SeatManager SET ConfigSeatID = ? WHERE SeatID = ?";
                                try (PreparedStatement updateStatement = conn.prepareStatement(updateSql)) {
                                    updateStatement.setInt(1, seatID);
                                    updateStatement.setInt(2, seatID);
                                    updateStatement.executeUpdate();
                                }
                                counter++;
                            }
                        }
                    }
                }
                // If the first value of ConfigSeatID is not zero, use it directly
                else {
                    deskNumbers.add(resultSet_config.getInt("ConfigSeatID"));
                    int zero_check = 0;
                    while (resultSet_config.next() && zero_check <= 1) {
                        int configSeatID = resultSet_config.getInt("ConfigSeatID");
                        if (configSeatID == 0) {
                            zero_check += 1;
                            continue;
                        } else {
                            zero_check = 0;
                            deskNumbers.add(configSeatID);
                        }
                    }
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

//DELETE FROM SeatManager WHERE ConfigSeatID = 3
//INSERT INTO NQueue_db.SeatManager (SeatID) VALUES (3)
