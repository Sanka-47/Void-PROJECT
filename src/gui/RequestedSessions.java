/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.raven.datechooser.DateBetween;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAction;
import com.raven.datechooser.listener.DateChooserAdapter;
import java.awt.Color;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rushma
 */
public class RequestedSessions extends javax.swing.JPanel {

    private static final Logger logger = LogManager.getLogger(RequestedSessions.class);

    private DateChooser chDate = new DateChooser();
    private String From;
    private String To;
    private DashboardInterface parent;

    /**
     * Creates new form RequestedSessions
     */
    public RequestedSessions(DashboardInterface parent) {
        this.parent = parent;
        initComponents();
        dateChooser();
        loadRequestedSessions("", "");
        rejectbtn.setEnabled(false);
        confirmBtn.setEnabled(false);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        jTable1.setDefaultRenderer(Object.class, renderer);

        jTable1.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && jTable1.getSelectedRow() != -1) {
                rejectbtn.setEnabled(true);
                int selectedRow = jTable1.getSelectedRow();
                String type = (String) jTable1.getValueAt(selectedRow, 10);
                System.out.println("type:" + type);
                if (type.equals("Cancel")) {
                    confirmBtn.setEnabled(true);
                } else {
                    confirmBtn.setEnabled(false);
                }
            }
        });
    }

    private void dateChooser() {
        chDate.setTextField(jTextField6);
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
                loadRequestedSessions(From, To);
            }
        });
    }

    private void loadRequestedSessions(String from, String to) {
        try {
            String sort = String.valueOf(jComboBox4.getSelectedItem());
            String query = "SELECT "
                    + "tutor.first_name, tutor.last_name, "
                    + "request_sessions.id, request_sessions.title, request_sessions.date, "
                    + "request_sessions.start_time, request_sessions.id, request_sessions.end_time, "
                    + "request_sessions.hallnumber, request_sessions.approve_status, "
                    + "request_sessions.reason, request_sessions.type, request_sessions.class_id, "
                    + "courses.name AS course_name "
                    + "FROM request_sessions "
                    + "INNER JOIN tutor ON request_sessions.tutor_id = tutor.id "
                    + "INNER JOIN courses ON request_sessions.courses_id = courses.id "
                    + "WHERE request_sessions.approve_status = 'Pending' "
                    + "OR request_sessions.approve_status = 'Rejected'";

            if (from != null && !from.isEmpty() && to != null && !to.isEmpty()) {
                if (query.contains("WHERE")) {
                    query += " AND date BETWEEN '" + from + "' AND '" + to + "'";
                } else {
                    query += " WHERE date BETWEEN '" + from + "' AND '" + to + "'";
                }
            }
            if (sort.equals("Hall Number ASC")) {
                query += " ORDER BY request_sessions.hallnumber ASC";
            } else if (sort.equals("Hall Number DESC")) {
                query += " ORDER BY request_sessions.hallnumber DESC";
            } else if (sort.equals("Tutor Name ASC")) {
                query += " ORDER BY tutor.first_name ASC";
            } else if (sort.equals("Tutor Name DESC")) {
                query += " ORDER BY tutor.first_name DESC";
            } else if (sort.equals("Subject ASC")) {
                query += " ORDER BY courses.name ASC";
            } else if (sort.equals("Subject DESC")) {
                query += " ORDER BY courses.name DESC";
            } else if (sort.equals("ID ASC")) {
                query += " ORDER BY request_sessions.id ASC";
            } else if (sort.equals("ID DESC")) {
                query += " ORDER BY request_sessions.id DESC";
            }

            ResultSet resultSet = MySQL2.executeSearch(query);

            // Get the table model and clear existing rows
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            // Add data to the table
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("request_sessions.id")); // Class ID
                vector.add(resultSet.getString("request_sessions.class_id")); // Class ID
                String requestedBy = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                vector.add(requestedBy); // Requested By
                vector.add(resultSet.getString("course_name")); // Course Name
                vector.add(resultSet.getString("title")); // Title
                vector.add(resultSet.getString("date")); // Date
                vector.add(resultSet.getString("start_time")); // Start Time
                vector.add(resultSet.getString("end_time")); // End Time
                vector.add(resultSet.getString("hallnumber")); // Hall Number
                vector.add(resultSet.getString("reason")); // Reason
                vector.add(resultSet.getString("type")); // Type
                vector.add(resultSet.getString("approve_status")); // Approval Status
                model.addRow(vector);
            }
        } catch (Exception e) {
            System.out.println(e);
            logger.error("Exception caught", e); // Log the exception for debugging
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
        rejectbtn = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        confirmBtn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1004, 586));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("Requested Sessions");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Class ID", "Tutor", "Course", "Title", "Date", "Start time", "End Time", "Hall Number", "Reason", "Type", "Approval Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(10);
            jTable1.getColumnModel().getColumn(1).setMinWidth(20);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
            jTable1.getColumnModel().getColumn(7).setResizable(false);
            jTable1.getColumnModel().getColumn(8).setResizable(false);
            jTable1.getColumnModel().getColumn(9).setResizable(false);
            jTable1.getColumnModel().getColumn(10).setResizable(false);
            jTable1.getColumnModel().getColumn(11).setResizable(false);
        }

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Reason");

        rejectbtn.setBackground(new java.awt.Color(78, 74, 207));
        rejectbtn.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        rejectbtn.setForeground(new java.awt.Color(255, 255, 255));
        rejectbtn.setText("Reject");
        rejectbtn.setPreferredSize(new java.awt.Dimension(201, 26));
        rejectbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rejectbtnActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Select a row to reject request");

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setText("Sort By :");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hall Number ASC", "Hall Number DESC", "Tutor Name ASC", "Tutor Name DESC", "Courses ASC", "Courses DESC", "ID ASC", "ID DESC" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel6.setText("Sort By Date");

        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField6KeyReleased(evt);
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

        confirmBtn.setBackground(new java.awt.Color(78, 74, 207));
        confirmBtn.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        confirmBtn.setForeground(new java.awt.Color(255, 255, 255));
        confirmBtn.setText("Confirm Cancellation");
        confirmBtn.setPreferredSize(new java.awt.Dimension(201, 26));
        confirmBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmBtnActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Select a row to confirm cancellation request");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Double click a row to confirm request and schedule the lesson");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(rejectbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(confirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(185, 185, 185)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addGap(52, 52, 52)
                .addComponent(jLabel8)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(rejectbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // Check if the click is a single click
        if (evt.getClickCount() == 1) {
            int selectedRow = jTable1.getSelectedRow();
            String type = (String) jTable1.getValueAt(selectedRow, 10);
            System.out.println("type:" + type);
            if (type.equals("Cancel")) {
                confirmBtn.setEnabled(true);
            } else {
                confirmBtn.setEnabled(false);
            }
        }

        if (evt.getClickCount() == 2) {

            // Get the selected row and column
            int selectedRow = jTable1.getSelectedRow();
            int selectedColumn = jTable1.getSelectedColumn();

            String type = (String) jTable1.getValueAt(selectedRow, 10);
            System.out.println("type:" + type);
            if (type.equals("Cancel")) {
                JOptionPane.showMessageDialog(this,
                        "This is a cancelleation request. Please select a new session request to scehdule",
                        "Cancellation",
                        JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                if (selectedRow != -1 && selectedColumn != -1) {
                    // Retrieve the value of the 8th column (index 7)
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    String column8Value = (String) model.getValueAt(selectedRow, 8);

                    // Check if the 8th column value is "Rejected"
                    if ("Rejected".equalsIgnoreCase(column8Value)) {
                        JOptionPane.showMessageDialog(this,
                                "You have rejected this request. You cannot proceed.",
                                "Request Rejected",
                                JOptionPane.WARNING_MESSAGE);
                        return; // Exit the method
                    }

                    // Get the data from the selected cell
                    Object cellData = jTable1.getValueAt(selectedRow, selectedColumn);

                    // Retrieve the entire row data
                    Vector<String> rowData = new Vector<>();
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        rowData.add((String) model.getValueAt(selectedRow, col));
                    }

                    // Switch to the TutorScheduleAndCalandar panel
                    TutorScheduleAndCalandar tutorPanel = new TutorScheduleAndCalandar(parent, rowData);
                    parent.switchPanel(tutorPanel);
                }
            }

        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void rejectbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rejectbtnActionPerformed
        // Check if jTextField1 is empty
        int selectedRow = jTable1.getSelectedRow();
        String column8Value = (String) jTable1.getValueAt(selectedRow, 8);

        // Check if the 8th column value is "Rejected"
        if ("Rejected".equalsIgnoreCase(column8Value)) {
            JOptionPane.showMessageDialog(this,
                    "You have already rejected this request.",
                    "Request Rejected",
                    JOptionPane.WARNING_MESSAGE);
            return; // Exit the method
        }

        if (jTextField1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a reason before rejecting.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method
        }

        if (selectedRow != -1) {
            String id = (String) jTable1.getValueAt(selectedRow, 0); // Get 'title' from selected row

            try {
                // Update approve_status to 'Rejected'
                String query = "UPDATE request_sessions SET approve_status = 'Rejected',reason = '" + jTextField1.getText() + "' WHERE `id`='" + id + "'";
                MySQL2.executeIUD(query);

                // Reload table data
                loadRequestedSessions("", "");
                JOptionPane.showMessageDialog(this, "Session rejected successfully!");

                // Disable buttons after action
                rejectbtn.setEnabled(false);
                reset();
            } catch (Exception e) {
                logger.error("Exception caught", e);
                JOptionPane.showMessageDialog(this, "Error while rejecting session.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No session selected. Please select a session to reject.", "Selection Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_rejectbtnActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        if (From != null && To == null) {
            loadRequestedSessions(From, "");
        } else if (From == null && To != null) {
            loadRequestedSessions("", To);
        } else if (From != null && To != null) {
            loadRequestedSessions(From, To);
        } else {
            loadRequestedSessions("", "");
        }
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased
        if (From != null && To == null) {
            loadRequestedSessions(From, "");
        } else if (From == null && To != null) {
            loadRequestedSessions("", To);
        } else if (From != null && To != null) {
            loadRequestedSessions(From, To);
        } else {
            loadRequestedSessions("", "");
        }
    }//GEN-LAST:event_jTextField6KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        // Reset the filter inputs
        From = null;
        To = null;
        jTextField6.setText("");  // Clear the date field
        jComboBox4.setSelectedIndex(0);  // Reset the sorting combo box to default

        // Reload the original data without any filters
        loadRequestedSessions("", "");

    }//GEN-LAST:event_jButton1ActionPerformed

    private void confirmBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmBtnActionPerformed
        // Check if jTextField1 is empty
        int selectedRow = jTable1.getSelectedRow();
        String column8Value = (String) jTable1.getValueAt(selectedRow, 8);
        String classId = (String) jTable1.getValueAt(selectedRow, 1);

        // Check if the 8th column value is "Rejected"
        if ("Rejected".equalsIgnoreCase(column8Value)) {
            JOptionPane.showMessageDialog(this,
                    "You have rejected this request.",
                    "Request Rejected",
                    JOptionPane.WARNING_MESSAGE);
            return; // Exit the method
        } else if ("Confirmed".equalsIgnoreCase(column8Value)) {
            JOptionPane.showMessageDialog(this,
                    "You have already confirmed this request.",
                    "Request Rejected",
                    JOptionPane.WARNING_MESSAGE);
            return; // Exit the method
        }

        if (selectedRow != -1) {
            String id = (String) jTable1.getValueAt(selectedRow, 0); // Get 'title' from selected row

            try {
                // Update approve_status to 'Rejected'
                String query = "UPDATE request_sessions SET approve_status = 'Confirmed',reason = 'none' WHERE `id`='" + id + "'";
                MySQL2.executeIUD(query);
                String query1 = "UPDATE class SET class_status_id = 3 WHERE id = '" + classId + "'";
                MySQL2.executeIUD(query1);
                // Reload table data
                loadRequestedSessions("", "");
                JOptionPane.showMessageDialog(this, "Request confirmed successfully!");

                // Disable buttons after action
                confirmBtn.setEnabled(false);
                reset();
            } catch (Exception e) {
                logger.error("Exception caught", e);
                JOptionPane.showMessageDialog(this, "Error while confirming request.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No session selected. Please select a session to confirm.", "Selection Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_confirmBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton confirmBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JButton rejectbtn;
    // End of variables declaration//GEN-END:variables

    private void reset() {
        From = null;
        To = null;
        jTextField6.setText("");  // Clear the date field
        jTextField1.setText("");  // Clear the date field
        jComboBox4.setSelectedIndex(0);  // Reset the sorting combo box to default

        // Reload the original data without any filters
        loadRequestedSessions("", "");
    }
}
