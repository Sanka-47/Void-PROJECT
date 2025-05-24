package gui;

import com.formdev.flatlaf.FlatLightLaf;
import model.MySQL2;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.logging.FileHandler;
//import java.util.logging.Level;
////import java.util.logging.Logger;
//import java.util.logging.SimpleFormatter;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.SecurePasswordFacade;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SignIn extends javax.swing.JFrame {

    private static final Logger logger = LogManager.getLogger(SignIn.class);
    public String email;

    public SignIn() {
        initComponents();
//        getLogger();
        loadIcon();
        SwingUtilities.invokeLater(() -> jTextField1.requestFocusInWindow());
    }

    @SuppressWarnings("unchecked")

//    public static Logger getLogger() {
//        try {
//            FileHandler fileHandler = new FileHandler("Admin.log", true);
//            fileHandler.setFormatter(new SimpleFormatter());
//            logger.addHandler(fileHandler);
//            logger.setUseParentHandlers(false); // Disable console logging
//        } catch (IOException e) {
//            logger.severe("Error setting up logger: " + e.getMessage());
//        }
//        return logger;
//    }
    private void loadIcon() {
        Image image = Toolkit.getDefaultToolkit().getImage("src//resource//VOID.png");
        this.setIconImage(image);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("VOID Sign In");
        setResizable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/AdminSignIn.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel2.setText("VOID Sign In");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel3.setText("NIC");

        jTextField1.setPreferredSize(new java.awt.Dimension(64, 38));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jLabel4.setText("Password");

        jButton1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton1.setText("Sign In");
        jButton1.setMinimumSize(new java.awt.Dimension(74, 38));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton2.setText("Forgot Password");
        jButton2.setPreferredSize(new java.awt.Dimension(137, 38));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/VOID.png"))); // NOI18N
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));

        jLabel6.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 14)); // NOI18N
        jLabel6.setText("© 2024 VOID. All rights reserved.");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/Gear.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(57, 57, 57))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(65, 65, 65)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String nic = jTextField1.getText();
        String password = String.valueOf(jPasswordField1.getPassword());

        if (nic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your NIC", "Warning", JOptionPane.WARNING_MESSAGE);
            logger.warn("NIC field is empty - user attempted login without NIC");
        } else if (!nic.matches("^(([5,6,7,8,9]{1})([0-9]{1})([0,1,2,3,5,6,7,8]{1})([0-9]{6})([v|V|x|X]))|(([1,2]{1})([0,9]{1})([0-9]{2})([0,1,2,3,5,6,7,8]{1})([0-9]{7}))")) {
            JOptionPane.showMessageDialog(this, "Please enter your valid NIC number!", "Warning", JOptionPane.WARNING_MESSAGE);
            logger.warn("Invalid NIC format entered: {}", nic);  // Log4j's parameterized logging
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your password", "Warning", JOptionPane.WARNING_MESSAGE);
            logger.warn("Password field is empty for NIC: {}", nic);
        } else {
            try {
                SecurePasswordFacade spf = new SecurePasswordFacade();
                logger.debug("Attempting login for NIC: {}", nic);

                // Check for employee login
                ResultSet resultSetEmployee = MySQL2.executeSearch("SELECT * FROM `employee` WHERE `nic` = '" + nic + "' AND `password` = '" + spf.encryptToFile(password, 6) + "' AND `roles_id` = '1'");
                if (resultSetEmployee.next()) {
                    String fName = resultSetEmployee.getString("first_name") + " " + resultSetEmployee.getString("last_name");
                    String employeeId = resultSetEmployee.getString("employee.id");
                    logger.info("Employee login successful - Name: {}, ID: {}", fName, employeeId);
                    EmployeeDashboard employeeDashboard = new EmployeeDashboard(fName, employeeId);
                    employeeDashboard.setVisible(true);
                    this.dispose();
                    return;
                }

                // Check for admin login
                ResultSet resultSetAdmin = MySQL2.executeSearch("SELECT * FROM `employee` WHERE `nic` = '" + nic + "' AND `password` = '" + spf.encryptToFile(password, 6) + "' AND `roles_id` = '2'");
                if (resultSetAdmin.next()) {
                    String fName = resultSetAdmin.getString("first_name") + " " + resultSetAdmin.getString("last_name");
                    String adminID = resultSetAdmin.getString("employee.id");
                    logger.info("Admin login successful - Name: {}, ID: {}", fName, adminID);
                    AdminDashboard adminDashboard = new AdminDashboard(fName, adminID);
                    adminDashboard.setVisible(true);
                    this.dispose();
                    return;
                }

                // Check for tutor login
                ResultSet resultSetTutor = MySQL2.executeSearch("SELECT * FROM `tutor` WHERE `nic` = '" + nic + "' AND `password` = '" + spf.encryptToFile(password, 6) + "'");
                if (resultSetTutor.next()) {
                    String fName = resultSetTutor.getString("first_name") + " " + resultSetTutor.getString("last_name");
                    int tutorID = resultSetTutor.getInt("tutor.id");
                    logger.info("Tutor login successful - Name: {}, ID: {}", fName, tutorID);
                    TutorDashboard tutorDashboard = new TutorDashboard(fName, tutorID);
                    tutorDashboard.setVisible(true);
                    this.dispose();
                    return;
                }

                // If no match found
                JOptionPane.showMessageDialog(this, "Invalid NIC or password", "Warning", JOptionPane.WARNING_MESSAGE);
                jTextField1.setText("");
                jPasswordField1.setText("");
                logger.warn("Login failed - Invalid credentials for NIC: {}", nic);

            } catch (SQLException e) {
                logger.error("Database error during login attempt for NIC: {}", nic, e);
                JOptionPane.showMessageDialog(this, "Database error occurred", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                logger.error("Unexpected error during login attempt", e);
                JOptionPane.showMessageDialog(this, "An unexpected error occurred", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        String nic = jTextField1.getText();

        if (nic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your NIC", "Warning", JOptionPane.WARNING_MESSAGE);

        } else if (!nic.matches("^(([5,6,7,8,9]{1})([0-9]{1})([0,1,2,3,5,6,7,8]{1})([0-9]{6})([v|V|x|X]))|(([1,2]{1})([0,9]{1})([0-9]{2})([0,1,2,3,5,6,7,8]{1})([0-9]{7}))")) {
            JOptionPane.showMessageDialog(this, "Please enter your valid NIC number!", "Warning", JOptionPane.WARNING_MESSAGE);

        } else {
            try {
                ResultSet resultSet = MySQL2.executeSearch("SELECT email FROM employee WHERE nic = '" + nic + "'");
                ResultSet resultSettu = MySQL2.executeSearch("SELECT `email` FROM `tutor` WHERE `nic` = '" + nic + "'");
                if (resultSet.next()) {
                    this.email = resultSet.getString("email");
                    ForgotPassword forgotPassword = new ForgotPassword(this, true, email, 1);
                    forgotPassword.setVisible(true);

//                } else {
//                    JOptionPane.showMessageDialog(this, "Invalid NIC or password", "Warning", JOptionPane.WARNING_MESSAGE);
//                    jTextField1.setText("");
//                    jPasswordField1.setText("");
//                    logger.log(Level.WARNING, "Invalid NIC or password for NIC: {0}", nic);
                } else if (resultSettu.next()) {
                    this.email = resultSettu.getString("email");
                    ForgotPassword forgotPassword = new ForgotPassword(this, true, email, 3);
                    forgotPassword.setVisible(true);
//                    logger.log(Level.INFO, "Email found for NIC: {0}", nic);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid NIC or password", "Warning", JOptionPane.WARNING_MESSAGE);
                    jTextField1.setText("");
                    jPasswordField1.setText("");
//                    logger.log(Level.WARNING, "Invalid NIC or password for NIC: {0}", nic);
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        SetupConnection sc = new SetupConnection();
        sc.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatLightLaf());
//        UIManager.put("Button.background", Color.decode("#4E4ACF"));
        UIManager.put("Button.focusedBackground", Color.decode("#6AAAFF"));
//        UIManager.put("Button.foreground", Color.decode("#FFFFFF"));
        UIManager.put("TextField.background", Color.decode("#F5F3FF"));
        UIManager.put("TextField.foreground", Color.decode("#4D4D4D"));
        UIManager.put("Panel.background", Color.decode("#EEF1EF"));
        UIManager.put("Table.background", Color.decode("#FFFFFF"));
        UIManager.put("Table.foreground", Color.decode("#4D4D4D"));
        UIManager.put("Table.selectionBackground", Color.decode("#40424E"));
        UIManager.put("Table.selectionForeground", Color.decode("#FFFFFF"));

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignIn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
