//author Kavishka
package gui;

import com.raven.datechooser.DateBetween;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAction;
import com.raven.datechooser.listener.DateChooserAdapter;
import java.awt.Color;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AllStudents extends javax.swing.JPanel {
    private static final Logger logger = LogManager.getLogger(AllStudents.class);

    private DashboardInterface parent;

    private StudentRegistration updateStudent;
    
    private DateChooser chDate = new DateChooser();

    private String From;
    private String To;

    public AllStudents(DashboardInterface parent) {
        this.parent = parent;
        initComponents();
        dateChooser();
        loadTable("","");
        this.updateStudent = new StudentRegistration(parent);
         DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        jTable1.setDefaultRenderer(Object.class, renderer);
    }

    private void switchToRegistration() {
        parent.switchPanel(updateStudent);
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
            
            String selectDateType = String.valueOf(jComboBox2.getSelectedItem());
            
            String searchText = jTextField1.getText().toLowerCase();

            String query = "SELECT `nic`, `first_name`, `last_name`, `dob`, `contact_info`, `registration_date`, `email`, `gender`.`name`, `intake`.`name` FROM `student` "
                    + "INNER JOIN `gender` ON `student`.`gender_id` = `gender`.`id`"
                    + "INNER JOIN `intake` ON `student`.`intake_id` = `intake`.`id`";
            
            if (!searchText.isEmpty()) {

                query += "WHERE (LOWER(`nic`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`first_name`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`last_name`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`dob`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`contact_info`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`registration_date`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`email`) LIKE '%" + searchText + "%') ";

            }
            
            if (selectDateType.equals("Date of Birth")) {
                if (from != null && !from.isEmpty() && to != null && !to.isEmpty()) {
                    if (query.contains("WHERE")) {
                        query += "AND `dob` BETWEEN '" + from + "' AND '" + to + "' ";
                    } else {
                        query += "WHERE `dob` BETWEEN '" + from + "' AND '" + to + "' ";
                    }
                }
            } else if (selectDateType.equals("Date of Registration")) {
                if (from != null && !from.isEmpty() && to != null && !to.isEmpty()) {
                    if (query.contains("WHERE")) {
                        query += "AND `registration_date` BETWEEN '" + from + "' AND '" + to + "' ";
                    } else {
                        query += "WHERE `registration_date` BETWEEN '" + from + "' AND '" + to + "' ";
                    }
                }
            }

            if (sort.equals("First Name ASC")) {
                query += "ORDER BY `student`.`first_name` ASC";
            } else if (sort.equals("First Name DESC")) {
                query += "ORDER BY `student`.`first_name` DESC";
            } else if (sort.equals("Last Name ASC")) {
                query += "ORDER BY `student`.`last_name` ASC";
            } else if (sort.equals("Last Name DESC")) {
                query += "ORDER BY `student`.`last_name` DESC";
            } else if (sort.equals("NIC ASC")) {
                query += "ORDER BY `nic` ASC";
            } else if (sort.equals("NIC DESC")) {
                query += "ORDER BY `nic` DESC";
            }

            ResultSet resultSet = MySQL2.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("NIC"));
                vector.add(resultSet.getString("first_name"));
                vector.add(resultSet.getString("last_name"));
                vector.add(resultSet.getString("dob"));
                vector.add(resultSet.getString("contact_info"));
                vector.add(resultSet.getString("registration_date"));
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("gender.name"));
                vector.add(resultSet.getString("intake.name"));

                model.addRow(vector);
            }

        } catch (Exception e) {
            logger.error("Exception caught", e);
        }
    }
    
    private void reset() {
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jTextField1.setText("");
        jTextField2.setText("");
        From = "";
        To = "";
        loadTable("","");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("All Students");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "NIC", "First Name", "Last Name", "Date of Birth", "Mobile", "Registration Date", "Email", "Gender", "Intake"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
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

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Sort By :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First Name ASC", "First Name DESC", "Last Name ASC", "Last Name DESC", "NIC ASC", "NIC DESC" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(78, 74, 207));
        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Clear All");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setText("Search");

        jTextField1.setText("Search");
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setText("Sort By ");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Date of Birth", "Date of Registration" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setText(":");

        jButton2.setBackground(new java.awt.Color(78, 74, 207));
        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Print");
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)))
                        .addGap(6, 6, 6))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel3)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
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

        String NIC = String.valueOf(jTable1.getValueAt(row, 0));
        String Firstname = String.valueOf(jTable1.getValueAt(row, 1));
        String LastName = String.valueOf(jTable1.getValueAt(row, 2));
        String DOB = String.valueOf(jTable1.getValueAt(row, 3));
        String Mobile = String.valueOf(jTable1.getValueAt(row, 4));
        String RegisteredDate = String.valueOf(jTable1.getValueAt(row, 5));
        String Email = String.valueOf(jTable1.getValueAt(row, 6));
        String Gender = String.valueOf(jTable1.getValueAt(row, 7));
        String Intake = String.valueOf(jTable1.getValueAt(row, 8));

        if (evt.getClickCount() == 2) {
//            switchToRegistration();
            if (updateStudent == null) {
//                updateStudent = new StudentRegistration();
            }

//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//
//            try {
//                Date dateofBirth = formatter.parse((DOB));
//                updateStudent.getjDateChooser1().setDate(dateofBirth);
//                System.out.println("Converted Date: " + dateofBirth);
//            } catch (Exception e) {
//                System.out.println("Error converting Object to Date: " + e.getMessage());
//            }

            updateStudent.getjButton1().setEnabled(false);
            updateStudent.getjButton2().setEnabled(true);
            updateStudent.getjButton3().setEnabled(true);
            updateStudent.getjButton4().setEnabled(true);
            updateStudent.loadIntake();
            updateStudent.getjTextField1().setText(Firstname);
            updateStudent.getjTextField2().setText(LastName);
            updateStudent.getjTextField3().setText(Mobile);
            updateStudent.getjTextField4().setText(Email);
            updateStudent.getjComboBox1().setSelectedItem(Gender);
            updateStudent.getjTextField5().setText(NIC);
            updateStudent.getjTextField5().setEnabled(false);
            updateStudent.getjTextField6().setText(DOB);
            updateStudent.getjComboBox2().setSelectedItem(Intake);
            
            //            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//
//            try {
//                Date dateofBirth = formatter.parse((DOB));
//                updateStudent.getjDateChooser1().setDate(dateofBirth);
//                System.out.println("Converted Date: " + dateofBirth);
//            } catch (Exception e) {
//                System.out.println("Error converting Object to Date: " + e.getMessage());
//            }

            if (parent != null) {
                switchToRegistration();
            } else {
                System.out.println("Null");
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

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

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        if (From != null && To == null) {
            loadTable(From, "");
        } else if (From == null && To != null) {
            loadTable("", To);
        } else if (From != null && To != null) {
            loadTable(From, To);
        } else {
            loadTable("", "");
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        reset();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //view or print report
        String path = "src//reports//AllStudents.jasper";

        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        HashMap<String, Object> params = new HashMap<>();
        params.put("Parameter1", dateTime);

        JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());

        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(path, params, dataSource);
        } catch (JRException e) {
            logger.error("Exception caught", e);
        }

        JasperViewer.viewReport(jasperPrint, false);
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
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