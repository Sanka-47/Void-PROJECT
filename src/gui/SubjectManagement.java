//RANDKIA
package gui;

import java.awt.Color;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author sky
 */
public class SubjectManagement extends CustomColor {

    private static final Logger logger = LogManager.getLogger(SubjectManagement.class);

//    
    private static HashMap<String, String> coursesMap = new HashMap<>();
    private final String searchPlaceholder = "ID, Name, Description...";


    public SubjectManagement() {
        initComponents();
        loadCourses();
        loadSubjectTable();
        SwingUtilities.invokeLater(() -> jTextField1.requestFocusInWindow());
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton4.setEnabled(false);
        
        jTextField3.setText(searchPlaceholder);
        jTextField3.setForeground(Color.GRAY);

        jTextField3.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (jTextField3.getText().equals(searchPlaceholder)) {
                    jTextField3.setText("");
                    jTextField3.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jTextField3.getText().isEmpty()) {
                    jTextField3.setForeground(Color.GRAY);
                    jTextField3.setText(searchPlaceholder);
                }
            }
        });

    }

    private void loadCourses() {

        try {
//            logger.log(Level.INFO, "Starting to load courses from the database.");

            ResultSet rs = MySQL2.executeSearch("SELECT * FROM `courses`");
            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (rs.next()) {
                String courseName = rs.getString("name");
                String courseId = rs.getString("id");

                vector.add(courseName);
                coursesMap.put(courseName, courseId);

//                logger.log(Level.INFO, "Loaded course: {0} with ID: {1}", new Object[]{courseName, courseId});
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);

//            logger.log(Level.INFO, "Courses loaded successfully and combo box model updated.");
        } catch (Exception e) {
//            logger.log(Level.SEVERE, "Error occurred while loading courses.", e);

        }

    }

    private void loadSubjectTable() {

        try {
//            logger.log(Level.INFO, "Starting to load subjects from the database.");

            String searchText = jTextField3.getText().toLowerCase();
            if (searchText.equals(searchPlaceholder.toLowerCase())) {
                searchText = ""; // Ignore searchPlaceholder in search
            }
            String query = "SELECT * FROM `subject` "
                    + "INNER JOIN `courses` ON `subject`.`courses_id` = `courses`.`id` ";
            if (!searchText.isEmpty()) {

                query += "WHERE (LOWER(`subject`.`id`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`subject`.`name`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`description`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`courses`.`name`) LIKE '%" + searchText + "%') ORDER BY `subject`.`id` ASC ";
                System.out.println(query);
            }
            ResultSet resultSet = MySQL2.executeSearch(query);

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("name"));
                vector.add(resultSet.getString("description"));
                vector.add(resultSet.getString("courses.name"));

                dtm.addRow(vector);
                jTable1.setModel(dtm);

//                logger.log(Level.INFO, "Loaded subject: {0}, Description: {1}, Course: {2}",
//                        new Object[]{resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("courses.name")});
            }

