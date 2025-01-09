
package gui;

import com.toedter.calendar.JDateChooser;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.MySQL2;

public class AddAssignmentJDialog extends javax.swing.JDialog {

    private String ID;
    
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    
    private static final Logger logger = Logger.getLogger(TutorSignIn.class.getName());

    public AddAssignmentJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadTutors();
        loadCourses();
        jButton2.setEnabled(false);
    }
    
    //Title of Assignment
    public JTextField getjTextField1() {
        return jTextField1;
    }

    //Description
    public JTextField getjTextField2() {
        return jTextField2;
    }

    //Tutor Name
    public JComboBox<String> getjComboBox1() {
        return jComboBox1;
    }
    
    //Course Name
    public JComboBox<String> getjComboBox2() {
        return jComboBox2;
    }
    
    //Due Date
    public JDateChooser getjDateChooser1() {
        return jDateChooser1;
    }
    
    //Save
    public JButton getjButton1() {
        return jButton1;
    }
    
    //Update
    public JButton getjButton2() {
        return jButton2;
    }
    
    private static HashMap<String, String> courseMap = new HashMap<>();
    private static HashMap<String, String> tutorMap = new HashMap<>();
    
    private void loadTutors() {

        try {

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM tutor");

            while (resultSet.next()) {
                String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                vector.add(fullName);
                tutorMap.put(fullName, resultSet.getString("id"));
                // contactMap.put(fullName, resultSet.getString("contact_info"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCourses() {
        logger.log(Level.INFO, "Started loading courses at {0}", new java.util.Date());

        try {
            Vector<String> vector = new Vector<>();
            vector.add("Select");

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM courses");
            logger.log(Level.INFO, "Executed query: SELECT * FROM courses at {0}", new java.util.Date());

            while (resultSet.next()) {
                String courseName = resultSet.getString("name");
                String courseId = resultSet.getString("id");

                vector.add(courseName);
                courseMap.put(courseName, courseId);

                logger.log(Level.INFO, "Added course: {0}, ID: {1} at {2}",
                        new Object[]{courseName, courseId, new java.util.Date()});
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox2.setModel(model);
            logger.log(Level.INFO, "Dropdown updated with {0} courses at {1}",
                    new Object[]{vector.size() - 1, new java.util.Date()}); // Subtract 1 for "Select"

        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred while loading courses at {0}", new Object[]{new java.util.Date(), e});
        }
    }

    private void clearAll() {
        jTextField1.setText("");
        jTextField2.setText("");
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jDateChooser1.setDate(null);
        jTextField1.setEditable(true);
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel6.setText("Title");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Due Date");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setText("Description");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Tutor Name");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setText("Course Name");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel1.setText("Add Assignment");

        jButton3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton3.setText("Clear All");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTextField2)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(26, 26, 26)))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel6)))
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 141, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        // Retrieve values from the UI components
        String title = jTextField1.getText();
        String description = jTextField2.getText();
        String tutorName = String.valueOf(jComboBox1.getSelectedItem());
        String courseName = String.valueOf(jComboBox2.getSelectedItem());
        Date duedate = jDateChooser1.getDate();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a title!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (duedate == null) {
            JOptionPane.showMessageDialog(this, "Please enter a date!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a description!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (tutorName.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a tutor!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (courseName.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a course!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                logger.log(Level.INFO, "Starting the assignment process.");

                String tutorID = tutorMap.get(tutorName);
                String courseID = courseMap.get(courseName);

                logger.log(Level.INFO, "Tutor ID retrieved: {0}", tutorID);
                logger.log(Level.INFO, "Course ID retrieved: {0}", courseID);

                MySQL2.executeIUD("INSERT INTO assignment (title, description, tutor_id, courses_id, due_date) "
                    + "VALUES ('" + title + "', '" + description + "', '" + tutorID + "', '" + courseID + "', '" + format.format(duedate) + "')");

                logger.log(Level.INFO, "Assignment inserted into the database successfully.");

                JOptionPane.showMessageDialog(this, "Success!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);

                clearAll();

                logger.log(Level.INFO, "Assignment process completed successfully.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "An error occurred while processing the assignment: {0}", e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        // Retrieve values from the UI components
        String title = jTextField1.getText();
        String description = jTextField2.getText();
        String tutorName = String.valueOf(jComboBox1.getSelectedItem());
        String courseName = String.valueOf(jComboBox2.getSelectedItem());
        Date duedate = jDateChooser1.getDate();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a title!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (duedate == null) {
            JOptionPane.showMessageDialog(this, "Please enter a date!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a description!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (tutorName.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a tutor!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (courseName.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a course!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                logger.log(Level.INFO, "Starting the assignment process.");

                String tutorID = tutorMap.get(tutorName);
                String courseID = courseMap.get(courseName);

                logger.log(Level.INFO, "Tutor ID retrieved: {0}", tutorID);
                logger.log(Level.INFO, "Course ID retrieved: {0}", courseID);

                MySQL2.executeIUD("UPDATE assignment SET title = '" + title + "', description = '"+description+"' , tutor_id = '"+tutorMap.get(tutorName)+"'"
                        + ", courses_id = '"+courseMap.get(courseName)+"', due_date = '" + format.format(duedate) + "' "
                                + "WHERE assignment.id = '" + ID + "' ");

                logger.log(Level.INFO, "Assignment inserted into the database successfully.");

                JOptionPane.showMessageDialog(this, "Success!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                clearAll();

                logger.log(Level.INFO, "Assignment process completed successfully.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "An error occurred while processing the assignment: {0}", e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
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
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
