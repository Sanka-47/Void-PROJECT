/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rushma
 */
public class NotificationManager {

    public static List<Notification> getDailyNotifications(int tutorId) {
        List<Notification> notifications = new ArrayList<>();
        try {
            System.out.println(tutorId);
            String query = "SELECT c.id, c.date, c.start_time, c.end_time, c.hallnumber "
                    + "FROM class c "
                    + "WHERE c.tutor_id = " + tutorId;
            ResultSet rs = MySQL2.executeSearch(query);
            while (rs.next()) {
                int classId = rs.getInt("id"); // Get the class ID
                String notificationDetails = "Class on " + rs.getDate("date")
                        + " from " + rs.getString("start_time")
                        + " to " + rs.getString("end_time")
                        + " at hall number" + rs.getString("hallnumber");
                // Add the class ID and details to the Notification list
                notifications.add(new Notification(classId, notificationDetails));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notifications;
    }
}
