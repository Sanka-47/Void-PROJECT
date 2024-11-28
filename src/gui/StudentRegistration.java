package gui;

import com.toedter.calendar.JCalendar;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.MySQL2;

public class StudentRegistration extends javax.swing.JPanel {

    public static HashMap<String, String> gender = new HashMap<>();

    public StudentRegistration() {
        initComponents();
        loadGender();
        jButton3.setEnabled(false);
        jButton4.setEnabled(false);

    }

    private void loadGender() {

        try {
            ResultSet rs = MySQL2.executeSearch("SELECT * FROM `gender`");
            Vector vector = new Vector();
            vector.add("Select");

            while (rs.next()) {
                vector.add(rs.getString("name"));
                gender.put(rs.getString("name"), rs.getString("id"));

            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);
//             logger.info("Gender data loaded successfully.");

        } catch (Exception e) {
//           logger.log(Level.SEVERE, "Error loading gender data: ", e);
            e.printStackTrace();
        }

    }

    private void reset() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jComboBox1.setSelectedIndex(0);
        jDateChooser1.setDate(null);

        jComboBox1.setSelectedIndex(0);
        jButton3.setEnabled(false);
        jButton4.setEnabled(false);
//        logger.info("Form reset.");

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Student Registration");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("First Name");

        jTextField1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setText("Last Name");

        jTextField2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Mobile");

        jTextField3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel6.setText("Gender");

        jComboBox1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel7.setText("NIC");

        jTextField5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jButton1.setText("Register");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jButton2.setText("Clear All");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel8.setText("Date of Brith");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setText("Email");

