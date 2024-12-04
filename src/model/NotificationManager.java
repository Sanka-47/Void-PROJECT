/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rushma
 */
public class NotificationManager {
      public static List<String> getDailyNotifications(int tutorId) {
        List<String> notifications = new ArrayList<>();
        try {
            String query = "SELECT c.date, c.start_time, c.end_time, c.location " +
                           "FROM class c " +
                           "WHERE c.date = CURDATE() AND c.tutor_id = " + tutorId;
            ResultSet rs = MySQL2.executeSearch(query);
            while (rs.next()) {
                String notification = "Class on " + rs.getDate("date") +
                                      " from " + rs.getString("start_time") +
                                      " to " + rs.getString("end_time") +
                                      " at " + rs.getString("location");
                notifications.add(notification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notifications;
    }
}
