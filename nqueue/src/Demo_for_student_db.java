import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.sql.*;
import java.util.logging.*;
public class Demo_for_student_db extends javax.swing.JFrame {

    Connection con;
    private int selectedRoom;
    private DeskPanel deskPanel;

    public Demo_for_student_db() {
        initComponents();
        createConnection();
        this.setLocationRelativeTo(null);

        deskPanel = new DeskPanel(this);
        deskPanel.setSelectedRoom(selectedRoom);
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(deskPanel, BorderLayout.CENTER);
        setSize(850, 600);
    }
    private void createConnection() { // Create connection for
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://192.168.1.133/NQueue_db", "root", "1234"); // ip ต้องคอยเปลี่ยนตลอด
            System.out.println("Created connection successfully");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Demo_for_student_db.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Demo_for_student_db.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setSelectedRoom(int selectedRoom) {
        this.selectedRoom = selectedRoom;
        if (deskPanel != null) {
            deskPanel.setSelectedRoom(selectedRoom); // Pass selected room to DeskPanel
        }
    }
    public void deskSetter(String desk_num) { // เปลี่ยน text เป็นโต๊ะที่เลือก
        jTextField3.setText(desk_num);
    }

    public String deskGetter() { // เปลี่ยน text เป็นโต๊ะที่เลือก
        return jTextField3.getText();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        Submit_b = new javax.swing.JButton(); // submit_button
        Cancel_b = new javax.swing.JButton(); // ปุ่ม cancel
        lab_combo = new javax.swing.JComboBox<>(); // combo_box ของกล่อง lab
        id_txt_f = new javax.swing.JTextField(); // studentid textfield
        name_txt_f = new javax.swing.JTextField(); // name_textfield
        jLabel1 = new javax.swing.JLabel(); // StudentID label
        jLabel2 = new javax.swing.JLabel(); // Name label
        jLabel3 = new javax.swing.JLabel(); // lab label
        jTextField3 = new javax.swing.JTextField(); // ตัวแทน logo และ เลขโต๊ะ
        jPanel1 = new javax.swing.JPanel(); // ใส่ไว้เฉยๆจะได้ดูเหมือนแบ่งช่อง
        jPanel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Submit_b.setText("Submit");
        Submit_b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Submit_bActionPerformed(evt);
            }
        });

        Cancel_b.setText("Cancel");

        lab_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lab09", "Lab10", "Lab11", "Lab12" }));
        lab_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lab_comboActionPerformed(evt);
            }
        });

        jLabel1.setText("StudentID :");

        jLabel2.setText("Name :");

        jLabel3.setText("Lab :");

        jTextField3.setText("LOGO WILL BE RIGHT HERE");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                        .addComponent(lab_combo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(id_txt_f)
                                        .addComponent(name_txt_f)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(Submit_b)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(Cancel_b)))
                                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jTextField3)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(id_txt_f, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(name_txt_f, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lab_combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                .addGap(51, 51, 51)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(Submit_b)
                                        .addComponent(Cancel_b))
                                .addGap(19, 19, 19))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
    }

    private void Submit_bActionPerformed(java.awt.event.ActionEvent evt) {
        int st_id = Integer.parseInt(id_txt_f.getText());
        String st_name = name_txt_f.getText();
        String lab_name = (String) lab_combo.getSelectedItem();

        String stmt = "INSERT INTO Student (StudentID, name, lab_name) VALUES (?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(stmt)) {
            statement.setInt(1, st_id);
            statement.setString(2, st_name);
            statement.setString(3, lab_name);
            statement.executeUpdate();
            System.out.println("Data inserted successfully");

        } catch (SQLException ex) {
            // Handle any SQL exceptions
            ex.printStackTrace();
        }

    }
    private void lab_comboActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {

    }


    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Demo_for_student_db.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Demo_for_student_db.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Demo_for_student_db.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Demo_for_student_db.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Demo_for_student_db().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify
    private javax.swing.JButton Cancel_b;
    private javax.swing.JButton Submit_b;
    private javax.swing.JTextField id_txt_f;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JComboBox<String> lab_combo;
    private javax.swing.JTextField name_txt_f;

}