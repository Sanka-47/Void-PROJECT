package gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.toedter.calendar.JDateChooser;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import model.MySQL2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AddAssignmentJDialog extends javax.swing.JDialog {
    private static final Logger logger = LogManager.getLogger(AddAssignmentJDialog.class);
    private int ID;
    private Integer assignmentID = null;

//    private static final Logger logger = Logger.getLogger(TutorSignIn.class.getName());
    public AddAssignmentJDialog(java.awt.Frame parent, boolean modal, int tutorID) {
        super(parent, modal);
        logger.debug("Entered method: AddAssignmentJDialog");
        logger.info("Entered method: AddAssignmentJDialog");
        initComponents();
        this.ID = tutorID;
        loadCourses();
//        loadTutors();
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        SwingUtilities.invokeLater(() -> jTextField1.requestFocusInWindow());
        jButton2.setEnabled(false);
    }

    //GUI Label
    public JLabel getjLabel1() {
        logger.debug("Entered method: getjLabel1");
        logger.info("Entered method: getjLabel1");
        return jLabel1;
    }

    //Title of Assignment
    public JTextField getjTextField1() {
        logger.debug("Entered method: getjTextField1");
        logger.info("Entered method: getjTextField1");
        return jTextField1;
    }

    //Description
    public JTextArea getjTextArea1() {
        logger.debug("Entered method: getjTextArea1");
        logger.info("Entered method: getjTextArea1");
        return jTextArea1;
    }

    //Tutor Name
    public JComboBox<String> getjComboBox1() {
        logger.debug("Entered method: getjComboBox1");
        logger.info("Entered method: getjComboBox1");
        return jComboBox1;
    }

    public void setAssignmentID(int assignmentID) {
        logger.debug("Entered method: setAssignmentID");
        logger.info("Entered method: setAssignmentID");
        this.assignmentID = assignmentID;
    }
//    //Course Name
//    public JComboBox<String> getjComboBox2() {
//        return jComboBox2;
//    }
    //Due Date

    public JDateChooser getjDateChooser1() {
        logger.debug("Entered method: getjDateChooser1");
        logger.info("Entered method: getjDateChooser1");
        return jDateChooser1;
    }

    //Save
    public JButton getjButton1() {
        logger.debug("Entered method: getjButton1");
        logger.info("Entered method: getjButton1");
        return jButton1;
    }

    //Update
    public JButton getjButton2() {
        logger.debug("Entered method: getjButton2");
        logger.info("Entered method: getjButton2");
        return jButton2;
    }

    private static HashMap<String, String> courseMap = new HashMap<>();
    private static HashMap<String, String> tutorMap = new HashMap<>();

    private void loadCourses() {
        logger.debug("Entered method: loadCourses");
        logger.info("Entered method: loadCourses");

        try {

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            ResultSet resultSet = MySQL2.executeSearch("SELECT `tutor`.`id`, `first_name`, `last_name`, `courses`.`name` FROM `tutor` "
                    + "INNER JOIN `courses` ON `tutor`.`courses_id` = `courses`.`id` WHERE `tutor`.`id` = '" + ID + "' ");

            while (resultSet.next()) {
                String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                vector.add(resultSet.getString("courses.name"));
                courseMap.put(resultSet.getString("courses.name"), resultSet.getString("tutor.id"));
                tutorMap.put(resultSet.getString("courses.name"), resultSet.getString("tutor.id"));
//                loadTutors();
                // contactMap.put(fullName, resultSet.getString("contact_info"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void loadTutors() {
////        logger.log(Level.INFO, "Started loading courses at {0}", new java.util.Date());
//
//        try {
//            Vector<String> vector = new Vector<>();
////            vector.add("Select");
//
////            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM courses");
////            logger.log(Level.INFO, "Executed query: SELECT * FROM courses at {0}", new java.util.Date());
//
//            if (courseMap.containsValue(ID)) {
//                String courseName = courseMap.get(ID);
////                String courseId = resultSet.getString("id");
//
//                vector.add(courseName);
////                courseMap.put(courseName, courseId);
//
////                logger.log(Level.INFO, "Added course: {0}, ID: {1} at {2}",
////                        new Object[]{courseName, courseId, new java.util.Date()});
//            } else {
//                vector.add("Select");
//            }
//
//            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
//            jComboBox2.setModel(model);
////            logger.log(Level.INFO, "Dropdown updated with {0} courses at {1}",
////                    new Object[]{vector.size() - 1, new java.util.Date()}); // Subtract 1 for "Select"
//
//        } catch (Exception e) {
////            logger.log(Level.SEVERE, "An error occurred while loading courses at {0}", new Object[]{new java.util.Date(), e});
//            e.printStackTrace();
//        }
//    }
    private void clearAll() {
        logger.debug("Entered method: clearAll");
        logger.info("Entered method: clearAll");
        jTextField1.setText("");
        jTextArea1.setText("");
        jComboBox1.setSelectedIndex(0);
//        jComboBox2.setSelectedIndex(0);
        jDateChooser1.setDate(null);
        jTextField1.setEditable(true);
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jComboBox1.setEnabled(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new CustomColor();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel1.setText("Add Assignment");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Title");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setText("Course Name");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Due Date");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setText("Description");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 3, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(209, 209, 209)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3)))
                        .addGap(70, 70, 70))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(324, 324, 324)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)))
                .addGap(33, 33, 33)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
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
        logger.debug("Entered method: jButton1ActionPerformed");
        logger.info("Entered method: jButton1ActionPerformed");

        // Retrieve values from the UI components
        String title = jTextField1.getText();
        String description = jTextArea1.getText();
        String courseName = String.valueOf(jComboBox1.getSelectedItem());
