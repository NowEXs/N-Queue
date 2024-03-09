import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class DeskPanel extends JPanel {
    private int selectedRoom;
    private JPanel deskPanel = new JPanel();
    private ArrayList<JButton> deskButtons = new ArrayList<>();
    private Demo_for_student_db demo;
    private JTable db_table;


    public DeskPanel(Demo_for_student_db demo) {
        this.demo = demo;
        this.setLayout(new BorderLayout());
        this.deskPanel.setLayout(new GridLayout(0, 4, 10, 15));
        this.deskButtons.clear();
        this.fetchDeskNumbers();
        this.add(this.deskPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(50, 200)); // Set preferred size here
    }

    public void setSelectedRoom(int selectedRoom) {
        this.selectedRoom = selectedRoom;
        this.deskPanel.removeAll(); // Clear existing desk buttons
        this.deskButtons.clear(); // Clear the list of desk buttons
        fetchDeskNumbers(); // Fetch desk numbers for the new selected room
    }

    private void fetchDeskNumbers() {
        // Database connection parameters
        String url = "jdbc:mysql://192.168.1.133:3306/NQueue_db"; //it's always change bruh
        String user = "root";
        String password = "1234";

        String addingSql = "SELECT SeatID FROM SeatManager WHERE RoomNumber = ? AND Availability IS NULL";
        String updateSql = "UPDATE SeatManager SET Availability = ? WHERE RoomNumber = ? AND SeatID = ?";
        String delSql = "UPDATE SeatManager SET Availability = NULL WHERE RoomNumber = ? AND SeatID = ?";
        String resetSql = "UPDATE SeatManager SET Availability = NULL";
        String s_addingSql = "SELECT SeatID FROM SeatManager WHERE SeatID = ? AND RoomNumber = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement addingstatement = conn.prepareStatement(addingSql);
             PreparedStatement updateStatement = conn.prepareStatement(updateSql)) {

            addingstatement.setInt(1, selectedRoom); // Select the seatID from the room that user selected
            ResultSet resultSet = addingstatement.executeQuery();

            int counter = 0;
            while (resultSet.next()) {
                int seatID = resultSet.getInt("SeatID");
                JButton deskButton = new JButton(selectedRoom + " - " + seatID);
                this.deskPanel.add(deskButton);
                final String buttonText = deskButton.getText();
                deskButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Desk button clicked: " + buttonText);
                        demo.deskSetter(buttonText);
                    }
                });
                deskButtons.add(deskButton); // Add the created desk button to the list
                updateStatement.setInt(1, 1); // Set Availability to 1
                updateStatement.setInt(2, selectedRoom); // RoomNumber
                updateStatement.setInt(3, seatID); // SeatID
                updateStatement.executeUpdate(); // Execute the update statement

                counter++;
                if (counter >= 20) {
                    break;
                }

            }
            JButton addingButton = new JButton("+"); // ปุ่มเพิ่ม
            JButton deletingButton = new JButton("-"); // ปุ่มลด
            JButton testerButton = new JButton("Reset the db");

            addingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try (Connection conn = DriverManager.getConnection(url, user, password);
                         PreparedStatement addingstatement = conn.prepareStatement(addingSql)) {
                        addingstatement.setInt(1, selectedRoom);
                        ResultSet resultSet = addingstatement.executeQuery();
                        String[] columnNames = {"Room", "Desknumber"};
                        List<Object[]> dataList = new ArrayList<>();
                        while (resultSet.next()) {
                            int seatID = resultSet.getInt("SeatID");
                            dataList.add(new Object[]{selectedRoom, seatID});
                        }

                        Object[][] data = new Object[dataList.size()][2]; // Assuming 2 columns (Room and Desknumber)
                        for (int i = 0; i < dataList.size(); i++) {
                            data[i] = dataList.get(i);
                        }

                        JTable table = new JTable(data, columnNames);
                        JScrollPane scrollPane = new JScrollPane(table);
                        JDialog dialog = new JDialog();
                        JButton addButton = new JButton("add the desk");
                        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                            @Override
                            public void valueChanged(ListSelectionEvent e) {
                                if (!e.getValueIsAdjusting())  { // getValueIsAdjusting() = เพื่อให้ mouse detect ที่แค่การ click row */
                                    int selectedRow = table.getSelectedRow();
                                    if (selectedRow != -1) { // condition กันบั๊กในกรณ๊ที่ ไม่เหลือข้อมูล
                                        Object room = table.getValueAt(selectedRow, 0);
                                        Object deskNumber = table.getValueAt(selectedRow, 1);
                                        String selectedText = "Room: " + room + ", Desk Number: " + deskNumber;
                                        System.out.println(selectedText);

                                        addButton.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                try (Connection conn = DriverManager.getConnection(url, user, password);
                                                     PreparedStatement addingStatement = conn.prepareStatement(s_addingSql);
                                                     PreparedStatement updateStatement = conn.prepareStatement(updateSql);
                                                     PreparedStatement r_tableStatement = conn.prepareStatement(addingSql)) {
                                                    addingStatement.setInt(1, (int) deskNumber);
                                                    r_tableStatement.setInt(1, selectedRoom);
                                                    addingStatement.setInt(2, selectedRoom);
                                                    ResultSet resultSet = addingStatement.executeQuery();
                                                    if (resultSet.next()) {
                                                        int deskNum = resultSet.getInt("SeatID");
                                                        JButton deskButton = new JButton(selectedRoom + " - " + deskNum);
                                                        DeskPanel.this.deskPanel.add(deskButton);
                                                        DeskPanel.this.deskPanel.revalidate();
                                                        DeskPanel.this.deskPanel.repaint();
                                                        updateStatement.setInt(1, 1);
                                                        updateStatement.setInt(2, selectedRoom);
                                                        updateStatement.setInt(3, deskNum);
                                                        updateStatement.executeUpdate();


                                                        ResultSet newDataResultSet = r_tableStatement.executeQuery();
                                                        List<Object[]> newDataList = new ArrayList<>();
                                                        while (newDataResultSet.next()) {
                                                            int seatID = newDataResultSet.getInt("SeatID");
                                                            newDataList.add(new Object[]{selectedRoom, seatID});
                                                        }
                                                        Object[][] newData = new Object[newDataList.size()][2];
                                                        for (int i = 0; i < newDataList.size(); i++) {
                                                            newData[i] = newDataList.get(i);
                                                        }
                                                        DefaultTableModel model = new DefaultTableModel(newData, columnNames);
                                                        table.setModel(model);
                                                    }
                                                } catch (SQLException ex) {
                                                    ex.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });

                        Container contentPane = dialog.getContentPane();
                        contentPane.setLayout(new BorderLayout());
                        contentPane.add(scrollPane, BorderLayout.CENTER);
                        contentPane.add(addButton, BorderLayout.SOUTH);
                        dialog.add(scrollPane);
                        dialog.pack();
                        dialog.setVisible(true);

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });


            deletingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try (Connection conn = DriverManager.getConnection(url, user, password);
                         PreparedStatement delStatement = conn.prepareStatement(delSql)){
                        if (!deskButtons.isEmpty()) {
                            for (int i = 0; i < deskButtons.size(); i++) {
                                JButton button = deskButtons.get(i);
                                if (button.getText().equals(demo.deskGetter())) {
                                    deskPanel.remove(button);
                                    deskButtons.remove(i);
                                    deskPanel.revalidate();
                                    deskPanel.repaint();
                                    String buttonText = demo.deskGetter();
                                    String[] parts = buttonText.split(" - ");
                                    String deskNumberText = parts[1];
                                    System.out.println("delete Desk number : " + demo.deskGetter());
                                    demo.deskSetter("Desk number : " + demo.deskGetter() + " has been deleted");
                                    delStatement.setInt(1, selectedRoom);
                                    delStatement.setInt(2, Integer.parseInt(deskNumberText));
                                    delStatement.executeUpdate();
                                    break;
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }
            });

            testerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try (Connection conn = DriverManager.getConnection(url, user, password);
                         PreparedStatement resetStatement = conn.prepareStatement(resetSql)) {
                        resetStatement.executeUpdate();
                        System.out.println("Data reset successful");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            this.deskPanel.add(addingButton);
            this.deskPanel.add(deletingButton);
            this.deskPanel.add(testerButton);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Demo_for_student_db demo = new Demo_for_student_db(); // Create an instance of Demo_for_student_db
            DeskPanel deskPanel = new DeskPanel(demo); // Pass the instance of Demo_for_student_db to DeskPanel
            demo.setVisible(true); // Show the JFrame of Demo_for_student_db
        });
    }
}
