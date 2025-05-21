/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rushma
 */
public class InvoiceReport extends javax.swing.JPanel {
    private static final Logger logger = LogManager.getLogger(InvoiceReport.class);
    
    
    private DashboardInterface parent;
    
    private II invoiceItem;
    private String id;
    
    private StudentInvoiceHistory invoiceReport;
    public InvoiceReport() {
        initComponents();
        this.invoiceReport = new StudentInvoiceHistory(parent);
        loadStudentInvoicesHistory(); 
        addTableMouseListener();
        addSearchFunctionality();
    }

    public void loadStudentInvoicesHistory() {
        
        String sort = String.valueOf(jComboBox1.getSelectedItem());
        
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
                    + "JOIN payment_method pm ON i.payment_method_id = pm.id ";
            
            if (sort.equals("Invoice ID ASC")) {
                query += "ORDER BY `invoice_id` ASC";
            } else if (sort.equals("Invoice ID DESC")) {
                query += "ORDER BY `invoice_id` DESC";
            } else if (sort.equals("Student NIC ASC")) {
                query += "ORDER BY `student_nic` ASC";
            } else if (sort.equals("Student NIC DESC")) {
                query += "ORDER BY `student_nic` DESC";
            } else if (sort.equals("Cash")) {
                query += "WHERE `pm`.`name` = 'Cash'";
            } else if (sort.equals("Card")) {
                query += "WHERE `pm`.`name` = 'Card'";
            }

            System.out.println(query);
            ResultSet rs = MySQL2.executeSearch(query);

            while (rs.next()) {
                Vector<String> vector = new Vector <>();
                vector.add(rs.getString("invoice_id"));
                vector.add(rs.getString("student_nic"));
                vector.add(rs.getString("student_name"));
                vector.add(rs.getString("payment_date"));
                vector.add(rs.getString("amount"));
                vector.add(rs.getString("payment_method"));

                // Add a row to the table
                model.addRow(vector);
            }
        } catch (Exception e) {
            logger.error("Exception caught", e);
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
        StudentDetails detailsFrame = new StudentDetails((Frame) parent, true, nic); // Pass NIC to the frame
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
        String sort = String.valueOf(jComboBox1.getSelectedItem());
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
                    + "LOWER(CONCAT(s.first_name, ' ', s.last_name)) LIKE '%" + searchText + "%' ";
            
            if (sort.equals("Invoice ID ASC")) {
                query += "ORDER BY `invoice_id` ASC";
            } else if (sort.equals("Invoice ID DESC")) {
                query += "ORDER BY `invoice_id` DESC";
            } else if (sort.equals("Student NIC ASC")) {
                query += "ORDER BY `student_nic` ASC";
            } else if (sort.equals("Student NIC DESC")) {
                query += "ORDER BY `student_nic` DESC";
            } else if (sort.equals("Cash")) {
                query += "AND `pm`.`name` = 'Cash'";
            } else if (sort.equals("Card")) {
                query += "AND `pm`.`name` = 'Card'";
            }

            System.out.println(query);
            ResultSet rs = MySQL2.executeSearch(query);

            while (rs.next()) {
                Vector<String> vector = new Vector <>();
                vector.add(rs.getString("invoice_id"));
                vector.add(rs.getString("student_nic"));
                vector.add(rs.getString("student_name"));
                vector.add(rs.getString("payment_date"));
                vector.add(rs.getString("amount"));
                vector.add(rs.getString("payment_method"));

                // Add a row to the table
                model.addRow(vector);
            }
        } catch (Exception e) {
            logger.error("Exception caught", e);
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
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("Invoice Report");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Search by Name or NIC");

        jLabel3.setText("Double click a row to view student details");

        jButton1.setBackground(new java.awt.Color(78, 74, 207));
        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(78, 74, 207));
        jButton3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Invoice Item");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setText("Sort By :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Invoice ID ASC", "Invoice ID DESC", "Student NIC ASC", "Student NIC DESC", "Cash", "Card" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
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
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 968, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        jTextField1.getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //view or print report
        String path = "src//reports//StudentInvoiceReport.jasper";
        
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
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        InvoiceItems invoiceItems = new InvoiceItems(parent, true, id);
        invoiceItems.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 1) {
  
            int selectedRow = jTable1.getSelectedRow();
            
            id = String.valueOf(jTable1.getValueAt(selectedRow, 0));
//            invoiceItem.setId(id);
            
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
//        if (From == null && To != null) {
//            loadInvoice(From,"");
//        } else if (From != null && To == null) {
//            loadInvoice("",To);
//        } else if (From != null && To != null) {
//            loadInvoice(From,To);
//        } else {
//            loadInvoice("","");
//        }
        loadStudentInvoicesHistory();
        filterTable();
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}