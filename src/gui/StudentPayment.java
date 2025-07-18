//author SANKA
package gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.InvoiceItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.MySQL2;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class StudentPayment extends javax.swing.JFrame {
    private static final Logger logger = LogManager.getLogger(StudentPayment.class);

    private StudentPayment parent;
    private String email;
    private static String code;
    private static int Subject_id;
    private static String description;
    private static String Student_id;
    private static double Selling_price;

    HashMap<String, InvoiceItem> invoiceItemMap = new HashMap<>();
    HashMap<String, String> paymentMethodMap = new HashMap<>();

//    
    private static DefaultTableModel model;

//    public void setCustomerName(String name) {
//        customerName.setText(name);
//    }
    private void loadPaymentMethods() {

        try {

            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `payment_method`");

            Vector<String> vector = new Vector<>();

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                paymentMethodMap.put(resultSet.getString("name"), resultSet.getString("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);

        } catch (Exception e) {
//            logger.severe("Error loading payment methods: " + e.getMessage());
            logger.error("Exception caught", e);

        }
    }

    private void EmptyTable() {
        DefaultTableModel m = (DefaultTableModel) jTable1.getModel();
        m.setRowCount(0);
        this.model = m;
        SwingUtilities.invokeLater(() -> StudentNameFields.requestFocusInWindow());
    }

    private void loadInvoiceItems() {

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        jTable1.setDefaultRenderer(Object.class, renderer);

        EmptyTable();

        total = 0;
        int rowCount = 1; // Counter for row numbers

        for (InvoiceItem invoiceItem : invoiceItemMap.values()) {
            Vector<String> vector = new Vector<>();

            vector.add(String.valueOf(rowCount)); // Add row count as the first column
            vector.add(invoiceItem.getSubject_name());
            vector.add(invoiceItem.getDesc());
            vector.add(invoiceItem.getSellingPrice());

            // Calculate total fees
            double courseFee = Double.parseDouble(invoiceItem.getSellingPrice());
            total += courseFee;

            vector.add(String.valueOf(courseFee)); // Add course fee as a column

            model.addRow(vector);

            rowCount++; // Increment the row count
        }

        totalField.setText(String.valueOf(total));

        // Call calculate() if it performs other operations
        calculate();
    }

//    //Student Name
//    public JTextField getjTextField2() {
//        return StudentNameFields;
//    }
//
//    //Student NIC
//    public JLabel getjLabel6() {
//        return customerName;
//    }
    //Course Name
    public JTextField getSubjectNameField() {
        return SubjectNameField;
    }

    //Student Name
    public JTextField getStudentNameField() {
        return StudentNameFields;
    }

    //Student NIC
    public JLabel getNICLabel() {
        return studentID;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    //stock id
//    public JTextField getjTextField3() {
//        return jTextField3;
//    }
    // Brand Name
//    public JLabel getjLabel10() {
//        return jLabel10;
//    }
    // Product Name
//    public JLabel getjLabel12() {
//        return jLabel12;
//    }
    //Selling Price 
//    public JTextField getjTextField5() {
//        return jTextField5;
//    }
    // MFD
//    public JLabel getjLabel14() {
//        return jLabel14;
//    }
    // EXP
//    public JLabel getjLabel16() {
//        return jLabel16;
//    }
    //QTY
//    public JFormattedTextField getjFormattedTextField1() {
//        return jFormattedTextField1;
//    }
    // Available Qty
//    public JLabel getjLabel21() {
//        return jLabel21;
//    }
    // Available Points
//    public JTextField getjTextField4() {
//        return jTextField4;
//    }
    public StudentPayment(String Fname, String Lname) {
        initComponents();
        employeeLable.setText(Fname + " " + Lname);

        printInvoiceButton.setEnabled(false);
//        employeeLable.setText(Signin.getEmail());
        generateInvoiceID();
//        jLabel3.setText("chamika@gmail.com");
        loadPaymentMethods();
        loadIcon();
    }

    private void loadIcon() {
        Image image = Toolkit.getDefaultToolkit().getImage("src//resource//VOID.png");
        this.setIconImage(image);
    }

    private void generateInvoiceID() {
        long id = System.currentTimeMillis();
        invoiceNumberField.setText(String.valueOf(id));
    }

    private double total = 0;
    private double discount = 0;
    private double payment = 0;
    private boolean withdawPoints = false;
    private double balance = 0;
    private String paymentMethod = "Select";
    private double newPoints = 0;

    private void calculate() {

        //settings
//        if (discountField.getText().isEmpty()) {
//            discount = 0;
//        } else {
//            discount = Double.parseDouble(discountField.getText());
//        }
        if (paymentField.getText().isEmpty()) {
            payment = 0;
        } else {
            payment = Double.parseDouble(paymentField.getText());
        }

        total = Double.parseDouble(totalField.getText());

        paymentMethod = String.valueOf(jComboBox1.getSelectedItem());
        //settings

        total -= discount;

        if (total < 0) {
            //error
        } else {
            //discount ok
//            if (withdawPoints) {
//
//                if (Double.parseDouble(jTextField4.getText()) == total) {
//                    newPoints = 0;
//                    total = 0;
//                    //no payment required
//
//                } else if (Double.parseDouble(jTextField4.getText()) < total) {
//                    newPoints = 0;
//                    total -= Double.parseDouble(jTextField4.getText());
//                    //payment required
//
//                } else {
//                    newPoints = Double.parseDouble(jTextField4.getText()) - total;
//                    total = 0;
//                    //no payment required
//                }
//            }
        }

        if (paymentMethod.equals("Cash")) {
            //cash
            paymentField.setEditable(true);
            balance = payment - total;

            if (balance < 0) {
                printInvoiceButton.setEnabled(false);
            } else {
                printInvoiceButton.setEnabled(true);
            }

        } else {
            //card
            payment = total;
            balance = 0;
            paymentField.setText(String.valueOf(payment));
            paymentField.setEditable(false);
            printInvoiceButton.setEnabled(true);
        }

        balanceFiled.setText(String.valueOf(balance));

    }

    private void clearAll() {
        invoiceNumberField.setText("");
        StudentNameFields.setText("");
        SubjectNameField.setText("");
        studentID.setText("STUDENT NIC HERE");
        totalField.setText("0");
        jComboBox1.setSelectedItem("Select");
        paymentField.setText("0");
        balanceFiled.setText("0");
        EmptyTable();
        printInvoiceButton.setEnabled(false);
        invoiceItemMap.clear();
        generateInvoiceID();
        jButton4.setEnabled(true);
        jButton5.setEnabled(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        employeeLable = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        invoiceNumberField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        StudentNameFields = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        studentID = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        SubjectNameField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        totalField = new javax.swing.JFormattedTextField();
        balanceFiled = new javax.swing.JFormattedTextField();
        paymentField = new javax.swing.JFormattedTextField();
        printInvoiceButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Invoice");

        jPanel4.setBackground(new java.awt.Color(233, 231, 224));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel1.setText("Invoice");

        jLabel2.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel2.setText("Employee :");

        employeeLable.setText("Employee Details");

        jLabel4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel4.setText("Invoice Number :");

        invoiceNumberField.setEditable(false);
        invoiceNumberField.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel5.setText("Student :");

        StudentNameFields.setEditable(false);
        StudentNameFields.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        jButton4.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jButton4.setText("Select");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        studentID.setFont(new java.awt.Font("Poppins", 3, 14)); // NOI18N
        studentID.setForeground(new java.awt.Color(131, 44, 46));
        studentID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        studentID.setText("STUDENT NIC HERE");
        studentID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setText("Scan QR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(studentID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(employeeLable, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))
                            .addComponent(invoiceNumberField)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(StudentNameFields))))
                .addGap(60, 60, 60))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(employeeLable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(invoiceNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(StudentNameFields)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(studentID, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(235, 235, 235));

        jButton2.setBackground(new java.awt.Color(78, 74, 207));
        jButton2.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Clear All");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(78, 74, 207));
        jButton3.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Add to Invoice");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel6.setText("Course");

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton6.setText("Select Course");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SubjectNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jLabel6)
                    .addComponent(SubjectNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Course Name", "Description", "Course Fee"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel3.setBackground(new java.awt.Color(221, 225, 227));

        jLabel18.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel18.setText("Total :");

        jLabel20.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel20.setText("Payment Method :");

        jComboBox1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel22.setText("Payment :");

        jLabel23.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel23.setText("Balance :");

        totalField.setEditable(false);
        totalField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        totalField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalField.setText("0");
        totalField.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        totalField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalFieldActionPerformed(evt);
            }
        });

        balanceFiled.setEditable(false);
        balanceFiled.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        balanceFiled.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        balanceFiled.setText("0");
        balanceFiled.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N

        paymentField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        paymentField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        paymentField.setText("0");
        paymentField.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        paymentField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                paymentFieldKeyReleased(evt);
            }
        });

        printInvoiceButton.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        printInvoiceButton.setForeground(new java.awt.Color(255, 51, 51));
        printInvoiceButton.setText("Print Invoice");
        printInvoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printInvoiceButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23)
                    .addComponent(jLabel20)
                    .addComponent(jLabel18)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalField)
                    .addComponent(balanceFiled, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(paymentField)
                    .addComponent(printInvoiceButton, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(paymentField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(balanceFiled, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(printInvoiceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void totalFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalFieldActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String subject_id = String.valueOf(Subject_id);
        String nic = studentID.getText();  // Use nic directly for the query
        String student_name = StudentNameFields.getText();
        String selling_price = String.valueOf(Selling_price);
        String description = String.valueOf(Selling_price);
        String paymentMethodID = paymentMethodMap.get(String.valueOf(jComboBox1.getSelectedItem()));

        if (student_name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a student", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (SubjectNameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a subject", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                // Correct the query to use nic instead of student_id
                ResultSet rs = MySQL2.executeSearch("SELECT `nic` FROM `student` WHERE `nic` ='" + nic + "'");

                ResultSet rs1 = MySQL2.executeSearch("SELECT `nic`, `student_nic`, `invoice`.`id`, `courses_id` FROM `student` INNER JOIN `invoice` ON `student`.`nic` = `invoice`.`student_nic` INNER JOIN `invoice_item` ON `invoice`.`id` = `invoice_item`.`invoice_id` WHERE `invoice`.`student_nic` = '" + nic + "' AND `invoice_item`.`courses_id` = '" + Subject_id + "'");

                if (rs.next()) {

                    if (rs1.next()) {

                        JOptionPane.showMessageDialog(this, "Student has already purchased the course!", "Warning", JOptionPane.WARNING_MESSAGE);
                        
                    } else {
                        ResultSet ds = MySQL2.executeSearch("SELECT `course_description` FROM `courses` WHERE `id`='" + subject_id + "' ");
                        ds.next();
                        // Create InvoiceItem with correct values
                        InvoiceItem invoiceItem = new InvoiceItem();
                        invoiceItem.setStudent_id(nic);  // Use nic as the student ID
                        invoiceItem.setStudent_name(student_name);
                        invoiceItem.setSubject_id(subject_id);
                        invoiceItem.setSubject_name(SubjectNameField.getText());
                        invoiceItem.setNic(nic);
                        invoiceItem.setSellingPrice(selling_price);
                        invoiceItem.setDesc(ds.getString("course_description"));

                        // Check and add/update invoice items
                        if (invoiceItemMap.get(nic + "-" + subject_id) == null) {
                            invoiceItemMap.put(nic + "-" + subject_id, invoiceItem);
                        } else {
                            JOptionPane.showMessageDialog(this, "Subject Already Added", "Info", JOptionPane.INFORMATION_MESSAGE);
                        }

                        // Refresh and reset
                        loadInvoiceItems();
                        jButton4.setEnabled(false);
                        jButton5.setEnabled(false);  // Disable button if required
                        reset();  // Clear the input fields
                        
//                        JOptionPane.showMessageDialog(this, "Student has already purchased the course!", "Warning", JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    
                    JOptionPane.showMessageDialog(this, "Student not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                logger.error("Exception caught", e);
            }
        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        //3
        calculate();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void paymentFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_paymentFieldKeyReleased
        // 4
        calculate();
    }//GEN-LAST:event_paymentFieldKeyReleased

    private void printInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printInvoiceButtonActionPerformed

        try {

            String invoiceID = invoiceNumberField.getText();
            String studentiD = studentID.getText();
            String student_name = StudentNameFields.getText();
            String employeeName = employeeLable.getText();
            String totalAmount = totalField.getText();
            String paymentMethod = String.valueOf(jComboBox1.getSelectedItem());
            String paymentMethodID = paymentMethodMap.get(paymentMethod);
            String paidAmount = paymentField.getText();
            String balance = balanceFiled.getText();
            String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            //insert to invoice
            if (!studentiD.isEmpty()) {
                MySQL2.executeIUD("INSERT INTO `invoice` VALUES('" + invoiceID + "','" + dateTime + "','" + paidAmount + "',"
                        + "'" + studentiD + "','" + paymentMethodID + "')");
            }

            for (InvoiceItem invoiceItem : invoiceItemMap.values()) {

                //insert to invoiceItem
                MySQL2.executeIUD("INSERT INTO `invoice_item`(`courses_id`,`invoice_id` )"
                        + "VALUES('" + invoiceItem.getSubject_id() + "','" + invoiceID + "')");
                //insert to invoiceItem
                MySQL2.executeIUD("INSERT INTO `student_courses` (`enrollment_date`, `status`, `student_nic`, `courses_id`) "
                        + "VALUES ('" + dateTime + "', 'Active', '" + studentiD + "', '" + invoiceItem.getSubject_id() + "')");

            }

            //Withdraw Points
            //view or print report
            String path = "src//reports//CoursePaymentEMS.jasper";
//            String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            HashMap<String, Object> params = new HashMap<>();
            params.put("Parameter1", invoiceID);
            params.put("Parameter2", student_name);
            params.put("Parameter3", employeeName);
            params.put("Parameter4", totalAmount);
            params.put("Parameter5", paymentMethod);
            params.put("Parameter6", paidAmount);
            params.put("Parameter7", balance);
            params.put("Parameter8", dateTime);

            params.put("QR Code", invoiceNumberField.getText());

            JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());
            JasperPrint jasperPrint = null;
            try {
                jasperPrint = JasperFillManager.fillReport(path, params, dataSource);
            } catch (JRException e) {
                logger.error("Exception caught", e);
            }
            JasperViewer.viewReport(jasperPrint, false);
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", 465);
            props.put("mail.smtp.user", "gayanlmdjayawardana@gmail.com"); // Add my email address
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.debug", "true");
            props.put("mail.smtp.socketFactory.port", 465);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");

            try {

//            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `employee` WHERE `email` = '' AND `roles_id`='2'");
                {
                    Session session = Session.getDefaultInstance(props, null);
                    session.setDebug(true);
                    MimeMessage message = new MimeMessage(session);
                    message.setSubject("Invoice from VOID Smart Edventures");

                    // Use StringBuilder for HTML content
                    StringBuilder invoiceHtml = new StringBuilder();
                    invoiceHtml.append("<!DOCTYPE html>");
                    invoiceHtml.append("<html><head>");
                    invoiceHtml.append("<meta charset='UTF-8'>");
                    invoiceHtml.append("<title>Invoice</title>");
                    invoiceHtml.append("<style>");
                    invoiceHtml.append("body { font-family: Arial, sans-serif; padding: 20px; max-width: 800px; margin: auto; border: 2px solid #000; background: #fff; }");
                    invoiceHtml.append("table { width: 100%; border-collapse: collapse; }");
                    invoiceHtml.append("th, td { border: 1px solid black; text-align: left; padding: 10px; }");
                    invoiceHtml.append(".invoice-info { margin-top: 20px; font-size: 16px; }");
                    invoiceHtml.append(".invoice-info span { font-weight: bold; }");
                    invoiceHtml.append(".total-section { margin-top: 20px; font-size: 16px; text-align: right; }");
                    invoiceHtml.append(".barcode { text-align: center; font-size: 20px; margin-top: 20px; }");
                    invoiceHtml.append(".footer { margin-top: 20px; text-align: center; font-size: 14px; font-style: italic; }");
                    invoiceHtml.append("</style></head><body>");

// Header with Invoice Title & Company Info
                    invoiceHtml.append("<table style='width: 100%; border-collapse: collapse;'>");
                    invoiceHtml.append("<tr>");
                    invoiceHtml.append("<td style='text-align: left;'><h2>INVOICE</h2></td>");
                    invoiceHtml.append("<td style='text-align: right; font-size: 14px;'>");
                    invoiceHtml.append("<strong>VOID Smart Edventures</strong><br>");
                    invoiceHtml.append("No.10, Peradeniya Road, Kandy<br>");
                    invoiceHtml.append("Email: voidsmartedventures@gmail.com<br>");
                    invoiceHtml.append("Mobile: 081 234 5678<br><br>");
                    invoiceHtml.append("<span>Date: ").append(dateTime).append("</span>");
                    invoiceHtml.append("</td>");
                    invoiceHtml.append("</tr>");
                    invoiceHtml.append("</table>");

                    invoiceHtml.append("<div class='invoice-info'>");
                    invoiceHtml.append("<p><span>Invoice Number: </span>").append(invoiceID).append("</p>");
                    invoiceHtml.append("<p><span>Student: </span>").append(student_name).append("</p>");
                    invoiceHtml.append("<p><span>Employee: </span>").append(employeeName).append("</p>");
                    invoiceHtml.append("</div>");

                    invoiceHtml.append("<div class='table-container'><table>");
                    invoiceHtml.append("<tr><th>#</th><th>Course Name</th><th>Description</th><th>Course Fee</th></tr>");

                    int i = 0;

                    for (InvoiceItem invoiceItem : invoiceItemMap.values()) {
                        i++;
                        invoiceHtml.append("<tr><td>").append(i).append("</td><td>").append(invoiceItem.getSubject_name()).append("</td><td>").append(invoiceItem.getDesc()).append("</td><td>").append(invoiceItem.getSellingPrice()).append("</td></tr>");
                    }
                    
                    invoiceHtml.append("</table></div>");

                    invoiceHtml.append("<div class='total-section'>");
                    invoiceHtml.append("<p><strong>Total: </strong>Rs.").append(totalAmount).append("</p>");
                    invoiceHtml.append("<p><strong>Payment Method: </strong>").append(paymentMethod).append("</p>");
                    invoiceHtml.append("<p><strong>Payment: </strong>Rs.").append(paidAmount).append("</p>");
                    invoiceHtml.append("<p><strong>Balance: </strong>Rs.").append(balance).append("</p>");
                    invoiceHtml.append("</div>");

                    invoiceHtml.append("<div class='footer'>© 2024 VOID. All rights reserved.<br>Thank You!</div>");

                    invoiceHtml.append("</body></html>");

                    message.setContent(invoiceHtml.toString(), "text/html; charset=utf-8");

                    message.setFrom(new InternetAddress("gayanlmdjayawardana@gmail.com")); // Add my email address
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                    message.saveChanges();
                    Transport transport = session.getTransport("smtp");
                    transport.connect("smtp.gmail.com", "gayanlmdjayawardana@gmail.com", "nprb ztii ypcf opnx"); // Add my email address
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();
                    JOptionPane.showMessageDialog(this, "Code sent, please check your email!", "Warning", JOptionPane.INFORMATION_MESSAGE);

                }

            } catch (Exception e) {
                logger.error("Exception caught", e);
                JOptionPane.showMessageDialog(this, "Email Address is not found!", "Error", JOptionPane.ERROR_MESSAGE);
//            jLabel1.setText("Email Address not found");
//            jLabel1.setHorizontalAlignment(SwingConstants.LEFT);

            }
            reset1();
            clearAll();
            printInvoiceButton.setEnabled(false);
            invoiceItemMap.clear();
            generateInvoiceID();

        } catch (Exception e) {
            logger.error("Exception caught", e);
        }

    }//GEN-LAST:event_printInvoiceButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        reset();
        clearAll();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        PaymentMenu menu = new PaymentMenu();
        menu.setVisible(true);
        menu.setInvoice(this);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        AllStudentsJDialog allStudents = new AllStudentsJDialog(parent, true, this);
        allStudents.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        AllCoursesJDialog allCourses = new AllCoursesJDialog(parent, true, this);
        allCourses.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentPayment("yeet", "laa").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField StudentNameFields;
    private javax.swing.JTextField SubjectNameField;
    private javax.swing.JFormattedTextField balanceFiled;
    private javax.swing.JLabel employeeLable;
    private javax.swing.JTextField invoiceNumberField;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JFormattedTextField paymentField;
    private javax.swing.JButton printInvoiceButton;
    private javax.swing.JLabel studentID;
    private javax.swing.JFormattedTextField totalField;
    // End of variables declaration//GEN-END:variables

    public void reset() {

        SubjectNameField.setText("");    // Clear subject name field
    }

    public void reset1() {
        // Clear specific fields only

        SubjectNameField.setText("");    // Clear subject name field
    }

    /**
     * @return the code
     */
    /**
     * @param code the code to set
     */
    public static void setCode(String code) {
        StudentPayment.code = code;
    }

    /**
     * @return the jTextField1
     */
    public javax.swing.JTextField getjTextField1() {
        return SubjectNameField;
    }

//    public JLabel getstudentID() {
//        return studentID;
//    }
    /**
     * @param aSubject_id the Subject_id to set
     */
    public static void setSubject_id(int aSubject_id) {
        Subject_id = aSubject_id;
    }

    public static void setStudent_id(String aStudent_id) {
        Student_id = aStudent_id;
    }

    public static void setDescription(String description) {
        StudentPayment.description = description;
    }

    /**
     * @param aSelling_price the Selling_price to set
     */
    public static void setSelling_price(double aSelling_price) {
        Selling_price = aSelling_price;
    }

}