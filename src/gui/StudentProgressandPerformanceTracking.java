package gui;

import static gui.StudentRegistration.gender;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;

public class StudentProgressandPerformanceTracking extends javax.swing.JPanel {

    public StudentProgressandPerformanceTracking(String fName, String lName) {
        initComponents();
//        loadAssignmentDetails();
        loadAssignmentDetails("");
        loadAssignmentTitle();
        loadassignmentID();
        setupSearchListener(); // Added search listener
        SwingUtilities.invokeLater(() -> jTextField1.requestFocusInWindow());
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        jTable1.setDefaultRenderer(Object.class, renderer);
        jButton1.setEnabled(false);
        jButton3.setEnabled(false);
    }

    private void loadassignmentID() {

        try {
            ResultSet rs = MySQL2.executeSearch("SELECT * FROM `assignment`");
            Vector vector = new Vector();
            vector.add("Select");

            while (rs.next()) {
                vector.add(rs.getString("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);
//             logger.info("Gender data loaded successfully.");

        } catch (Exception e) {
//           logger.log(Level.SEVERE, "Error loading gender data: ", e);
            e.printStackTrace();
        }

    }

    private void loadAssignmentDetails(String searchQuery) {

//        try {
//            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `grades` "
//                    + "INNER JOIN `student` ON `grades`.`student_nic` = `student`.`nic`"
//                    + "INNER JOIN `assignment` ON  `grades`.`assignment_id` = `assignment`.`id`");
//
//            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//            model.setRowCount(0);
//
//            while (resultSet.next()) {
//
//                Vector<String> vector = new Vector<>();
//                vector.add(resultSet.getString("student.nic"));
//                vector.add(resultSet.getString("assignment.id"));
//                vector.add(resultSet.getString("grades.grade"));
//
//                model.addRow(vector);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            String sortOption = String.valueOf(jComboBox4.getSelectedItem());
            String orderByClause = "";

            switch (sortOption) {
                case "NIC ASC":
                    orderByClause = "ORDER BY student.nic ASC";
                    break;
                case "NIC DESC":
                    orderByClause = "ORDER BY student.nic DESC";
                    break;
                case "Marks ASC":
                    orderByClause = "ORDER BY grades.grade ASC";
                    break;
                case "Marks DESC":
                    orderByClause = "ORDER BY grades.grade DESC";
                    break;
                case "ID ASC":
                    orderByClause = "ORDER BY assignment.id ASC";
                    break;
                case "ID DESC":
                    orderByClause = "ORDER BY assignment.id DESC";
                    break;
                default:
                    orderByClause = "";
            }

            String query = "SELECT * FROM `grades` "
                    + "INNER JOIN `student` ON `grades`.`student_nic` = `student`.`nic` "
                    + "INNER JOIN `assignment` ON `grades`.`assignment_id` = `assignment`.`id`";

            if (!searchQuery.isEmpty()) {
                query += " WHERE student.nic LIKE '%" + searchQuery + "%' "
                        + "OR assignment.id LIKE '%" + searchQuery + "%' "
                        + "OR assignment.title LIKE '%" + searchQuery + "%'";
            }
            query += " " + orderByClause;

            ResultSet resultSet = MySQL2.executeSearch(query);
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("student.nic"));
                vector.add(resultSet.getString("assignment.id"));
                vector.add(resultSet.getString("grades.grade"));
                model.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Listener for real-time search
    private void setupSearchListener() {
        jTextField4.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                loadAssignmentDetails(jTextField4.getText().trim());
            }

            public void removeUpdate(DocumentEvent e) {
                loadAssignmentDetails(jTextField4.getText().trim());
            }

            public void changedUpdate(DocumentEvent e) {
                loadAssignmentDetails(jTextField4.getText().trim());
            }
        });
    }

    private void loadAssignmentTitle() {

        try {
            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `assignment`");

            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("assignment.id"));
                vector.add(resultSet.getString("assignment.title"));
//                vector.add(resultSet.getString("assignment.description"));

                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reset() {
        jTextField1.setText("");
        jComboBox1.setSelectedItem("Select");
        jTextField3.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel7.setText("Student NIC");

        jTextField1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jTextField1.setMaximumSize(new java.awt.Dimension(206, 35));
        jTextField1.setMinimumSize(new java.awt.Dimension(206, 35));
        jTextField1.setPreferredSize(new java.awt.Dimension(206, 35));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel10.setText("Assignment ID");

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel12.setText("Marks");

        jTextField3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setText("Edit");
        jButton1.setPreferredSize(new java.awt.Dimension(201, 26));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton2.setText("Add");
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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NIC", "Assignment ID", "Marks"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jTable1.setMinimumSize(new java.awt.Dimension(45, 25));
        jTable1.setPreferredSize(new java.awt.Dimension(980, 550));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Assignment ID", "Assignment Title"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setMinimumSize(new java.awt.Dimension(60, 45));
        jTable2.setPreferredSize(new java.awt.Dimension(300, 100));
        jScrollPane2.setViewportView(jTable2);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("Student Progress and");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel3.setText("Performance Tracking");

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setText("Sort By :");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NIC ASC", "NIC DESC", "Marks ASC", "Marks DESC", "ID ASC", "ID DESC" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setText("Search");

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton4.setText("Clear All");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
                        .addGap(12, 12, 12))))
        );

        jTextField3.getAccessibleContext().setAccessibleName("");
        jComboBox1.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            int selectedRow = jTable1.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an Assignment to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                String nic = jTextField1.getText();
                String assignmentId = String.valueOf(jComboBox1.getSelectedItem());
                String marks = jTextField3.getText();

