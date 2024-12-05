/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Rushma
 */
public class Notification {
      private int classId;
    private String details;

    public Notification(int classId, String details) {
        this.classId = classId;
        this.details = details;
    }

    public int getClassId() {
        return classId;
    }

    public String getDetails() {
        return details;
    }
}
