//author KAVISHA
package gui;

import com.raven.datechooser.DateBetween;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAction;
import com.raven.datechooser.listener.DateChooserAdapter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.AssignmentData;
import model.MySQL2;
import javax.swing.JLabel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AssignmentManagement extends javax.swing.JPanel {

    private static final Logger logger = LogManager.getLogger(AssignmentManagement.class);

    private TutorDashboard parent;

    private DateChooser chDate = new DateChooser();

    private String From;
    private String To;

    private String assignment;
    private String course;
    private int tutorId;

    private final String searchPlaceholder = "Search Name...";

    public AssignmentManagement(int tutorID) {
        this.tutorId = tutorID;
        initComponents();

        jTextField1.setText(searchPlaceholder);
        jTextField1.setForeground(Color.GRAY);

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (jTextField1.getText().equals(searchPlaceholder)) {
                    jTextField1.setText("");
                    jTextField1.setForeground(Color.BLACK);
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

        dateChooser();
        loadTable("", "");
//        loadTable("", "");
//        loadCourses();

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        jTable1.setDefaultRenderer(Object.class, renderer);
    }

    private static HashMap<String, String> courseMap = new HashMap<>();
    private static HashMap<String, String> tutorMap = new HashMap<>();

    private void dateChooser() {
        chDate.setTextField(jTextField2);
        chDate.setDateSelectionMode(DateChooser.DateSelectionMode.BETWEEN_DATE_SELECTED);
        chDate.setLabelCurrentDayVisible(false);
        chDate.setForeground(Color.black);
        chDate.setBackground(Color.white);
        chDate.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        chDate.addActionDateChooserListener(new DateChooserAdapter() {
            @Override
            public void dateBetweenChanged(DateBetween date, DateChooserAction action) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                From = df.format(date.getFromDate());
                To = df.format(date.getToDate());
                loadTable(From, To);
            }
        });
    }

    private void loadTable(String from, String to) {

        try {

            String TID = String.valueOf(tutorId);
            StringBuilder query = new StringBuilder();
            String searchText = jTextField1.getText().toLowerCase();
            if (searchText.equals(searchPlaceholder.toLowerCase())) {
                searchText = ""; // Ignore searchPlaceholder in search
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            boolean hasCondition = false;

            // Base query
            query.append("SELECT `assignment`.`id`, `assignment`.`title`, `assignment`.`description`, `assignment`.`due_date`, `assignment`.`tutor_id`, `tutor`.`first_name`, `tutor`.`last_name`, `courses`.`id`, `courses`.`name` FROM `assignment` ")
                    .append("INNER JOIN `tutor` ON `assignment`.`tutor_id` = `tutor`.`id` ")
                    .append("INNER JOIN `courses` ON `tutor`.`courses_id` = `courses`.`id` ")
                    .append("WHERE `tutor`.`id` = '").append(TID).append("' ");

            hasCondition = true;

            if (!searchText.isEmpty()) {
                query.append(hasCondition ? "AND " : "WHERE ");
                query.append("(LOWER(`assignment`.`title`) LIKE '%").append(searchText).append("%' ")
                        .append("OR LOWER(`courses`.`name`) LIKE '%").append(searchText).append("%' ")
                        .append("OR LOWER(`tutor`.`first_name`) LIKE '%").append(searchText).append("%' ")
                        .append("OR LOWER(`tutor`.`last_name`) LIKE '%").append(searchText).append("%') ");
                hasCondition = true;
            }

            if (from.isEmpty() || to == null) {

            } else if (to.isEmpty() || from == null) {

            } else if (from != null || to != null) {
                query.append("AND `assignment`.`due_date` > '" + from + "' AND `assignment`.`due_date` < '" + to + "' ");
            }

            // Execute query
            ResultSet rs = MySQL2.executeSearch(query.toString());
//            System.out.println(query); // Debugging purposes

            // Update table model
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                Vector<Object> vectorE = new Vector<>();
                vectorE.add(rs.getString("id"));
                vectorE.add(rs.getString("title"));
                vectorE.add(rs.getString("description"));
                vectorE.add(rs.getString("due_date"));
                String fullname = rs.getString("tutor.first_name") + " " + rs.getString("tutor.last_name");
                String coursename = rs.getString("courses.name");
//                vectorE.add(fullname);
                vectorE.add(coursename);
                tutorMap.put(fullname, rs.getString("tutor_id"));
                courseMap.put(coursename, rs.getString("courses.id"));

                model.addRow(vectorE);
            }

            jTable1.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Exception caught", e);
        } catch (Exception e) {
            logger.error("Exception caught", e);
        }
    }

    private void loadOriginalTable() {
        try {
            String TID = String.valueOf(tutorId);
            StringBuilder query = new StringBuilder();

            // Base query to fetch all assignments for the tutor
            query.append("SELECT * FROM `assignment` ")
                    .append("INNER JOIN `tutor` ON `assignment`.`tutor_id` = `tutor`.`id` ")
                    .append("INNER JOIN `courses` ON `assignment`.`courses_id` = `courses`.`id` ")
                    .append("WHERE `tutor`.`id` = '").append(TID).append("' ");

            // Execute query
            ResultSet rs = MySQL2.executeSearch(query.toString());

            // Update table model
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                Vector<Object> vectorE = new Vector<>();
                vectorE.add(rs.getString("id"));
                vectorE.add(rs.getString("title"));
                vectorE.add(rs.getString("description"));
                vectorE.add(rs.getString("due_date"));
                String fullname = rs.getString("tutor.first_name") + " " + rs.getString("tutor.last_name");
                String coursename = rs.getString("courses.name");
//                vectorE.add(fullname);
                vectorE.add(coursename);
                tutorMap.put(fullname, rs.getString("tutor_id"));
                courseMap.put(coursename, rs.getString("courses_id"));

                model.addRow(vectorE);
            }

            jTable1.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Exception caught", e);
        } catch (Exception e) {
            logger.error("Exception caught", e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel1.setText("Assignments");

        jButton5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton5.setText("Add");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton6.setText("Delete");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Double click relevant row to 'Update' the row");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Description", "Due Date", "Course Name"
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
        jScrollPane2.setViewportView(jTable1);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setText("Search");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7.setText("Sort By Due Date");

        jButton1.setText("Clear All");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 181, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(41, 41, 41))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

//        AAJ addAssignmentJFrame = new AAJ();
//        addAssignmentJFrame.setVisible(true);
        AddAssignmentJDialog AAJ = new AddAssignmentJDialog(parent, true, tutorId);
        AAJ.setVisible(true);
        loadTable("", "");

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

//        try {
//
//            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this row?", "Message",
//                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
//
//            if (option == JOptionPane.YES_OPTION) {
//
////                MySQL2.executeIUD("DELETE * FROM assignment WHERE assignment.id = '" + assignment + "' ");
//                MySQL2.executeIUD("DELETE FROM assignment WHERE id = '" + assignment + "' ");
//
//            }
//
//            loadTable("", "");
//
//        } catch (Exception e) {
//
//            logger.error("Exception caught", e);
//
//        }
         try {
        if (assignment == null || assignment.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete first.");
            return;
        }

        int option = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this row?",
            "Delete Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            MySQL2.executeIUD("DELETE FROM assignment WHERE id = '" + assignment + "'");
            loadTable("", ""); // Refresh the table
            assignment = null; // Clear the selected ID after deletion
        }

    } catch (Exception e) {
        logger.error("Exception caught while deleting assignment", e);
        JOptionPane.showMessageDialog(this, "Error while deleting assignment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
        

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int row = jTable1.getSelectedRow(); // Selected row

        // Validate indices
        if (row < 0 || row >= jTable1.getRowCount()) {
            System.out.println("Invalid row selected.");
            return;
        }

        // Get values from the table
        String idStr = String.valueOf(jTable1.getValueAt(row, 0));
        assignment = idStr; // stores the selected assignment ID for delete button
        String title = String.valueOf(jTable1.getValueAt(row, 1));
        String description = String.valueOf(jTable1.getValueAt(row, 2));
        String dueDateStr = String.valueOf(jTable1.getValueAt(row, 3));
        String courseName = String.valueOf(jTable1.getValueAt(row, 4));

        if (evt.getClickCount() == 2) {

//            // Create dialog and pass tutor ID
//            AddAssignmentJDialog AAJ = new AddAssignmentJDialog(parent, true, tutorId);
//
//            // 👉 Set the assignment ID for updating
//            try {
//                int assignmentID = Integer.parseInt(idStr);
//                AAJ.setAssignmentID(assignmentID);
//            } catch (NumberFormatException e) {
//                JOptionPane.showMessageDialog(this, "Invalid assignment ID format.", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            // Update dialog UI with selected data
//            AAJ.getjLabel1().setText("Update Assignment");
//            AAJ.getjLabel1().setHorizontalAlignment(SwingConstants.CENTER);
//            AAJ.getjTextField1().setText(title);
//            AAJ.getjTextArea1().setText(description);
//            AAJ.getjComboBox1().setSelectedItem(courseName);
//            AAJ.getjComboBox1().setEnabled(false);
//
//            // Set due date
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            try {
//                Date dueDate = formatter.parse(dueDateStr);
//                AAJ.getjDateChooser1().setDate(dueDate);
//            } catch (Exception e) {
//                System.out.println("Error converting Object to Date: " + e.getMessage());
//            }
//
//            // Enable update, disable save
//            AAJ.getjButton1().setEnabled(false);
//            AAJ.getjButton2().setEnabled(true);
//
//            // Show the update dialog
//            AAJ.setVisible(true);
//
//            // Reload table after update
//            loadTable("", "");
            // Validate due date before allowing update
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date dueDate = formatter.parse(dueDateStr);
                Date today = new Date();

                // Strip time from current date
                String todayStr = formatter.format(today);
                today = formatter.parse(todayStr);

                if (dueDate.before(today)) {
                    JOptionPane.showMessageDialog(this,
                            "You cannot enter a past due date.\nPlease choose today or a future date.",
                            "Invalid Date",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

            } catch (Exception e) {
                System.out.println("Error converting Object to Date: " + e.getMessage());
                return;
            }

            // Create dialog and pass tutor ID
            AddAssignmentJDialog AAJ = new AddAssignmentJDialog(parent, true, tutorId);

            // 👉 Set the assignment ID for updating
            try {
                int assignmentID = Integer.parseInt(idStr);
                AAJ.setAssignmentID(assignmentID);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid assignment ID format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update dialog UI with selected data
            AAJ.getjLabel1().setText("Update Assignment");
            AAJ.getjLabel1().setHorizontalAlignment(SwingConstants.CENTER);
            AAJ.getjTextField1().setText(title);
            AAJ.getjTextArea1().setText(description);
            AAJ.getjComboBox1().setSelectedItem(courseName);
            AAJ.getjComboBox1().setEnabled(false);

            // Set due date
            try {
                Date dueDate = formatter.parse(dueDateStr);
                AAJ.getjDateChooser1().setDate(dueDate);

                // Optional: prevent selecting past dates in the dialog
                AAJ.getjDateChooser1().setMinSelectableDate(new Date());

            } catch (Exception e) {
                System.out.println("Error converting Object to Date: " + e.getMessage());
            }

            // Enable update, disable save
            AAJ.getjButton1().setEnabled(false);
            AAJ.getjButton2().setEnabled(true);

            // Show the update dialog
            AAJ.setVisible(true);

            // Reload table after update
            loadTable("", "");
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if (From != null && To == null) {
            loadTable(From, "");
        } else if (From == null && To != null) {
            loadTable("", To);
        } else if (From != null && To != null) {
            loadTable(From, To);
        } else {
            loadTable("", "");
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

// Clear search field and filters
        jTextField1.setText(""); // Clear search field
//        jDateChooserFrom.setDate(null); // Clear "from" date
//        jDateChooserTo.setDate(null);   // Clear "to" date

        // Reload the original table data
        loadOriginalTable();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
