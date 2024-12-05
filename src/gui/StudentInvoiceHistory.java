/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;

/**
 *
 * @author Rushma
 */
public class StudentInvoiceHistory extends javax.swing.JPanel {

    /**
     * Creates new form StudentInvoiceHistory
     */
    public StudentInvoiceHistory() {
        initComponents();
        loadStudentInvoicesHistory(); 
        addTableMouseListener();
        addSearchFunctionality();
    }

    public void loadStudentInvoicesHistory() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear the table

        try {
            String query = "SELECT i.id AS invoice_id, "
                    + "i.student_nic, "
                    + "CONCAT(s.first_name, ' ', s.last_name) AS student_name, "
                    + "i.date AS payment_date, "
                    + "i.total AS amount, "
                    + "pm.name AS payment_method "
                    + "FROM invoice i "
                    + "JOIN student s ON i.student_nic = s.nic "
                    + "JOIN payment_method pm ON i.payment_method_id = pm.id";

            ResultSet rs = MySQL2.executeSearch(query);

            while (rs.next()) {
                long invoiceId = rs.getLong("invoice_id");
                String studentNic = rs.getString("student_nic");
                String studentName = rs.getString("student_name");
                String paymentDate = rs.getString("payment_date");
                double amount = rs.getDouble("amount");
                String paymentMethod = rs.getString("payment_method");

                // Add a row to the table
                model.addRow(new Object[]{invoiceId, studentNic, studentName, paymentDate, amount, paymentMethod});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTableMouseListener() {
        jTable1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Detect double-click
                    int selectedRow = jTable1.getSelectedRow();
                    if (selectedRow != -1) {
                        String studentNic = jTable1.getValueAt(selectedRow, 1).toString(); // Get Student NIC
                        openStudentDetailsFrame(studentNic);
                    }
                }
            }
        });
    }

    private void openStudentDetailsFrame(String nic) {
//        int nic = Integer.parseInt(studentNic);
        StudentDetails detailsFrame = new StudentDetails(nic); // Pass NIC to the frame
        detailsFrame.setVisible(true);
    }

    private void addSearchFunctionality() {
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
            String query = "SELECT i.id AS invoice_id, "
                    + "i.student_nic, "
                    + "CONCAT(s.first_name, ' ', s.last_name) AS student_name, "
                    + "i.date AS payment_date, "
                    + "i.total AS amount, "
                    + "pm.name AS payment_method "
                    + "FROM invoice i "
                    + "JOIN student s ON i.student_nic = s.nic "
                    + "JOIN payment_method pm ON i.payment_method_id = pm.id "
                    + "WHERE LOWER(i.student_nic) LIKE '%" + searchText + "%' OR "
                    + "LOWER(CONCAT(s.first_name, ' ', s.last_name)) LIKE '%" + searchText + "%'";

            ResultSet rs = MySQL2.executeSearch(query);

            while (rs.next()) {
                long invoiceId = rs.getLong("invoice_id");
                String studentNic = rs.getString("student_nic");
                String studentName = rs.getString("student_name");
                String paymentDate = rs.getString("payment_date");
                double amount = rs.getDouble("amount");
                String paymentMethod = rs.getString("payment_method");

                // Add a row to the table
                model.addRow(new Object[]{invoiceId, studentNic, studentName, paymentDate, amount, paymentMethod});
            }
        } catch (Exception e) {
            e.printStackTrace();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice ID", "Student NIC", "Student Name", "Payment date", "Amount", "Payment Method"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel1.setText("Students Invoices History");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Search");

        jLabel3.setText("Double click a row to view student details");

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
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 863, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addContainerGap(17, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
