package gui;

import java.sql.ResultSet;
import model.MySQL2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rushma
 */
public class StudentDetails extends javax.swing.JDialog {
    private static final Logger logger = LogManager.getLogger(StudentDetails.class);

    public StudentDetails(java.awt.Frame parent, boolean modal, String nic) {
        super(parent, modal);
        initComponents();
        loadStudentDetails(nic);
    }

    private void loadStudentDetails(String studentNic) {
        try {
            // Query to fetch student details using inner joins
            String query = "SELECT s.nic, "
                    + "CONCAT(s.first_name, ' ', s.last_name) AS name, "
                    + "s.contact_info, "
                    + "g.name AS gender, " // Joining gender table
                    + "s.dob, "
                    + "s.registration_date, "
                    + "s.email "
                    + "FROM student s "
                    + "INNER JOIN gender g ON s.gender_id = g.id " // Joining gender table
                    + "WHERE s.nic = '" + studentNic + "'";  // Filtering by NIC

            // Execute the query
            ResultSet rs = MySQL2.executeSearch(query);

            // If data is available, populate the labels
            if (rs.next()) {
                nicLabel.setText(rs.getString("nic"));
                nameLabel.setText(rs.getString("name"));
                contactLabel.setText(rs.getString("contact_info"));
                genderLabel.setText(rs.getString("gender"));
                dobLabel.setText(rs.getString("dob"));
                registrationLabel.setText(rs.getString("registration_date"));
                emailLabel.setText(rs.getString("email"));
            } else {
                // If no data is found, show a default message
                nameLabel.setText("No data found");
            }

            // Close the ResultSet
            rs.close();
        } catch (Exception e) {
            logger.error("Exception caught", e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        contactLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        genderLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        nicLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        dobLabel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        registrationLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Student Details");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel1.setText("Student Details");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel2.setText("Name :");

        nameLabel.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        nameLabel.setText("Name :");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel4.setText("Contact Information :");

        contactLabel.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        contactLabel.setText("Contact Information :");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel5.setText("Gender :");

        genderLabel.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        genderLabel.setText("Gender :");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel7.setText("NIC :");

        nicLabel.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        nicLabel.setText("NIC :");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel8.setText("Date of Birth :");

        dobLabel.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        dobLabel.setText("NIC :");

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel9.setText("Date of Registration :");

        registrationLabel.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        registrationLabel.setText("NIC :");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel10.setText("Email :");

        emailLabel.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        emailLabel.setText("NIC :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel)
                    .addComponent(contactLabel)
                    .addComponent(genderLabel)
                    .addComponent(nicLabel)
                    .addComponent(dobLabel)
                    .addComponent(registrationLabel)
                    .addComponent(emailLabel))
                .addContainerGap(311, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nameLabel))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(contactLabel))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(genderLabel))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(nicLabel))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(dobLabel))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(registrationLabel))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(emailLabel))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel contactLabel;
    private javax.swing.JLabel dobLabel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel genderLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel nicLabel;
    private javax.swing.JLabel registrationLabel;
    // End of variables declaration//GEN-END:variables
}