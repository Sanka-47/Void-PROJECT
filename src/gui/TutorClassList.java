package gui;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;

public class TutorClassList extends javax.swing.JPanel {

    private DashboardInterface parent;
    private static HashMap<String, String> courseMap = new HashMap<>();

    private String mobile;

    private int tutorId;

    public TutorClassList(DashboardInterface parent, int TutorID) {
        this.parent = parent;
        this.tutorId = TutorID;
//        this.addSession = new AddSession();
        initComponents();
        loadTable();
        loadCourses();

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        jTable1.setDefaultRenderer(Object.class, renderer);
        this.tutorId = TutorID;

        cancelBtn.setEnabled(false);

        // Add selection listener to enable buttons when a row is selected
        jTable1.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && jTable1.getSelectedRow() != -1) {
                cancelBtn.setEnabled(true);
            }
        });
    }

//    private void switchToAddSession() {
//        parent.switchPanel(addSession);
//    }
    private void loadTable() {
        String TID = String.valueOf(tutorId);

        try {
            String searchText = jTextField1.getText().toLowerCase();

            String startTime = jTextField3.getText();

            String endTime = jTextField4.getText();

            // Base query
            String query = "SELECT * FROM class "
                    + "INNER JOIN tutor ON class.tutor_id = tutor.id "
                    + "INNER JOIN courses ON class.courses_id = courses.id "
                    + "INNER JOIN class_status ON class.class_status_id = class_status.id "
                    + "WHERE `tutor`.`id` = '" + TID + "' ";

            // Search text filtering
            
            if (!searchText.isEmpty()) {
                query += "AND (LOWER(`class`.`id`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`class`.`name`) LIKE '%" + searchText + "%' "
                        //                        + "OR LOWER(`class`.`date`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`class`.`hallnumber`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`courses`.`name`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`class_status`.`name`) LIKE '%" + searchText + "%') ";
            }

            if (!startTime.isEmpty()) {
                query += "AND (`class`.`start_time` >= '" + startTime + "') "; // Compare start_time directly
                System.out.println("Start Time: " + startTime);
            }

            if (!endTime.isEmpty()) {
                query += "AND (`class`.`end_time` <= '" + endTime + "') "; // Compare end_time directly
                System.out.println("End Time: " + endTime);
            }

            Date start = null;
            Date end = null;

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            if (jDateChooser1.getDate() != null || jDateChooser2.getDate() != null) {
                if (query.contains("WHERE")) {
                    query += "AND ";
                } else {
                    query += "WHERE ";
                }

                if (jDateChooser1.getDate() != null && jDateChooser2.getDate() != null) {
                    start = jDateChooser1.getDate();
                    end = jDateChooser2.getDate();

                    query += "`class`.`date` BETWEEN '" + format.format(start) + "' AND '" + format.format(end) + "' ";

                } else if (jDateChooser1.getDate() != null && jDateChooser2.getDate() == null) {
                    start = jDateChooser1.getDate();

                    query += "`class`.`date` >= '" + format.format(start) + "' ";

                } else if (jDateChooser1.getDate() == null && jDateChooser2.getDate() != null) {
                    end = jDateChooser2.getDate();

                    query += "`class`.`date` <= '" + format.format(end) + "' ";

                }
            }

//            if (jDateChooser1.getDate() != null && jDateChooser2.getDate() != null) {
//                start = jDateChooser1.getDate();
//                end = jDateChooser2.getDate();
//
//                query += "BETWEEN `class`.`date` > '" + format.format(start) + "' AND `class`.`date` < '" + format.format(end) + "' ";
//
//            } else if (jDateChooser1.getDate() != null && jDateChooser2.getDate() == null) {
//                start = jDateChooser1.getDate();
//
//                query += "`class`.`date` >= '" + format.format(start) + "' ";
//
//            } else if (jDateChooser1.getDate() == null && jDateChooser2.getDate() != null) {
//                end = jDateChooser2.getDate();
//
//                query += "`class`.`date` <= '" + format.format(end) + "' ";
//
//            }
            ResultSet resultSet = MySQL2.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("class.id"));
                vector.add(resultSet.getString("class.name"));
                vector.add(resultSet.getString("date"));
                vector.add(resultSet.getString("start_time"));
                vector.add(resultSet.getString("end_time"));
                vector.add(resultSet.getString("hallnumber"));
                vector.add(resultSet.getString("amount"));
                vector.add(resultSet.getString("courses.name"));
                vector.add(resultSet.getString("class_status.name"));

                model.addRow(vector);

                jLabel4.setText(resultSet.getString("tutor.first_name") + " " + (resultSet.getString("tutor.last_name")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCourses() {

        try {
            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `courses`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                courseMap.put(resultSet.getString("name"), resultSet.getString("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cancelBtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel1.setText("Tutor Class List");

        cancelBtn.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        cancelBtn.setText("Cancel Session");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton1.setText("Request New Session");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setText("Tutor Name :");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Tutor Info");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Class ID", "Class Name", "Date", "Start Time", "End Time", "Hall Number", "Amount", "Course Name", "Class Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Sort By Date");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setText("TO");

        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton2.setText("Sort");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel6.setText("Search");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel7.setText("Select a row to cancel the session");

        jLabel8.setText("Reason for cancelling");

        jLabel10.setText("Start Time :");

        jLabel11.setText("End Time :");

        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(324, 324, 324)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel1)
                                .addGap(51, 51, 51)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel11)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        RequestSession rs = new RequestSession(parent, tutorId);
        parent.switchPanel(rs);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        // Check if the text field (jTextField2) is empty
        if (jTextField2.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide a reason for requesting session cancellation.");
            return; // Exit the method if the text field is empty
        }

        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            String classId = (String) jTable1.getValueAt(selectedRow, 0); // Class ID is in column 0
            String className = (String) jTable1.getValueAt(selectedRow, 1); // Class Name is in column 1
            String date = (String) jTable1.getValueAt(selectedRow, 2); // Date is in column 2
            String startTime = (String) jTable1.getValueAt(selectedRow, 3); // Start Time is in column 3
            String endTime = (String) jTable1.getValueAt(selectedRow, 4); // End Time is in column 4
            String hallNumber = (String) jTable1.getValueAt(selectedRow, 5); // Hall Number is in column 5
            String course = (String) jTable1.getValueAt(selectedRow, 7); // Hall Number is in column 5
            String reason = jTextField2.getText().trim(); // Reason provided in text field
            int tutorId = this.tutorId; // Replace with the actual tutor's ID from your application
            // Replace if the Course ID needs to be dynamically set

            try {
                // Insert cancellation request into the `request_sessions` table
                String query = "INSERT INTO request_sessions (title, date, start_time, end_time, hallnumber, tutor_id, approve_status, reason, courses_id, type) "
                        + "VALUES ('" + className + "', '" + date + "', '" + startTime + "', '" + endTime + "', '" + hallNumber + "', '" + tutorId + "', 'Pending', '" + reason + "', " + courseMap.get(course) + ", 'Cancel')";
                MySQL2.executeIUD(query);

                // Reload table data
                loadTable();
                JOptionPane.showMessageDialog(this, "Cancellation request sent to admin successfully!");

                // Disable button and clear text field after action
                cancelBtn.setEnabled(false);
                jTextField2.setText("");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while sending cancellation request.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a session to cancel.");
        }

    }//GEN-LAST:event_cancelBtnActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
//        int row = jTable1.getSelectedRow(); // Selected row
//        int col = jTable1.getSelectedColumn(); // Selected column
//
//        // Validate indices
//        if (row >= 0 && col >= 0 && col < jTable1.getColumnCount()) {
//            Object value = jTable1.getValueAt(row, col);
//            System.out.println("Value: " + value);
//        } else {
//            System.out.println("Invalid row/column selected.");
//        }
//
//        String ClassID = String.valueOf(jTable1.getValueAt(row, 0));
//        String ClassName = String.valueOf(jTable1.getValueAt(row, 1));
//        String Date = String.valueOf(jTable1.getValueAt(row, 2));
//        String StartTime = String.valueOf(jTable1.getValueAt(row, 3));
//        String EndTime = String.valueOf(jTable1.getValueAt(row, 4));
//        String HallNumber = String.valueOf(jTable1.getValueAt(row, 5));
//        String Price = String.valueOf(jTable1.getValueAt(row, 6));
//        String TutorName = jLabel4.getText();
//        String CourseName = String.valueOf(jTable1.getValueAt(row, 7));
//        String ClassStatus = String.valueOf(jTable1.getValueAt(row, 8));

//        if (evt.getClickCount() == 2) {
//            //            switchToRegistration();
//            if (addSession == null) {
//                addSession = new AddSession();
//            }
//
//            addSession.getjTextField1().setText(ClassID);
//            addSession.getjTextField2().setText(ClassName);
//
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//
//            try {
//                Date date = formatter.parse((Date));
//                addSession.getjDateChooser1().setDate(date);
//                System.out.println("Converted Date: " + date);
//            } catch (Exception e) {
//                System.out.println("Error converting Object to Date: " + e.getMessage());
//            }
//
//            addSession.getjFormattedTextField1().setText(StartTime);
//            addSession.getjFormattedTextField2().setText(EndTime);
//            addSession.getjTextField3().setText(HallNumber);
//            addSession.getjFormattedTextField3().setText(Price);
//            addSession.getjComboBox1().setSelectedItem(TutorName);
//            addSession.getjComboBox2().setSelectedItem(CourseName);
//            addSession.getjComboBox3().setSelectedItem(ClassStatus);
//            addSession.getjTextField1().setEditable(false);
//
//            switchToAddSession();
//        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        loadTable();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        loadTable();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        loadTable();
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        loadTable();
    }//GEN-LAST:event_jTextField4KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    // End of variables declaration//GEN-END:variables
}
