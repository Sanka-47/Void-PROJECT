//author KAVISHKA

package gui;

import com.toedter.calendar.JDateChooser;
import model.MySQL2;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddSession extends javax.swing.JPanel {
    
    private String tMobile;

    private static HashMap<String, String> courseMap = new HashMap<>();
    private static HashMap<String, String> tutorMap = new HashMap<>();
    private static HashMap<String, String> contactMap = new HashMap<>();
    private static HashMap<String, String> statusMap = new HashMap<>();

    public AddSession() {
        initComponents();
        generateSessionID();
        loadCourses();
        loadTutor();
        loadStatus();
    }

    private void generateSessionID() {
        long id = System.currentTimeMillis();
        jTextField1.setText(String.valueOf(id));
    }
    
    //Class ID
    public JTextField getjTextField1() {
        return jTextField1;
    }
    
    //Class Name
    public JTextField getjTextField2() {
        return jTextField2;
    }
    
    //Date
    public JDateChooser getjDateChooser1() {
        return jDateChooser1;
    }
    
    //Start Time
    public JFormattedTextField getjFormattedTextField1() {
        return jFormattedTextField1;
    }
    
    //End Time
    public JFormattedTextField getjFormattedTextField2() {
        return jFormattedTextField2;
    }
    
    //Hall Number
    public JTextField getjTextField3() {
        return jTextField3;
    }
    
    //Price
    public JFormattedTextField getjFormattedTextField3() {
        return jFormattedTextField3;
    }
    
    //Tutor Name
    public JComboBox<String> getjComboBox1() {
        return jComboBox1;
    }
    
    //Course Name
    public JComboBox<String> getjComboBox2() {
        return jComboBox2;
    }
    
    //Status
    public JComboBox<String> getjComboBox3() {
        return jComboBox3;
    }
    
    private void loadTutor() {

        try {

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `tutor`");

            while (resultSet.next()) {
                String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                vector.add(fullName);
                tutorMap.put(fullName, resultSet.getString("id"));
                contactMap.put(fullName, resultSet.getString("contact_info"));
            }

            jComboBox1.setModel(new DefaultComboBoxModel<>(vector));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCourses() {

        try {

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `courses`");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                courseMap.put(resultSet.getString("name"), resultSet.getString("id"));
            }

            jComboBox2.setModel(new DefaultComboBoxModel<>(vector));

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

//    private void loadTutorMobile() {
//        try {
//            String selectedTutor = String.valueOf(jComboBox1.getSelectedItem());
//            if (selectedTutor != null && contactMap.containsKey(selectedTutor)) {
//                jTextField1.setText(contactMap.get(selectedTutor));
//            } else {
//                jTextField1.setText("-");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();

        setPreferredSize(new java.awt.Dimension(1000, 581));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("Add Class Schedule");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel10.setText("Course");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        jComboBox2.setPreferredSize(new java.awt.Dimension(158, 35));

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel9.setText("Tutor Name");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        jComboBox1.setPreferredSize(new java.awt.Dimension(158, 35));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setText("Date");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setText("Time");
        jLabel5.setPreferredSize(new java.awt.Dimension(79, 38));

        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton1.setText("Add Session");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton2.setText("Edit Session");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Class ID");

        jLabel6.setText("TO");

        jFormattedTextField2.setPreferredSize(new java.awt.Dimension(48, 35));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7.setText("Hall Number");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel8.setText("Amount");

        jFormattedTextField3.setPreferredSize(new java.awt.Dimension(158, 35));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel11.setText("Status");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setText("Class Name");

        jButton3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton3.setText("Clear All");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(141, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, 182, Short.MAX_VALUE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel6)
                                    .addGap(18, 18, 18)
                                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
                                .addComponent(jFormattedTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(141, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String sessionID = jTextField1.getText();
        String className = jTextField2.getText();
        Date date = jDateChooser1.getDate();
        String startTime = jFormattedTextField1.getText();
        String endTime = jFormattedTextField2.getText();
        String hallnumber = jTextField3.getText();
        String price = jFormattedTextField3.getText();
        String tName = String.valueOf(jComboBox1.getSelectedItem());
        String course = String.valueOf(jComboBox2.getSelectedItem());
        String status = String.valueOf(jComboBox3.getSelectedItem());
        
        if (className.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the class name!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (date == null) {
            JOptionPane.showMessageDialog(this, "Please enter a date!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (startTime.isEmpty() || startTime.equals(0.00)) {
            JOptionPane.showMessageDialog(this, "Please enter the class starting time!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (endTime.isEmpty() || endTime.equals(0.00)) {
            JOptionPane.showMessageDialog(this, "Please enter the class ending time!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (hallnumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the location!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "PLease enter a price!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!price.matches("^[0-9]*\\.?[0-9]+$")) {
            JOptionPane.showMessageDialog(this, "Please enter only values!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (tName.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a tutor!", "Warning", JOptionPane.WARNING_MESSAGE);
        } if (course.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a course!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (status.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please enter the class status!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                ResultSet resultSet1 = MySQL2.executeSearch("SELECT `id` FROM `tutor` WHERE `id` = '" + tutorMap.get(tName) + "' AND `contact_info` = '" + tMobile + "'");

                if (resultSet1.next()) {

//                    String tutorID = resultSet.getString("id");
//                    
//                    ResultSet resultSet2 = MySQL2.executeSearch("SELECT `id` FROM `courses` WHERE `name` = '" + course + "'");
//                    String courseID = resultSet2.getString("id");
//                    
//                    ResultSet resultSet3 = MySQL2.executeSearch("SELECT `id` FROM  `class_status` WHERE `name` = '" + status + "'");
//                    String statusID = resultSet3.getString("id");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    
                    System.out.println("Session ID: " + sessionID);
                    System.out.println("Class Name :" + className);
                    System.out.println("Date: " + format.format(date));
                    System.out.println("Start Time: " + startTime);
                    System.out.println("End Time :" + endTime);
                    System.out.println("Hall Number :" + hallnumber);
                    System.out.println("Price :" + price);
                    System.out.println("Tutor ID :" + tutorMap.get(tName));
                    System.out.println("Course ID :" + courseMap.get(course));
                    System.out.println("Status ID :" + statusMap.get(status));

                    MySQL2.executeIUD("INSERT INTO `class` (`id`,`name`,`date`,`start_time`,`end_time`,`location`,`amount`,`tutor_id`,`courses_id`,`class_status_id`)"
                            + "VALUES ('" + sessionID + "','" + className + "','" + format.format(date) + "','" + startTime + "','" + endTime + "','" + hallnumber + "','" + price + "',"
                                    + "'" + tutorMap.get(tName) + "','" + courseMap.get(course) + "','" + statusMap.get(status) + "')");

                } else {
                    JOptionPane.showMessageDialog(this, "Tutor's phone number does not match with the previously saved number! Please try again!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String sessionID = jTextField1.getText();
        String className = jTextField2.getText();
        Date date = jDateChooser1.getDate();
        String startTime = jFormattedTextField1.getText();
        String endTime = jFormattedTextField2.getText();
        String hallnumber = jTextField3.getText();
        String price = jFormattedTextField3.getText();
        String tName = String.valueOf(jComboBox1.getSelectedItem());
        String course = String.valueOf(jComboBox2.getSelectedItem());
        String status = String.valueOf(jComboBox3.getSelectedItem());

        if (className.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the class name!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (date == null) {
            JOptionPane.showMessageDialog(this, "Please enter a date!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (startTime.isEmpty() || startTime.equals(0.00)) {
            JOptionPane.showMessageDialog(this, "Please enter the class starting time!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (endTime.isEmpty() || endTime.equals(0.00)) {
            JOptionPane.showMessageDialog(this, "Please enter the class ending time!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (hallnumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the location!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "PLease enter a price!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!price.matches("^[0-9]*\\.?[0-9]+$")) {
            JOptionPane.showMessageDialog(this, "Please enter only values!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (tName.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a tutor!", "Warning", JOptionPane.WARNING_MESSAGE);
        }else if (course.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a course!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (status.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please enter the class status!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

//                ResultSet resultSet1 = MySQL2.executeSearch("SELECT `id` FROM `tutor` WHERE `id` = '" + tutorMap.get(tName) + "' AND `contact_info` = '" + tMobile + "'");
//                if (resultSet1.next()) {
//                    String tutorID = resultSet.getString("id");
//                    
//                    ResultSet resultSet2 = MySQL2.executeSearch("SELECT `id` FROM `courses` WHERE `name` = '" + course + "'");
//                    String courseID = resultSet2.getString("id");
//                    
//                    ResultSet resultSet3 = MySQL2.executeSearch("SELECT `id` FROM  `class_status` WHERE `name` = '" + status + "'");
//                    String statusID = resultSet3.getString("id");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                System.out.println("Session ID: " + sessionID);
                System.out.println("Class Name :" + className);
                System.out.println("Date: " + format.format(date));
                System.out.println("Start Time: " + startTime);
                System.out.println("End Time :" + endTime);
                System.out.println("Hall Number :" + hallnumber);
                System.out.println("Price :" + price);
                System.out.println("Tutor ID :" + tutorMap.get(tName));
                System.out.println("Course ID :" + courseMap.get(course));
                System.out.println("Status ID :" + statusMap.get(status));

                MySQL2.executeIUD("UPDATE `class` SET `name` = '" + className + "', `date` = '" + format.format(date) + "', `start_time` = '" + startTime + "', "
                        + "`end_time` = '" + endTime + "', `hallnumber` = '" + hallnumber + "', `amount` = '" + price + "', `tutor_id` = '" + tutorMap.get(tName) + "', "
                        + "`courses_id` = '" + courseMap.get(course) + "', `class_status` = '" + statusMap.get(status) + "' WHERE `class`.`id` = '" + sessionID + "'");

                JOptionPane.showMessageDialog(this, "Tutor's phone number does not match with the previously saved number! Please try again!", "Warning", JOptionPane.WARNING_MESSAGE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
