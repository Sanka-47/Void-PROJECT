package gui;

import com.toedter.calendar.JDateChooser;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import model.MySQL2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddStudentAttendanceJDialog extends javax.swing.JDialog {
    private static final Logger logger = LogManager.getLogger(AddStudentAttendanceJDialog.class);

    private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    private int tutorID;

    private HashMap<String, String> statusMap;
    private HashMap<String, String> classMap;
    private HashMap<String, String> studentMap;
    private HashMap<String, String> classDateMap;

    public AddStudentAttendanceJDialog(java.awt.Frame parent, boolean modal, int tutorID) {
        super(parent, modal);
        initComponents();
        initMap();
        this.tutorID = tutorID;
        loadStatus();
        loadStudent();
        loadClass();
        jButton2.setEnabled(false);
        SwingUtilities.invokeLater(() -> jComboBox1.requestFocusInWindow());
        jTextField1.setEnabled(false);
    }

//    //Attendance ID
//    public JLabel getjLabel7() {
//        return jLabel7;
//    }
    // Status
    public JComboBox<String> getjComboBox1() {
        return jComboBox1;
    }

    //Student Name
    public JComboBox<String> getjComboBox2() {
        return jComboBox2;
    }

    //Class Name
    public JComboBox<String> getjComboBox3() {
        return jComboBox3;
    }

    //Due Date
    public JTextField getjTextField1() {
        return jTextField1;
    }

    //Save
    public JButton getjButton1() {
        return jButton1;
    }

    //Update
    public JButton getjButton2() {
        return jButton2;
    }

    private void initMap() {
        this.statusMap = new HashMap<>();
        this.classMap = new HashMap<>();
        this.studentMap = new HashMap<>();
        this.classDateMap = new HashMap<>();
    }

    private void loadStatus() {

        try {

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `attendance_status`");

            while (resultSet.next()) {

                vector.add(resultSet.getString("status"));
                statusMap.put(resultSet.getString("status"), resultSet.getString("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);

        } catch (Exception e) {
            logger.error("Exception caught", e);
        }
    }

    private void loadStudent() {

        try {

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            ResultSet resultSet = MySQL2.executeSearch("SELECT student.first_name, student.last_name, student.nic FROM student "
                    + "INNER JOIN student_courses ON student.nic = student_courses.student_nic "
                    + "INNER JOIN courses ON student_courses.courses_id = courses.id "
                    + "INNER JOIN tutor ON courses.id = tutor.courses_id WHERE tutor.id = '" + tutorID + "'");

            while (resultSet.next()) {
                String fullName = resultSet.getString("student.first_name") + " " + resultSet.getString("student.last_name");
                vector.add(fullName);
                studentMap.put(fullName, resultSet.getString("nic"));
                // contactMap.put(fullName, resultSet.getString("contact_info"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox2.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadClass() {
        try {
            Vector<String> vector = new Vector<>();
            vector.add("Select");

            ResultSet resultSet = MySQL2.executeSearch("SELECT id, name, date FROM class WHERE tutor_id = '" + tutorID + "'");

            while (resultSet.next()) {
                String className = resultSet.getString("name");
                String classId = resultSet.getString("id");
                String classDate = resultSet.getString("date");

                vector.add(className);
                classMap.put(className, classId);
                classDateMap.put(className, classDate);
            }

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(vector);
            jComboBox3.setModel(model);

        } catch (Exception e) {
            logger.error("Exception caught while loading classes", e);
        }
    }

    private void clearAll() {
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);

        jComboBox1.setEnabled(true);
        jComboBox2.setEnabled(true);
        jComboBox3.setEnabled(true);
        jTextField1.setEnabled(false);
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new CustomColor();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel1.setText("Add Student Attendance");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel6.setText("Status");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Date");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Student Name");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setText("Class Name");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton3.setText("Clear All");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(277, 277, 277)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(129, 129, 129)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(42, 42, 42))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        // Retrieve values from the UI components
        String Status = String.valueOf(jComboBox1.getSelectedItem());
        String StudentName = String.valueOf(jComboBox2.getSelectedItem());
        String ClassName = String.valueOf(jComboBox3.getSelectedItem());
        String duedate = jTextField1.getText();

        if (Status.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a Status!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (duedate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a date!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (StudentName.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a student!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (ClassName.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a class!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                ResultSet rs = MySQL2.executeSearch(
                        "SELECT * FROM attendance WHERE student_nic = '" + studentMap.get(StudentName) + "' "
                        + "AND class_id = '" + classMap.get(ClassName) + "' "
                        + "AND date = '" + format.format(duedate) + "'"
                );

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Attendance already marked for this student on this date.", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
                    return; // Stop execution
                }

                System.out.println("Student NIC :" + studentMap.get(StudentName));
                System.out.println("Class ID :" + classMap.get(ClassName));

                MySQL2.executeIUD("INSERT INTO `attendance` ( attendance_status_id, student_nic, class_id, date) "
                        + "VALUES ('" + statusMap.get(Status) + "', '" + studentMap.get(StudentName) + "', '" + classMap.get(ClassName) + "', '" + format.format(duedate) + "')");

                JOptionPane.showMessageDialog(this, "Success!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);

                clearAll();

            } catch (Exception e) {
                logger.error("Exception caught", e);
                javax.swing.JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        // Retrieve values from the UI components
        String Status = String.valueOf(jComboBox1.getSelectedItem());
        String StudentName = String.valueOf(jComboBox2.getSelectedItem());
        String ClassName = String.valueOf(jComboBox3.getSelectedItem());
        String duedate = jTextField1.getText();

        if (Status.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a Status!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (duedate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a date!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (StudentName.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a student!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (ClassName.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a class!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                System.out.println("Status Map :" + statusMap.get(Status));
                System.out.println("Student NIC :" + studentMap.get(StudentName));
                System.out.println("Class ID :" + classMap.get(ClassName));

                MySQL2.executeIUD("UPDATE `attendance` SET `attendance_status_id` = '" + statusMap.get(Status) + "', `student_nic` = '" + studentMap.get(StudentName) + "'"
                        + ", `class_id` = '" + classMap.get(ClassName) + "', `date` = '" + format.format(duedate) + "'"
                        + "WHERE `attendance`.`id` = '" + ID + "'");

                JOptionPane.showMessageDialog(this, "Success!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                clearAll();
                this.dispose();

            } catch (Exception e) {
                logger.error("Exception caught", e);
                javax.swing.JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clearAll();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        String selectedClass = String.valueOf(jComboBox3.getSelectedItem());
        if (selectedClass != null && !selectedClass.equals("Select")) {
            String classDate = classDateMap.get(selectedClass);
            jTextField1.setText(classDate);
        } else {
            jTextField1.setText("");
        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(AddStudentAttendanceJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(AddStudentAttendanceJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(AddStudentAttendanceJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(AddStudentAttendanceJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                AddStudentAttendanceJDialog dialog = new AddStudentAttendanceJDialog(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}