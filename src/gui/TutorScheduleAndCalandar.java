//author CHANULI
package gui;

import com.raven.datechooser.DateBetween;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAction;
import com.raven.datechooser.listener.DateChooserAdapter;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TutorScheduleAndCalandar extends javax.swing.JPanel {
    private static final Logger logger = LogManager.getLogger(TutorScheduleAndCalandar.class);

    private DateChooser chDate = new DateChooser();
    private DateChooser sessionChDate = new DateChooser();

//    DateChooser ravenDateChooser = new DateChooser();
    JDateChooser jDateChooser = new JDateChooser();

    private String From;
    private String To;
    private DashboardInterface parent;
    private Vector<String> rowData;
    private static HashMap<String, String> subjectMap = new HashMap<>();
    private static HashMap<String, String> courseMap = new HashMap<>();
    private static HashMap<String, String> tutorMap = new HashMap<>();
    private static HashMap<String, String> statusMap = new HashMap<>();

    // Original constructor (no changes)
    public TutorScheduleAndCalandar(DashboardInterface parent) {
        this.parent = parent;
        initComponents();
        SwingUtilities.invokeLater(() -> jTextField1.requestFocusInWindow());
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        jTable1.setDefaultRenderer(Object.class, renderer);
        loadSessions("", "");
        loadCourses();
        loadTutors();
        loadStatus();
        jComboBox3.setSelectedItem("pending");
        jComboBox3.setEnabled(false);
        dateChooser();
        SessionDateChooser();
//        loadStatus();
        // Call this method during component initialization
        initializePlaceholder();
//        jTextField5.setText("Pending");
//        jTextField5.setEditable(false);
//        jTextField1.setEditable(false);
        setPlaceholder(jFormattedTextField1, "12.00"); // Placeholder for start time
        setPlaceholder(jFormattedTextField2, "14.00"); // Placeholder for end time
        setNextSessionId();
        jTextField1.setEditable(false);
    }

// Overloaded constructor with new parameters
    public TutorScheduleAndCalandar(DashboardInterface parent, Vector<String> rowData) {
        this(parent); // Call the original constructor
        // Use the additional data
        System.out.println("row data" + rowData);
        if (!rowData.isEmpty()) {
            this.rowData = rowData;
            populateFields(rowData);
            // Example: Set data to components
//            someLabel.setText("Selected Data: " + rowData.get(0)); // Example for first column data
        }
    }

    private void dateChooser() {
        chDate.setTextField(jTextField5);
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

    private void SessionDateChooser() {
        sessionChDate.setTextField(jTextField6); // Set jTextField6 for date input
        sessionChDate.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED); // Single date selection for session
        sessionChDate.setLabelCurrentDayVisible(false);
        sessionChDate.setForeground(Color.black);
        sessionChDate.setBackground(Color.white);
        sessionChDate.setDateFormat(new SimpleDateFormat("yyyy-MM-dd")); // Ensure the format matches yyyy-MM-dd

        sessionChDate.addActionDateChooserListener(new DateChooserAdapter() {

            public void dateSelected(Date date, DateChooserAction action) {
                try {
                    // Format the selected date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(date);

                    // Set the formatted date to the text field
                    jTextField6.setText(formattedDate);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Invalid date selection. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void integrateDateChoosers() {

        // Configure Raven DateChooser
        chDate.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        chDate.setLabelCurrentDayVisible(false);
        chDate.setForeground(Color.black);
        chDate.setBackground(Color.white);
        chDate.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        // Add listener to sync Raven DateChooser with JDateChooser
        chDate.addActionDateChooserListener(new DateChooserAdapter() {
            public void dateSelected(Date date, DateChooserAction action) {
                // Update the JDateChooser with the selected date
                jDateChooser.setDate(date);
            }
        });
    }

    // Initialization Code (e.g., in the constructor or initialization method)
    private void initializePlaceholder() {
        searchInputField.setText("ID, Title, or Class Status"); // Set default placeholder text
        searchInputField.setForeground(Color.GRAY); // Set placeholder text color

        searchInputField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchInputField.getText().equals("ID, Title, or Class Status")) {
                    searchInputField.setText(""); // Clear placeholder text
                    searchInputField.setForeground(Color.BLACK); // Set text color for user input
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchInputField.getText().trim().isEmpty()) {
                    // Reset placeholder text only if the field is empty
                    searchInputField.setText("ID, Title, or Class Status");
                    searchInputField.setForeground(Color.GRAY); // Reset placeholder text color
                } else {
                    searchInputField.setForeground(Color.BLACK); // Ensure user text remains visible
                }
            }
        });
    }

    private void loadSessions(String from, String to) {

        try {

            String sort = String.valueOf(jComboBox4.getSelectedItem());

            String searchText = searchInputField.getText().toLowerCase();

            String query = "SELECT * FROM `class` INNER JOIN `tutor` ON `class`.`tutor_id` = `tutor`.`id` "
                    + "INNER JOIN `courses` ON `class`.`courses_id` = `courses`.`id` "
                    + "INNER JOIN `class_status` ON `class`.`class_status_id` = `class_status`.`id` ";

            if (!searchText.isEmpty() && !searchText.equalsIgnoreCase("id, title, or class status")) {
                if (!query.contains("WHERE")) {
                    query += "WHERE (LOWER(`class`.`id`) LIKE '%" + searchText + "%' "
                            + "OR LOWER(`class`.`name`) LIKE '%" + searchText + "%' "
                            + "OR LOWER(`class_status`.`name`) LIKE '%" + searchText + "%') ";
                } else {
                    query += "AND (LOWER(`class`.`id`) LIKE '%" + searchText + "%' "
                            + "OR LOWER(`class`.`name`) LIKE '%" + searchText + "%' "
                            + "OR LOWER(`class_status`.`name`) LIKE '%" + searchText + "%') ";
                }
            }
            Date start = null;
            Date end = null;

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            if (from != null && !from.isEmpty() && to != null && !to.isEmpty()) {
                System.out.println("Date filter: " + from + " to " + to); // Debugging: Check if date range is being set correctly
                System.out.println("Date range: " + from + " to " + to); // Check if dates are passed correctly
                if (query.contains("WHERE")) {
                    query += " AND date BETWEEN '" + from + "' AND '" + to + "'";
                } else {
                    query += " WHERE date BETWEEN '" + from + "' AND '" + to + "'";
                }
            } else {
//                System.out.println("No date range provided, skipping date filter");
            }
//            if (sort.equals("Hall Number ASC")) {
//                query += "ORDER BY `hallnumber` ASC ";
//            } else if (sort.equals("Hall Number DESC")) {
//                query += "ORDER BY `hallnumber` DESC";
//            } else if (sort.equals("Tutor Name ASC")) {
//                query += "ORDER BY `tutor`.`first_name` ASC";
//            } else if (sort.equals("Tutor Name DESC")) {
//                query += "ORDER BY `tutor`.`first_name` DESC";
//            } else if (sort.equals("Subject ASC")) {
//                query += "ORDER BY LOWER(TRIM(`courses`.`name`)) ASC";
//            } else if (sort.equals("Subject DESC")) {
//                query += "ORDER BY LOWER(`courses`.`name`) DESC";
//            } else if (sort.equals("ID ASC")) {
//                query += "ORDER BY `class`.`id` ASC";
//            } else if (sort.equals("ID DESC")) {
//                query += "ORDER BY `class`.`id` DESC";
//            }

            // Sort the query based on the selected value from the combo box
            if (sort.equals("Hall Number ASC")) {
                query += " ORDER BY `hallnumber` ASC";
            } else if (sort.equals("Hall Number DESC")) {
                query += " ORDER BY `hallnumber` DESC";
            } else if (sort.equals("Tutor Name ASC")) {
                query += " ORDER BY `tutor`.`first_name` ASC";
            } else if (sort.equals("Tutor Name DESC")) {
                query += " ORDER BY `tutor`.`first_name` DESC";
            } else if (sort.equals("Courses ASC")) {
                query += " ORDER BY `courses`.`name` ASC";
            } else if (sort.equals("Courses DESC")) {
                query += " ORDER BY `courses`.`name` DESC";
            } else if (sort.equals("ID ASC")) {
                query += " ORDER BY `class`.`id` ASC";
            } else if (sort.equals("ID DESC")) {
                query += " ORDER BY `class`.`id` DESC";
            }

//            System.out.println("Executing Query: " + query); // Ensure this is showing the correct query
            ResultSet resultSet = MySQL2.executeSearch(query);
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("class.id"));
                vector.add(resultSet.getString("courses.name"));
                vector.add(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                vector.add(resultSet.getString("class.name"));
                vector.add(resultSet.getString("date"));
                vector.add(resultSet.getString("start_time"));
                vector.add(resultSet.getString("end_time"));
                vector.add(resultSet.getString("hallnumber"));
                vector.add(resultSet.getString("amount"));
                vector.add(resultSet.getString("class_status.name"));

//                System.out.println("Adding row: " + vector); // Debugging: Print each row added to the table
                model.addRow(vector); // Add the row to the table model
            }

            model.fireTableDataChanged(); // Refresh the table data

        } catch (Exception e) {
            logger.error("Exception caught", e); // Print stack trace for debugging if there's an error
        }
    }

    private void setNextSessionId() {
        try {
            // SQL query to get the maximum session ID from the class table
            String query = "SELECT MAX(id) FROM `class`";
            ResultSet resultSet = MySQL2.executeSearch(query);

            int nextSessionId = 1; // Default session ID to 1, in case there are no entries

            if (resultSet.next()) {
                nextSessionId = resultSet.getInt(1) + 1; // Increment the max ID by 1
            }

            // Set the next session ID as the placeholder in jTextField1
            jTextField1.setText(String.format("%02d", nextSessionId)); // Format to always show two digits

        } catch (Exception e) {
            logger.error("Exception caught", e); // Handle exceptions (e.g., database connection issues)
        }
    }

    public void populateFields(Vector<String> rowData) {
        try {
            System.out.println(rowData);
            // Tutor Name
            if (rowData.size() > 0) {
                String tutorName = rowData.get(2);
                jComboBox2.setSelectedItem(tutorName);
                jComboBox1.setSelectedItem(rowData.get(3));

                if (rowData.get(1) != null) {
                    jTextField1.setText(rowData.get(1)); // Set text only if not null
                    jButton8.setEnabled(false);
                    jButton2.setEnabled(false);
                    // Query the database using the value from rowData.get(0)
                    ResultSet resultSet = MySQL2.executeSearch("SELECT `amount` FROM `class` WHERE id = '" + rowData.get(1) + "'");
                    while (resultSet.next()) {
                        jFormattedTextField3.setText(resultSet.getString("amount"));
                    }

                }
            }

            // Title
            if (rowData.size() > 1) {
                String title = rowData.get(4);
                jTextField2.setText(title); // Assuming title goes in jTextField2
            }

            // Date
//            if (rowData.size() > 2) {
//                String date = rowData.get(5);
//                java.util.Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//                jDateChooser1.setDate(parsedDate); // Assuming date goes in jDateChooser1
//            }
            // Date (replacing jDateChooser1 with jTextField6)
            if (rowData.size() > 2) {
                String date = rowData.get(5);
                jTextField6.setText(date); // Set the date directly in jTextField6
            }

            // Start Time
            if (rowData.size() > 3) {
                String startTime = rowData.get(6);
                jFormattedTextField1.setText(startTime); // Assuming startTime goes in jFormattedTextField1
            }

            // End Time
            if (rowData.size() > 4) {
                String endTime = rowData.get(7);
                jFormattedTextField2.setText(endTime); // Assuming endTime goes in jFormattedTextField2
            }

            // Hall Number
            if (rowData.size() > 5) {
                String hallNumber = rowData.get(8);
                jTextField3.setText(hallNumber); // Assuming hallNumber goes in jTextField3
            }

        } catch (Exception e) {
            logger.error("Exception caught", e);
        }

    }

    private void loadTutorSchedule() {

        try {
            // Corrected SQL query with DISTINCT and removal of unnecessary joins
            ResultSet resultSet = MySQL2.executeSearch(
                    "SELECT DISTINCT `class`.`id`, `courses`.`name` AS course_name, "
                    + "`tutor`.`first_name`, `tutor`.`last_name`, `class`.`name` AS class_name, "
                    + "`class`.`date`, `class`.`start_time`, `class`.`end_time`, `class`.`hallnumber`, "
                    + "`class`.`amount`, `class_status`.`name` AS status_name "
                    + "FROM `class` "
                    + "INNER JOIN `tutor` ON `class`.`tutor_id` = `tutor`.`id` "
                    + "INNER JOIN `courses` ON `class`.`courses_id` = `courses`.`id` "
                    + "INNER JOIN `class_status` ON `class`.`class_status_id` = `class_status`.`id`"
            );

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("course_name"));
                vector.add(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                vector.add(resultSet.getString("class_name"));
                vector.add(resultSet.getString("date"));
                vector.add(resultSet.getString("start_time"));
                vector.add(resultSet.getString("end_time"));
                vector.add(resultSet.getString("hallnumber"));
                vector.add(resultSet.getString("amount"));
                vector.add(resultSet.getString("status_name"));

                model.addRow(vector);
            }

        } catch (Exception e) {
            logger.error("Exception caught", e);
        }

    }

    private void loadCourses() {

        try {
            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `courses`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                courseMap.put(resultSet.getString("name"), resultSet.getString("id"));

                DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
                jComboBox1.setModel(model);

            }

        } catch (Exception e) {
            logger.error("Exception caught", e);
        }
    }

    private void loadStatus() {

        try {

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `class_status`");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                statusMap.put(resultSet.getString("name"), resultSet.getString("id"));
            }

            jComboBox3.setModel(new DefaultComboBoxModel<>(vector));

        } catch (Exception e) {
            logger.error("Exception caught", e);

        }
    }

    private void loadTutors() {

        try {
            ResultSet resultSet = MySQL2.executeSearch("SELECT first_name, last_name, id FROM `tutor`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
//                vector.add(resultSet.getString("name"));
//                tutorMap.put(resultSet.getString("name"), resultSet.getString("id"));

                String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                vector.add(fullName);
                tutorMap.put(fullName, resultSet.getString("id"));

                DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
                jComboBox2.setModel(model);

            }

        } catch (Exception e) {
            logger.error("Exception caught", e);
        }
    }

    private void reset() {
        setNextSessionId();
        jComboBox2.setSelectedIndex(0);
        jComboBox1.setSelectedIndex(0);
        jComboBox3.setSelectedItem("pending");
        jComboBox3.setEnabled(false);
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField6.setText("");
//      jDateChooser1.setDate(null);
        jFormattedTextField1.setText("");
        jFormattedTextField2.setText("");
        jFormattedTextField3.setText("");
        jComboBox4.setSelectedItem("Hall Number ASC");
        searchInputField.setText("ID, Title, or Class Status");
        searchInputField.setForeground(Color.GRAY);
        jTextField5.setText("");
        From = "";
        To = "";
        loadSessions("", "");
        jButton2.setEnabled(true);
        jButton8.setEnabled(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel13 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jButton2 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        searchInputField = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel13.setText("Class ID");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel8.setText("Course Name");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel15.setText("Tutor");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel7.setText("Title");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel9.setText("Date");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel10.setText("Start Time");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel11.setText("End Time");

        jFormattedTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField2ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel12.setText("Hall Number");

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel14.setText("Status");

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel16.setText("Amount");

        jButton2.setBackground(new java.awt.Color(78, 74, 207));
        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Edit Session");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(78, 74, 207));
        jButton10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Cancel Session");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(78, 74, 207));
        jButton8.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Schedule Session");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Course Name", "Tutor", "Title", "Date", "Start Time", "End Time", "Hall Number", "Amount", "Class Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
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

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setText("Sort By :");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hall Number ASC", "Hall Number DESC", "Tutor Name ASC", "Tutor Name DESC", "Courses ASC", "Courses DESC", "ID ASC", "ID DESC" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Sort By Date");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("Add Schedule");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setText("Search");

        searchInputField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchInputFieldKeyReleased(evt);
            }
        });

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setBackground(new java.awt.Color(78, 74, 207));
        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Clear All");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel15)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel10))
                                    .addGap(61, 61, 61)
                                    .addComponent(jLabel11)
                                    .addGap(54, 54, 54))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9))
                                    .addGap(11, 11, 11)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel16)))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(11, 11, 11)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(30, 30, 30)
                                    .addComponent(jLabel1))
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(11, 11, 11)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel14)
                                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchInputField, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addGap(13, 13, 13))
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13)
                            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(searchInputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel16))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jComboBox1.getAccessibleContext().setAccessibleName("");
        jFormattedTextField1.getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String sessionID = jTextField1.getText();
        String course = String.valueOf(jComboBox1.getSelectedItem());
        String tName = String.valueOf(jComboBox2.getSelectedItem());
        String className = jTextField2.getText();
