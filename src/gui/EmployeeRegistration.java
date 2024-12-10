//author KAVISHKA
package gui;

import com.toedter.calendar.JDateChooser;
import javax.swing.JOptionPane;
import model.MySQL2;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author sky
 */
public class EmployeeRegistration extends javax.swing.JPanel {

    private AdminDashboard parent;

    public void setAdminDashboard(AdminDashboard ad) {
        this.parent = ad;
    }

    private EmployeeDashboard eparent;

    public void setEmployeeDashboard(EmployeeDashboard ed) {
        this.eparent = ed;
    }

    public AllEmployees allEmployees;

    public static HashMap<String, Integer> genderMap = new HashMap();
    public static HashMap<String, Integer> roleMap = new HashMap();

    public EmployeeRegistration(AllEmployees al) {
        this.allEmployees = al;
        initComponents();
        loadGender();
        loadRole();
        jButton2.setEnabled(false);
        jButton4.setEnabled(false);
    }

    private void switchToAllEmployeesAD() {
        if (parent != null && allEmployees != null) {
            parent.switchPanel(allEmployees);
        } else {
            System.err.println("Error: AdminDashboard or AllEmployees is null.");
        }
    }

    private void switchToAllEmployeesED() {
        if (eparent != null && allEmployees != null) {
            eparent.switchPanel(allEmployees);
        } else {
            System.err.println("Error: EmployeeDashboard or AllEmployees is null.");
        }
    }

    //First Name
    public JTextField getjTextField1() {
        return jTextField1; // Replace with the correct field reference
    }

    //Last Name
    public JTextField getjTextField2() {
        return jTextField2;
    }

    //Mobile
    public JTextField getjTextField3() {
        return jTextField3;
    }

    //NIC
    public JTextField getjTextField4() {
        return jTextField4;
    }

    //Gender
    public JComboBox<String> getjComboBox1() {
        return jComboBox1;
    }

    //Role
    public JComboBox<String> getjComboBox2() {
        return jComboBox2;
    }

    //Password
    public JPasswordField getjPasswordField1() {
        return jPasswordField1;
    }

    //Register
    public JButton getjButton1() {
        return jButton1;
    }

    //Update
    public JButton getjButton2() {
        return jButton2;
    }

    //Clear All
    public JButton getjButton3() {
        return jButton3;
    }

    //Back
    public JButton getjButton4() {
        return jButton4;
    }

