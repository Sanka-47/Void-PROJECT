//author Kavishka
package gui;

import com.raven.datechooser.DateBetween;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAction;
import com.raven.datechooser.listener.DateChooserAdapter;
import java.awt.Color;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;

public class CancelledSessions extends javax.swing.JPanel {

    private DateChooser chDate = new DateChooser();
    private String From;
    private String To;

    public CancelledSessions() {
        initComponents();
        loadSessions("", "");
        dateChooser();
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        jTable1.setDefaultRenderer(Object.class, renderer);
    }

    private void dateChooser() {
        chDate.setTextField(jTextField6);
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
                loadSessions(From, To);
            }
        });
    }

    private void loadSessions(String from, String to) {

        try {

            String sort = String.valueOf(jComboBox1.getSelectedItem());

            String query = "SELECT * FROM `class` INNER JOIN `tutor` ON `class`.`tutor_id` = `tutor`.`id` "
                    + "INNER JOIN `courses` ON `class`.`courses_id` = `courses`.`id` "
                    + "INNER JOIN `class_status` ON `class`.`class_status_id` = `class_status`.`id` ";

            Date start = null;
            Date end = null;

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            if (from != null && !from.isEmpty() && to != null && !to.isEmpty()) {
                if (query.contains("WHERE")) {
                    query += " AND date BETWEEN '" + from + "' AND '" + to + "'";
                } else {
                    query += " WHERE date BETWEEN '" + from + "' AND '" + to + "'";
                }
            }

            if (query.contains("WHERE")) {
                query += "AND `class_status`.`name` = 'Cancelled' ";
            } else {
                query += "WHERE `class_status`.`name` = 'Cancelled' ";
            }

            if (sort.equals("Hall Number ASC")) {
                query += "ORDER BY `hallnumber` ASC ";
            } else if (sort.equals("Hall Number DESC")) {
                query += "ORDER BY `hallnumber` DESC";
            } else if (sort.equals("Tutor Name ASC")) {
                query += "ORDER BY `tutor`.`first_name` ASC";
            } else if (sort.equals("Tutor Name DESC")) {
                query += "ORDER BY `tutor`.`first_name` DESC";
            } else if (sort.equals("Subject ASC")) {
                query += "ORDER BY `courses`.`name` ASC";
            } else if (sort.equals("Subject DESC")) {
                query += "ORDER BY `courses`.`name` DESC";
            }

            ResultSet resultSet = MySQL2.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("class.id"));
                vector.add(resultSet.getString("class.name"));
                vector.add(resultSet.getString("date"));
                vector.add(resultSet.getString("start_time"));
                vector.add(resultSet.getString("end_time"));
                vector.add(resultSet.getString("hallnumber"));
                vector.add(resultSet.getString("amount"));
                vector.add(resultSet.getString("tutor.first_name") + " " + resultSet.getString("tutor.last_name"));
                vector.add(resultSet.getString("courses.name"));
                vector.add(resultSet.getString("class_status.name"));

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
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField6 = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(1000, 581));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("Cancelled Sessions");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Sort By Date:");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setText("Sort By :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hall Number ASC", "Hall Number DESC", "Tutor Name ASC", "Tutor Name DESC", "Course ASC", "Course DESC" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Class ID", "Class Name", "Date", "Start Time", "End Time", "Hall Number", "Amount", "Tutor Name", "Course Name", "Class Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(jTable1);

        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField6KeyReleased(evt);
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 475, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (From != null && To == null) {
            loadSessions(From, "");
        } else if (From == null && To != null) {
            loadSessions("", To);
        } else if (From != null && To != null) {
            loadSessions(From, To);
        } else {
            loadSessions("", "");
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased
        if (From != null && To == null) {
            loadSessions(From, "");
        } else if (From == null && To != null) {
            loadSessions("", To);
        } else if (From != null && To != null) {
            loadSessions(From, To);
        } else {
            loadSessions("", "");
        }
    }//GEN-LAST:event_jTextField6KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
