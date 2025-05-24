//author KAVISHKA
package gui;

import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAdapter;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JOptionPane;
import model.MySQL2;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import model.SecurePasswordFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author sky
 */
public class EmployeeRegistration extends CustomColor {
    private static final Logger logger = LogManager.getLogger(EmployeeRegistration.class);

//        
    private DashboardInterface parent;
    private DateChooser chDate = new DateChooser();
    public AllEmployees allEmployees;

    public static HashMap<String, Integer> genderMap = new HashMap();
    public static HashMap<String, Integer> roleMap = new HashMap();

    public EmployeeRegistration(DashboardInterface parent) {
        this.parent = parent;
        initComponents();
        loadGender();
        loadRole();
        dateChooser();
        jButton2.setEnabled(false);
        jButton4.setEnabled(false);
        SwingUtilities.invokeLater(() -> jTextField1.requestFocusInWindow());
    }

    private void switchToAllEmployees() {
        AllEmployees allEmployees = new AllEmployees(parent); // Create AllSession panel
        parent.switchPanel(allEmployees);
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

    public JLabel getjLabel8() {
        return jLabel8;
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

    //Email
    public JTextField getjTextField5() {
        return jTextField5;
    }

    //Date of Birth
    public JTextField getjTextField6() {
        return jTextField6;
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

    private void dateChooser() {
        chDate.setTextField(jTextField6);
        chDate.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        chDate.setLabelCurrentDayVisible(false);
        chDate.setForeground(Color.black);
        chDate.setBackground(Color.white);
        chDate.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

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

    private void loadRole() {

        try {
//            logger.log(Level.INFO, "Starting to load roles from the database.");

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `roles`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                String roleName = resultSet.getString("name");
                int roleId = resultSet.getInt("id");

                vector.add(roleName);
                roleMap.put(roleName, roleId);

//                logger.log(Level.INFO, "Loaded role: {0} with ID: {1}", new Object[]{roleName, roleId});
            }

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(vector);
            jComboBox2.setModel(model);

//            logger.log(Level.INFO, "Roles successfully loaded into the combo box.");
        } catch (Exception e) {
//            logger.log(Level.SEVERE, "Error occurred while loading roles.", e);
        }

    }

    private void clearAll() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jComboBox1.setSelectedItem("Select");
        jComboBox2.setSelectedItem("Select");
        jPasswordField1.setText("");
        jTextField4.setEnabled(true);
        jPasswordField1.setEnabled(true);
//        jDateChooser1.setDate(null);
        jTextField1.grabFocus();
        jComboBox1.setEnabled(true);
        jComboBox2.setEnabled(true);
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(true);
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
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Employee Registration");

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

        jButton1.setBackground(new java.awt.Color(78, 74, 207));
        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Register");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(78, 74, 207));
        jButton2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(78, 74, 207));
        jButton3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Clear All");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(78, 74, 207));
        jButton4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel9.setText("Email");

        jLabel10.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel10.setText("© 2024 VOID. All rights reserved.");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel11.setText("Date of Birth");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(110, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel8))
                            .addComponent(jLabel11))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField1)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, 223, Short.MAX_VALUE)
                                    .addComponent(jPasswordField1, javax.swing.GroupLayout.Alignment.LEADING)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField6))))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBox2, 0, 230, Short.MAX_VALUE)
                            .addComponent(jTextField5)
                            .addComponent(jTextField4)
                            .addComponent(jTextField2)))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(110, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        String email = jTextField5.getText();
        String dob = jTextField6.getText();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        String fdate = dateFormat.format(date);
        Date currentDate = new Date();

        if (FirstName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter First Name", "Warning", JOptionPane.WARNING_MESSAGE);
            jTextField1.grabFocus();
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
        } else if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            JOptionPane.showMessageDialog(this, "Please Enter Valid Email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (dob == null) {
            JOptionPane.showMessageDialog(this, "Please enter the date of birth!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = inputFormat.parse(dob);
                String formattedDOB = dateFormat.format(parsedDate);

                // Calculate age
                Calendar dobCal = Calendar.getInstance();
                dobCal.setTime(parsedDate);

                Calendar today = Calendar.getInstance();

                int age = today.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR);
                if (parsedDate != null && parsedDate.after(date)) {
                    JOptionPane.showMessageDialog((Component) parent, "Date of birth cannot be in the future!", "Invalid Date", JOptionPane.ERROR_MESSAGE);
//                      jTextField6.setText(""); // Clear the invalid date
                    // Adjust if birthday hasn't occurred this year yet
                } else if (today.get(Calendar.DAY_OF_YEAR) < dobCal.get(Calendar.DAY_OF_YEAR)) {
                    age--;
                } else if (age < 18 || age > 60) {
                    // Check age limit (e.g., 18 to 60)
                    JOptionPane.showMessageDialog((Component) parent, "Age must be between 18 and 60 years.", "Invalid Age", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Age is valid: " + age + " years.");

//            logger.log(Level.INFO, "Starting employee registration process for NIC: {0}", nic);
                    int genderId = genderMap.get(Gender);
                    int roleId = roleMap.get(role);
//            logger.log(Level.INFO, "Resolved gender ID: {0} and role ID: {1}", new Object[]{genderId, roleId});

                    ResultSet rs = MySQL2.executeSearch("SELECT * FROM `employee` WHERE `nic`='" + nic + "'");
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "This NIC number is already registered", "Warning", JOptionPane.WARNING_MESSAGE);
//                logger.log(Level.WARNING, "NIC already registered: {0}", nic);
                    } else {

                        SecurePasswordFacade spf = new SecurePasswordFacade();

                        int result = MySQL2.executeIUD("INSERT INTO `employee`"
                                + "(`first_name`,`last_name`,`contact_info`,`roles_id`,`gender_id`,`password`,`nic`,`email`,`dob`,`registration_date`)"
                                + "VALUES ('" + FirstName + "','" + LastName + "','" + Mobile + "','" + roleId + "','" + genderId + "','" + spf.encryptToFile(password, 6) + "',"
                                + "'" + nic + "','" + email + "', '" + formattedDOB + "', '" + fdate + "')");

                        spf = null;

                        if (result > 0) {
                            clearAll();
                            JOptionPane.showMessageDialog(this, "Employee registration success!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                            AllEmployees allEmployees = new AllEmployees(parent);
                            parent.switchPanel(allEmployees);
//                    logger.log(Level.INFO, "Employee registered successfully with NIC: {0}", nic);
                        }

                    }
                }

            } catch (Exception e) {
//            logger.log(Level.SEVERE, "Error occurred during employee registration for NIC: " + nic, e);
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
        String email = jTextField5.getText();
        String dob = jTextField6.getText();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        Date currentDate = new Date();

        if (FirstName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter First Name", "Warning", JOptionPane.WARNING_MESSAGE);
            jTextField1.grabFocus();
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
        } else if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            JOptionPane.showMessageDialog(this, "Please Enter Valid Email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (dob == null) {
            JOptionPane.showMessageDialog(this, "Please enter the date of birth!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = inputFormat.parse(dob);
                String formattedDOB = dateFormat.format(parsedDate);

                // Calculate age
                Calendar dobCal = Calendar.getInstance();
                dobCal.setTime(parsedDate);

                Calendar today = Calendar.getInstance();

                int age = today.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR);
                if (parsedDate != null && parsedDate.after(date)) {
                    JOptionPane.showMessageDialog((Component) parent, "Date of birth cannot be in the future!", "Invalid Date", JOptionPane.ERROR_MESSAGE);
//                      jTextField6.setText(""); // Clear the invalid date
                    // Adjust if birthday hasn't occurred this year yet
                } else if (today.get(Calendar.DAY_OF_YEAR) < dobCal.get(Calendar.DAY_OF_YEAR)) {
                    age--;
                } else if (age < 18 || age > 60) {
                    // Check age limit (e.g., 18 to 60)
                    JOptionPane.showMessageDialog((Component) parent, "Age must be between 18 and 60 years.", "Invalid Age", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Age is valid: " + age + " years.");
                    // Prepare for update
                    int genderId = genderMap.get(Gender);
                    int roleId = roleMap.get(role);

                    MySQL2.executeIUD("UPDATE `employee` SET `first_name` = '" + FirstName + "', `last_name` = '" + LastName + "', "
                            + "`contact_info` = '" + Mobile + "', `email` = '" + email + "', `roles_id` = '" + roleId + "', "
                            + "`gender_id` = '" + genderId + "', `dob` = '" + formattedDOB + "' WHERE `nic` = '" + nic + "'");
//                

                    JOptionPane.showMessageDialog(this, "Successfully Updated!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    clearAll();
                    jButton4.setEnabled(true);

                }

            } catch (Exception e) {
//            logger.log(Level.SEVERE, "Error occurred during employee update for NIC: " + nic, e);
                logger.error("Exception caught", e);
            }

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clearAll();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (parent != null) {
            switchToAllEmployees();
        } else {
            System.out.println("Null");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        jTextField2.grabFocus();
    }//GEN-LAST:event_jTextField1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
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
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables

}