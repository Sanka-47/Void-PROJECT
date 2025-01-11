//author CHANULI
package gui;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;

public class RequestSession extends javax.swing.JPanel {

    private DashboardInterface parent;

    private int tutorId;

    private String fName;
    private String lName;

    private static HashMap<String, String> subjectMap = new HashMap<>();
    private static HashMap<String, String> courseMap = new HashMap<>();
    private static HashMap<String, String> tutorMap = new HashMap<>();
    private static HashMap<String, String> statusMap = new HashMap<>();

    public RequestSession(DashboardInterface parent, int TutorID) {
        this.parent = parent;
        this.tutorId = TutorID;
        initComponents();
        loadTutorSchedule();
        loadCourses();
//        loadTutors();
        loadStatus();
    }
    

    private void loadTutorSchedule() {

//        try {
//            // Corrected SQL query with DISTINCT and removal of unnecessary joins
//            ResultSet resultSet = MySQL2.executeSearch(
//                    "SELECT DISTINCT `class`.`id`, `courses`.`name` AS course_name, "
//                    + "`tutor`.`first_name`, `tutor`.`last_name`, `class`.`name` AS class_name, "
//                    + "`class`.`date`, `class`.`start_time`, `class`.`end_time`, `class`.`hallnumber`, "
//                    + "`class`.`amount`, `class_status`.`name` AS status_name "
//                    + "FROM `class` "
//                    + "INNER JOIN `tutor` ON `class`.`tutor_id` = `tutor`.`id` "
//                    + "INNER JOIN `courses` ON `class`.`courses_id` = `courses`.`id` "
//                    + "INNER JOIN `class_status` ON `class`.`class_status_id` = `class_status`.`id`"
//            );
//
//            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//            model.setRowCount(0);
//
//            while (resultSet.next()) {
//                Vector<String> vector = new Vector<>();
//                vector.add(resultSet.getString("id"));
//                vector.add(resultSet.getString("course_name"));
//                vector.add(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
//                vector.add(resultSet.getString("class_name"));
//                vector.add(resultSet.getString("date"));
//                vector.add(resultSet.getString("start_time"));
//                vector.add(resultSet.getString("end_time"));
//                vector.add(resultSet.getString("hallnumber"));
//                vector.add(resultSet.getString("amount"));
//                vector.add(resultSet.getString("status_name"));
//
//                model.addRow(vector);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            // SQL query filtering sessions for the signed-in tutor
//            ResultSet resultSet = MySQL2.executeSearch(
//                    "SELECT DISTINCT `class`.`id`, `courses`.`name` AS course_name, "
//                    + "`class`.`name` AS class_name, `class`.`date`, `class`.`start_time`, "
//                    + "`class`.`end_time`, `class`.`hallnumber`, `class`.`amount`, "
//                    + "`class_status`.`name` AS status_name "
//                    + "FROM `class` "
//                    + "INNER JOIN `courses` ON `class`.`courses_id` = `courses`.`id` "
//                    + "INNER JOIN `class_status` ON `class`.`class_status_id` = `class_status`.`id` "
//                    + "WHERE `class`.`tutor_id` = '" + tutorId + "'"
//            );
            ResultSet resultSet = MySQL2.executeSearch(
                    "SELECT DISTINCT `class`.`id`, `courses`.`name` AS course_name, "
                    + "`tutor`.`first_name` AS first_name, `tutor`.`last_name` AS last_name, "
                    + "`class`.`name` AS class_name, `class`.`date`, `class`.`start_time`, "
                    + "`class`.`end_time`, `class`.`hallnumber`, `class`.`amount`, "
                    + "`class_status`.`name` AS status_name "
                    + "FROM `class` "
                    + "INNER JOIN `tutor` ON `class`.`tutor_id` = `tutor`.`id` "
                    + "INNER JOIN `courses` ON `class`.`courses_id` = `courses`.`id` "
                    + "INNER JOIN `class_status` ON `class`.`class_status_id` = `class_status`.`id`"
            );

            // Retrieve the table model and clear existing rows
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            // Populate the table with the filtered data
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("course_name"));
                this.fName = resultSet.getString("first_name");
                this.lName = resultSet.getString("last_name");
                vector.add(resultSet.getString("class_name"));
                vector.add(resultSet.getString("date"));
                vector.add(resultSet.getString("start_time"));
                vector.add(resultSet.getString("end_time"));
                vector.add(resultSet.getString("hallnumber"));
                vector.add(resultSet.getString("amount"));
                vector.add(resultSet.getString("status_name"));

                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadCourses() {

        try {
            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `courses` INNER JOIN `tutor` ON "
                    + "courses.id = tutor.courses_id");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                courseMap.put(resultSet.getString("name"), resultSet.getString("id"));
               courseField.setText(resultSet.getString("name"));
//                DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
//                jComboBox2.setModel(model);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadStatus() {

        try {

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `class_status`");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                statusMap.put(resultSet.getString("name"), resultSet.getString("id"));
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }

//    private void loadTutors() {
//
//        try {
//            ResultSet resultSet = MySQL2.executeSearch("SELECT first_name, last_name, id FROM `tutor`");
//
//            Vector<String> vector = new Vector<>();
//            vector.add("Select");
//
//            while (resultSet.next()) {
////                vector.add(resultSet.getString("name"));
////                tutorMap.put(resultSet.getString("name"), resultSet.getString("id"));
//
//                String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
//                vector.add(fullName);
//                tutorMap.put(fullName, resultSet.getString("id"));
//
//                DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
//                jComboBox1.setModel(model);
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    private void reset() {
        title.setText("");
        title.setText("");
        hallNumber.setText("");
        jDateChooser1.setDate(null);
        startTime.setText("");
        endTime.setText("");
        courseField.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel15 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        title = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        startTime = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        endTime = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        hallNumber = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton11 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        courseField = new javax.swing.JTextField();

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel15.setText("All available sessions");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel7.setText("Title");

        title.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel9.setText("Date");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel10.setText("Start Time");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel11.setText("End Time");

        endTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endTimeActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel12.setText("Hall Number");

        jButton10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton10.setText("Clear");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Course", "Title", "Date", "Start Time", "End Time", "Hall Number", "Amount", "Class Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton11.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton11.setText("Request Session");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel16.setText("Request New Session");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel8.setText("Course");

        courseField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(hallNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(startTime, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel11))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(endTime, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(title)
                            .addComponent(jLabel7))
                        .addGap(0, 0, 0))
                    .addComponent(jLabel8)
                    .addComponent(courseField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(392, 392, 392)
                .addComponent(jLabel15)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jLabel16)
                .addGap(21, 21, 21)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(courseField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(endTime, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startTime, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hallNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void titleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titleActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        reset();
        //        String sessionID = jTextField3.getText();
        //
        //        if (sessionID.isEmpty()) {
        //            JOptionPane.showMessageDialog(this, "Please enter a session ID to cancel!", "Warning", JOptionPane.WARNING_MESSAGE);
        //            return;
        //        }
        //
        //        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this session?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
        //        if (confirm == JOptionPane.NO_OPTION) {
        //            return;
        //        }
        //
        //        try {
        //            ResultSet resultSet = MySQL2.executeSearch("SELECT `id` FROM `class` WHERE `id` = '" + sessionID + "'");
        //
        //            if (resultSet.next()) {
        //
        //                MySQL2.executeIUD("DELETE FROM `class` WHERE `id` = '" + sessionID + "'");
        //
        //                JOptionPane.showMessageDialog(this, "Session canceled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        //
        //                reset();
        //                loadTutorSchedule();
        //
        //            } else {
        //                JOptionPane.showMessageDialog(this, "The session ID does not exist. Please check and try again.", "Error", JOptionPane.ERROR_MESSAGE);
        //            }
        //
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //            JOptionPane.showMessageDialog(this, "An error occurred while canceling the session. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        //        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

//        int row = jTable1.getSelectedRow();
//
//        String id = String.valueOf(jTable1.getValueAt(row, 0));
//        jTextField3.setText(id);
//
//        String courseName = String.valueOf(jTable1.getValueAt(row, 1));
//        jTextField1.setText(courseName);
//
//        String tutor = String.valueOf(jTable1.getValueAt(row, 2));
//        jTextField2.setText(tutor);
//
//        String date = String.valueOf(jTable1.getValueAt(row, 3));
//        try {
//            java.util.Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//            jDateChooser1.setDate(parsedDate);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        String startTime = String.valueOf(jTable1.getValueAt(row, 4));
//        jFormattedTextField1.setText(startTime);
//
//        String endTime = String.valueOf(jTable1.getValueAt(row, 5));
//        jFormattedTextField2.setText(endTime);
//
//        String hallNumber = String.valueOf(jTable1.getValueAt(row, 6));
//        jTextField7.setText(hallNumber);
//
//        String amount = String.valueOf(jTable1.getValueAt(row, 7));
//        jFormattedTextField3.setText(amount);
//
//        String status = String.valueOf(jTable1.getValueAt(row, 8));
//        jComboBox3.setSelectedItem(status);
    }//GEN-LAST:event_jTable1MouseClicked

    private void endTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_endTimeActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
         // Retrieve values from the UI components
    String Title = title.getText();
    String Course = courseField.getText();
    Date SessionDate = jDateChooser1.getDate();
    String StartTime = startTime.getText();
    String EndTime = endTime.getText();
    String HallNumber = hallNumber.getText();
    int TutorID = this.tutorId; // Assuming you fetch this dynamically or use a default value.

    // Validation
    if (Title.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a title!", "Warning", JOptionPane.WARNING_MESSAGE);
    } else if (SessionDate == null) {
        JOptionPane.showMessageDialog(this, "Please select a date!", "Warning", JOptionPane.WARNING_MESSAGE);
    } else if (StartTime.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a start time!", "Warning", JOptionPane.WARNING_MESSAGE);
    } else if (EndTime.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter an end time!", "Warning", JOptionPane.WARNING_MESSAGE);
    } else if (HallNumber.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a hall number!", "Warning", JOptionPane.WARNING_MESSAGE);
    } else if (Course.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter the course!", "Warning", JOptionPane.WARNING_MESSAGE);
    } else {

        try {
            // Format the date
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            // Insert into the database
            String query = "INSERT INTO request_sessions (title, date, start_time, end_time, hallnumber, tutor_id,approve_status,courses_id,type) "
                         + "VALUES ('" + Title + "', '" + format.format(SessionDate) + "', '" + StartTime + "', '" + EndTime + "', '" + HallNumber + "', '" + TutorID + "','Pending','"+courseMap.get(Course)+"','New Session')";

            MySQL2.executeIUD(query);

            JOptionPane.showMessageDialog(this, "Session requested successfully!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);

            // Clear all fields after successful insertion
            title.setText("");
            jDateChooser1.setDate(null);
            startTime.setText("");
            endTime.setText("");
            hallNumber.setText("");
            courseField.setText("");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void courseFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_courseFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField courseField;
    private javax.swing.JFormattedTextField endTime;
    private javax.swing.JTextField hallNumber;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JFormattedTextField startTime;
    private javax.swing.JTextField title;
    // End of variables declaration//GEN-END:variables
}
