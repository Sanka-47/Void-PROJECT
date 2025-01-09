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

/**
 *
 * @author Rushma
 */
public class Notifications extends javax.swing.JFrame {

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

        // Clear the parent container
        notificationsPanel.removeAll();
        notificationsPanel.setBackground(Color.WHITE);

        // Set layout for the notifications panel
        notificationsPanel.setLayout(new BoxLayout(notificationsPanel, BoxLayout.Y_AXIS));

        // Create a scrollable view
        JScrollPane scrollPane = new JScrollPane(notificationsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setPreferredSize(new Dimension(800, 600));

        // Add title with minimal top gap
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Notifications");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titlePanel.add(titleLabel);
        notificationsPanel.add(titlePanel);

        notificationsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Reduced spacing

        // If there are no notifications, show a default message
        if (notifications.isEmpty()) {
            JLabel noNotificationLabel = new JLabel("No classes scheduled.");
            noNotificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noNotificationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            notificationsPanel.add(noNotificationLabel);
        } else {
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBackground(Color.WHITE);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

            for (Notification notification : notifications) {
                // Create notification panel
                JPanel notificationBox = new JPanel();
                notificationBox.setLayout(new BorderLayout());
                notificationBox.setPreferredSize(new Dimension(1000, 60));
                notificationBox.setMaximumSize(new Dimension(1000, 60));
                notificationBox.setBackground(Color.WHITE);

                // Rounded border for notification panel
                notificationBox.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(0, 0, 8, 0),
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true) // Rounded corners
                ));

                JLabel notificationLabel = new JLabel(notification.getDetails());
                notificationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                notificationLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

                JButton moreInfoButton = new JButton("More Info");
                moreInfoButton.setPreferredSize(new Dimension(100, 30));
                moreInfoButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                moreInfoButton.setForeground(Color.WHITE);
                // Updated color to match the "Student Progress" header blue
                moreInfoButton.setBackground(new Color(63, 81, 181)); // Rich blue color
                moreInfoButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                moreInfoButton.setFocusPainted(false);
                moreInfoButton.setHorizontalAlignment(SwingConstants.CENTER);

                JPanel buttonWrapper = new JPanel(new GridBagLayout());
                buttonWrapper.setBackground(Color.WHITE);
                buttonWrapper.add(moreInfoButton);

                JPanel buttonPanel = new JPanel(new BorderLayout());
                buttonPanel.setBackground(Color.WHITE);
                buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
                buttonPanel.add(buttonWrapper, BorderLayout.CENTER);

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

                notificationBox.add(notificationLabel, BorderLayout.WEST);
                notificationBox.add(buttonPanel, BorderLayout.EAST);

                contentPanel.add(notificationBox);
            }

            notificationsPanel.add(contentPanel);
        }

        jScrollPane1.setViewportView(scrollPane);

        notificationsPanel.revalidate();
        notificationsPanel.repaint();
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