//            logger.log(Level.INFO, "Subjects loaded successfully into the table.");
        } catch (Exception e) {
//            logger.log(Level.SEVERE, "Error occurred while loading subjects.", e);

        }

    }

    private void reset() {
        jTextField1.setText("");
        jTextField2.setText("");
        jComboBox1.setSelectedIndex(0);

        //jTextField1.grabFocus();
        jTextField1.requestFocus();
        jTable1.setEnabled(true);
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        jButton4.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new CustomColor();
        jLabel31 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 581));

        jLabel31.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Subject Management");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("Subject");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("Description");

        jTextField1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jTextField2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jTable1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Subject Name", "Description", "Course Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
        jScrollPane2.setViewportView(jTable1);

        jButton1.setBackground(new java.awt.Color(78, 74, 207));
        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Add Subject");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(78, 74, 207));
        jButton2.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Update Details");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(78, 74, 207));
        jButton3.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Remove Subject");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(78, 74, 207));
        jButton4.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel3.setText("Course Name");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setText("Search ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(33, 33, 33)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE))
                .addGap(52, 52, 52))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel31)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(51, 51, 51))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String subject = jTextField1.getText();
        String description = jTextField2.getText();
        String course = String.valueOf(jComboBox1.getSelectedItem());
        String stream = String.valueOf(jComboBox1.getSelectedItem());

        if (subject.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Subject Name", "Warning", JOptionPane.WARNING_MESSAGE);
        }else if (subject.length() > 45) {
                JOptionPane.showMessageDialog(this, "Subject Name is too long (maximum 30 characters)", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!subject.matches("^[A-Za-z0-9 ]+$")) {
            JOptionPane.showMessageDialog(this, "You Can Only Use Simple And Capital Letters For subject Name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Description", "Warning", JOptionPane.WARNING_MESSAGE);
//        } else if (description.length() > 50) {
//            JOptionPane.showMessageDialog(this, "description is too long (maximum 30 characters)", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (course.matches("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Your Course Name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            String courseId = coursesMap.get(stream);

            try {
//            logger.log(Level.INFO, "Starting to register a new subject: {0}", subject);
                ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `subject` WHERE  `name` = '" + subject + "' AND `courses_id` = '" + courseId + "'");

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "This subject already registered", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    Integer integer = MySQL2.executeIUD("INSERT INTO `subject` "
                            + "(`name`, `description`, `courses_id`) "
                            + "VALUES ('" + subject + "', '" + description + "', '" + courseId + "')");

                    JOptionPane.showMessageDialog(this, "Subject registration success!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

//            logger.log(Level.INFO, "Subject {0} registered successfully with description: {1} and course ID: {2}",
//                    new Object[]{subject, description, courseId});
                    loadSubjectTable();
                    reset();
                }

                // reset(); // Uncomment if needed
            } catch (Exception e) {
//            logger.log(Level.SEVERE, "Error occurred while registering the subject: " + subject, e);

            }

        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        if (evt.getClickCount() == 2) {

            jTable1.setEnabled(false);
            jButton1.setEnabled(false);
            jButton2.setEnabled(true);
            jButton3.setEnabled(true);
            jButton4.setEnabled(true);

            int selectedRow = jTable1.getSelectedRow();

            String subjectName = String.valueOf(jTable1.getValueAt(selectedRow, 1));
            jTextField1.setText(subjectName);

            String description = String.valueOf(jTable1.getValueAt(selectedRow, 2));
            jTextField2.setText(description);

            String courses = String.valueOf(jTable1.getValueAt(selectedRow, 3));
            jComboBox1.setSelectedItem(courses);

        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please Selected Row", "Error", JOptionPane.ERROR_MESSAGE);

        } else {

            String id = String.valueOf(jTable1.getValueAt(selectedRow, 0));
            String subject = jTextField1.getText();
            String description = jTextField2.getText();
            String course = String.valueOf(jComboBox1.getSelectedItem());
            String stream = String.valueOf(jComboBox1.getSelectedItem());

            if (subject.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter Subject Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (subject.length() > 45) {
                JOptionPane.showMessageDialog(this, "Subject Name is too long (maximum 30 characters)", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (!subject.matches("^[A-Za-z0-9 ]+$")) {
                JOptionPane.showMessageDialog(this, "You Can Only Use Simple And Capital Letters For subject Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter Description", "Warning", JOptionPane.WARNING_MESSAGE);
                //        } else if (description.length() > 50) {
                //            JOptionPane.showMessageDialog(this, "description is too long (maximum 30 characters)", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (course.matches("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Your Course Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                String courseId = coursesMap.get(stream);

                try {
//            logger.log(Level.INFO, "Starting to update subject with ID: {0}", id);

                    Integer integer = MySQL2.executeIUD("UPDATE `subject` SET "
                            + "`name` = '" + subject + "',"
                            + "`description` = '" + description + "',"
                            + "`courses_id` = '" + courseId + "'"
                            + "WHERE `id` = '" + id + "'");

                    JOptionPane.showMessageDialog(this, "Success!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

//            logger.log(Level.INFO, "Subject with ID {0} updated successfully. New name: {1}, description: {2}, course ID: {3}",
//                    new Object[]{id, subject, description, courseId});
                    loadSubjectTable();
                    reset();

                } catch (Exception e) {
//            logger.log(Level.SEVERE, "Error occurred while updating the subject with ID: " + id, e);

                }
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        reset();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please Selected Row", "Error", JOptionPane.ERROR_MESSAGE);

        } else {

            String id = String.valueOf(jTable1.getValueAt(selectedRow, 0));

            try {
//            logger.log(Level.INFO, "Starting to delete subject with ID: {0}", id);

                Integer integer = MySQL2.executeIUD("DELETE FROM `subject` "
                        + "WHERE `id` = '" + id + "'");

                JOptionPane.showMessageDialog(this, "Success!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

//            logger.log(Level.INFO, "Subject with ID {0} deleted successfully.", id);
                loadSubjectTable();
                reset();

            } catch (Exception e) {
//            logger.log(Level.SEVERE, "Error occurred while deleting the subject with ID: " + id, e);

            }

        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        loadSubjectTable();
    }//GEN-LAST:event_jTextField3KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
