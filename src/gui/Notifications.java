/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import model.Notification;
import model.NotificationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rushma
 */
public class Notifications extends javax.swing.JFrame {
    private static final Logger logger = LogManager.getLogger(Notifications.class);

    Map<Integer, NotificationDetails> notificationDetailsMap = new HashMap<>();

    public Notifications(int tutorId) {
        initComponents();
        loadNotifications(tutorId); // Pass the tutor ID (example: 1)
    }

    private Notifications() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void loadNotifications(int tutorId) {
        // Fetch notifications for the given tutor
        List<Notification> notifications = NotificationManager.getDailyNotifications(tutorId);

        // Main panel with BorderLayout to fill entire width
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            public Dimension getPreferredSize() {
                // Make sure panel fills parent width
                Dimension size = super.getPreferredSize();
                size.width = getParent() != null ? getParent().getWidth() : size.width;
                return size;
            }
        };
        mainPanel.setBackground(Color.WHITE);

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Notifications");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titlePanel.add(titleLabel);

        // Content panel that will stretch full width
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        if (notifications.isEmpty()) {
            JLabel noNotificationLabel = new JLabel("No classes scheduled.");
            noNotificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noNotificationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            noNotificationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(noNotificationLabel);
        } else {
            for (Notification notification : notifications) {
                JPanel notificationBox = createNotificationBox(notification);
                contentPanel.add(notificationBox);
                contentPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            }
        }

        // Content scroll pane that fills width
        JScrollPane contentScrollPane = new JScrollPane(contentPanel) {
            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width = getParent() != null ? getParent().getWidth() : size.width;
                return size;
            }
        };
        contentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentScrollPane.setBorder(null);
        contentScrollPane.getViewport().setBackground(Color.WHITE);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(contentScrollPane, BorderLayout.CENTER);

        // Configure the main scroll pane
        jScrollPane1.setViewportView(mainPanel);
        jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        jScrollPane1.setBackground(Color.WHITE);
        jScrollPane1.getViewport().setBackground(Color.WHITE);
    }

    private JPanel createNotificationBox(Notification notification) {
        // Wrapper panel that will stretch full width
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(Color.WHITE);
        wrapperPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Main notification panel
        JPanel notificationBox = new JPanel(new BorderLayout());
        notificationBox.setBackground(Color.WHITE);
        notificationBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));

        // Notification label
        JLabel notificationLabel = new JLabel(notification.getDetails());
        notificationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        notificationLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        // Button with vertical centering
        JButton moreInfoButton = new JButton("More Info");
        moreInfoButton.setPreferredSize(new Dimension(100, 30));
        moreInfoButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        moreInfoButton.setForeground(Color.WHITE);
        moreInfoButton.setBackground(new Color(63, 81, 181));
        moreInfoButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        moreInfoButton.setFocusPainted(false);

        // Button action listener
        moreInfoButton.addActionListener(e -> {
            int classId = notification.getClassId();
            if (!notificationDetailsMap.containsKey(classId)) {
                NotificationDetails details = new NotificationDetails(classId);
                notificationDetailsMap.put(classId, details);
                details.setVisible(true);
            } else {
                NotificationDetails details = notificationDetailsMap.get(classId);
                if (!details.isVisible()) {
                    details.setVisible(true);
                } else {
                    details.toFront();
                    details.requestFocus();
                }
            }
        });

        // Create vertically centered button panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
        buttonPanel.add(moreInfoButton);

        // Add components to notification box
        notificationBox.add(notificationLabel, BorderLayout.CENTER);
        notificationBox.add(buttonPanel, BorderLayout.EAST);

        // Add notification box to wrapper
        wrapperPanel.add(notificationBox, BorderLayout.CENTER);

        return wrapperPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        notificationsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout notificationsPanelLayout = new javax.swing.GroupLayout(notificationsPanel);
        notificationsPanel.setLayout(notificationsPanelLayout);
        notificationsPanelLayout.setHorizontalGroup(
            notificationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        notificationsPanelLayout.setVerticalGroup(
            notificationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)
                    .addComponent(notificationsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(notificationsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private NotificationDetails tw;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatArcIJTheme.setup();

        java.awt.EventQueue.invokeLater(() -> new Notifications().setVisible(true));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel notificationsPanel;
    // End of variables declaration//GEN-END:variables
}