//author RUSHMA
package gui;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
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
        SwingUtilities.invokeLater(() -> courseField.requestFocusInWindow());
    }

    private void loadTutorSchedule() {

        try {

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(startTime, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel11))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(endTime))))
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(courseField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(title, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hallNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void titleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titleActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        reset();

    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

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
        int TutorID = this.tutorId; // Assuming this is fetched dynamically or passed in.

        // Regex for 24-hour time format (e.g., 12.00, 14.00)
        String timeRegex = "^([01]?\\d|2[0-3])\\.\\d{2}$";

        // Validation
        if (Title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a title!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (SessionDate == null) {
            JOptionPane.showMessageDialog(this, "Please select a date!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (SessionDate.before(new Date())) {
            JOptionPane.showMessageDialog(this, "The session date must be a future date!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (StartTime.isEmpty() || !StartTime.matches(timeRegex)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid start time in the format HH.mm (e.g., 12.00)!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (EndTime.isEmpty() || !EndTime.matches(timeRegex)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid end time in the format HH.mm (e.g., 14.00)!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (HallNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a hall number!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (Course.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the course!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Format the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(SessionDate);

            // Parse and validate the start and end times
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH.mm");
            Date startTimeDate = timeFormat.parse(StartTime);
            Date endTimeDate = timeFormat.parse(EndTime);

            // Check if the duration is within 10 hours
            long durationInMillis = endTimeDate.getTime() - startTimeDate.getTime();
            if (durationInMillis < 0) {
                durationInMillis += 24 * 60 * 60 * 1000; // Handle overnight sessions
            }
            long durationInHours = durationInMillis / (60 * 60 * 1000);
            if (durationInHours > 10) {
                JOptionPane.showMessageDialog(this, "The session duration cannot exceed 10 hours!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convert times to AM/PM format for database
            SimpleDateFormat amPmFormat = new SimpleDateFormat("hh.mm a");
            String startTimeAmPm = amPmFormat.format(startTimeDate);
            String endTimeAmPm = amPmFormat.format(endTimeDate);

            // Check if the title already exists
            String queryCheckTitle = "SELECT COUNT(*) AS count FROM class WHERE name = '" + Title + "'";
            ResultSet resultSet = MySQL2.executeSearch(queryCheckTitle);

            if (resultSet.next() && resultSet.getInt("count") > 0) {
                JOptionPane.showMessageDialog(this, "The session title already exists. Please choose a different title.", "Conflict", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Check for time conflicts with the tutor
            String tutorConflictQuery = "SELECT * FROM `class` WHERE `tutor_id` = '" + TutorID + "' AND `date` = '" + dateString + "' "
                    + "AND ((`start_time` <= '" + startTimeAmPm + "' AND `end_time` > '" + startTimeAmPm + "') "
                    + "OR (`start_time` < '" + endTimeAmPm + "' AND `end_time` >= '" + endTimeAmPm + "') "
                    + "OR (`start_time` >= '" + startTimeAmPm + "' AND `end_time` <= '" + endTimeAmPm + "'))";
            ResultSet tutorConflictCheck = MySQL2.executeSearch(tutorConflictQuery);

            if (tutorConflictCheck.next()) {
                JOptionPane.showMessageDialog(this, "The tutor is already scheduled during this time slot. Please choose a different time.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Check for hall conflicts
            String hallConflictQuery = "SELECT * FROM `class` WHERE `hallnumber` = '" + HallNumber + "' AND `date` = '" + dateString + "' "
                    + "AND ((`start_time` <= '" + startTimeAmPm + "' AND `end_time` > '" + startTimeAmPm + "') "
                    + "OR (`start_time` < '" + endTimeAmPm + "' AND `end_time` >= '" + endTimeAmPm + "') "
                    + "OR (`start_time` >= '" + startTimeAmPm + "' AND `end_time` <= '" + endTimeAmPm + "'))";
            ResultSet hallConflictCheck = MySQL2.executeSearch(hallConflictQuery);

            if (hallConflictCheck.next()) {
                JOptionPane.showMessageDialog(this, "The selected hall is already booked during this time slot. Please choose a different location or time.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            // Insert session data into the database
            // Insert into the database
            String query = "INSERT INTO request_sessions (title, date, start_time, end_time, hallnumber, tutor_id, approve_status, courses_id, type) "
                    + "VALUES ('" + Title + "', '" + format.format(SessionDate) + "', '" + StartTime + "', '" + EndTime + "', '" + HallNumber + "', '" + TutorID + "', 'Pending', '" + courseMap.get(Course) + "', 'New Session')";
            MySQL2.executeIUD(query);

            JOptionPane.showMessageDialog(this, "Session requested successfully!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            TutorRequestedSessions tr = new TutorRequestedSessions(TutorID);
            parent.switchPanel(tr);

            // Clear all fields after successful insertion
            title.setText("");
            jDateChooser1.setDate(null);
            startTime.setText("");
            endTime.setText("");
            hallNumber.setText("");
            courseField.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while adding the class. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
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
