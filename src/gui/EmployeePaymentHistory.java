/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;

/**
 *
 * @author Rushma
 */
public class EmployeePaymentHistory extends javax.swing.JPanel {

    /**
     * Creates new form EmployeePaymentHistory
     */
    public EmployeePaymentHistory() {
        initComponents();
        loadEmployeePaymentHistory();
        addSearchFunctionality();

        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = jTable1.getSelectedRow();
                if (row != -1) {
                    // Get the NIC or ID of the clicked row
                    String employeeNIC = (String) jTable1.getValueAt(row, 0); // Assuming NIC is in the first column

                    // Open EmployeeDetails with the NIC or other ID
                    openEmployeeDetails(employeeNIC);
                }
            }
        });// Load all employee payments
    }

    public void loadEmployeePaymentHistory() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear the table

        try {
            String query = "SELECT e.nic AS employee_nic, "
                    + "CONCAT(e.first_name, ' ', e.last_name) AS employee_name, "
                    + "eph.payment_date, "
                    + "eph.amount "
                    + "FROM employee_payment_history eph "
                    + "JOIN employee e ON eph.employee_id = e.id";

            ResultSet rs = MySQL2.executeSearch(query);

            while (rs.next()) {
                String nic = rs.getString("employee_nic");
                String name = rs.getString("employee_name");
                String paymentDate = rs.getString("payment_date");
                double amount = rs.getDouble("amount");

                // Add a row to the table
                model.addRow(new Object[]{nic, name, paymentDate, amount});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSearchFunctionality() {
        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }
        });
    }

    private void filterTable() {
        String searchText = jTextField1.getText().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        model.setRowCount(0); // Clear the table

        try {
            String query = "SELECT e.nic AS employee_nic, "
                    + "CONCAT(e.first_name, ' ', e.last_name) AS employee_name, "
                    + "eph.payment_date, "
                    + "eph.amount "
                    + "FROM employee_payment_history eph "
                    + "JOIN employee e ON eph.employee_id = e.id "
                    + "WHERE LOWER(e.nic) LIKE '%" + searchText + "%' OR "
                    + "LOWER(e.first_name) LIKE '%" + searchText + "%' OR "
                    + "LOWER(e.last_name) LIKE '%" + searchText + "%'";

            ResultSet rs = MySQL2.executeSearch(query);

            while (rs.next()) {
                String nic = rs.getString("employee_nic");
                String name = rs.getString("employee_name");
                String paymentDate = rs.getString("payment_date");
                double amount = rs.getDouble("amount");

                // Add a row to the table
                model.addRow(new Object[]{nic, name, paymentDate, amount});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openEmployeeDetails(String employeeID) {
        // Open the EmployeeDetails dialog or frame
        // Assuming you have a method to open EmployeeDetails by NIC (employeeNIC)
        int nic = Integer.parseInt(employeeID);
        try {
            // Replace this with your actual method to show EmployeeDetails
            EmployeeDetails employeeDetails = new EmployeeDetails(nic);
            employeeDetails.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error opening employee details.");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel1.setText("Employee Payments History");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee NIC", "Employee Name", "Payment date", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Search");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(259, 259, 259)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 863, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