        jTextField4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jButton3.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jButton4.setText("Update Detais");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField3)
                            .addComponent(jComboBox1, 0, 182, Short.MAX_VALUE)))
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(35, 35, 35)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)))
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(160, 160, 160))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String FirstName = jTextField1.getText();
        String LastName = jTextField2.getText();
        String Mobile = jTextField3.getText();
        String Email = jTextField4.getText();
        String Gender = String.valueOf(jComboBox1.getSelectedItem());
        String NIC = jTextField5.getText();
        Date selectedDate = jDateChooser1.getDate();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        String fdate = dateFormat.format(date);

        if (FirstName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter First Name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (FirstName.length() > 30) {
            JOptionPane.showMessageDialog(this, "First Name is too long (maximum 30 characters)", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!FirstName.matches("^[A-Za-z]+$")) {
            JOptionPane.showMessageDialog(this, "You Can Only Use Simple And Capital Letters For First Name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (LastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Last Name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (LastName.length() > 30) {
            JOptionPane.showMessageDialog(this, "Last Name is too long (maximum 30 characters)", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!LastName.matches("^[A-Za-z]+$")) {
            JOptionPane.showMessageDialog(this, "You Can Only Use Simple And Capital Letters For Last Name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (Mobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Mobile Number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!Mobile.matches("^07[01245678]{1}[0-9]{7}$")) {
            JOptionPane.showMessageDialog(this, "Please Enter A Valid Mobile Number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (Email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!Email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            JOptionPane.showMessageDialog(this, "Please Enter Valid Email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (Gender.matches("Select")) {
            JOptionPane.showMessageDialog(this, "Please Enter Your Gender", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (NIC.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your NIC!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!NIC.matches("^(([5,6,7,8,9]{1})([0-9]{1})([0,1,2,3,5,6,7,8]{1})([0-9]{6})([v|V|x|X]))|(([1,2]{1})([0,9]{1})([0-9]{2})([0,1,2,3,5,6,7,8]{1})([0-9]{7}))")) {
            JOptionPane.showMessageDialog(this, "Please enter your valid NIC number!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Please enter a your date of brith!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {

                ResultSet rs = MySQL2.executeSearch("SELECT * FROM `student` WHERE `nic`='" + NIC + "'");
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "This NIC number is already registered", "Warning", JOptionPane.WARNING_MESSAGE);
                    //                     logger.info("Mobile number already registered: " + Mobile);
                } else {
                    rs = MySQL2.executeSearch("SELECT * FROM `student` WHERE `contact_info`='" + Mobile + "'");
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "This mobile number is already registered", "Warning", JOptionPane.WARNING_MESSAGE);
                        //                     logger.info("Mobile number already registered: " + Mobile);
                    } else {

                        rs = MySQL2.executeSearch("SELECT * FROM `student` WHERE `email`='" + Email + "'");
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(this, "Student Already Registered", "Warning", JOptionPane.WARNING_MESSAGE);
                            //                         logger.info("Email already registered: " + Email);
                        } else {
                            if (selectedDate != null) {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String formattedDate = format.format(selectedDate); // Format the date
//                            System.out.println("Formatted Date: " + formattedDate); // Output example: "2024-11-14"

                                MySQL2.executeIUD(
                                        "INSERT INTO `student`(`nic`, `first_name`, `last_name`, `dob`, `contact_info`, `registration_date`, `email`, `gender_id`) "
                                        + "VALUES ('" + NIC + "', '" + FirstName + "', '" + LastName + "', '" + formattedDate + "', '" + Mobile + "', '" + fdate + "', '" + Email + "', '" + gender.get(Gender) + "')");
                            }
                            JOptionPane.showMessageDialog(this, "Account Created Successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
                            //                         logger.info("New customer registered successfully: " + Mobile);
                            reset();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                //               logger.log(Level.SEVERE, "Error during customer registration: ", e);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        reset();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String FirstName = jTextField1.getText();
        String LastName = jTextField2.getText();
        String Mobile = jTextField3.getText();
        String Email = jTextField4.getText();
        String Gender = String.valueOf(jComboBox1.getSelectedItem());
        String NIC = jTextField5.getText();
        String DOB = jDateChooser1.getDateFormatString();

        if (FirstName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter First Name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (FirstName.length() > 30) {
            JOptionPane.showMessageDialog(this, "First Name is too long (maximum 30 characters)", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!FirstName.matches("^[A-Za-z]+$")) {
            JOptionPane.showMessageDialog(this, "You Can Only Use Simple And Capital Letters For First Name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (LastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Last Name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (LastName.length() > 30) {
            JOptionPane.showMessageDialog(this, "Last Name is too long (maximum 30 characters)", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!LastName.matches("^[A-Za-z]+$")) {
            JOptionPane.showMessageDialog(this, "You Can Only Use Simple And Capital Letters For Last Name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (Mobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Mobile Number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!Mobile.matches("^07[01245678]{1}[0-9]{7}$")) {
            JOptionPane.showMessageDialog(this, "Please Enter A Valid Mobile Number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (Email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!Email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)(\\.[A-Za-z]{2,})$")) {
            JOptionPane.showMessageDialog(this, "Please Enter Valid Email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (Gender.matches("Select")) {
            JOptionPane.showMessageDialog(this, "Please Enter Your Gender", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (NIC.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your NIC!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!NIC.matches("^(([5,6,7,8,9]{1})([0-9]{1})([0,1,2,3,5,6,7,8]{1})([0-9]{6})([v|V|x|X]))|(([1,2]{1})([0,9]{1})([0-9]{2})([0,1,2,3,5,6,7,8]{1})([0-9]{7}))")) {
            JOptionPane.showMessageDialog(this, "Please enter your valid NIC number!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {

                ResultSet rs = MySQL2.executeSearch("SELECT * FROM student WHERE mobile='" + Mobile + "'");
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "This mobile number is already registered", "Warning", JOptionPane.WARNING_MESSAGE);
                    //                     logger.info("Mobile number already registered: " + Mobile);
                } else {

                    rs = MySQL2.executeSearch("SELECT * FROM student WHERE email='" + Email + "'");
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "Student Already Registered", "Warning", JOptionPane.WARNING_MESSAGE);
                        //                         logger.info("Email already registered: " + Email);
                    } else {
                        MySQL2.executeIUD("UPDATE student SET first_name = '" + FirstName + "', last_name = '" + LastName + "', dob = '" + DOB + "',"
                                + " contact_info = '" + Mobile + "', gender_id = '" + gender.get(Gender) + "' WHERE email = '" + Email + "'");
                        JOptionPane.showMessageDialog(this, "Account Created Successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
                        //                         logger.info("New customer registered successfully: " + Mobile);
                        reset();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                //               logger.log(Level.SEVERE, "Error during customer registration: ", e);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
