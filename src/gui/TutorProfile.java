package gui;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.MySQL2;
import model.SecurePasswordFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author sky
 */
public class TutorProfile extends CustomColor {
    private static final Logger logger = LogManager.getLogger(TutorProfile.class);

    private int tutorID;

    public static HashMap<String, Integer> genderMap = new HashMap();
    public static HashMap<String, Integer> coursesMap = new HashMap();

    public TutorProfile(int tutorID) {
        initComponents();
        loadGender();
        loadcourses();
        this.tutorID = tutorID;
        System.out.println(tutorID);
        jTextField5.setEnabled(false);
        jTextField6.setEnabled(false);
        jComboBox1.setEnabled(false);
        jComboBox2.setEnabled(false);
        loadEmployeeDetails();
        SwingUtilities.invokeLater(() -> jTextField1.requestFocusInWindow());
    }

    private void loadGender() {

        try {
//            logger.log(Level.INFO, "Starting to load genders from the database.");

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `gender`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                String genderName = resultSet.getString("name");
                int genderId = resultSet.getInt("id");

                vector.add(genderName);
                genderMap.put(genderName, genderId);

//                logger.log(Level.INFO, "Loaded gender: {0} with ID: {1}", new Object[]{genderName, genderId});
            }

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(vector);
            jComboBox1.setModel(model);

//            logger.log(Level.INFO, "Genders successfully loaded into the combo box.");
        } catch (Exception e) {
//            logger.log(Level.SEVERE, "Error occurred while loading genders.", e);
        }

    }

    private void loadcourses() {

        try {
//            logger.log(Level.INFO, "Starting to load roles from the database.");

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `courses`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                String coursesName = resultSet.getString("name");
                int coursesId = resultSet.getInt("id");

                vector.add(coursesName);
                coursesMap.put(coursesName, coursesId);

//                logger.log(Level.INFO, "Loaded role: {0} with ID: {1}", new Object[]{roleName, roleId});
            }

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(vector);
            jComboBox2.setModel(model);

//            logger.log(Level.INFO, "Roles successfully loaded into the combo box.");
        } catch (Exception e) {
//            logger.log(Level.SEVERE, "Error occurred while loading roles.", e);
        }

    }

    private void loadEmployeeDetails() {

        try {

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `tutor` "
                    + "INNER JOIN `gender` ON `tutor`.`gender_id` = `gender`.`id` "
                    + "INNER JOIN `courses` ON `tutor`.`courses_id` = `courses`.`id` "
                    + "WHERE `tutor`.`id` ='" + tutorID + "' ");

            if (resultSet.next()) {

                jTextField1.setText(resultSet.getString("first_name"));
                jTextField2.setText(resultSet.getString("last_name"));
                jTextField3.setText(resultSet.getString("contact_info"));
                jTextField4.setText(resultSet.getString("email"));
                jTextField5.setText(resultSet.getString("qualification"));
                jTextField6.setText(resultSet.getString("nic"));

                // Get values from result set
                String gender = resultSet.getString("gender.name");
                String courses = resultSet.getString("courses.name");

                // Set combo box selections
                jComboBox1.setSelectedItem(gender);
                jComboBox2.setSelectedItem(courses);
                SecurePasswordFacade spf = new SecurePasswordFacade();
                jPasswordField1.setText(spf.decryptFromFile(resultSet.getString("password"), 6));
                spf = null;
            } else {
                System.out.println("No tutor found with ID: " + tutorID);
            }

        } catch (Exception e) {
            logger.error("Exception caught", e);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel1.setText("First Name");

        jTextField1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel2.setText("Last Name");

        jTextField2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel3.setText("Mobile");

        jTextField3.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel6.setText("Email");

        jTextField4.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel9.setText("Qualification");

        jTextField5.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel7.setText("NIC");

        jTextField6.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel5.setText("Gender");

        jComboBox1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel8.setText("Course");

        jComboBox2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel4.setText("Password");

        jPasswordField1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N

        jButton4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton4.setText("View");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton4MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton4MouseReleased(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jButton2.setText("Update Details");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Tutor Profile");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(280, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(56, 56, 56)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(280, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        jTextField2.grabFocus();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        jTextField3.grabFocus();
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MousePressed
        jPasswordField1.setEchoChar('\u0000');
    }//GEN-LAST:event_jButton4MousePressed

    private void jButton4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseReleased
        jPasswordField1.setEchoChar('\u2022');
    }//GEN-LAST:event_jButton4MouseReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //        String pw = String.valueOf(jPasswordField1.getPassword());

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String FirstName = jTextField1.getText();
        String LastName = jTextField2.getText();
        String Mobile = jTextField3.getText();
        String email = jTextField4.getText();
        String qualification = jTextField5.getText();
        String nic = jTextField6.getText();
        String Gender = String.valueOf(jComboBox1.getSelectedItem());
        String courses = String.valueOf(jComboBox2.getSelectedItem());
        String password = String.valueOf(jPasswordField1.getPassword());

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
        } else if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            JOptionPane.showMessageDialog(this, "Please Enter Valid Email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (nic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter NIC", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!nic.matches("^(([5,6,7,8,9]{1})([0-9]{1})([0,1,2,3,5,6,7,8]{1})([0-9]{6})([v|V|x|X]))|(([1,2]{1})([0,9]{1})([0-9]{2})([0,1,2,3,5,6,7,8]{1})([0-9]{7}))")) {
            JOptionPane.showMessageDialog(this, "Please Enter Valid NIC", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (Gender.matches("Select")) {
            JOptionPane.showMessageDialog(this, "Please Enter Your Gender", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (courses.matches("Select")) {
            JOptionPane.showMessageDialog(this, "Please Enter Your Role", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Password", "Warning", JOptionPane.WARNING_MESSAGE);

//            }else if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")) {
//                    JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long and include a letter, a number, and a special character.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            int genderId = genderMap.get(Gender);
            int roleId = coursesMap.get(courses);

            try {

                boolean canUpdate = false;

                // Check if the email exists
                ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `tutor` "
                        + "INNER JOIN `gender` ON `tutor`.`gender_id` = `gender`.`id` "
                        + "INNER JOIN `courses` ON `tutor`.`courses_id` = `courses`.`id` "
                        + "WHERE `tutor`.`id` ='" + tutorID + "' ");
                if (resultSet.next()) {

                    if (!resultSet.getString("password").equals(password)
                            || !resultSet.getString("first_name").equals(FirstName)
                            || !resultSet.getString("last_name").equals(LastName)
                            || !resultSet.getString("contact_info").equals(Mobile)
                            || !resultSet.getString("email").equals(email)
                            || !resultSet.getString("qualification").equals(qualification)
                            || !resultSet.getString("nic").equals(nic)
                            || !resultSet.getString("gender.name").equals(Gender)
                            || !resultSet.getString("courses.name").equals(courses)) {
                        canUpdate = true;
                    } else {
                        JOptionPane.showMessageDialog(this, "Please Change Data Before Updating", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    canUpdate = true;
                }

                if (canUpdate) {

                    SecurePasswordFacade spf = new SecurePasswordFacade();

                    Integer integer = MySQL2.executeIUD("UPDATE `tutor` SET `first_name` = '" + FirstName + "', `last_name` = '" + LastName + "', "
                            + "`contact_info` = '" + Mobile + "', `email` = '" + email + "', "
                            + "`gender_id` = '" + genderId + "', `password` = '" + spf.encryptToFile(password, 6) + "' WHERE `nic` = '" + nic + "'");

                    JOptionPane.showMessageDialog(this, "Successfully Updated!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    //            logger.log(Level.INFO, "Employee updated successfully for NIC: {0}", nic);

                    spf = null;
                }

            } catch (Exception e) {
                //            logger.log(Level.SEVERE, "Error occurred during employee update for NIC: " + nic, e);
                logger.error("Exception caught", e);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}