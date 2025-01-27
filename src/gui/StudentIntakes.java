package gui;

import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAdapter;
import java.awt.Color;
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

public class StudentIntakes extends javax.swing.JPanel {
    
    private static HashMap<String, String> statusMap = new HashMap<>();
    
    
    private String Iid;
    private DateChooser chDate = new DateChooser();
    public StudentIntakes() {
        initComponents();
        loadStatus();
        loadTable();
        dateChooser1();
        dateChooser2();
        Iid = "1";
        SwingUtilities.invokeLater(() -> jTextField1.requestFocusInWindow());
    }
    
    private void dateChooser1() {
        chDate.setTextField(jTextField2);
        chDate.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        chDate.setLabelCurrentDayVisible(false);
        chDate.setForeground(Color.black);
        chDate.setBackground(Color.white);
        chDate.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        chDate.addActionDateChooserListener(new DateChooserAdapter() {
        });
    }
    
    private void dateChooser2() {
        chDate.setTextField(jTextField3);
        chDate.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        chDate.setLabelCurrentDayVisible(false);
        chDate.setForeground(Color.black);
        chDate.setBackground(Color.white);
        chDate.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        chDate.addActionDateChooserListener(new DateChooserAdapter() {
        });
    }
    
    private void loadStatus() {
        
        try {
            
            ResultSet resultSet = MySQL2.executeSearch("SELECT `id`, `name` FROM `intake_status`");
            
            Vector vector = new Vector();
            vector.add("Select");
            
            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                statusMap.put(resultSet.getString("name"), resultSet.getString("id"));
            }
            
            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadTable() {
        
        try {
            
            String sort = String.valueOf(jComboBox2.getSelectedItem());
            
            String searchText = jTextField5.getText().toLowerCase();
            
            String query = "SELECT `intake`.`id`, `intake`.`name`, `start_date`, `end_date`, `capacity`, `created_at`, "
                    + "`updated_at`, `intake_status`.`name` FROM `intake` "
                    + "INNER JOIN `intake_status` ON `intake`.`intake_status_id` = `intake_status`.`id` ";
            
            if (!searchText.isEmpty()) {
                
                query += "WHERE (LOWER(`intake`.`id`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`intake`.`name`) LIKE '%" + searchText + "%') ";
//                        + "OR LOWER(`last_name`) LIKE '%" + searchText + "%' "
//                        + "OR LOWER(`contact_info`) LIKE '%" + searchText + "%' "
//                        + "OR LOWER(`email`) LIKE '%" + searchText + "%' "
//                        + "OR LOWER(`roles`.`name`) LIKE '%" + searchText + "%' "
//                        + "OR LOWER(`nic`) LIKE '%" + searchText + "%') ";

            }
            
            if (sort.equals("Intake ID ASC")) {
                query += "ORDER BY `intake`.`id` ASC";
            } else if (sort.equals("Intake ID DESC")) {
                query += "ORDER BY `intake`.`id` DESC";
            } else if (sort.equals("Intake Name ASC")) {
                query += "ORDER BY `intake`.`name` ASC";
            } else if (sort.equals("Intake Name DESC")) {
                query += "ORDER BY `intake`.`name` DESC";
            } else if (sort.equals("Capacity ASC")) {
                query += "ORDER BY `capacity` ASC";
            } else if (sort.equals("Capacity DESC")) {
                query += "ORDER BY `capacity` DESC";
            } else if (sort.equals("Status: Opened")) {
                query += "AND `intake_status`.`name` = 'Opened'";
            } else if (sort.equals("Status: Closed")) {
                query += "AND `intake_status`.`name` = 'Closed'";
            } else if (sort.equals("Status: Pending")) {
                query += "AND `intake_status`.`name` = 'Pending'";
            } else if (sort.equals("Status: Archived")) {
                query += "AND `intake_status`.`name` = 'Archived'";
            }
            
            ResultSet resultSet = MySQL2.executeSearch(query);
            
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("intake.id"));
                vector.add(resultSet.getString("intake.name"));
                vector.add(resultSet.getString("start_date"));
                vector.add(resultSet.getString("end_date"));
                vector.add(resultSet.getString("capacity"));
                vector.add(resultSet.getString("created_at"));
                vector.add(resultSet.getString("updated_at"));
                vector.add(resultSet.getString("intake_status.name"));
                
                model.addRow(vector);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void reset() {
        jTextField1.setText("");
//        jDateChooser1.setDate(null);
//        jDateChooser2.setDate(null);
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jComboBox1.setSelectedItem("Select");
        jComboBox2.setSelectedItem("Select");
        jTextField5.setText("");
        jTextField1.grabFocus();
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("Student Intakes");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Intake Name");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setText("Start Date");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("End Date");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setText("Capacity");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel6.setText("Status");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setText("Add New Intake");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton2.setText("Update Intake");
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
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Intake ID", "Intake Name", "Start Date", "End Date", "Capacity", "Created Date", "Update Date", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7.setText("Sort By :");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Intake ID ASC", "Intake ID DESC", "Intake Name ASC", "Intake Name DESC", "Capacity ASC", "Capacity DESC", "Status: Opened", "Status: Closed", "Status: Pending", "Status: Archived" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel8.setText("Search");

        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });

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
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2)
                            .addComponent(jComboBox1, 0, 132, Short.MAX_VALUE)
                            .addComponent(jTextField3)
                            .addComponent(jTextField4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        try {
            
            String intakeName = jTextField1.getText();
            String startDate = jTextField3.getText();
            String endDate = jTextField4.getText();
            String capacity = jTextField2.getText();
            String status = String.valueOf(jComboBox1.getSelectedItem());
            
            if (intakeName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an Intake Name!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextField1.grabFocus();
            } else if (startDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a start date!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextField2.grabFocus();
            } else if (endDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a end date!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextField3.grabFocus();
            } else if (capacity.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a capacity!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextField4.grabFocus();
            } else if (!capacity.matches("^\\d+$")) {
                JOptionPane.showMessageDialog(this, "Please enter only values", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextField4.grabFocus();
            } else if (status.matches("Select")) {
                JOptionPane.showMessageDialog(this, "Please select a status!", "Warning", JOptionPane.WARNING_MESSAGE);
                jComboBox2.grabFocus();
            }

//            ResultSet rs = MySQL2.executeSearch("SELECT `intake`.`id`, `intake`.`name` FROM `intake` "
//                    + "WHERE `intake`.`id` = '" + Iid + "' AND `intake`.`name` = '" + intakeName + "'");
//            if (rs.next()) {
//                JOptionPane.showMessageDialog(this, "This NIC number is already registered", "Warning", JOptionPane.WARNING_MESSAGE);
//            }
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (date != null) {
                String formattedDate = format.format(date);
                System.out.println("Formatted Date: " + formattedDate);
                MySQL2.executeIUD("INSERT INTO `intake` (`intake`.`name`, `start_date`, `end_date`, `capacity`, `created_at`, `updated_at`, `intake_status_id`) "
                        + "VALUES ('" + intakeName + "', '" + startDate + "', '" + endDate + "', '" + capacity + "', '" + format.format(date) + "', '" + format.format(date) + "', '" + statusMap.get(status) + "')");
                JOptionPane.showMessageDialog(this, "Intake successfully registered!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                loadTable();
                reset();
            } else {
                System.out.println("Date is null.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            
            String intakeName = jTextField1.getText();
            String startDate = jTextField3.getText();
            String endDate = jTextField4.getText();
            String capacity = jTextField2.getText();
            String status = String.valueOf(jComboBox1.getSelectedItem());
            
            if (intakeName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an Intake Name!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextField1.grabFocus();
            } else if (startDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a start date!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextField2.grabFocus();
            } else if (endDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a end date!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextField3.grabFocus();
            } else if (capacity.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a capacity!", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextField4.grabFocus();
            } else if (!capacity.matches("^\\d+$")) {
                JOptionPane.showMessageDialog(this, "Please enter only values", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextField4.grabFocus();
            } else if (status.matches("Select")) {
                JOptionPane.showMessageDialog(this, "Please select a status!", "Warning", JOptionPane.WARNING_MESSAGE);
                jComboBox2.grabFocus();
            }
            
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (date != null) {
                String formattedDate = format.format(date);
                System.out.println("Formatted Date: " + formattedDate);
                MySQL2.executeIUD("UPDATE `intake` SET `intake`.`name` = '" + intakeName + "', `start_date` = '" + startDate + "', "
                        + "`end_date` = '" + endDate + "', `capacity` = '" + capacity + "', `updated_at` = '" + formattedDate + "', "
                        + "`intake_status_id` = '" + statusMap.get(status) + "' WHERE `intake`.`id` = '" + Iid + "'");
                JOptionPane.showMessageDialog(this, "Intake successfully updated!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                loadTable();
                reset();
            } else {
                System.out.println("Date is null.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        reset();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        
        int row = jTable1.getSelectedRow();
        int col = jTable1.getSelectedColumn();
        
        String IntakeID = String.valueOf(jTable1.getValueAt(row, 0));
        String IntakeName = String.valueOf(jTable1.getValueAt(row, 1));
        String StartDate = String.valueOf(jTable1.getValueAt(row, 2));
        String EndDate = String.valueOf(jTable1.getValueAt(row, 3));
        String Capacity = String.valueOf(jTable1.getValueAt(row, 4));
        String CreatedAt = String.valueOf(jTable1.getValueAt(row, 5));
        String UpdateAt = String.valueOf(jTable1.getValueAt(row, 6));
        String Status = String.valueOf(jTable1.getValueAt(row, 7));
        
        if (evt.getClickCount() == 2) {
            
            Iid = IntakeID;
            jTextField1.setText(IntakeName);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            
//            try {
//                Date sDate = formatter.parse((StartDate));
//                jDateChooser1.setDate(sDate);
//                System.out.println("Converted Date: " + sDate);
//                Date eDate = formatter.parse((EndDate));
//                jDateChooser2.setDate(eDate);
//                System.out.println("Converted Date: " + eDate);
//            } catch (Exception e) {
//                System.out.println("Error converting Object to Date: " + e.getMessage());
//            }
            
            jTextField2.setText(StartDate);
            jTextField3.setText(EndDate);
            jTextField4.setText(Capacity);
            jComboBox1.setSelectedItem(Status);
            
            jButton1.setEnabled(false);
            jButton2.setEnabled(true);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        loadTable();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
        loadTable();
    }//GEN-LAST:event_jTextField5KeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jTextField5.setText("");  // Clear the date field
        jComboBox2.setSelectedIndex(0);  // Reset the sorting combo box to default

        // Reload the original data without any filters
        loadTable();
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
