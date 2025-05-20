/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Rushma
 */
public class AllTutorsWithdrawHistory extends javax.swing.JPanel {

    /**
     * Creates new form AllTutorsWithdrawHistory
     */
    public AllTutorsWithdrawHistory() {
        initComponents();
        loadAllTutorsWithdrawHistory();
        addSearchFunctionality();
        addTableClickListener();
//        

    }

    public void loadAllTutorsWithdrawHistory() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear the table

        try {
            String query = "SELECT t.id AS tutor_id, "
                    + "CONCAT(t.first_name, ' ', t.last_name) AS tutor_name, "
                    + "th.withdraw_date AS withdrawal_date, "
                    + "th.withdrawal_amount AS amount "
                    + "FROM tutor_withdraw_history th "
                    + "JOIN tutor t ON th.tutor_id = t.id";

            ResultSet rs = MySQL2.executeSearch(query);

            while (rs.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(rs.getString("tutor_id"));
                vector.add(rs.getString("tutor_name"));
                vector.add(rs.getString("withdrawal_date"));
                vector.add(rs.getString("amount"));

                model.addRow(vector);
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
            String query = "SELECT t.id AS tutor_id, "
                    + "CONCAT(t.first_name, ' ', t.last_name) AS tutor_name, "
                    + "th.withdraw_date AS payment_date, "
                    + "th.withdrawal_amount AS amount "
                    + "FROM tutor_withdraw_history th "
                    + "JOIN tutor t ON th.tutor_id = t.id "
                    + "WHERE LOWER(CONCAT(t.first_name, ' ', t.last_name)) LIKE '%" + searchText + "%' OR "
                    + "LOWER(t.id) LIKE '%" + searchText + "%'";

            ResultSet rs = MySQL2.executeSearch(query);

            while (rs.next()) {
                int id = rs.getInt("tutor_id");
                String name = rs.getString("tutor_name");
                String date = rs.getString("payment_date");
                double amount = rs.getDouble("amount");

                // Add a row to the table
                model.addRow(new Object[]{id, name, date, amount});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTableClickListener() {
       jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double-click to open details
                    int selectedRow = jTable1.getSelectedRow();
                    if (selectedRow != -1) {
                        // Get the tutor ID from the selected row
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                        String tutorIdStr = model.getValueAt(selectedRow, 0).toString();

                        try {
                            // Parse tutor ID to integer
                            int tutorId = Integer.parseInt(tutorIdStr);

                            // Open the TutorDetails panel or form
                            openTutorDetails(tutorId);
                        } catch (NumberFormatException ex) {
                            System.err.println("Invalid tutor ID format: " + tutorIdStr);
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void openTutorDetails(int tutorId) {
        System.out.println(tutorId);
        TutorDetails detailsFrame = new TutorDetails(tutorId); // Pass NIC to the frame
        detailsFrame.setVisible(true);
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
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("Tutors Withdrawal History");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tutor ID", "Tutor Name", "Withdrawal Date", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Search by Name");

        jLabel3.setText("Double click a row to view tutor details");

        jButton1.setBackground(new java.awt.Color(78, 74, 207));
        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
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
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 970, Short.MAX_VALUE)))
                .addGap(16, 16, 16))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //view or print report
        String path = "src/reports/TutorWithdrawalHistory.jasper";
        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        HashMap<String, Object> params = new HashMap<>();
        params.put("Parameter1", dateTime);

        JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());

        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(path, params, dataSource);

            if (jasperPrint != null) {
                JasperViewer.viewReport(jasperPrint, false);
            } else {
                System.err.println("Report generation failed.");
            }
        } catch (JRException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
//       jTable1.addMouseListener(new MouseAdapter() {
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            if (e.getClickCount() == 2) { // Double-click to open details
//                int selectedRow = jTable1.getSelectedRow();
//                if (selectedRow != -1) {
//                    // Get the tutor ID from the selected row
//                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//                    String tutorIdStr = model.getValueAt(selectedRow, 0).toString();
//
//                    try {
//                        // Parse tutor ID to integer
//                        int tutorId = Integer.parseInt(tutorIdStr);
//
//                        // Open the TutorDetails panel or form
//                        openTutorDetails(tutorId);
//                    } catch (NumberFormatException ex) {
//                        System.err.println("Invalid tutor ID format: " + tutorIdStr);
//                        ex.printStackTrace();
//                    }
//                }
//            }
//        }
//    });
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
