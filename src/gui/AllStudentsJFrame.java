package gui;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;

public class AllStudentsJFrame extends javax.swing.JFrame {

    public StudentPayment setInvoice;

    public AllStudentsJFrame(StudentPayment setInvoice) {
        initComponents();
        loadTable();
        this.setInvoice = setInvoice;
    }

    private void loadTable() {
        try {

            String sort = String.valueOf(jComboBox1.getSelectedItem());

            String query = "SELECT * FROM `student` INNER JOIN `gender` ON `student`.`gender_id` = `gender`.`id`";

            if (sort.equals("First Name ASC")) {
                query += "ORDER BY `student`.`first_name` ASC";
            } else if (sort.equals("First Name DESC")) {
                query += "ORDER BY `student`.`first_name` DESC";
            } else if (sort.equals("Last Name ASC")) {
                query += "ORDER BY `student`.`last_name` ASC";
            } else if (sort.equals("Last Name DESC")) {
                query += "ORDER BY `student`.`last_name` DESC";
            } else if (sort.equals("ID ASC")) {
                query += "ORDER BY `id` ASC";
            } else if (sort.equals("Subject DESC")) {
                query += "ORDER BY `id` DESC";
            }

            ResultSet resultSet = MySQL2.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("NIC"));
                vector.add(resultSet.getString("first_name"));
                vector.add(resultSet.getString("last_name"));
                vector.add(resultSet.getString("dob"));
                vector.add(resultSet.getString("contact_info"));
                vector.add(resultSet.getString("registration_date"));
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("gender.name"));

                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        searchName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("All Students");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "NIC", "First Name", "Last Name", "Date of Birth", "Mobile", "Registration Date", "Email", "Gender"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Sort By :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First Name ASC", "First Name DESC", "Last Name ASC", "Last Name DESC", "ID ASC", "ID DESC" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setText("Generate Report");

        searchName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchNameKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setText("Search By Name or NIC :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchName, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(23, 23, 23))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(searchName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 582, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int row = jTable1.getSelectedRow(); // Selected row
        int col = jTable1.getSelectedColumn(); // Selected column

        // Validate indices
        if (row >= 0 && col >= 0 && col < jTable1.getColumnCount()) {
            Object value = jTable1.getValueAt(row, col);
            System.out.println("Value: " + value);
        } else {
            System.out.println("Invalid row/column selected.");
        }

        String NIC = String.valueOf(jTable1.getValueAt(row, 0));
        String Firstname = String.valueOf(jTable1.getValueAt(row, 1));
        String LastName = String.valueOf(jTable1.getValueAt(row, 2));
        String DOB = String.valueOf(jTable1.getValueAt(row, 3));
        String Mobile = String.valueOf(jTable1.getValueAt(row, 4));
        String RegisteredDate = String.valueOf(jTable1.getValueAt(row, 5));
        String Email = String.valueOf(jTable1.getValueAt(row, 6));
        String Gender = String.valueOf(jTable1.getValueAt(row, 7));

        if (evt.getClickCount() == 2) {
            //            switchToRegistration();
//            if (updateStudent == null) {
//                updateStudent = new StudentRegistration();
//            }

            setInvoice.getStudentNameField().setText(Firstname + " " + LastName);
//            setInvoice.getjTextField2().setText(LastName);
//            setInvoice.getjTextField3().setText(Mobile);
//            setInvoice.getjTextField4().setText(Email);
//            setInvoice.getjComboBox1().setSelectedItem(Gender);
            setInvoice.getNICLabel().setText(NIC);
//            setInvoice.getjTextField5().setEnabled(false);

//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//
//            try {
//                Date dateofBirth = formatter.parse((DOB));
//                setInvoice.getjDateChooser1().setDate(dateofBirth);
//                System.out.println("Converted Date: " + dateofBirth);
//            } catch (Exception e) {
//                System.out.println("Error converting Object to Date: " + e.getMessage());
//            }
//            updateStudent.getjButton1().setEnabled(false);
//            updateStudent.getjButton2().setEnabled(true);
//            updateStudent.getjButton3().setEnabled(true);
//            updateStudent.getjButton4().setEnabled(true);
//            switchToRegistration();
            this.dispose();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void searchNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchNameKeyReleased
        String searchText = searchName.getText().trim();
        loadTableWithSearch(searchText);
    }//GEN-LAST:event_searchNameKeyReleased

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        loadSortTable();
    }//GEN-LAST:event_jComboBox1ActionPerformed
    private void loadSortTable() {
        try {
            String sort = String.valueOf(jComboBox1.getSelectedItem());

            String query = "SELECT * FROM `student` INNER JOIN `gender` ON `student`.`gender_id` = `gender`.`id` ";

            if (sort.equals("First Name ASC")) {
                query += "ORDER BY `student`.`first_name` ASC";
            } else if (sort.equals("First Name DESC")) {
                query += "ORDER BY `student`.`first_name` DESC";
            } else if (sort.equals("Last Name ASC")) {
                query += "ORDER BY `student`.`last_name` ASC";
            } else if (sort.equals("Last Name DESC")) {
                query += "ORDER BY `student`.`last_name` DESC";
            } else if (sort.equals("NIC ASC")) {
                query += "ORDER BY `student`.`nic` ASC";
            } else if (sort.equals("NIC DESC")) {
                query += "ORDER BY `student`.`nic` DESC";
            }

            ResultSet resultSet = MySQL2.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Clear the table

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("NIC"));
                vector.add(resultSet.getString("first_name"));
                vector.add(resultSet.getString("last_name"));
                vector.add(resultSet.getString("dob"));
                vector.add(resultSet.getString("contact_info"));
                vector.add(resultSet.getString("registration_date"));
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("gender.name"));

                model.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTableWithSearch(String searchText) {
        try {
            String sort = String.valueOf(jComboBox1.getSelectedItem());
            String query = "SELECT * FROM `student` INNER JOIN `gender` ON `student`.`gender_id` = `gender`.`id`";

            if (!searchText.isEmpty()) {
                query += " WHERE `student`.`first_name` LIKE '%" + searchText + "%' "
                        + "OR `student`.`last_name` LIKE '%" + searchText + "%' "
                        + "OR `student`.`nic` LIKE '%" + searchText + "%'";
            }

            if (sort.equals("First Name ASC")) {
                query += " ORDER BY `student`.`first_name` ASC";
            } else if (sort.equals("First Name DESC")) {
                query += " ORDER BY `student`.`first_name` DESC";
            } else if (sort.equals("Last Name ASC")) {
                query += " ORDER BY `student`.`last_name` ASC";
            } else if (sort.equals("Last Name DESC")) {
                query += " ORDER BY `student`.`last_name` DESC";
            } else if (sort.equals("NIC ASC")) {
                query += " ORDER BY `student`.`nic` ASC";
            } else if (sort.equals("NIC DESC")) {
                query += " ORDER BY `student`.`nic` DESC";
            }

            ResultSet resultSet = MySQL2.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("nic"));
                vector.add(resultSet.getString("first_name"));
                vector.add(resultSet.getString("last_name"));
                vector.add(resultSet.getString("dob"));
                vector.add(resultSet.getString("contact_info"));
                vector.add(resultSet.getString("registration_date"));
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("gender.name"));

                model.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField searchName;
    // End of variables declaration//GEN-END:variables
}