//                if (nic.isEmpty()) {
//                    JOptionPane.showMessageDialog(this, "Please enter NIC", "Warning", JOptionPane.WARNING_MESSAGE);
//                } else if (assignmentId.isEmpty()) {
//                    JOptionPane.showMessageDialog(this, "Please enter Assignment ID", "Warning", JOptionPane.WARNING_MESSAGE);
//                } else if (marks.isEmpty()) {
//                    JOptionPane.showMessageDialog(this, "Please enter Marks", "Warning", JOptionPane.WARNING_MESSAGE);
//                } else {
//                    String updateAssignmentQuery = "UPDATE `grades` SET `assignment_id` = '" + assignmentId
//                            + "', `grade` = '" + marks + "' WHERE `assignment_id` = '" + assignmentId + "' AND `student_nic` = '" + nic + "'";
//                    MySQL2.executeIUD(updateAssignmentQuery);
//
//                    loadAssignmentDetails();
//                    reset();
//
//                    JOptionPane.showMessageDialog(this, "Assignment updated successfully!");
//                }
                if (nic.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter NIC", "Warning", JOptionPane.WARNING_MESSAGE);
                } else if (assignmentId.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter Assignment ID", "Warning", JOptionPane.WARNING_MESSAGE);
                } else if (marks.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter Marks", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    String updateAssignmentQuery = "UPDATE `grades` SET `assignment_id` = '" + assignmentId
                            + "', `grade` = '" + marks + "' WHERE `assignment_id` = '" + assignmentId + "' AND `student_nic` = '" + nic + "'";
                    MySQL2.executeIUD(updateAssignmentQuery);

                    loadAssignmentDetails(""); // Fixed call to refresh the table properly
                    reset();

                    JOptionPane.showMessageDialog(this, "Assignment updated successfully!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update assignment.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        try {
            String nic = jTextField1.getText();
            String assignmentId = String.valueOf(jComboBox1.getSelectedItem());
            String marks = jTextField3.getText();

//            if (nic.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Please enter NIC", "Warning", JOptionPane.WARNING_MESSAGE);
//            } else if (!nic.matches("^\\d{9}[vw]$") && !nic.matches("^\\d{12}$")) {
//                JOptionPane.showMessageDialog(this, "Invalid NIC format. Please enter a valid NIC.", "Warning", JOptionPane.WARNING_MESSAGE);
//            } else if (assignmentId.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Please enter Assignment ID", "Warning", JOptionPane.WARNING_MESSAGE);
//            } else if (marks.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Please enter Marks", "Warning", JOptionPane.WARNING_MESSAGE);
//            } else {
//
//                // Query to check if the assignment for this student already exists
//                String checkExistenceQuery = "SELECT COUNT(*) FROM `grades` WHERE `student_nic` = '" + nic + "' AND `assignment_id` = '" + assignmentId + "'";
//
//                // Check if the record already exists using executeSearch
//                ResultSet rs = MySQL2.executeSearch(checkExistenceQuery);
//                if (rs != null && rs.next() && rs.getInt(1) > 0) {
//                    JOptionPane.showMessageDialog(this, "This student has already been assigned this assignment.", "Warning", JOptionPane.WARNING_MESSAGE);
//                    return; // Stop further execution if the assignment exists
//                }
//
//                String insertAssignmentQuery = "INSERT INTO `grades` (`student_nic`, `assignment_id`, `grade`) "
//                        + "VALUES ('" + nic + "', '" + assignmentId + "', '" + marks + "')";
//                MySQL2.executeIUD(insertAssignmentQuery);
//
//                loadAssignmentDetails();
//                reset();
//
//                JOptionPane.showMessageDialog(this, "Assignment added successfully!");
//            }
            if (nic.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter NIC", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (!nic.matches("^\\d{9}[vw]$") && !nic.matches("^\\d{12}$")) {
                JOptionPane.showMessageDialog(this, "Invalid NIC format. Please enter a valid NIC.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (assignmentId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Assignment ID", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (marks.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Marks", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                String insertAssignmentQuery = "INSERT INTO `grades` (`student_nic`, `assignment_id`, `grade`) "
                        + "VALUES ('" + nic + "', '" + assignmentId + "', '" + marks + "')";
                MySQL2.executeIUD(insertAssignmentQuery);
                loadAssignmentDetails(""); // Fixed call to refresh the table properly
                reset();
                JOptionPane.showMessageDialog(this, "Assignment added successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add assignment.", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        reset();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow != -1) {

            String nic = String.valueOf(jTable1.getValueAt(selectedRow, 0));
            String assignmentId = String.valueOf(jTable1.getValueAt(selectedRow, 1));
            String marks = String.valueOf(jTable1.getValueAt(selectedRow, 2));

            jTextField1.setText(nic);
            jComboBox1.setSelectedItem(assignmentId);
            jTextField3.setText(marks);

            jButton2.setEnabled(false);
            jButton1.setEnabled(true);
            jButton3.setEnabled(true);
            jTextField1.setEditable(false);
            jComboBox1.setEnabled(false);

        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        loadAssignmentDetails(jTextField4.getText().trim());
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased

    }//GEN-LAST:event_jTextField4KeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        reset();
        loadAssignmentDetails(""); // Reload assignments without sorting
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
