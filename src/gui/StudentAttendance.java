//author Kavisha
package gui;

import com.raven.datechooser.DateBetween;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAction;
import com.raven.datechooser.listener.DateChooserAdapter;
import java.awt.Color;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;

public class StudentAttendance extends javax.swing.JPanel {

    private TutorDashboard parent;

    private DateChooser chDate = new DateChooser();

    private String From;
    private String To;
    private int tutorID;
    
    // Initialize the original table model to store data for reset.
    private DefaultTableModel originalTableModel;

    public StudentAttendance(int tutorID) {
        initComponents();
        this.tutorID = tutorID;
        dateChooser();
        loadTable("", "");

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        jTable1.setDefaultRenderer(Object.class, renderer);
        // Store the original table model
        originalTableModel = (DefaultTableModel) jTable1.getModel();

    }

    private void dateChooser() {
        chDate.setTextField(jTextField2);
        chDate.setDateSelectionMode(DateChooser.DateSelectionMode.BETWEEN_DATE_SELECTED);
        chDate.setLabelCurrentDayVisible(false);
        chDate.setForeground(Color.black);
        chDate.setBackground(Color.white);
        chDate.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        chDate.addActionDateChooserListener(new DateChooserAdapter() {
            @Override
            public void dateBetweenChanged(DateBetween date, DateChooserAction action) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                From = df.format(date.getFromDate());
                To = df.format(date.getToDate());
                loadTable(From, To);
            }
        });
    }

    private void loadTable(String from, String to) {
        try {
            String sort = String.valueOf(jComboBox1.getSelectedItem());
            String searchText = jTextField1.getText().toLowerCase();

            String query = "SELECT `attendance`.`id`, `attendance`.`date`, `student`.`first_name`, `student`.`last_name`, `class`.`name`, `attendance_status`.`status` "
                    + "FROM attendance "
                    + "INNER JOIN student ON attendance.student_nic = student.nic "
                    + "INNER JOIN class ON attendance.class_id = class.id "
                    + "INNER JOIN `attendance_status` ON `attendance`.`attendance_status_id` = `attendance_status`.`id` WHERE `class`.`tutor_id` = '" + tutorID + "' ";

            if (!searchText.isEmpty()) {
                query += "AND (LOWER(attendance.id) LIKE '%" + searchText + "%' "
                        + "OR LOWER(student.first_name) LIKE '%" + searchText + "%' "
                        + "OR LOWER(student.last_name) LIKE '%" + searchText + "%') ";
            }

            if (from != null && !from.isEmpty() && to != null && !to.isEmpty()) {
                if (query.contains("WHERE")) {
                    query += "AND attendance.date BETWEEN '" + from + "' AND '" + to + "' ";
                } else {
                    query += "WHERE attendance.date BETWEEN '" + from + "' AND '" + to + "' ";
                }
            }

            if (sort.equals("Class Name ASC")) {
                query += "ORDER BY class.name ASC ";
            } else if (sort.equals("Class Name DESC")) {
                query += "ORDER BY class.name DESC ";
            } else if (sort.equals("Status ASC")) {
                query += "ORDER BY `attendance_status`.`status` ASC ";
            } else if (sort.equals("Status DESC")) {
                query += "ORDER BY `attendance_status`.`status` DESC ";
            }

            ResultSet resultSet = MySQL2.executeSearch(query);
            System.out.println(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("attendance.id"));
                vector.add(resultSet.getString("date"));
                vector.add(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                vector.add(resultSet.getString("name"));
                vector.add(resultSet.getString("attendance_status.status"));

                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel1.setText("Student Attendance");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Attendance ID", "Date", "Student Name", "Class Name", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton3.setText("Clear All");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Double click relevant row to 'Update' the row");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Class Name ASC", "Class Name DESC", "Status ASC", "Status DESC", " " }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setText("Sort By :");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setText("Search");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setText("Sort By Date");

        jButton2.setText("Clear All");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addGap(20, 20, 20)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );
    }// </editor-fold>//GEN-END:initComponents

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
        String Date = String.valueOf(jTable1.getValueAt(row, 1));
        String StudentName = String.valueOf(jTable1.getValueAt(row, 2));
        String ClassName = String.valueOf(jTable1.getValueAt(row, 3));
        String Status = String.valueOf(jTable1.getValueAt(row, 4));
//        String Email = String.valueOf(jTable1.getValueAt(row, 6));
//        String Gender = String.valueOf(jTable1.getValueAt(row, 7));

        if (evt.getClickCount() == 2) {

            AddStudentAttendanceJDialog ASA = new AddStudentAttendanceJDialog(parent, true, tutorID);

            ASA.setID(Id);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date dateofBirth = formatter.parse((Date));
                ASA.getjDateChooser1().setDate(dateofBirth);
                ASA.getjDateChooser1().setEnabled(false);
                System.out.println("Converted Date: " + dateofBirth);
            } catch (Exception e) {
                System.out.println("Error converting Object to Date: " + e.getMessage());
            }

            ASA.getjComboBox1().setSelectedItem(Status);
            ASA.getjComboBox2().setSelectedItem(StudentName);
            ASA.getjComboBox2().setEnabled(false);
            ASA.getjComboBox3().setSelectedItem(ClassName);
            ASA.getjComboBox3().setEnabled(false);

            ASA.getjButton1().setEnabled(false);
            ASA.getjButton2().setEnabled(true);

            ASA.setVisible(true);
            loadTable("","");
//            switchToRegistration();
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

//        ASA addStudentAttendance = new ASA();
//        addStudentAttendance.setVisible(true);
        AddStudentAttendanceJDialog ASA = new AddStudentAttendanceJDialog(parent, true, tutorID);
        ASA.setVisible(true);
        loadTable("", "");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (From != null && To == null) {
            loadTable(From, "");
        } else if (From == null && To != null) {
            loadTable("", To);
        } else if (From != null && To != null) {
            loadTable(From, To);
        } else {
            loadTable("", "");
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if (From != null && To == null) {
            loadTable(From, "");
        } else if (From == null && To != null) {
            loadTable("", To);
        } else if (From != null && To != null) {
            loadTable(From, To);
        } else {
            loadTable("", "");
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        jComboBox1.setSelectedIndex(0);
        jTextField1.setText("");
        jTextField2.setText("");
        loadTable("", "");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        // Reset the table to its original state
        jTable1.setModel(originalTableModel);
        jComboBox1.setSelectedIndex(0);
        jTextField1.setText("");
        jTextField2.setText("");

    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
