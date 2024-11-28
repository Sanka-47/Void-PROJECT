package gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.Color;
import javax.swing.ImageIcon;


public class SplashWindow extends javax.swing.JFrame {
    
    private static SplashWindow splashWindow;
    
    private void loadingAnimation() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    jProgressBar1.setValue(i);
                    
                    if (i == 10) {
                        loadingText.setText("LOADING...");
                    }
                    if (i == 20 || i == 30 || i == 40 || i == 50 || i == 60 || i == 70 || i == 80 ) {
                        loadingText.setText("INITIALIZING.");
                    } else if (i == 23 || i == 33 || i == 43 || i == 53 || i == 63 || i == 73 || i == 83) {
                        loadingText.setText("INITIALIZING..");
                    } else if (i == 26 || i == 36 || i == 46 || i == 56 || i == 66 || i == 76 || i == 86) {
                        loadingText.setText("INITIALIZING...");
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
                splashWindow.dispose();
                new AdminSignIn().setVisible(true);
            }
        });
        t.start();
    }

    public SplashWindow() {
        initComponents();
        loadingAnimation();
        setBackground(new Color(0, 0, 0, 0));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        loadingText = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(160, 38));

        loadingText.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        loadingText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loadingText.setText("LOADING DATABASE...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loadingText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loadingText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 360, 210, -1));

        jProgressBar1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jProgressBar1.setForeground(new java.awt.Color(153, 153, 255));
        jProgressBar1.setStringPainted(true);
        getContentPane().add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 400, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/loading1.gif"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 400));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        FlatMacDarkLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                splashWindow = new SplashWindow();
                splashWindow.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel loadingText;
    // End of variables declaration//GEN-END:variables
}
