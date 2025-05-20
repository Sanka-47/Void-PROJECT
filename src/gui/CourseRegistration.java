//author CHANULI
package gui;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;

public class CourseRegistration extends javax.swing.JPanel {

    private static HashMap<String, String> gradeMap = new HashMap<>();

    public CourseRegistration() {
        initComponents();
        loadCourseDetails();
        loadGrades();
        SwingUtilities.invokeLater(() -> jTextField1.requestFocusInWindow());
        jButton1.setEnabled(false);
        jButton3.setEnabled(false);

    }

    private void loadCourseDetails() {

        try {

            String sort = String.valueOf(jComboBox2.getSelectedItem());

            String searchText = jTextField3.getText().toLowerCase();

            String query = "SELECT * FROM `courses` "
                    + "INNER JOIN `course_status` ON `courses`.`course_status_id` = `course_status`.`id` ";

            if (!searchText.isEmpty()) {

                query += "WHERE LOWER(`courses`.`id`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`courses`.`name`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`courses`.`fee`) LIKE '%" + searchText + "%' ";

            }

            if (sort.equals("Price ASC")) {
                query += "ORDER BY `courses`.`fee` ASC";
            } else if (sort.equals("Price DESC")) {
                query += "ORDER BY `courses`.`fee` DESC";
            } else if (sort.equals("Name ASC")) {
                query += "ORDER BY `courses`.`name` ASC";
            } else if (sort.equals("Name DESC")) {
                query += "ORDER BY `courses`.`name` DESC";
            } else if (sort.equals("ID ASC")) {
                query += "ORDER BY `courses`.`id` ASC";
            } else if (sort.equals("ID DESC")) {
                query += "ORDER BY `courses`.`id` DESC";
            }

            if (query.contains("WHERE")) {
                if (sort.equals("Active")) {
                    query += "`course_status`.`name` = 'Active'";
                } else if (sort.equals("Deactive")) {
                    query += "`course_status`.`name` = 'Deactive'";
                }
            } else {
                if (sort.equals("Active")) {
                    query += "WHERE `course_status`.`name` = 'Active'";
                } else if (sort.equals("Deactive")) {
                    query += "WHERE `course_status`.`name` = 'Deactive'";
                }
            }

            ResultSet resultSet = MySQL2.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("courses.id"));
                vector.add(resultSet.getString("courses.name"));
                vector.add(resultSet.getString("courses.grade_level"));
                vector.add(resultSet.getString("courses.course_description"));
//                vector.add(resultSet.getString("tutor.first_name") + " " + resultSet.getString("tutor.last_name"));
                vector.add(resultSet.getString("courses.fee"));
                vector.add(resultSet.getString("course_status.name"));

                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGrades() {

        try {
            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `courses`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("grade_level"));
                gradeMap.put(resultSet.getString("grade_level"), resultSet.getString("id"));

                DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
                jComboBox1.setModel(model);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();

        jButton4.setBackground(new java.awt.Color(78, 74, 207));
        jButton4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Reset");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(78, 74, 207));
        jButton3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Disable Course");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(78, 74, 207));
        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Add Course");
        jButton2.setPreferredSize(new java.awt.Dimension(201, 26));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(78, 74, 207));
        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Edit Course");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setText("Course Name");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Level");

        jLabel3.setText("Fees");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("Course Registration");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Sort By :");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Price ASC", "Price DESC", "Name ASC", "Name DESC", "ID ASC", "ID DESC", "Active", "Deactive" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setText("Search");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Course Name", "Grade Level", "Description", "Fees", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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

        jLabel5.setText("Description");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(78, 74, 207));
        jButton5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Clear All");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel1)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel3)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, 257, Short.MAX_VALUE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String courseId = jTable1.getValueAt(selectedRow, 0).toString();
        String courseName = jTextField1.getText();
        String grade = String.valueOf(jComboBox1.getSelectedItem());
        String fee = jTextField2.getText();
        String description = jTextField4.getText();

