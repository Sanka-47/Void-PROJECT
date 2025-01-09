//author KAVISHA
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.AssignmentData;
import model.MySQL2;
import javax.swing.JLabel;

public class AssignmentManagement extends javax.swing.JPanel {

    private TutorDashboard parent;

    private String assignment;
    private String course;
    private int tutorId;

    public AssignmentManagement(int tutorID) {
        this.tutorId = tutorID;
        initComponents();
        loadTable();
//        loadCourses();

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        jTable1.setDefaultRenderer(Object.class, renderer);
    }

    private static HashMap<String, String> courseMap = new HashMap<>();
    private static HashMap<String, String> tutorMap = new HashMap<>();

    // Example sortAscending method
//    private void sortAscending() {
//        // Assuming you have a data model for your JTable, such as DefaultTableModel
//        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
//        jTable1.setRowSorter(sorter);
//
//        // Set the sorting order on the desired column (e.g., column 0)
//        sorter.setComparator(0, Comparator.naturalOrder());
//        sorter.sort();
//    }
// Example sortDescending method
//    private void sortDescending() {
//        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
//        jTable1.setRowSorter(sorter);
//
//        // Set the sorting order on the desired column (e.g., column 0)
//        sorter.setComparator(0, Comparator.reverseOrder());
//        sorter.sort();
//    }
    private void loadTable() {

        try {
            String TID = String.valueOf(tutorId);
            StringBuilder query = new StringBuilder();
            String searchText = jTextField1.getText().toLowerCase();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            boolean hasCondition = false;

            // Base query
            query.append("SELECT * FROM `assignment` ")
                    .append("INNER JOIN `tutor` ON `assignment`.`tutor_id` = `tutor`.`id` ")
                    .append("INNER JOIN `courses` ON `assignment`.`courses_id` = `courses`.`id` ")
                    .append("WHERE `tutor`.`id` = '").append(TID).append("' ");

            hasCondition = true;

            if (!searchText.isEmpty()) {
                query.append(hasCondition ? "AND " : "WHERE ");
                query.append("(LOWER(`assignment`.`title`) LIKE '%").append(searchText).append("%' ")
                        .append("OR LOWER(`courses`.`name`) LIKE '%").append(searchText).append("%' ")
                        .append("OR LOWER(`tutor`.`first_name`) LIKE '%").append(searchText).append("%' ")
                        .append("OR LOWER(`tutor`.`last_name`) LIKE '%").append(searchText).append("%') ");
                hasCondition = true;
            }

            // Date filtering
            if (jDateChooser1.getDate() != null || jDateChooser2.getDate() != null) {
                query.append(hasCondition ? "AND " : "WHERE ");
                if (jDateChooser1.getDate() != null && jDateChooser2.getDate() != null) {
                    query.append("`assignment`.`due_date` BETWEEN '")
                            .append(format.format(jDateChooser1.getDate())).append("' AND '")
                            .append(format.format(jDateChooser2.getDate())).append("' ");
                } else if (jDateChooser1.getDate() != null) {
                    query.append("`assignment`.`due_date` >= '")
                            .append(format.format(jDateChooser1.getDate())).append("' ");
                } else {
                    query.append("`assignment`.`due_date` <= '")
                            .append(format.format(jDateChooser2.getDate())).append("' ");
                }
            }

            // Execute query
            ResultSet rs = MySQL2.executeSearch(query.toString());
//            System.out.println(query); // Debugging purposes

            // Update table model
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                Vector<Object> vectorE = new Vector<>();
                vectorE.add(rs.getString("id"));
                vectorE.add(rs.getString("title"));
                vectorE.add(rs.getString("description"));
                vectorE.add(rs.getString("due_date"));
                String fullname = rs.getString("tutor.first_name") + " " + rs.getString("tutor.last_name");
                String coursename = rs.getString("courses.name");
                vectorE.add(fullname);
                vectorE.add(coursename);
                tutorMap.put(fullname, rs.getString("tutor_id"));
                courseMap.put(coursename, rs.getString("courses_id"));

                model.addRow(vectorE);
            }

            jTable1.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel1.setText("Assignments");

        jButton5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton5.setText("Add");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton6.setText("Delete");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Double click relevant row to 'Update' the row");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Description", "Due Date", "Tutor Name", "Course Name"
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
        jScrollPane2.setViewportView(jTable1);

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setText("Search");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel6.setText("TO");

        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton2.setText("Sort");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7.setText("Sort By Date");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 207, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addGap(41, 41, 41))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

//        AAJ addAssignmentJFrame = new AAJ();
//        addAssignmentJFrame.setVisible(true);
        AddAssignmentJDialog AAJ = new AddAssignmentJDialog(parent, true);
        AAJ.setVisible(true);

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        try {

            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this row?", "Message",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {

//                MySQL2.executeIUD("DELETE * FROM assignment WHERE assignment.id = '" + assignment + "' ");
                MySQL2.executeIUD("DELETE FROM assignment WHERE id = '" + assignment + "' ");

            }

            loadTable();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int row = jTable1.getSelectedRow(); // Selected row
        int col = jTable1.getSelectedColumn(); // Selected column

// Validate indices
        if (row >= 0 && col >= 0 && col < jTable1.getColumnCount()) {
            Object value = jTable1.getValueAt(row, col);
        } else {
            System.out.println("Invalid row/column selected.");
        }

        String Id = String.valueOf(jTable1.getValueAt(row, 0));
        String Name = String.valueOf(jTable1.getValueAt(row, 1));
        String Description = String.valueOf(jTable1.getValueAt(row, 2));
        String DueDate = String.valueOf(jTable1.getValueAt(row, 3));
        String TutorName = String.valueOf(jTable1.getValueAt(row, 4));
        String CourseName = String.valueOf(jTable1.getValueAt(row, 5));
//        String Email = String.valueOf(jTable1.getValueAt(row, 6));
//        String Gender = String.valueOf(jTable1.getValueAt(row, 7));

        if (evt.getClickCount() == 2) {
//            switchToRegistration();
//            if (updateStudent == null) {
//                updateStudent = new StudentRegistration();
//            }

            AddAssignmentJDialog AAJ = new AddAssignmentJDialog(parent, true);

            AAJ.setID(Id);
            AAJ.getjTextField1().setText(Name);
            AAJ.getjTextField2().setText(Description);
            AAJ.getjComboBox1().setSelectedItem(TutorName);
            AAJ.getjComboBox1().setEnabled(false);
            AAJ.getjComboBox2().setSelectedItem(CourseName);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date dateofBirth = formatter.parse((DueDate));
                AAJ.getjDateChooser1().setDate(dateofBirth);
            } catch (Exception e) {
                System.out.println("Error converting Object to Date: " + e.getMessage());
            }
            
            AAJ.getjButton1().setEnabled(false);
            AAJ.getjButton2().setEnabled(true);

            AAJ.setVisible(true);
//            switchToRegistration();
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        loadTable();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        loadTable();
    }//GEN-LAST:event_jTextField1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