//        Date date = jDateChooser1.getDate();
        String dateText = jTextField6.getText(); // Replacing jDateChooser1 with jTextField6
        String startTime = jFormattedTextField1.getText();
        String endTime = jFormattedTextField2.getText();
        String hallnumber = jTextField3.getText();
        String price = jFormattedTextField3.getText();

        // Regex for 24-hour time format (e.g., 12.00, 14.00)
        String timeRegex = "^([01]?\\d|2[0-3])\\.\\d{2}$";

        if (sessionID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Class ID!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (course.equals("Select") || courseMap.get(course) == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid course!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (tName.equals("Select") || tutorMap.get(tName) == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid tutor!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (className.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Title!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (!startTime.matches(timeRegex)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Starting time in 24-hour format (e.g., 12.00)!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (!endTime.matches(timeRegex)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Ending time in 24-hour format (e.g., 14.00)!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (dateText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Date!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (hallnumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Location!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Amount!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {

            // Validate and parse the date from jTextField6
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            try {
                date = format.parse(dateText); // Parse the date
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid Date in the format yyyy-MM-dd!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convert times to 24-hour format and check duration
            String[] startSplit = startTime.split("\\.");
            String[] endSplit = endTime.split("\\.");

            int startHour = Integer.parseInt(startSplit[0]);
            int startMinute = Integer.parseInt(startSplit[1]);
            int endHour = Integer.parseInt(endSplit[0]);
            int endMinute = Integer.parseInt(endSplit[1]);

            // Calculate duration in minutes
            int startTotalMinutes = startHour * 60 + startMinute;
            int endTotalMinutes = endHour * 60 + endMinute;

            if (endTotalMinutes < startTotalMinutes) {
                endTotalMinutes += 24 * 60; // Handle overnight sessions
            }

            int duration = endTotalMinutes - startTotalMinutes;

            if (duration > 600) { // 10 hours = 600 minutes
                JOptionPane.showMessageDialog(this, "The duration between start and end times cannot exceed 10 hours!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Determine AM/PM
            String startPeriod = (startHour < 12) ? "A.M" : "P.M";
            String endPeriod = (endHour < 12) ? "A.M" : "P.M";

            // Adjust hours for AM/PM display
            if (startHour > 12) {
                startHour -= 12;
            }
            if (endHour > 12) {
                endHour -= 12;
            }

            String formattedStartTime = String.format("%02d.%02d %s", startHour, startMinute, startPeriod);
            String formattedEndTime = String.format("%02d.%02d %s", endHour, endMinute, endPeriod);

//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("Session ID: " + sessionID);
            System.out.println("Class Name: " + className);
            System.out.println("Date: " + format.format(date));
            System.out.println("Start Time: " + formattedStartTime);
            System.out.println("End Time: " + formattedEndTime);
            System.out.println("Hall Number: " + hallnumber);
            System.out.println("Price: " + price);
            System.out.println("Tutor ID: " + tutorMap.get(tName));
            System.out.println("Course ID: " + courseMap.get(course));

            // Update the class
            MySQL2.executeIUD("UPDATE `class` SET `name` = '" + className + "', `date` = '" + format.format(date) + "', `start_time` = '" + formattedStartTime + "', "
                    + "`end_time` = '" + formattedEndTime + "', `hallnumber` = '" + hallnumber + "', `amount` = '" + price + "', `tutor_id` = '" + tutorMap.get(tName) + "', "
                    + "`courses_id` = '" + courseMap.get(course) + "' WHERE `class`.`id` = '" + sessionID + "'");

            JOptionPane.showMessageDialog(this, "Class updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            reset();
            loadSessions("", "");

        } catch (Exception e) {
            logger.error("Exception caught", e);
            JOptionPane.showMessageDialog(this, "Failed to update the class. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

    }//GEN-LAST:event_jButton10ActionPerformed

    private void setPlaceholder(JFormattedTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY); // Set placeholder text color
        textField.setText(placeholder);

        textField.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK); // Change text color to black when user starts typing
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY); // Reset text color to gray
                    textField.setText(placeholder);
                }
            }
        });
    }

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        String sessionID = jTextField1.getText();
        String course = String.valueOf(jComboBox1.getSelectedItem());
        String tName = String.valueOf(jComboBox2.getSelectedItem());
        String className = jTextField2.getText();
        String dateString = jTextField6.getText();

        String startTime = jFormattedTextField1.getText();
        String endTime = jFormattedTextField2.getText();
        String hallnumber = jTextField3.getText();
        String price = jFormattedTextField3.getText();

        Date currentDate = new Date();

        String timeRegex = "^([01]?\\d|2[0-3])\\.\\d{2}$";

        // Validation checks
        if (sessionID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Class ID!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (course.equals("Select") || courseMap.get(course) == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid course!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (tName.equals("Select") || tutorMap.get(tName) == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid tutor!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (className.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Title!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (startTime.isEmpty() || !startTime.matches(timeRegex)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Starting time in the format HH.mm (e.g., 12.00)!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (endTime.isEmpty() || !endTime.matches(timeRegex)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Ending time in the format HH.mm (e.g., 14.00)!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (hallnumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Location!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (dateString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Date!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
//        } else if (LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd")).isBefore(LocalDate.now())) {
        } 

        else if (price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Amount!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            try {
                // Parse and validate the date
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = inputFormat.parse(dateString);
                String formattedDate = inputFormat.format(parsedDate);
                System.out.println(formattedDate);

                if (parsedDate != null && parsedDate.before(currentDate)) {
                    JOptionPane.showMessageDialog((AdminDashboard) parent, "Please select a future date!", "Warning!", JOptionPane.ERROR_MESSAGE);
                } else {
                    
                    // Parse the start and end times
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH.mm");
                    Date startTimeDate = timeFormat.parse(startTime);
                    Date endTimeDate = timeFormat.parse(endTime);

                    // Check if the duration is within 10 hours
                    long durationInMillis = endTimeDate.getTime() - startTimeDate.getTime();
                    if (durationInMillis < 0) {
                        durationInMillis += 24 * 60 * 60 * 1000; // Handle overnight sessions
                    }
                    long durationInHours = durationInMillis / (60 * 60 * 1000);
                    if (durationInHours > 10) {
                        JOptionPane.showMessageDialog(this, "The session duration cannot exceed 10 hours!", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Convert times to AM/PM format for database
                    SimpleDateFormat amPmFormat = new SimpleDateFormat("hh.mm a");
                    String startTimeAmPm = amPmFormat.format(startTimeDate);
                    String endTimeAmPm = amPmFormat.format(endTimeDate);

                    // Fetch tutor details from the database
                    ResultSet resultSet1 = MySQL2.executeSearch("SELECT `id` FROM `tutor` WHERE `id` = '" + tutorMap.get(tName) + "'");

                    if (resultSet1.next()) {

                        // Get the status ID for "Pending" (assuming it's 1)
                        int pendingStatusID = 1; // Adjust this if your "Pending" status ID is different

                        // Check for time conflicts
                        String query = "SELECT * FROM `class` WHERE `tutor_id` = '" + tutorMap.get(tName) + "' AND `date` = '" + formattedDate + "' "
                                + "AND ((`start_time` <= '" + startTimeAmPm + "' AND `end_time` > '" + startTimeAmPm + "') "
                                + "OR (`start_time` < '" + endTimeAmPm + "' AND `end_time` >= '" + endTimeAmPm + "') "
                                + "OR (`start_time` >= '" + startTimeAmPm + "' AND `end_time` <= '" + endTimeAmPm + "'))";

                        ResultSet conflictCheck = MySQL2.executeSearch(query);

                        if (conflictCheck.next()) {
                            JOptionPane.showMessageDialog(this, "This tutor is already scheduled for another session during this time slot. Please choose a different time.", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        // Check for hall conflicts
                        String hallQuery = "SELECT * FROM `class` WHERE `hallnumber` = '" + hallnumber + "' AND `date` = '" + formattedDate + "' "
                                + "AND ((`start_time` <= '" + startTimeAmPm + "' AND `end_time` > '" + startTimeAmPm + "') "
                                + "OR (`start_time` < '" + endTimeAmPm + "' AND `end_time` >= '" + endTimeAmPm + "') "
                                + "OR (`start_time` >= '" + startTimeAmPm + "' AND `end_time` <= '" + endTimeAmPm + "'))";

                        ResultSet hallConflictCheck = MySQL2.executeSearch(hallQuery);

                        if (hallConflictCheck.next()) {
                            JOptionPane.showMessageDialog(this, "The selected hall is already booked during this time slot. Please choose a different location or time.", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        // Insert data into the database with the "Pending" status ID
                        MySQL2.executeIUD("INSERT INTO `class` (`id`, `name`, `date`, `start_time`, `end_time`, `hallnumber`, `amount`, `tutor_id`, `courses_id`, `class_status_id`) "
                                + "VALUES ('" + sessionID + "', '" + className + "', '" + formattedDate + "', '" + startTimeAmPm + "', '" + endTimeAmPm + "', '" + hallnumber + "', '" + price + "', "
                                + "'" + tutorMap.get(tName) + "', '" + courseMap.get(course) + "', '" + pendingStatusID + "')");

                        if (rowData != null && !rowData.isEmpty()) {
                            MySQL2.executeIUD("UPDATE `request_sessions` SET `approve_status` = 'Approved' WHERE `id` = '" + rowData.get(0) + "'");
                        }

                        JOptionPane.showMessageDialog(this, "Class added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        reset();
                        loadSessions("", "");

                    } else {
                        JOptionPane.showMessageDialog(this, "The tutor's ID does not match the record in the system. Please check the tutor information and try again.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }

            } catch (Exception e) {
                logger.error("Exception caught", e);
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        jButton8.setEnabled(false);

        jTextField1.setEditable(false);

        int row = jTable1.getSelectedRow();

        String id = String.valueOf(jTable1.getValueAt(row, 0));
        jTextField1.setText(id);

        String courseName = String.valueOf(jTable1.getValueAt(row, 1));
        jComboBox1.setSelectedItem(courseName);

        String tutor = String.valueOf(jTable1.getValueAt(row, 2));
        jComboBox2.setSelectedItem(tutor);

        String title = String.valueOf(jTable1.getValueAt(row, 3));
        jTextField2.setText(title);

        String date = String.valueOf(jTable1.getValueAt(row, 4));
        jTextField6.setText(date);

//        
//        jFormattedTextField2.setText(endTime);
        String startTime = String.valueOf(jTable1.getValueAt(row, 5)).replaceAll("[^\\dA-Za-z: ]", "");
        String endTime = String.valueOf(jTable1.getValueAt(row, 6)).replaceAll("[^\\dA-Za-z: ]", "");

// Ensure a colon is added if missing between hours and minutes
        startTime = startTime.replaceAll("(\\d{1,2})([APM]{2})", "$1:00 $2");
        endTime = endTime.replaceAll("(\\d{1,2})([APM]{2})", "$1:00 $2");

// Ensure there's a space between time and AM/PM if it's missing
        startTime = startTime.replaceAll("(\\d{4})([APM]{2})", "$1 $2");
        endTime = endTime.replaceAll("(\\d{4})([APM]{2})", "$1 $2");

// Add a colon between hours and minutes if not present
        startTime = startTime.replaceAll("(\\d{1,2})(\\d{2} [APM]{2})", "$1:$2");
        endTime = endTime.replaceAll("(\\d{1,2})(\\d{2} [APM]{2})", "$1:$2");

        SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a"); // 12-hour format with AM/PM
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm"); // 24-hour format

        try {
            // Parse the start time and convert to 24-hour format
            if (!startTime.isEmpty()) {
                java.util.Date startDate = inputFormat.parse(startTime);
                startTime = outputFormat.format(startDate);
            }

            // Parse the end time and convert to 24-hour format
            if (!endTime.isEmpty()) {
                java.util.Date endDate = inputFormat.parse(endTime);
                endTime = outputFormat.format(endDate);
            }

            // Replace the colon with a period in the formatted start and end times
            startTime = startTime.replace(":", ".");
            endTime = endTime.replace(":", ".");

            // Set the formatted start and end times
            jFormattedTextField1.setText(startTime);
            jFormattedTextField2.setText(endTime);
        } catch (ParseException e) {
            logger.error("Exception caught", e); // Handle the exception if time parsing fails
        }

        String hallNumber = String.valueOf(jTable1.getValueAt(row, 7));
        jTextField3.setText(hallNumber);

        String amount = String.valueOf(jTable1.getValueAt(row, 8));
        jFormattedTextField3.setText(amount);

        String status = String.valueOf(jTable1.getValueAt(row, 9));
        jComboBox3.setSelectedItem(status);
        jComboBox3.setEnabled(false);


    }//GEN-LAST:event_jTable1MouseClicked

    private void jFormattedTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

        String selectedCourse = String.valueOf(jComboBox1.getSelectedItem());

        // Check if the selected course has an assigned tutor
        if (courseMap.containsKey(selectedCourse)) {
            String assignedTutorId = courseMap.get(selectedCourse); // Get the assigned tutor ID
            String assignedTutorName = ""; // Initialize the tutor name

            try {
                // Fetch the tutor's name based on the assigned tutor ID
                // The query has been modified to prevent column ambiguity
                ResultSet rs = MySQL2.executeSearch("SELECT CONCAT(first_name, ' ', last_name) AS full_name FROM tutor WHERE id = '" + assignedTutorId + "'");
                if (rs.next()) {
                    assignedTutorName = rs.getString("full_name");
                }

               
                jComboBox2.removeAllItems();         // Clear any existing items
                jComboBox2.addItem(assignedTutorName); // Add the assigned tutor name
                jComboBox2.setEnabled(false);          // Enable the tutor JComboBox for visibility

            } catch (Exception e) {
                logger.error("Exception caught", e);
                JOptionPane.showMessageDialog(this, "Error fetching tutor information!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // If no tutor is assigned, reset the tutor JComboBox
            jComboBox2.removeAllItems();
            jComboBox2.addItem("Select");
            jComboBox2.setEditable(false);
        }


    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        if (From != null && To == null) {
            loadSessions(From, "");
        } else if (From == null && To != null) {
            loadSessions("", To);
        } else if (From != null && To != null) {
            loadSessions(From, To);
        } else {
            loadSessions("", "");
        }
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void searchInputFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchInputFieldKeyReleased
        if (From != null && To == null) {
            loadSessions(From, "");
        } else if (From == null && To != null) {
            loadSessions("", To);
        } else if (From != null && To != null) {
            loadSessions(From, To);
        } else {
            loadSessions("", "");
        }
    }//GEN-LAST:event_searchInputFieldKeyReleased

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
        if (From != null && To == null) {
            loadSessions(From, "");
        } else if (From == null && To != null) {
            loadSessions("", To);
        } else if (From != null && To != null) {
            loadSessions(From, To);
        } else {
            loadSessions("", "");
        }
    }//GEN-LAST:event_jTextField5KeyReleased

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        reset();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField searchInputField;
    // End of variables declaration//GEN-END:variables
}