//        String courseName = String.valueOf(jComboBox2.getSelectedItem());
        Date duedate = jDateChooser1.getDate();

        if (title.isEmpty()) {
        logger.warn("Validation check triggered: (title.isEmpty())");
        logger.warn("User prompted with message: (this, Please select a title!, Warning, JOptionPane.WARNING_MESSAGE);");
            JOptionPane.showMessageDialog(this, "Please select a title!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (duedate == null) {
        logger.warn("User prompted with message: (this, Please enter a date!, Warning, JOptionPane.WARNING_MESSAGE);");
            JOptionPane.showMessageDialog(this, "Please enter a date!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (description.isEmpty()) {
        logger.warn("User prompted with message: (this, Please enter a description!, Warning, JOptionPane.WARNING_MESSAGE);");
            JOptionPane.showMessageDialog(this, "Please enter a description!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (courseName.equals("Select")) {
        logger.warn("User prompted with message: (this, Please select a course!, Warning, JOptionPane.WARNING_MESSAGE);");
            JOptionPane.showMessageDialog(this, "Please select a course!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

//                logger.log(Level.INFO, "Starting the assignment process.");
                String tutorID = tutorMap.get(courseName);
                String courseID = courseMap.get(courseName);

//                logger.log(Level.INFO, "Tutor ID retrieved: {0}", tutorID);
//                logger.log(Level.INFO, "Course ID retrieved: {0}", courseID);
        logger.info("Executing insert query in assignment");
                MySQL2.executeIUD("INSERT INTO assignment (title, description, tutor_id, courses_id, due_date) "
                        + "VALUES ('" + title + "', '" + description + "', '" + tutorID + "', '" + courseID + "', '" + format.format(duedate) + "')");

//                logger.log(Level.INFO, "Assignment inserted into the database successfully.");
        logger.warn("User prompted with message: (this, Success!, SUCCESS, JOptionPane.INFORMATION_MESSAGE);");
                JOptionPane.showMessageDialog(this, "Success!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);

                clearAll();

//                logger.log(Level.INFO, "Assignment process completed successfully.");
            } catch (Exception e) {
//                logger.log(Level.SEVERE, "An error occurred while processing the assignment: {0}", e.getMessage());
                e.printStackTrace();
        logger.warn("User prompted with message: (this, An error occurred:  + e.getMessage(), Error, JOptionPane.ERROR_MESSAGE);");
                JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        logger.debug("Entered method: jButton2ActionPerformed");
        logger.info("Entered method: jButton2ActionPerformed");

        // Retrieve values from the UI components
        String title = jTextField1.getText();
        String description = jTextArea1.getText();
        String courseName = String.valueOf(jComboBox1.getSelectedItem());
//        String courseName = String.valueOf(jComboBox2.getSelectedItem());
        Date duedate = jDateChooser1.getDate();

        if (title.isEmpty()) {
        logger.warn("Validation check triggered: (title.isEmpty())");
        logger.warn("User prompted with message: (this, Please select a title!, Warning, JOptionPane.WARNING_MESSAGE);");
            JOptionPane.showMessageDialog(this, "Please select a title!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (duedate == null) {
        logger.warn("User prompted with message: (this, Please enter a date!, Warning, JOptionPane.WARNING_MESSAGE);");
            JOptionPane.showMessageDialog(this, "Please enter a date!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (description.isEmpty()) {
        logger.warn("User prompted with message: (this, Please enter a description!, Warning, JOptionPane.WARNING_MESSAGE);");
            JOptionPane.showMessageDialog(this, "Please enter a description!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (courseName.equals("Select")) {
        logger.warn("User prompted with message: (this, Please select a course!, Warning, JOptionPane.WARNING_MESSAGE);");
            JOptionPane.showMessageDialog(this, "Please select a course!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            if (assignmentID == null || assignmentID <= 0) {
        logger.warn("Validation check triggered: (assignmentID == null || assignmentID <= 0)");
        logger.warn("User prompted with message: (this, Assignment ID is missing. Cannot update!, Error, JOptionPane.ERROR_MESSAGE);");
                JOptionPane.showMessageDialog(this, "Assignment ID is missing. Cannot update!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String tutorID = tutorMap.get(courseName);
                String courseID = courseMap.get(courseName);

                String query = "UPDATE assignment SET title = '" + title + "', description = '" + description + "', "
                        + "tutor_id = '" + tutorID + "', courses_id = '" + courseID + "', due_date = '" + format.format(duedate) + "' "
                        + "WHERE id = '" + assignmentID + "'";

                MySQL2.executeIUD(query);
        logger.warn("User prompted with message: (this, Assignment updated successfully!, SUCCESS, JOptionPane.INFORMATION_MESSAGE);");
                JOptionPane.showMessageDialog(this, "Assignment updated successfully!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);

                clearAll();
                this.dispose();

            } catch (Exception e) {
                e.printStackTrace();
        logger.warn("User prompted with message: (this, An error occurred:  + e.getMessage(), Error, JOptionPane.ERROR_MESSAGE);");
                JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        logger.debug("Entered method: jButton3ActionPerformed");
        logger.info("Entered method: jButton3ActionPerformed");
        clearAll();
    }//GEN-LAST:event_jButton3ActionPerformed

//    public static void main(String args[]) {
//
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                AddAssignmentJDialog dialog = new AddAssignmentJDialog(new javax.swing.JFrame(), true);
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
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
