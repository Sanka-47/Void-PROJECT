package gui;

import com.raven.datechooser.DateBetween;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAction;
import com.raven.datechooser.listener.DateChooserAdapter;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;

public class TutorClassList extends javax.swing.JPanel {

    private DateChooser chDate = new DateChooser();

    private String From;
    private String To;

    private DashboardInterface parent;
    private HashMap<String, String> courseMap;

    private String mobile;

    private int tutorId;

    public TutorClassList(DashboardInterface parent, int TutorID) {
        this.parent = parent;
        this.tutorId = TutorID;
        this.courseMap = new HashMap<>();
//        this.addSession = new AddSession();
        initComponents();
        dateChooser();

        loadCourses();
        loadTable("", "");
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        jTable1.setDefaultRenderer(Object.class, renderer);

        cancelBtn.setEnabled(false);

        jTable1.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && jTable1.getSelectedRow() != -1) {
                cancelBtn.setEnabled(true);
            }
        });
    }

    private void dateChooser() {
        chDate.setTextField(sortByDateInputField);
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

    private void loadCourses() {

        try {
            ResultSet resultSet = MySQL2.executeSearch("SELECT `id`, `name` FROM `courses`");
            while (resultSet.next()) {
                courseMap.put(resultSet.getString("name"), resultSet.getString("id"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTable(String from, String to) {
        String TID = String.valueOf(tutorId);

        try {
            String searchText = searchInputField.getText().toLowerCase();

            String startTime = startTimeInputField.getText();

            String endTime = endTimeInputField.getText();

            String status = String.valueOf(sortByStatausComboBox.getSelectedItem());

            // Base query
            String query = "SELECT * FROM class "
                    + "INNER JOIN tutor ON class.tutor_id = tutor.id "
                    + "INNER JOIN courses ON class.courses_id = courses.id "
                    + "INNER JOIN class_status ON class.class_status_id = class_status.id "
                    + "WHERE `tutor`.`id` = '" + TID + "' ";

            // Search text filtering
            if (!searchText.isEmpty()) {
                query += "AND (LOWER(`class`.`id`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`class`.`name`) LIKE '%" + searchText + "%' "
                        //                        + "OR LOWER(`class`.`date`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`class`.`hallnumber`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`courses`.`name`) LIKE '%" + searchText + "%' "
                        + "OR LOWER(`class_status`.`name`) LIKE '%" + searchText + "%') ";
            }

            if (!startTime.isEmpty()) {
                // Use LIKE operator for partial matching instead of exact match
                query += "AND (`class`.`start_time` LIKE '%" + startTime + "%' ";

                // Try to handle numeric formats (with or without am/pm)
                try {
                    // Parse the input to see if it's a number
                    int timeValue = Integer.parseInt(startTime.replaceAll("[^0-9]", ""));

                    // Add additional conditions to catch different formats
                    query += "OR `class`.`start_time` LIKE '%" + timeValue + "%' ";

                    // Check for AM/PM variants
                    if (startTime.toLowerCase().contains("am")) {
                        query += "OR `class`.`start_time` LIKE '%" + timeValue + "%' ";
                        query += "OR `class`.`start_time` LIKE '%" + timeValue + " am%' ";
                        query += "OR `class`.`start_time` LIKE '%" + timeValue + "am%' ";
                    } else if (startTime.toLowerCase().contains("pm")) {
                        query += "OR `class`.`start_time` LIKE '%" + timeValue + "%' ";
                        query += "OR `class`.`start_time` LIKE '%" + timeValue + " pm%' ";
                        query += "OR `class`.`start_time` LIKE '%" + timeValue + "pm%' ";
                    } else {
                        // If no am/pm specified, search for both possibilities
                        query += "OR `class`.`start_time` LIKE '%" + timeValue + " am%' ";
                        query += "OR `class`.`start_time` LIKE '%" + timeValue + "am%' ";
                        query += "OR `class`.`start_time` LIKE '%" + timeValue + " pm%' ";
                        query += "OR `class`.`start_time` LIKE '%" + timeValue + "pm%' ";
                    }
                } catch (NumberFormatException e) {
                    // Not a number, just use the LIKE search we already added
                }

                query += ") ";
                System.out.println("Start Time: " + startTime);
            }

            if (!endTime.isEmpty()) {
                query += "AND (`class`.`end_time` LIKE '%" + endTime + "%' ";

                try {
                    int timeValue = Integer.parseInt(endTime.replaceAll("[^0-9]", ""));

                    query += "OR `class`.`end_time` LIKE '%" + timeValue + "%' ";

                    if (endTime.toLowerCase().contains("am")) {
                        query += "OR `class`.`end_time` LIKE '%" + timeValue + "%' ";
                        query += "OR `class`.`end_time` LIKE '%" + timeValue + " am%' ";
                        query += "OR `class`.`end_time` LIKE '%" + timeValue + "am%' ";
                    } else if (endTime.toLowerCase().contains("pm")) {
                        query += "OR `class`.`end_time` LIKE '%" + timeValue + "%' ";
                        query += "OR `class`.`end_time` LIKE '%" + timeValue + " pm%' ";
                        query += "OR `class`.`end_time` LIKE '%" + timeValue + "pm%' ";
                    } else {
                        // If no am/pm specified, search for both possibilities
                        query += "OR `class`.`end_time` LIKE '%" + timeValue + " am%' ";
                        query += "OR `class`.`end_time` LIKE '%" + timeValue + "am%' ";
                        query += "OR `class`.`end_time` LIKE '%" + timeValue + " pm%' ";
                        query += "OR `class`.`end_time` LIKE '%" + timeValue + "pm%' ";
                    }
                } catch (NumberFormatException e) {
                    // Not a number, just use the LIKE search we already added
                }

                query += ") ";
                System.out.println("End Time: " + endTime);
            }

            if (status.equals("Today's Sessions")) {
                from = null;
                to = null;
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                query += "AND `class`.`date` = '" + format.format(date) + "' ";
            } else if (!from.isEmpty() && to == null) {
                if (status.equals("Today's Sessions")) {
                    sortByStatausComboBox.setSelectedItem("Select");
                    sortByStatausComboBox.setEnabled(false);
                }
                query += "AND `class`.`date` > '" + from + "' ";
            } else if (!to.isEmpty() && from == null) {
                if (status.equals("Today's Sessions")) {
                    sortByStatausComboBox.setSelectedItem("Select");
                    sortByStatausComboBox.setEnabled(false);
                }
                query += "AND `class`.`date` < '" + to + "' ";
            } else if (from != null && !from.isEmpty() && to != null && !to.isEmpty()) {
                if (status.equals("Today's Sessions")) {
                    sortByStatausComboBox.setSelectedItem("Select");
                    sortByStatausComboBox.setEnabled(false);
                }
                query += "AND `class`.`date` > '" + from + "' AND `class`.`date` < '" + to + "' ";
            }

            if (status.equals("Select")) {
                query += "";
            } else if (status.equals("Pending")) {
                query += "AND `class_status`.`name` = 'Pending'";
            } else if (status.equals("Completed")) {
                query += "AND `class_status`.`name` = 'Completed'";
            } else if (status.equals("Cancelled")) {
                query += "AND `class_status`.`name` = 'Cancelled'";
            } else if (status.equals("Rescheduled")) {
                query += "AND `class_status`.`name` = 'Rescheduled'";
            }

            System.out.println(query);
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
                vector.add(resultSet.getString("courses.name"));
                vector.add(resultSet.getString("class_status.name"));

                model.addRow(vector);

                jLabel4.setText(resultSet.getString("tutor.first_name") + " " + (resultSet.getString("tutor.last_name")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reset() {
        searchInputField.setText("");
        jTextField2.setText("");
        startTimeInputField.setText("");
        endTimeInputField.setText("");
        sortByDateInputField.setText("");
        sortByStatausComboBox.setSelectedItem("Select");
        From = "";
        To = "";
        loadTable("", "");
        cancelBtn.setEnabled(false);
        sortByStatausComboBox.setEnabled(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cancelBtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        searchInputField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        startTimeInputField = new javax.swing.JTextField();
        endTimeInputField = new javax.swing.JTextField();
        sortByDateInputField = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        sortByStatausComboBox = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1000, 581));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel1.setText("Tutor Class List");

        cancelBtn.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        cancelBtn.setText("Cancel Session");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
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

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel6.setText("Search");

        searchInputField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchInputFieldKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7.setText("Select a row to cancel the session");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel8.setText("Reason for cancelling");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel10.setText("Start Time :");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel11.setText("End Time :");

        startTimeInputField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTimeInputFieldActionPerformed(evt);
            }
        });
        startTimeInputField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                startTimeInputFieldKeyReleased(evt);
            }
        });

        endTimeInputField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                endTimeInputFieldKeyReleased(evt);
            }
        });

        sortByDateInputField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortByDateInputFieldActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton2.setText("Clear All");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setText("Sort By Status :");

        sortByStatausComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Pending", "Completed", "Cancelled", "Rescheduled", "Today's Sessions" }));
        sortByStatausComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortByStatausComboBoxActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton3.setText("Complete Session");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sortByDateInputField)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sortByStatausComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startTimeInputField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(endTimeInputField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchInputField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(8, 8, 8)
                .addComponent(jLabel1)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sortByDateInputField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(sortByStatausComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(startTimeInputField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(endTimeInputField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchInputField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        RequestSession rs = new RequestSession(parent, tutorId);
        parent.switchPanel(rs);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        // Check if the text field (jTextField2) is empty
        if (jTextField2.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide a reason for requesting session cancellation.");
            return; // Exit the method if the text field is empty
        }

        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            String classId = (String) jTable1.getValueAt(selectedRow, 0); // Class ID is in column 0
            String className = (String) jTable1.getValueAt(selectedRow, 1); // Class Name is in column 1
            String date = (String) jTable1.getValueAt(selectedRow, 2); // Date is in column 2
            String startTime = (String) jTable1.getValueAt(selectedRow, 3); // Start Time is in column 3
            String endTime = (String) jTable1.getValueAt(selectedRow, 4); // End Time is in column 4
            String hallNumber = (String) jTable1.getValueAt(selectedRow, 5); // Hall Number is in column 5
            String course = (String) jTable1.getValueAt(selectedRow, 7); // Hall Number is in column 5
            String reason = jTextField2.getText().trim(); // Reason provided in text field
            int tutorId = this.tutorId; // Replace with the actual tutor's ID from your application
            // Replace if the Course ID needs to be dynamically set

            try {
                // Insert cancellation request into the `request_sessions` table
                String query = "INSERT INTO request_sessions (title, date, start_time, end_time, hallnumber, tutor_id, approve_status, reason, courses_id, type) "
                        + "VALUES ('" + className + "', '" + date + "', '" + startTime + "', '" + endTime + "', '" + hallNumber + "', '" + tutorId + "', 'Pending', '" + reason + "', " + courseMap.get(course) + ", 'Cancel')";
                MySQL2.executeIUD(query);

                // Reload table data
                loadTable("", "");
                JOptionPane.showMessageDialog(this, "Cancellation request sent to admin successfully!");

                // Disable button and clear text field after action
                cancelBtn.setEnabled(false);
                jTextField2.setText("");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while sending cancellation request.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a session to cancel.");
        }

    }//GEN-LAST:event_cancelBtnActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

    }//GEN-LAST:event_jTable1MouseClicked

    private void searchInputFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchInputFieldKeyReleased
        if (From != null && To == null) {
            loadTable(From, "");
        } else if (From == null && To != null) {
            loadTable("", To);
        } else if (From != null && To != null) {
            loadTable(From, To);
        } else {
            loadTable("", "");
        }
    }//GEN-LAST:event_searchInputFieldKeyReleased

    private void startTimeInputFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_startTimeInputFieldKeyReleased
        if (From != null && To == null) {
            loadTable(From, "");
        } else if (From == null && To != null) {
            loadTable("", To);
        } else if (From != null && To != null) {
            loadTable(From, To);
        } else {
            loadTable("", "");
        }
    }//GEN-LAST:event_startTimeInputFieldKeyReleased

    private void endTimeInputFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_endTimeInputFieldKeyReleased
        if (From != null && To == null) {
            loadTable(From, "");
        } else if (From == null && To != null) {
            loadTable("", To);
        } else if (From != null && To != null) {
            loadTable(From, To);
        } else {
            loadTable("", "");
        }
    }//GEN-LAST:event_endTimeInputFieldKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        reset();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void sortByStatausComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortByStatausComboBoxActionPerformed
        if (From != null && To == null) {
            loadTable(From, "");
        } else if (From == null && To != null) {
            loadTable("", To);
        } else if (From != null && To != null) {
            loadTable(From, To);
        } else {
            loadTable("", "");
        }
    }//GEN-LAST:event_sortByStatausComboBoxActionPerformed

    private void sortByDateInputFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortByDateInputFieldActionPerformed
        if (sortByStatausComboBox.getSelectedItem().equals("Today's Sessions")) {
            sortByStatausComboBox.setSelectedItem("Select");
            sortByStatausComboBox.setEnabled(false);
        }
    }//GEN-LAST:event_sortByDateInputFieldActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow != -1) {
            String classId = (String) jTable1.getValueAt(selectedRow, 0); // Session ID
            String sessionDate = (String) jTable1.getValueAt(selectedRow, 2); // Assuming date is in column 2 (format: yyyy-MM-dd)
            String amount = String.valueOf(jTable1.getValueAt(selectedRow, 6));

            // Get today's date
            java.time.LocalDate today = java.time.LocalDate.now();

            // Convert sessionDate string to LocalDate
            java.time.LocalDate sessionLocalDate = java.time.LocalDate.parse(sessionDate);

            if (!sessionLocalDate.isEqual(today)) {
                JOptionPane.showMessageDialog(this, "Only today's sessions can be marked as completed.");
                return;
            }

            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to mark this session as completed?",
                    "Confirm Completion", JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    String query = "UPDATE class SET class_status_id = 2 WHERE id = '" + classId + "'";
                    MySQL2.executeIUD(query);
                    String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    MySQL2.executeIUD("INSERT INTO `wallet`(`tutor_id`,`class_id`,`withdrawal_status_id`,`date`,`amount`) VALUES ('" + tutorId + "', '" + classId + "', '1', '" + formattedDate + "', '" + amount + "')");

                    loadTable("", "");

                    JOptionPane.showMessageDialog(this, "Session marked as completed successfully!");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error while completing the session.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a session to mark as completed.");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void startTimeInputFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTimeInputFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startTimeInputFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTextField endTimeInputField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField searchInputField;
    private javax.swing.JTextField sortByDateInputField;
    private javax.swing.JComboBox<String> sortByStatausComboBox;
    private javax.swing.JTextField startTimeInputField;
    // End of variables declaration//GEN-END:variables
}
