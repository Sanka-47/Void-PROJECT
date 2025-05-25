package gui;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.MySQL2;

public class Backup_Restore extends javax.swing.JFrame {

    String fp;

    public Backup_Restore() {
        initComponents();
        
        loadIcon();
    }
    
    
    private void loadIcon() {
        Image image = Toolkit.getDefaultToolkit().getImage("src//resource//VOID.png");
        this.setIconImage(image);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VOID Database Backup");

        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setText("Set Path");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton2.setText("Backup");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addGap(24, 24, 24))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            fileChooser.setAcceptAllFileFilterUsed(false);

            int showOpenDialog = fileChooser.showOpenDialog(this);

            if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                fp = filePath.replace("\\", "/");
                jTextField1.setText(fp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
//            ProcessBuilder processBuilder;
            Process p1 = null;
            Date date = new Date();

            String date1 = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
            FileInputStream fileInputStream = new FileInputStream("dbinfo.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            MySQL2 db = (MySQL2) objectInputStream.readObject();
            objectInputStream.close();

            String path = fp + "/backup_" + date1 + ".sql";
            Runtime runtime = Runtime.getRuntime();

            String command = db.dump + " -u" + db.un + " -p" + db.pw + " --add-drop-database -B " + db.dbname + " -r \"" + path + "\"";

            String[] command1 = {db.dump, "-u" + db.un, "-p" + db.pw, "--add-drop-database", "-B", db.dbname, "-r", path};

            p1 = runtime.exec(command);
            int processComplete = p1.waitFor();

            if (processComplete == 0) {
                System.out.println("Backup created successfully!");
            } else {
                System.err.println("Backup failed. Error stream output:");
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(p1.getErrorStream()));
                String line;
                while ((line = errorReader.readLine()) != null) {
                    System.err.println(line);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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
//        UIManager.put("Label.success", Color.decode("#6DD3AF"));
//        UIManager.put("Label.warning", Color.decode("#FFD56B"));
//        UIManager.put("Label.error", Color.decode("#FF6F61"));

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Backup_Restore().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