//        if (courseName.isEmpty() || fee.isEmpty() || grade.equals("Select")) {
//            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Warning", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
        if (courseName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Course Name must be filled!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (fee.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fee must be filled!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (grade.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a grade!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (description.length() < 10 || description.length() > 255) {
            JOptionPane.showMessageDialog(this, "Course description must be between 10 and 255 characters!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String gradeId = gradeMap.get(grade);
            MySQL2.executeIUD("UPDATE `courses` SET `name`='" + courseName + "', `grade_level`='"
                    + gradeId + "',`course_description`='" + description + "', `fee`='" + fee + "' "
                    + "WHERE `id`='" + courseId + "'");

            loadCourseDetails();
            reset();
            JOptionPane.showMessageDialog(this, "Course updated successfully!");
            jButton2.setEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update the course.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        try {
            String courseName = jTextField1.getText();
//            Double fee = Double.parseDouble(jTextField2.getText());
            String fee = String.valueOf(jTextField2.getText());
            String grade = String.valueOf(jComboBox1.getSelectedItem());
            String description = jTextField4.getText();

            if (courseName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the course name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (fee.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the course fee", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (grade.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select a grade", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (description.length() < 10 || description.length() > 255) {
                JOptionPane.showMessageDialog(this, "Course description must be between 10 and 255 characters!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            } else {

                String gradeId = gradeMap.get(grade);

                MySQL2.executeIUD("INSERT INTO `courses`(`name`, `grade_level`, `fee`,`course_description`,`course_status_id`) VALUES ('"
                        + courseName + "', '" + gradeId + "', '" + fee + "','" + description + "',0)");

                loadCourseDetails();
                reset();
                JOptionPane.showMessageDialog(this, "Course added successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add the course.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
//
//        int selectedRow = jTable1.getSelectedRow();
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(this, "Please select a course to Activate/Deactivate.", "Warning", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        String courseId = jTable1.getValueAt(selectedRow, 0).toString();
//        String currentStatus = jTable1.getValueAt(selectedRow, 4).toString();
//
//        try {
//
//            String newStatusId = currentStatus.equals("active") ? "0" : "1";
//
//            MySQL2.executeIUD("UPDATE `courses` SET `course_status_id`='" + newStatusId + "' WHERE `id`='" + courseId + "'");
//
//            loadCourseDetails();
//            reset();
//
//            JOptionPane.showMessageDialog(this, "Course status changed to " + (newStatusId.equals("1") ? "Active" : "Inactive") + " successfully!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Failed to change the course status.", "Error", JOptionPane.ERROR_MESSAGE);
//        }

        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course to deactivate.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String courseId = jTable1.getValueAt(selectedRow, 0).toString();
        String currentStatus = jTable1.getValueAt(selectedRow, 5).toString().trim(); // Trim spaces

        // Debugging output to check the actual stored value
        System.out.println("Debug: Current Status Retrieved -> '" + currentStatus + "'");

        // Adjust condition based on the actual table value
        if (currentStatus.equalsIgnoreCase("Inactive") || currentStatus.equals("0")) {
            JOptionPane.showMessageDialog(this, "The course is already deactivated.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return; // No changes needed
        }

        try {
            // Change status to "Inactive"
            MySQL2.executeIUD("UPDATE `courses` SET `course_status_id`='0' WHERE `id`='" + courseId + "'");

            // Refresh table data and reset fields
            loadCourseDetails();
            reset();

            JOptionPane.showMessageDialog(this, "Course successfully deactivated!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to deactivate the course.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        reset();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        loadCourseDetails();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        loadCourseDetails();
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int Row = jTable1.getSelectedRow();

        if (evt.getClickCount() == 2) {

            jTextField1.setText(String.valueOf(jTable1.getValueAt(Row, 1)));
            jComboBox1.setSelectedItem(String.valueOf(jTable1.getValueAt(Row, 2)));
            jTextField4.setText(String.valueOf(jTable1.getValueAt(Row, 3)));
            jTextField2.setText(String.valueOf(jTable1.getValueAt(Row, 4)));

            jButton1.setEnabled(true);
            jButton2.setEnabled(false);
            jButton3.setEnabled(true);

        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jTextField3.setText("");  // Clear the date field
        jComboBox2.setSelectedIndex(0);  // Reset the sorting combo box to default

        // Reload the original data without any filters
        loadCourseDetails();
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables

    private void reset() {
        jTextField1.setText("");
        jComboBox1.setSelectedIndex(0);
        jTextField2.setText("");
        jTextField4.setText("");

        jButton1.setEnabled(false);
        jButton3.setEnabled(false);
    }
}
