package gui;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;

public class TutorClassList extends javax.swing.JPanel {

    private DashboardInterface parent;

    private String mobile;

    private int tutorId;

    public TutorClassList(DashboardInterface parent, int TutorID) {
        this.parent = parent;
        this.tutorId = TutorID;
//        this.addSession = new AddSession();
        initComponents();
        loadTable();

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        jTable1.setDefaultRenderer(Object.class, renderer);
        this.tutorId = TutorID;
    }

//    private void switchToAddSession() {
//        parent.switchPanel(addSession);
//    }
    private void loadTable() {

        String TID = String.valueOf(tutorId);

        try {
            String searchText = jTextField1.getText().toLowerCase();

            // String sort = String.valueOf(jComboBox1.getSelectedItem());
            String query = "SELECT * FROM class INNER JOIN tutor ON class.tutor_id = tutor.id "
                    + " INNER JOIN courses ON class.courses_id = courses.id "
                    + "INNER JOIN class_status ON class.class_status_id = class_status.id WHERE `tutor_id` = '" + TID + "' ";

//            if (sort.equals("Student Name ASC")) {
//                query += "ORDER BY `teacher`.`id` ASC";
//            } else if (sort.equals("Student Name DESC")) {
//                query += "ORDER BY `teacher`.`id` DESC";
//            } else if (sort.equals("Tutor Name ASC")) {
//                query += "ORDER BY `teacher`.`first_name` ASC";
//            } else if (sort.equals("Tutor Name DESC")) {
//                query += "ORDER BY `teacher`.`last_name` DESC";
//            }
//            else if (sort.equals("Subject ASC")) {
//                query += "ORDER BY `subject.name` ASC";
//            } else if (sort.equals("Subject DESC")) {
//                query += "ORDER BY `subject.name` DESC";
//            }
           if (!searchText.isEmpty()) {

//                if (query.contains("WHERE")) {
//
//                    query += "AND LOWER(`class`.`id`) LIKE '%" + searchText + "%' "
//                            + "OR LOWER(`class`.`name`) LIKE '%" + searchText + "%' ";
//
//                } else {
                query += "AND (LOWER(`class`.`name`) LIKE '%" + searchText + "%') ";

//                }
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
            }

            if (jDateChooser1.getDate() != null && jDateChooser2.getDate() != null) {
                start = jDateChooser1.getDate();
                end = jDateChooser2.getDate();

                query += "BETWEEN `class`.`date` > '" + format.format(start) + "' AND `class`.`date` < '" + format.format(end) + "' ";

            } else if (jDateChooser1.getDate() != null && jDateChooser2.getDate() == null) {
                start = jDateChooser1.getDate();

                query += "`class`.`date` >= '" + format.format(start) + "' ";

            } else if (jDateChooser1.getDate() == null && jDateChooser2.getDate() != null) {
                end = jDateChooser2.getDate();

                query += "`class`.`date` <= '" + format.format(end) + "' ";

            }

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
//                vector.add(resultSet.getString("tutor.first_name") + " " + resultSet.getString("tutor.last_name"));
                vector.add(resultSet.getString("courses.name"));
                vector.add(resultSet.getString("class_status.name"));

                model.addRow(vector);

                jLabel4.setText(resultSet.getString("tutor.first_name") + " " + (resultSet.getString("tutor.last_name")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
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

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel1.setText("Tutor Class List");

        jButton3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton3.setText("Cancel Session");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(241, 241, 241)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(190, 190, 190)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(210, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
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
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
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
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        RequestSession rs = new RequestSession(parent, tutorId);
        parent.switchPanel(rs);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        try {

            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this row?", "Message",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                MySQL2.executeIUD("DELETE FROM tutor WHERE tutor.id = '" + tutorId + "'");
            } else {
                JOptionPane.showMessageDialog(this, "Row not deleted", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
            loadTable();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }//GEN-LAST:event_jButton3ActionPerformed

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

        String ClassID = String.valueOf(jTable1.getValueAt(row, 0));
        String ClassName = String.valueOf(jTable1.getValueAt(row, 1));
        String Date = String.valueOf(jTable1.getValueAt(row, 2));
        String StartTime = String.valueOf(jTable1.getValueAt(row, 3));
        String EndTime = String.valueOf(jTable1.getValueAt(row, 4));
        String HallNumber = String.valueOf(jTable1.getValueAt(row, 5));
        String Price = String.valueOf(jTable1.getValueAt(row, 6));
        String TutorName = jLabel4.getText();
        String CourseName = String.valueOf(jTable1.getValueAt(row, 7));
        String ClassStatus = String.valueOf(jTable1.getValueAt(row, 8));

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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
