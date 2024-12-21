//author KAVISHKA
package gui;

import model.MySQL2;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class AllTutors extends javax.swing.JPanel {

    private static HashMap<String, String> passwordMap = new HashMap<>();

    private DashboardInterface parent;

//    public void setAdminDashboard(AdminDashboard ad) {
//        this.parent = ad;
//    }
//    
//    private EmployeeDashboard eparent;
//    
//    public void setEmployeeDashboard(EmployeeDashboard ed) {
//        this.eparent = ed;
//    }
    private AddTutor updateTutor;

    public AllTutors(DashboardInterface parent) {
        this.parent = parent;
        initComponents();
        loadTable();
        this.updateTutor = new AddTutor(parent);
    }

    private void switchToRegistrationAD() {
        parent.switchPanel(updateTutor);
    }

//    private void switchToRegistrationED() {
//        eparent.switchPanel(updateTutor);
//    }
    private void loadTable() {
        try {

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `tutor` "
                    + "INNER JOIN `gender` ON `tutor`.`gender_id` = `gender`.`id` "
                    + "INNER JOIN `courses` ON `tutor`.`courses_id` = `courses`.`id`");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("tutor.id"));
                vector.add(resultSet.getString("first_name"));
                vector.add(resultSet.getString("last_name"));
                vector.add(resultSet.getString("qualification"));
                vector.add(resultSet.getString("contact_info"));
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("gender.name"));
                vector.add(resultSet.getString("courses.name"));
                vector.add(resultSet.getString("nic"));
                passwordMap.put(resultSet.getString("password"), resultSet.getString("tutor.id"));

                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        searchName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("All Tutors");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Tutor ID", "First Name", "Last Name", "Qualification", "Mobile", "Email", "Gender", "Course", "NIC"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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

        searchName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchNameActionPerformed(evt);
            }
        });
        searchName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchNameKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setText("Search By Name :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchName, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 977, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(searchName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        loadTableSort();
    }//GEN-LAST:event_jComboBox1ActionPerformed
    private void loadTableSort() {
         try {
        // Get the selected sort option from the combo box
        String sort = String.valueOf(jComboBox1.getSelectedItem());
        
        // Default query to fetch tutor data
        String query = "SELECT * FROM `tutor` "
                + "INNER JOIN `gender` ON `tutor`.`gender_id` = `gender`.`id` "
                + "INNER JOIN `courses` ON `tutor`.`courses_id` = `courses`.`id`";

        // Modify the query based on the selected sort option
        if (sort.equals("First Name ASC")) {
            query += " ORDER BY `tutor`.`first_name` ASC";
        } else if (sort.equals("First Name DESC")) {
            query += " ORDER BY `tutor`.`first_name` DESC";
        } else if (sort.equals("Last Name ASC")) {
            query += " ORDER BY `tutor`.`last_name` ASC";
        } else if (sort.equals("Last Name DESC")) {
            query += " ORDER BY `tutor`.`last_name` DESC";
        } else if (sort.equals("ID ASC")) {
            query += " ORDER BY `tutor`.`id` ASC";
        } else if (sort.equals("ID DESC")) {
            query += " ORDER BY `tutor`.`id` DESC";
        }

        // Execute the query to fetch sorted data
        ResultSet resultSet = MySQL2.executeSearch(query);

        // Get the table model and clear the existing rows
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        // Add rows to the table for each result from the query
        while (resultSet.next()) {
            Vector<String> vector = new Vector<>();
            vector.add(resultSet.getString("tutor.id"));
            vector.add(resultSet.getString("first_name"));
            vector.add(resultSet.getString("last_name"));
            vector.add(resultSet.getString("qualification"));
            vector.add(resultSet.getString("contact_info"));
            vector.add(resultSet.getString("email"));
            vector.add(resultSet.getString("gender.name"));
            vector.add(resultSet.getString("courses.name"));
            vector.add(resultSet.getString("nic"));

            // Store the password mapping for later use
            passwordMap.put(resultSet.getString("password"), resultSet.getString("tutor.id"));

            // Add the row to the table model
            model.addRow(vector);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

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

        String TutorID = String.valueOf(jTable1.getValueAt(row, 0));
        String FirstName = String.valueOf(jTable1.getValueAt(row, 1));
        String LastName = String.valueOf(jTable1.getValueAt(row, 2));
        String Qualification = String.valueOf(jTable1.getValueAt(row, 3));
        String Mobile = String.valueOf(jTable1.getValueAt(row, 4));
        String Email = String.valueOf(jTable1.getValueAt(row, 5));
        System.out.println(Email);
        String Gender = String.valueOf(jTable1.getValueAt(row, 6));
        System.out.println(Gender);
        String Course = String.valueOf(jTable1.getValueAt(row, 7));
        String NIC = String.valueOf(jTable1.getValueAt(row, 8));

        if (evt.getClickCount() == 2) {
//            switchToRegistration();

            updateTutor.getjTextField1().setText(FirstName);
            updateTutor.getjTextField2().setText(LastName);
            updateTutor.getjTextField3().setText(NIC);
            updateTutor.getjTextField4().setText(Qualification);
            updateTutor.getjComboBox1().setSelectedItem(Course);
            updateTutor.getjComboBox2().setSelectedItem(Gender);
            updateTutor.getjTextField5().setText(Mobile);
            updateTutor.getjPasswordField1().setText(passwordMap.get(TutorID));
            System.out.println(passwordMap.get(TutorID));
            updateTutor.getjTextField6().setText(Email);

            updateTutor.getjButton1().setEnabled(false);
            updateTutor.getjButton2().setEnabled(true);
            updateTutor.getjTextField3().setEnabled(false);
            updateTutor.getjButton3().setEnabled(true);
            updateTutor.getjButton4().setEnabled(true);

            if (parent != null) {
                switchToRegistrationAD();
            } //            else if (eparent != null) {
            //                switchToRegistrationED();
            //            }
            else {
                System.out.println("Null");
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void searchNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchNameKeyReleased
        String searchText = searchName.getText().trim();
        loadTableWithSearch(searchText);
    }//GEN-LAST:event_searchNameKeyReleased

    private void searchNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchNameActionPerformed
        String selectedSort = (String) jComboBox1.getSelectedItem();
        loadTable(selectedSort);
    }//GEN-LAST:event_searchNameActionPerformed

    private void loadTable(String sortOption) {
        try {
            String orderByClause = "";

            switch (sortOption) {
                case "First Name ASC":
                    orderByClause = "ORDER BY `first_name` ASC";
                    break;
                case "First Name DESC":
                    orderByClause = "ORDER BY `first_name` DESC";
                    break;
                case "Last Name ASC":
                    orderByClause = "ORDER BY `last_name` ASC";
                    break;
                case "Last Name DESC":
                    orderByClause = "ORDER BY `last_name` DESC";
                    break;
                case "ID ASC":
                    orderByClause = "ORDER BY `tutor`.`id` ASC";
                    break;
                case "ID DESC":
                    orderByClause = "ORDER BY `tutor`.`id` DESC";
                    break;
                default:
                    break; // No sorting applied
            }

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `tutor` "
                    + "INNER JOIN `gender` ON `tutor`.`gender_id` = `gender`.`id` "
                    + "INNER JOIN `courses` ON `tutor`.`courses_id` = `courses`.`id` "
                    + orderByClause);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("tutor.id"));
                vector.add(resultSet.getString("first_name"));
                vector.add(resultSet.getString("last_name"));
                vector.add(resultSet.getString("qualification"));
                vector.add(resultSet.getString("contact_info"));
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("gender.name"));
                vector.add(resultSet.getString("courses.name"));
                vector.add(resultSet.getString("nic"));
                passwordMap.put(resultSet.getString("password"), resultSet.getString("tutor.id"));
//
                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTableWithSearch(String searchText) {
        try {
            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `tutor` "
                    + "INNER JOIN `gender` ON `tutor`.`gender_id` = `gender`.`id` "
                    + "INNER JOIN `courses` ON `tutor`.`courses_id` = `courses`.`id` "
                    + "WHERE `first_name` LIKE '%" + searchText + "%' OR `last_name` LIKE '%" + searchText + "%'");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("tutor.id"));
                vector.add(resultSet.getString("first_name"));
                vector.add(resultSet.getString("last_name"));
                vector.add(resultSet.getString("qualification"));
                vector.add(resultSet.getString("contact_info"));
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("gender.name"));
                vector.add(resultSet.getString("courses.name"));
                vector.add(resultSet.getString("nic"));
                passwordMap.put(resultSet.getString("password"), resultSet.getString("tutor.id"));

                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField searchName;
    // End of variables declaration//GEN-END:variables
}
