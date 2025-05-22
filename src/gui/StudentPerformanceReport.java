package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author mrtkb
 */
public class StudentPerformanceReport extends javax.swing.JPanel {

    private static final Logger logger = LogManager.getLogger(StudentPerformanceReport.class);

    private TutorDashboard parent;

    private String grade;

    private int tutorID;

    private final String searchPlaceholder = "ID, Grade, Student Name, Assignment name";

    public StudentPerformanceReport(int tutorID) {
        initComponents();

        jTextField1.setText(searchPlaceholder);
        jTextField1.setForeground(Color.GRAY);

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (jTextField1.getText().equals(searchPlaceholder)) {
                    jTextField1.setText("");
                    jTextField1.setForeground(Color.BLACK); // Normal input color
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jTextField1.getText().isEmpty()) {
                    jTextField1.setForeground(Color.GRAY);
                    jTextField1.setText(searchPlaceholder);
                }
            }
        });

        this.tutorID = tutorID;
        loadTable();

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        jTable1.setDefaultRenderer(Object.class, renderer);

        jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                searchAndSort();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                searchAndSort();
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                searchAndSort();
            }
        });

        jComboBox1.addActionListener(e -> searchAndSort());

    }

    private void searchAndSort() {
//        try {
//            String keyword = jTextField1.getText().trim().toLowerCase();
//            String sortOption = (String) jComboBox1.getSelectedItem();
//
//            String query = "SELECT `grades`.`id`, `grades`.`grade`, `grades`.`comments`, `student`.`nic`,  `student`.`first_name`, "
//                    + "`student`.`last_name`, `assignment`.`id`, `assignment`.`title` FROM `grades` "
//                    + "INNER JOIN `student` ON `grades`.`student_nic` = `student`.`nic` "
//                    + "INNER JOIN `assignment` ON `grades`.`assignment_id` = `assignment`.`id` "
//                    + "INNER JOIN `tutor` ON `assignment`.`tutor_id` = `tutor`.`id` "
//                    + "WHERE `tutor`.`id` = '" + tutorID + "'";
//
//            ResultSet rs = MySQL2.executeSearch(query);
//            List<Vector<String>> rows = new ArrayList<>();
//
//            while (rs.next()) {
//                String id = rs.getString("grades.id");
//                String grade = rs.getString("grade");
//                String comments = rs.getString("grades.comments");
//                String studentName = rs.getString("student.first_name") + " " + rs.getString("student.last_name");
//                String assignmentTitle = rs.getString("assignment.title");
//
//                if (id.toLowerCase().contains(keyword) || grade.toLowerCase().contains(keyword)
//                        || studentName.toLowerCase().contains(keyword) || assignmentTitle.toLowerCase().contains(keyword)) {
//                    Vector<String> row = new Vector<>();
//                    row.add(id);
//                    row.add(grade);
//                    row.add(comments);
//                    row.add(studentName);
//                    row.add(assignmentTitle);
//                    rows.add(row);
//                }
//            }
//
//            // Sort logic
//            switch (sortOption) {
//                case "ID ASC":
//                    rows.sort((a, b) -> Integer.compare(Integer.parseInt(a.get(0)), Integer.parseInt(b.get(0))));
//                    break;
//                case "ID DESC":
//                    rows.sort((a, b) -> Integer.compare(Integer.parseInt(b.get(0)), Integer.parseInt(a.get(0))));
//                    break;
//                case "Grade ASC":
//                    rows.sort((a, b) -> a.get(1).compareToIgnoreCase(b.get(1)));
//                    break;
//                case "Grade DESC":
//                    rows.sort((a, b) -> b.get(1).compareToIgnoreCase(a.get(1)));
//                    break;
//                case "Student Name ASC":
//                    rows.sort((a, b) -> a.get(3).compareToIgnoreCase(b.get(3)));
//                    break;
//                case "Student Name DESC":
//                    rows.sort((a, b) -> b.get(3).compareToIgnoreCase(a.get(3)));
//                    break;
//                case "Assignment Name ASC":
//                    rows.sort((a, b) -> a.get(4).compareToIgnoreCase(b.get(4)));
//                    break;
//                case "Assignment Name DESC":
//                    rows.sort((a, b) -> b.get(4).compareToIgnoreCase(a.get(4)));
//                    break;
//            }
//
//            // Load to table
//            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//            model.setRowCount(0);
//            for (Vector<String> row : rows) {
//                model.addRow(row);
//            }
//
//        } catch (Exception e) {
//            logger.error("Exception in searchAndSort()", e);
//        }
        try {
            String keyword = jTextField1.getText().trim().toLowerCase();
            if (keyword.equals(searchPlaceholder.toLowerCase())) {
                keyword = ""; // Ignore placeholder during actual search
            }

            String sortOption = (String) jComboBox1.getSelectedItem();

            String query = "SELECT `grades`.`id`, `grades`.`grade`, `grades`.`comments`, `student`.`nic`,  `student`.`first_name`, "
                    + "`student`.`last_name`, `assignment`.`id`, `assignment`.`title` FROM `grades` "
                    + "INNER JOIN `student` ON `grades`.`student_nic` = `student`.`nic` "
                    + "INNER JOIN `assignment` ON `grades`.`assignment_id` = `assignment`.`id` "
                    + "INNER JOIN `tutor` ON `assignment`.`tutor_id` = `tutor`.`id` "
                    + "WHERE `tutor`.`id` = '" + tutorID + "'";

            ResultSet rs = MySQL2.executeSearch(query);
            List<Vector<String>> rows = new ArrayList<>();

            while (rs.next()) {
                String id = rs.getString("grades.id");
                String grade = rs.getString("grade");
                String comments = rs.getString("grades.comments");
                String studentName = rs.getString("student.first_name") + " " + rs.getString("student.last_name");
                String assignmentTitle = rs.getString("assignment.title");

                if (keyword.isEmpty()
                        || id.toLowerCase().contains(keyword)
                        || grade.toLowerCase().contains(keyword)
                        || studentName.toLowerCase().contains(keyword)
                        || assignmentTitle.toLowerCase().contains(keyword)) {

                    Vector<String> row = new Vector<>();
                    row.add(id);
                    row.add(grade);
                    row.add(comments);
                    row.add(studentName);
                    row.add(assignmentTitle);
                    rows.add(row);
                }
            }

            // Sort logic
            switch (sortOption) {
                case "ID ASC":
                    rows.sort((a, b) -> Integer.compare(Integer.parseInt(a.get(0)), Integer.parseInt(b.get(0))));
                    break;
                case "ID DESC":
                    rows.sort((a, b) -> Integer.compare(Integer.parseInt(b.get(0)), Integer.parseInt(a.get(0))));
                    break;
                case "Grade ASC":
                    rows.sort((a, b) -> a.get(1).compareToIgnoreCase(b.get(1)));
                    break;
                case "Grade DESC":
                    rows.sort((a, b) -> b.get(1).compareToIgnoreCase(a.get(1)));
                    break;
                case "Student Name ASC":
                    rows.sort((a, b) -> a.get(3).compareToIgnoreCase(b.get(3)));
                    break;
                case "Student Name DESC":
                    rows.sort((a, b) -> b.get(3).compareToIgnoreCase(a.get(3)));
                    break;
                case "Assignment Name ASC":
                    rows.sort((a, b) -> a.get(4).compareToIgnoreCase(b.get(4)));
                    break;
                case "Assignment Name DESC":
                    rows.sort((a, b) -> b.get(4).compareToIgnoreCase(a.get(4)));
                    break;
            }

            // Load to table
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            for (Vector<String> row : rows) {
                model.addRow(row);
            }

        } catch (Exception e) {
            logger.error("Exception in searchAndSort()", e);
        }
    }

    private void loadTable() {

        try {

            // String sort = String.valueOf(jComboBox1.getSelectedItem());
            String query = "SELECT `grades`.`id`, `grades`.`grade`, `grades`.`comments`, `student`.`nic`,  `student`.`first_name`, "
                    + "`student`.`last_name`, `assignment`.`id`, `assignment`.`title` FROM `grades` "
                    + "INNER JOIN `student` ON `grades`.`student_nic` = `student`.`nic` "
                    + "INNER JOIN `assignment` ON `grades`.`assignment_id` = `assignment`.`id` "
                    + "INNER JOIN `tutor` ON `assignment`.`tutor_id` = `tutor`.`id` WHERE `tutor`.`id` = '" + tutorID + "'";

            ResultSet resultSet = MySQL2.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("grades.id"));
                vector.add(resultSet.getString("grade"));
                vector.add(resultSet.getString("grades.comments"));
                vector.add(resultSet.getString("student.first_name") + " " + resultSet.getString("student.last_name"));
                vector.add(resultSet.getString("assignment.title"));

                model.addRow(vector);

            }

        } catch (Exception e) {
            logger.error("Exception caught", e);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel1.setText("Student Performance Report");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Grade", "Comments", "Student Name", "Assignment Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Double click relevant row to 'Update' the row");

        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton2.setText("Print");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Sort by:");

        jLabel4.setText("Search");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID ASC", "ID DESC", "Grade ASC", "Grade DESC", "Student Name ASC", "Student Name DESC", "Assignment Name ASC", "Assignment Name DESC" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 343, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        AddStudentPerformanceReportJDialog ASR = new AddStudentPerformanceReportJDialog(parent, true);
        ASR.setVisible(true);
        loadTable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete!", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String gradeIdToDelete = String.valueOf(jTable1.getValueAt(selectedRow, 0));

        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this row with Report ID: " + gradeIdToDelete + "?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            try {
                MySQL2.executeIUD("DELETE FROM grades WHERE grades.id = '" + gradeIdToDelete + "'");
                loadTable();
            } catch (Exception e) {
                logger.error("Exception caught", e);
                JOptionPane.showMessageDialog(this, "Error occurred while deleting: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_jButton3ActionPerformed


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

        String gradeId = String.valueOf(jTable1.getValueAt(row, 0));
        String Grade = String.valueOf(jTable1.getValueAt(row, 1));
        String Comments = String.valueOf(jTable1.getValueAt(row, 2));
        String StudentName = String.valueOf(jTable1.getValueAt(row, 3));
        String AssignmentName = String.valueOf(jTable1.getValueAt(row, 4));
//        String Email = String.valueOf(jTable1.getValueAt(row, 6));
//        String Gender = String.valueOf(jTable1.getValueAt(row, 7));

        if (evt.getClickCount() == 1) {
            grade = gradeId;
        }

        if (evt.getClickCount() == 2) {

            AddStudentPerformanceReportJDialog ASR = new AddStudentPerformanceReportJDialog(parent, true);

            ASR.getjLabel7().setText(gradeId);
            ASR.getjTextField1().setText(Grade);
            ASR.getjTextArea1().setText(Comments);
            ASR.getjComboBox1().setSelectedItem(StudentName);
            ASR.getjComboBox2().setSelectedItem(AssignmentName);

            ASR.getjButton1().setEnabled(false);
            ASR.getjButton2().setEnabled(true);

            ASR.setVisible(true);
            loadTable();

        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //view or print report
        String path = "src//reports//StudentPerformanceReport.jasper";
        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        HashMap<String, Object> params = new HashMap<>();
        params.put("Parameter1", dateTime);
        JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(path, params, dataSource);
        } catch (JRException e) {
            logger.error("Exception caught", e);
        }
        JasperViewer.viewReport(jasperPrint, false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
