//author CHANULI
package gui;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;

public class TutorScheduleAndCalandar extends javax.swing.JPanel {
    
    private DashboardInterface parent;

    private static HashMap<String, String> subjectMap = new HashMap<>();
    private static HashMap<String, String> courseMap = new HashMap<>();
    private static HashMap<String, String> tutorMap = new HashMap<>();
    private static HashMap<String, String> statusMap = new HashMap<>();
    
    public TutorScheduleAndCalandar(DashboardInterface parent) {
        initComponents();
        loadSessions();
        loadCourses();
        loadTutors();
        loadStatus();
    }
    
    private void loadSessions() {

        try {

            String sort = String.valueOf(jComboBox1.getSelectedItem());

            String query = "SELECT * FROM `class` INNER JOIN `tutor` ON `class`.`tutor_id` = `tutor`.`id` "
                    + "INNER JOIN `courses` ON `class`.`courses_id` = `courses`.`id` "
                    + "INNER JOIN `class_status` ON `class`.`class_status_id` = `class_status`.`id` ";

            Date start = null;
            Date end = null;

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            if (jDateChooser1.getDate() != null && jDateChooser2.getDate() != null) {
                start = jDateChooser1.getDate();
                end = jDateChooser2.getDate();
                query += "WHERE `class`.`date` > '" + format.format(start) + "' AND `class`.`date` < '" + format.format(end) + "' ";
            } else if (jDateChooser1.getDate() != null && jDateChooser2.getDate() == null) {
                    start = jDateChooser1.getDate();
                    query += "WHERE `class`.`date` > '" + format.format(start) + "' ";
            } else if (jDateChooser1.getDate() == null && jDateChooser2.getDate() != null) {
                    end = jDateChooser2.getDate();
                    query += "WHERE `class`.`date` < '" + format.format(end) + "' ";
            }

            if (sort.equals("Hall Number ASC")) {
                query += "ORDER BY `hallnumber` ASC ";
            } else if (sort.equals("Hall Number DESC")) {
                query += "ORDER BY `hallnumber` DESC";
            } else if (sort.equals("Tutor Name ASC")) {
                query += "ORDER BY `tutor`.`first_name` ASC";
            } else if (sort.equals("Tutor Name DESC")) {
                query += "ORDER BY `tutor`.`first_name` DESC";
            } else if (sort.equals("Subject ASC")) {
                query += "ORDER BY `courses`.`name` ASC";
            } else if (sort.equals("Subject DESC")) {
                query += "ORDER BY `courses`.`name` DESC";
            }

            
            ResultSet resultSet = MySQL2.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("class.id"));
                vector.add(resultSet.getString("courses.name"));
                vector.add(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                vector.add(resultSet.getString("class.name"));
                vector.add(resultSet.getString("date"));
                vector.add(resultSet.getString("start_time"));
                vector.add(resultSet.getString("end_time"));
                vector.add(resultSet.getString("hallnumber"));
                vector.add(resultSet.getString("amount"));
                vector.add(resultSet.getString("class_status.name"));

                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadTutorSchedule() {

        try {
            // Corrected SQL query with DISTINCT and removal of unnecessary joins
            ResultSet resultSet = MySQL2.executeSearch(
                    "SELECT DISTINCT `class`.`id`, `courses`.`name` AS course_name, "
                    + "`tutor`.`first_name`, `tutor`.`last_name`, `class`.`name` AS class_name, "
                    + "`class`.`date`, `class`.`start_time`, `class`.`end_time`, `class`.`hallnumber`, "
                    + "`class`.`amount`, `class_status`.`name` AS status_name "
                    + "FROM `class` "
                    + "INNER JOIN `tutor` ON `class`.`tutor_id` = `tutor`.`id` "
                    + "INNER JOIN `courses` ON `class`.`courses_id` = `courses`.`id` "
                    + "INNER JOIN `class_status` ON `class`.`class_status_id` = `class_status`.`id`"
            );

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("course_name"));
                vector.add(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                vector.add(resultSet.getString("class_name"));
                vector.add(resultSet.getString("date"));
                vector.add(resultSet.getString("start_time"));
                vector.add(resultSet.getString("end_time"));
                vector.add(resultSet.getString("hallnumber"));
                vector.add(resultSet.getString("amount"));
                vector.add(resultSet.getString("status_name"));

                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadCourses() {

        try {
            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `courses`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                courseMap.put(resultSet.getString("name"), resultSet.getString("id"));

                DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
                jComboBox2.setModel(model);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStatus() {

        try {

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `class_status`");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                statusMap.put(resultSet.getString("name"), resultSet.getString("id"));
            }

            jComboBox3.setModel(new DefaultComboBoxModel<>(vector));

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void loadTutors() {

        try {
            ResultSet resultSet = MySQL2.executeSearch("SELECT first_name, last_name, id FROM `tutor`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
//                vector.add(resultSet.getString("name"));
//                tutorMap.put(resultSet.getString("name"), resultSet.getString("id"));

                String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                vector.add(fullName);
                tutorMap.put(fullName, resultSet.getString("id"));

                DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
                jComboBox1.setModel(model);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void reset() {
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField7.setText("");
        jDateChooser1.setDate(null);
        jFormattedTextField1.setText("");
        jFormattedTextField2.setText("");
        jFormattedTextField3.setText("");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel13 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jButton2 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel13.setText("Class ID");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel8.setText("Course");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel15.setText("Tutor");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel7.setText("Title");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel9.setText("Date");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel10.setText("Start Time");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel11.setText("End Time");

        jFormattedTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField2ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel12.setText("Hall Number");

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel14.setText("Status");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel16.setText("Amount");

        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton2.setText("Edit Session");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton10.setText("Cancel Session");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton8.setText("Schedule Session");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Course", "Tutor", "Title", "Date", "Start Time", "End Time", "Hall Number", "Amount", "Class Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
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

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setText("Sort By :");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hall Number ASC", "Hall Number DESC", "Tutor Name ASC", "Tutor Name DESC", "Courses ASC", "Courses DESC" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Sort By Date");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setText("TO");

        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setText("Sort");
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14))
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)))
                        .addGap(12, 12, 12))
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField2)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFormattedTextField3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(27, 27, 27)
                                    .addComponent(jLabel11))
                                .addGroup(layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel8))
                                .addGap(32, 32, 32))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addComponent(jLabel15))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jDateChooser3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(10, 10, 10)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        String sessionID = jTextField3.getText();
        String course = String.valueOf(jComboBox2.getSelectedItem());
        String tName = String.valueOf(jComboBox1.getSelectedItem());
        String className = jTextField2.getText();
        Date date = jDateChooser1.getDate();
        String startTime = jFormattedTextField1.getText();
        String endTime = jFormattedTextField2.getText();
        String hallnumber = jTextField7.getText();
        String status = String.valueOf(jComboBox3.getSelectedItem());
        String price = jFormattedTextField3.getText();

        if (sessionID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Class ID!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (course.equals("Select") || courseMap.get(course) == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid course!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (tName.equals("Select") || tutorMap.get(tName) == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid tutor!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (className.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Title!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (date == null) {
            JOptionPane.showMessageDialog(this, "Please enter a Date!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (startTime.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the class Starting time!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (endTime.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the class Ending time!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (hallnumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Location!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (status.equals("Select") || statusMap.get(status) == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid Status!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }else if (price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Amount!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;

        }try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            System.out.println("Session ID: " + sessionID);
            System.out.println("Class Name: " + className);
            System.out.println("Date: " + format.format(date));
            System.out.println("Start Time: " + startTime);
            System.out.println("End Time: " + endTime);
            System.out.println("Hall Number: " + hallnumber);
            System.out.println("Price: " + price);
            System.out.println("Tutor ID: " + tutorMap.get(tName));
            System.out.println("Course ID: " + courseMap.get(course));
            System.out.println("Status ID: " + statusMap.get(status));

            MySQL2.executeIUD("UPDATE `class` SET `name` = '" + className + "', `date` = '" + format.format(date) + "', `start_time` = '" + startTime + "', "
                + "`end_time` = '" + endTime + "', `hallnumber` = '" + hallnumber + "', `amount` = '" + price + "', `tutor_id` = '" + tutorMap.get(tName) + "', "
                + "`courses_id` = '" + courseMap.get(course) + "', `class_status_id` = '" + statusMap.get(status) + "' WHERE `class`.`id` = '" + sessionID + "'");

            JOptionPane.showMessageDialog(this, "Class updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            reset();
            loadTutorSchedule();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update the class. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

        //        String sessionID = jTextField3.getText();
        //
        //        if (sessionID.isEmpty()) {
            //            JOptionPane.showMessageDialog(this, "Please enter a session ID to cancel!", "Warning", JOptionPane.WARNING_MESSAGE);
            //            return;
            //        }
        //
        //        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this session?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
        //        if (confirm == JOptionPane.NO_OPTION) {
            //            return;
            //        }
        //
        //        try {
            //            ResultSet resultSet = MySQL2.executeSearch("SELECT `id` FROM `class` WHERE `id` = '" + sessionID + "'");
            //
            //            if (resultSet.next()) {
                //
                //                MySQL2.executeIUD("DELETE FROM `class` WHERE `id` = '" + sessionID + "'");
                //
                //                JOptionPane.showMessageDialog(this, "Session canceled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                //
                //                reset();
                //                loadTutorSchedule();
                //
                //            } else {
                //                JOptionPane.showMessageDialog(this, "The session ID does not exist. Please check and try again.", "Error", JOptionPane.ERROR_MESSAGE);
                //            }
            //
            //        } catch (Exception e) {
            //            e.printStackTrace();
            //            JOptionPane.showMessageDialog(this, "An error occurred while canceling the session. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            //        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        String sessionID = jTextField3.getText();
        String course = String.valueOf(jComboBox2.getSelectedItem());
        String tName = String.valueOf(jComboBox1.getSelectedItem());
        String className = jTextField2.getText();
        Date date = jDateChooser1.getDate();
        String startTime = jFormattedTextField1.getText();
        String endTime = jFormattedTextField2.getText();
        String hallnumber = jTextField7.getText();
        String status = String.valueOf(jComboBox3.getSelectedItem());
        String price = jFormattedTextField3.getText();

        // Validation checks
        if (sessionID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Class ID!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (course.equals("Select") || courseMap.get(course) == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid course!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (tName.equals("Select") || tutorMap.get(tName) == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid tutor!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (className.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Title!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (date == null) {
            JOptionPane.showMessageDialog(this, "Please enter a Date!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (startTime.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the class Starting time!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (endTime.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the class Ending time!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (hallnumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Location!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (status.equals("Select") || statusMap.get(status) == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid Status!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }else if (price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Amount!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;

        } else {

            try {
                // Fetch tutor details from the database
                ResultSet resultSet1 = MySQL2.executeSearch("SELECT `id` FROM `tutor` WHERE `id` = '" + tutorMap.get(tName) + "'");

                if (resultSet1.next()) {
                    // Format date
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    // Output to console for debugging
                    System.out.println("Session ID: " + sessionID);
                    System.out.println("Class Name: " + className);
                    System.out.println("Date: " + format.format(date));
                    System.out.println("Start Time: " + startTime);
                    System.out.println("End Time: " + endTime);
                    System.out.println("Hall Number: " + hallnumber);
                    System.out.println("Price: " + price);
                    System.out.println("Tutor ID: " + tutorMap.get(tName));
                    System.out.println("Course ID: " + courseMap.get(course));
                    System.out.println("Status ID: " + statusMap.get(status));

                    // Insert data into the database
                    MySQL2.executeIUD("INSERT INTO `class` (`id`, `name`, `date`, `start_time`, `end_time`, `hallnumber`, `amount`, `tutor_id`, `courses_id`, `class_status_id`) "
                        + "VALUES ('" + sessionID + "', '" + className + "', '" + format.format(date) + "', '" + startTime + "', '" + endTime + "', '" + hallnumber + "', '" + price + "', "
                        + "'" + tutorMap.get(tName) + "', '" + courseMap.get(course) + "', '" + statusMap.get(status) + "')");

                    JOptionPane.showMessageDialog(this, "Class added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    reset();
                    loadTutorSchedule();

                } else {
                    JOptionPane.showMessageDialog(this, "The tutor's ID does not match the record in the system. Please check the tutor information and try again.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int row = jTable1.getSelectedRow();

        String id = String.valueOf(jTable1.getValueAt(row, 0));
        jTextField3.setText(id);

        String courseName = String.valueOf(jTable1.getValueAt(row, 1));
        jComboBox2.setSelectedItem(courseName);

        String tutor = String.valueOf(jTable1.getValueAt(row, 2));
        jComboBox1.setSelectedItem(tutor);

        String title = String.valueOf(jTable1.getValueAt(row, 3));
        jTextField2.setText(title);

        String date = String.valueOf(jTable1.getValueAt(row, 4));
        try {
            java.util.Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            jDateChooser1.setDate(parsedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String startTime = String.valueOf(jTable1.getValueAt(row, 5));
        jFormattedTextField1.setText(startTime);

        String endTime = String.valueOf(jTable1.getValueAt(row, 6));
        jFormattedTextField2.setText(endTime);

        String hallNumber = String.valueOf(jTable1.getValueAt(row, 7));
        jTextField7.setText(hallNumber);

        String amount = String.valueOf(jTable1.getValueAt(row, 9));
        jFormattedTextField3.setText(amount);

        String status = String.valueOf(jTable1.getValueAt(row, 8));
        jComboBox3.setSelectedItem(status);
    }//GEN-LAST:event_jTable1MouseClicked

    private void jFormattedTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField2ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        loadSessions();
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadSessions();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
