//author KAVISHA
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
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
import model.AssignmentData;
import model.MySQL2;


public class AssignmentManagement extends javax.swing.JPanel {
    
    private UpdateAssignmentJFrame updateStudent;
    
    private String assignment;

    private String course;
    
    private int tutorId;
    
    public AssignmentManagement(int tutorID) {
        this.tutorId = tutorID;
        initComponents();
        loadTable("Select");
        loadCourses();
        this.updateStudent = new UpdateAssignmentJFrame();
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        jTable1.setDefaultRenderer(Object.class, renderer);
        System.out.println("Tutor ID :" + tutorID);
    }

    private static HashMap<String, String> courseMap = new HashMap<>();
    private static HashMap<String, String> tutorMap = new HashMap<>();

    private void loadTable(String column) {

        try {
            
            String TID = String.valueOf(tutorId);
            
            String query = "";
            
            if (column == null || column.equals("Select")) {
                query = "SELECT * FROM `assignment` INNER JOIN `tutor` ON `assignment`.`tutor_id` = `tutor`.`id`"
                    + "INNER JOIN `courses` ON `assignment`.`courses_id` = `courses`.`id` WHERE `tutor`.`id` = '" + TID + "'";
                System.out.println("1");
            } else {
                query = "SELECT * FROM `assignment` INNER JOIN `tutor` ON `assignment`.`tutor_id` = `tutor`.`id`"
                    + "INNER JOIN `courses` ON `assignment`.`courses_id` = `courses`.`id` WHERE `courses`.`name` = '"+column+"' AND `tutor`.`id` = '" + TID + "'";
                System.out.println("2");
            }

            ResultSet rs = MySQL2.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Clear existing rows

            // Iterate through the result set and populate jTable1
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

                model.addRow(vectorE); // Add row to the table model
            }

            jTable1.setModel(model);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void loadCourses() {

        try {

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM courses");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                courseMap.put(resultSet.getString("name"), resultSet.getString("id"));
                
                course = resultSet.getString("name");
                
            }

            jComboBox1.setModel(new DefaultComboBoxModel<>(vector));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel1.setText("Assignments");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setText("Course : ");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButton5.setText("Add");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButton6.setText("Delete");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("Double click relevant row to 'Update' the row");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6)
                .addGap(455, 455, 455))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(472, 472, 472)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(41, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton5)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(jButton6)
                .addGap(34, 34, 34))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        AddAssignmentJFrame addAssignmentJFrame = new AddAssignmentJFrame();
        addAssignmentJFrame.setVisible(true);

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        
        try {
        
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this row?", "Message",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {

                    MySQL2.executeIUD("DELETE * FROM assignment WHERE assignment.id = '"+assignment+"' ");
                    
                } else {

                    JOptionPane.showMessageDialog(this, "Row not deleted", "Information", JOptionPane.INFORMATION_MESSAGE);

                }
            
        

         loadTable("Select");
        
        } catch (Exception e) {
            
            e.printStackTrace();
            
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

        loadTable(course);
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
       
        int row = jTable1.getSelectedRow(); // Selected row
        int col = jTable1.getSelectedColumn(); // Selected column

// Validate indices
        if (row >= 0 && col >= 0 && col < jTable1.getColumnCount()) {
            Object value = jTable1.getValueAt(row, col);
            System.out.println("Value: " + value);
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
            updateStudent.getjTextLabel8().setText(Id);
            updateStudent.getjTextField1().setText(Name);
            updateStudent.getjTextField2().setText(Description);
            updateStudent.getjComboBox1().setSelectedItem(TutorName);
            updateStudent.getjComboBox2().setSelectedItem(CourseName);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date dateofBirth = formatter.parse((DueDate));
                updateStudent.getjDateChooser1().setDate(dateofBirth);
                System.out.println("Converted Date: " + dateofBirth);
            } catch (Exception e) {
                System.out.println("Error converting Object to Date: " + e.getMessage());
            }
            
              updateStudent.setVisible(true);
//            switchToRegistration();
        }
        
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
