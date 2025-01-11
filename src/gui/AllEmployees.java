//author KAVISHKA
package gui;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class AllEmployees extends javax.swing.JPanel {
    
    private DashboardInterface parent;
    
    private static HashMap<String, String> passwordMap = new HashMap<>();

    public EmployeeRegistration updateEmployee;

    public AllEmployees(DashboardInterface parent) {
        this.parent = parent;
        initComponents();
        loadTable();
        this.updateEmployee = new EmployeeRegistration((AdminDashboard) parent);
    }

    private void switchToRegistration() {
        parent.switchPanel(updateEmployee);
    }

    private void loadTable() {

        try {

            String sort = String.valueOf(jComboBox1.getSelectedItem());

            String searchText = jTextField1.getText().toLowerCase();

            String query = "SELECT `employee`.`id`, `first_name`, `last_name`, `contact_info`, `roles`.`name`, `gender`.`name`, `password`, `nic`, `email` FROM `employee` "
                    + "INNER JOIN `gender` ON `employee`.`gender_id` = `gender`.`id` "
                    + "INNER JOIN `roles` ON `employee`.`roles_id` = `roles`.`id` ";

            if (!searchText.isEmpty()) {

                query += "WHERE (LOWER(`employee`.`id`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`first_name`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`last_name`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`contact_info`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`email`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`roles`.`name`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`nic`) LIKE '%" + searchText + "%') ";

            }

            if (sort.equals("First Name ASC")) {
                query += "ORDER BY `first_name` ASC";
            } else if (sort.equals("First Name DESC")) {
                query += "ORDER BY `first_name` DESC";
            } else if (sort.equals("Last Name ASC")) {
                query += "ORDER BY `last_name` ASC";
            } else if (sort.equals("Last Name DESC")) {
                query += "ORDER BY `last_name` DESC";
            } else if (sort.equals("ID ASC")) {
                query += "ORDER BY `employee`.`id` ASC";
            } else if (sort.equals("ID DESC")) {
                query += "ORDER BY `employee`.`id` DESC";
            }

            ResultSet resultSet = MySQL2.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("employee.id"));
                vector.add(resultSet.getString("first_name"));
                vector.add(resultSet.getString("last_name"));
                vector.add(resultSet.getString("contact_info"));
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("roles.name"));
                vector.add(resultSet.getString("gender.name"));
                vector.add(resultSet.getString("nic"));
                passwordMap.put(resultSet.getString("password"), resultSet.getString("employee.id"));


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
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("All Employees");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Employee ID", "First Name", "Last Name", "Mobile", "Email", "Role", "Gender", "NIC"
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

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Sort By :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First Name ASC", "First Name DESC", "Last Name ASC", "Last Name DESC", "ID ASC", "ID DESC" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setText("Search");

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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 228, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(338, 338, 338)))
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

        String EmployeeID = String.valueOf(jTable1.getValueAt(row, 0));
        String Firstname = String.valueOf(jTable1.getValueAt(row, 1));
        String LastName = String.valueOf(jTable1.getValueAt(row, 2));
        String Mobile = String.valueOf(jTable1.getValueAt(row, 3));
        String Email = String.valueOf(jTable1.getValueAt(row, 4));
        String Role = String.valueOf(jTable1.getValueAt(row, 5));
        String Gender = String.valueOf(jTable1.getValueAt(row, 6));
        String NIC = String.valueOf(jTable1.getValueAt(row, 7));

        if (evt.getClickCount() == 2) {

            updateEmployee.getjTextField1().setText(Firstname);
            updateEmployee.getjTextField2().setText(LastName);
            updateEmployee.getjTextField3().setText(Mobile);
            updateEmployee.getjTextField4().setText(NIC);
            updateEmployee.getjComboBox1().setSelectedItem(Gender);
            updateEmployee.getjComboBox2().setSelectedItem(Role);
            String password = passwordMap.get(EmployeeID);
            System.out.println("dsa" + password);
            if (password != null && !password.isEmpty()) {
                updateEmployee.getjPasswordField1().setText(password);
            } else {
                updateEmployee.getjPasswordField1().setText(""); // Clear if no password is set
            }
            updateEmployee.getjPasswordField1().setVisible(false);
            updateEmployee.getjLabel8().setVisible(false);
            updateEmployee.getjTextField5().setText(Email);

            updateEmployee.getjLabel8().setVisible(false);
            updateEmployee.getjButton1().setEnabled(false);
            updateEmployee.getjButton2().setEnabled(true);
            updateEmployee.getjButton3().setEnabled(true);
            updateEmployee.getjButton4().setEnabled(true);

            switchToRegistration();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        loadTable();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        loadTable();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //view or print report
        String path = "src//reports//AllEmployees.jasper";
        
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
