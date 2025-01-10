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

            // Base query
            String query = "SELECT * FROM class "
                    + "INNER JOIN tutor ON class.tutor_id = tutor.id "
                    + "INNER JOIN courses ON class.courses_id = courses.id "
                    + "INNER JOIN class_status ON class.class_status_id = class_status.id "
                    + "WHERE `tutor_id` = '" + TID + "' ";

            // Search text filtering
            if (!searchText.isEmpty()) {
                query += "AND LOWER(`class`.`name`) LIKE '%" + searchText + "%' ";
            }

            Date start = null;
            Date end = null;

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            // Add date filtering if dates are selected
            boolean dateConditionAdded = false; // To track if WHERE or AND has been added already

            if (jDateChooser1.getDate() != null || jDateChooser2.getDate() != null) {
                if (!query.contains("WHERE")) {
                    query += "AND ";
                }

                if (jDateChooser1.getDate() != null && jDateChooser2.getDate() != null) {
                    start = jDateChooser1.getDate();
                    end = jDateChooser2.getDate();
                    query += "`class`.`date` BETWEEN '" + format.format(start) + "' AND '" + format.format(end) + "' ";
                    dateConditionAdded = true;
                } else if (jDateChooser1.getDate() != null && jDateChooser2.getDate() == null) {
                    start = jDateChooser1.getDate();
                    query += "`class`.`date` >= '" + format.format(start) + "' ";
                    dateConditionAdded = true;
                } else if (jDateChooser1.getDate() == null && jDateChooser2.getDate() != null) {
                    end = jDateChooser2.getDate();
                    query += "`class`.`date` <= '" + format.format(end) + "' ";
                    dateConditionAdded = true;
                }
            }

            // If there was no date filter and only the search text is used, ensure "WHERE" is present
            if (!dateConditionAdded && searchText.isEmpty()) {
                query = query.replace("WHERE", "WHERE 1 = 1 AND");
            }

            // Execute query and populate table
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
        jLabel6.setText("Search by class name :");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel7.setText("Select a row to cancel the session");

        jLabel8.setText("Reason for cancelling");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField2))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(jLabel5)
                            .addGap(9, 9, 9))))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(5, 5, 5)
                .addComponent(jLabel7)
                .addContainerGap())
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
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
    // End of variables declaration//GEN-END:variables
}