    private void loadGender() {

        try {

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `gender`");

            Vector vector = new Vector();

            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                genderMap.put(resultSet.getString("name"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadRole() {

        try {

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `roles`");

            Vector vector = new Vector();

            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                roleMap.put(resultSet.getString("name"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox2.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void clearAll() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jComboBox1.setSelectedItem("Select");
        jComboBox2.setSelectedItem("Select");
        jPasswordField1.setText("");
        jTextField1.grabFocus();
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(true);
        jTextField4.setEnabled(true);
        jButton4.setEnabled(false);
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
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Employee Registration");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("First Name");

        jTextField1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setText("Last Name");

        jTextField2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Mobile");

        jTextField3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setText("NIC");

        jTextField4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel6.setText("Gender");

        jComboBox1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel7.setText("Role");

        jComboBox2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel8.setText("Password");

        jPasswordField1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jButton1.setText("Register");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jButton3.setText("Clear All");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(116, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)))
                            .addGap(16, 16, 16)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPasswordField1)
                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                                .addComponent(jTextField3)))
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String FirstName = jTextField1.getText();
        String LastName = jTextField2.getText();
        String Mobile = jTextField3.getText();
        String nic = jTextField4.getText();
        String Gender = String.valueOf(jComboBox1.getSelectedItem());
        String role = String.valueOf(jComboBox2.getSelectedItem());
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
        } else if (nic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter NIC", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!nic.matches("^(([5,6,7,8,9]{1})([0-9]{1})([0,1,2,3,5,6,7,8]{1})([0-9]{6})([v|V|x|X]))|(([1,2]{1})([0,9]{1})([0-9]{2})([0,1,2,3,5,6,7,8]{1})([0-9]{7}))")) {
            JOptionPane.showMessageDialog(this, "Please Enter Valid NIC", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (Gender.matches("Select")) {
            JOptionPane.showMessageDialog(this, "Please Enter Your Gender", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (role.matches("Select")) {
            JOptionPane.showMessageDialog(this, "Please Enter Your Role", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your Password", "Worning", JOptionPane.WARNING_MESSAGE);
        } else if (password.length() > 30) {
            JOptionPane.showMessageDialog(this, "Password is too long (maximum 30 characters)", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {

                int genderId = genderMap.get(Gender);
                int roleId = roleMap.get(role);

                ResultSet rs = MySQL2.executeSearch("SELECT * FROM `employee` WHERE `nic`='" + nic + "'");
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "This NIC number is already registered", "Warning", JOptionPane.WARNING_MESSAGE);
                    //                     logger.info("Mobile number already registered: " + Mobile);
                } else {

                    Integer integer = MySQL2.executeIUD("INSERT INTO `employee`"
                            + "(`first_name`,`last_name`,`contact_info`,`roles_id`,`gender_id`,`password`,`nic`)"
                            + "VALUES ('" + FirstName + "','" + LastName + "','" + Mobile + "','" + roleId + "','" + genderId + "','" + password + "','" + nic + "')");

                    JOptionPane.showMessageDialog(this, "Employee registration success!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    reset();

                }

            } catch (Exception e) {
                e.printStackTrace();
                //               logger.log(Level.SEVERE, "Error during customer registration: ", e);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String FirstName = jTextField1.getText();
        String LastName = jTextField2.getText();
        String Mobile = jTextField3.getText();
        String nic = jTextField4.getText();
        String Gender = String.valueOf(jComboBox1.getSelectedItem());
        String role = String.valueOf(jComboBox2.getSelectedItem());
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
        } else if (nic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter NIC", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!nic.matches("^(([5,6,7,8,9]{1})([0-9]{1})([0,1,2,3,5,6,7,8]{1})([0-9]{6})([v|V|x|X]))|(([1,2]{1})([0,9]{1})([0-9]{2})([0,1,2,3,5,6,7,8]{1})([0-9]{7}))")) {
            JOptionPane.showMessageDialog(this, "Please Enter Valid NIC", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (Gender.matches("Select")) {
            JOptionPane.showMessageDialog(this, "Please Enter Your Gender", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (role.matches("Select")) {
            JOptionPane.showMessageDialog(this, "Please Enter Your Role", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your Password", "Worning", JOptionPane.WARNING_MESSAGE);
        } else if (password.length() > 30) {
            JOptionPane.showMessageDialog(this, "Password is too long (maximum 30 characters)", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {

                int genderId = genderMap.get(Gender);
                int roleId = roleMap.get(role);

                MySQL2.executeIUD("UPDATE `employee` SET `first_name` = '" + FirstName + "', `last_name` = '" + LastName + "', "
                        + "`contact_info` = '" + Mobile + "', `roles_id` = '" + roleMap.get(role) + "', `gender_id` = '" + genderMap.get(Gender) + "', "
                        + "`password` = '" + password + "' WHERE `nic` = '" + nic + "'");

                JOptionPane.showMessageDialog(this, "Successfully Updated!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                reset();

            } catch (Exception e) {
                e.printStackTrace();
                //               logger.log(Level.SEVERE, "Error during customer registration: ", e);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clearAll();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (parent != null) {
            switchToAllEmployeesAD();
        } else if (eparent != null) {
            switchToAllEmployeesED();
        } else {
            System.out.println("Null");
        }
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
private void reset() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jPasswordField1.setText("");

        jComboBox1.setSelectedIndex(0);
//        logger.info("Form reset.");

    }
}
