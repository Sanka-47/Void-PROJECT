//author KAVISHKA
package gui;

import model.MySQL2;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class AllTutors extends javax.swing.JPanel {

    private static HashMap<String, String> passwordMap = new HashMap<>();

    private DashboardInterface parent;

//    public void setAdminDashboard(AdminDashboard ad) {
//        this.parent = ad;
//    }
//    
//    private EmployeeDashboard eparent;
//    
//    public void setEmployeeDashboard(EmployeeDashboard ed) {
//        this.eparent = ed;
//    }
    private AddTutor updateTutor;

    public AllTutors(DashboardInterface parent) {
        this.parent = parent;
        initComponents();
        loadTable();
        this.updateTutor = new AddTutor(parent);
    }

    private void switchToRegistrationAD() {
        parent.switchPanel(updateTutor);
    }

//    private void switchToRegistrationED() {
//        eparent.switchPanel(updateTutor);
//    }
    private void loadTable() {
        try {
            
            String sort = String.valueOf(jComboBox1.getSelectedItem());
            
            String searchText = jTextField1.getText().toLowerCase();
            
            String query = "SELECT `tutor`.`id`, `first_name`, `last_name`, `qualification`, `contact_info`, "
                    + "`gender`.`name`, `courses`.`name`, `password`, `nic`, `email` FROM `tutor` "
                    + "INNER JOIN `gender` ON `tutor`.`gender_id` = `gender`.`id` "
                    + "INNER JOIN `courses` ON `tutor`.`courses_id` = `courses`.`id`";

            if (!searchText.isEmpty()) {

                query += "WHERE (LOWER(`tutor`.`id`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`first_name`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`last_name`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`qualification`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`contact_info`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`courses`.`name`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`nic`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`email`) LIKE '%" + searchText + "%') ";

            }
            
            if (sort.equals("First Name ASC")) {
                query += " ORDER BY `tutor`.`first_name` ASC";
            } else if (sort.equals("First Name DESC")) {
                query += " ORDER BY `tutor`.`first_name` DESC";
            } else if (sort.equals("Last Name ASC")) {
                query += " ORDER BY `tutor`.`last_name` ASC";
            } else if (sort.equals("Last Name DESC")) {
                query += " ORDER BY `tutor`.`last_name` DESC";
            } else if (sort.equals("ID ASC")) {
                query += " ORDER BY `tutor`.`id` ASC";
            } else if (sort.equals("ID DESC")) {
                query += " ORDER BY `tutor`.`id` DESC";
            }
            
            ResultSet resultSet = MySQL2.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("tutor.id"));
                vector.add(resultSet.getString("first_name"));
                vector.add(resultSet.getString("last_name"));
                vector.add(resultSet.getString("qualification"));
                vector.add(resultSet.getString("contact_info"));
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("gender.name"));
                vector.add(resultSet.getString("courses.name"));
                vector.add(resultSet.getString("nic"));
                passwordMap.put(resultSet.getString("password"), resultSet.getString("tutor.id"));

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
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("All Tutors");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Tutor ID", "First Name", "Last Name", "Qualification", "Mobile", "Email", "Gender", "Course", "NIC"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First Name ASC", "First Name DESC", "Last Name ASC", "Last Name DESC", "ID ASC", "ID DESC" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setText("Search ");

        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 426, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        loadTable();
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

        String TutorID = String.valueOf(jTable1.getValueAt(row, 0));
        String FirstName = String.valueOf(jTable1.getValueAt(row, 1));
        String LastName = String.valueOf(jTable1.getValueAt(row, 2));
        String Qualification = String.valueOf(jTable1.getValueAt(row, 3));
        String Mobile = String.valueOf(jTable1.getValueAt(row, 4));
        String Email = String.valueOf(jTable1.getValueAt(row, 5));
        String Gender = String.valueOf(jTable1.getValueAt(row, 6));
        String Course = String.valueOf(jTable1.getValueAt(row, 7));
        String NIC = String.valueOf(jTable1.getValueAt(row, 8));

        if (evt.getClickCount() == 2) {
            // Populate fields
            updateTutor.getjTextField1().setText(FirstName);
            updateTutor.getjTextField2().setText(LastName);
            updateTutor.getjTextField3().setText(NIC);
            updateTutor.getjTextField4().setText(Qualification);
            updateTutor.getjComboBox1().setSelectedItem(Course);
            updateTutor.getjComboBox2().setSelectedItem(Gender);
            updateTutor.getjTextField5().setText(Mobile);
            updateTutor.getjTextField6().setText(Email);

            // Optional password field (can remain empty)
            String password = passwordMap.get(TutorID);
            if (password != null && !password.isEmpty()) {
                updateTutor.getjPasswordField1().setText(password);
            } else {
                updateTutor.getjPasswordField1().setText(""); // Clear if no password is set
            }
            updateTutor.getjPasswordField1().setVisible(false);
            updateTutor.getjLabel5().setVisible(false);// Hide if not required

            // Disable buttons/fields as needed
            updateTutor.getjButton1().setEnabled(false);
            updateTutor.getjButton2().setEnabled(true);
            updateTutor.getjTextField3().setEnabled(false);
            updateTutor.getjButton3().setEnabled(true);
            updateTutor.getjButton4().setEnabled(true);

            if (parent != null) {
                switchToRegistrationAD();
            } else {
                System.out.println("Null");
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        loadTable();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String path = "src//reports//AllTutors.jasper";
        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        HashMap<String, Object> params = new HashMap<>();
        params.put("Parameter1", dateTime);
        JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(path, params, dataSource);
        } catch (JRException e) {
            e.printStackTrace();
        }
        JasperViewer.viewReport(jasperPrint, false);

    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
