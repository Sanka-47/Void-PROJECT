/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import model.MySQL2;
import model.Task;

/**
 *
 * @author Rushma
 */
public class TodoList extends javax.swing.JPanel {

    /**
     * Creates new form TodoList
     */
    // A HashMap to store tasks for each tutor
    private HashMap<String, ArrayList<Task>> tutorTasks = new HashMap<>();

    public TodoList() {
        initComponents();
        String tutorId = "1"; // Replace with actual tutor ID logic if dynamic
        updateTaskList(tutorId);
    }

    private void addTask(String tutorId) {
        String taskDescription = txtTaskInput.getText(); // Get task description from the input field
        if (!taskDescription.isEmpty()) {
            try {
                String taskId = generateTaskId(); // Generate a unique task ID
                String query = String.format(
                        "INSERT INTO tasks (task_id, tutor_id, description, is_completed) VALUES ('%s', '%s', '%s', %b)",
                        taskId, '1', taskDescription, false
                );
                MySQL2.executeIUD(query); // Execute the insert query
                updateTaskList(tutorId);
                reset();// Refresh the task list
            } catch (Exception e) {
                e.printStackTrace(); // Handle any exceptions
            }
        }
    }

    // Function to delete a task for a specific tutor
    private void deleteTask(String tutorId) {
        int selectedIndex = jList1.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task to delete.");
            return;
        }
// Get the selected task index
        if (selectedIndex != -1) {
            try {
                ArrayList<Task> tasks = getTasksFromDatabase(tutorId); // Fetch tasks
                if (tasks != null && selectedIndex < tasks.size()) {
                    Task task = tasks.get(selectedIndex); // Get the task to delete
                    String query = String.format("DELETE FROM tasks WHERE task_id = '%s'", task.getTaskId());
                    MySQL2.executeIUD(query); // Execute the delete query
                    updateTaskList(tutorId); // Refresh the task list
                }
            } catch (Exception e) {
                e.printStackTrace(); // Handle any exceptions
            }
        }
    }

    // Function to mark a task as complete
    private void markTaskComplete(String tutorId) {
        int selectedIndex = jList1.getSelectedIndex();
         if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task to mark as complete.");
            return;
        }// Get the selected task index
        if (selectedIndex != -1) {
            try {
                ArrayList<Task> tasks = getTasksFromDatabase(tutorId);
                if (tasks != null && selectedIndex < tasks.size()) {
                    Task task = tasks.get(selectedIndex); // Get the selected task
                    boolean newStatus = !task.isCompleted(); // Toggle the completion status
                    task.setCompleted(newStatus); // Update task object

                    // Update the database
                    String query = String.format(
                            "UPDATE tasks SET is_completed = %d WHERE task_id = '%s'",
                            newStatus ? 1 : 0, // Store 1 for completed, 0 for incomplete
                            task.getTaskId()
                    );
                    MySQL2.executeIUD(query); // Execute the update query
                    // Refresh the task list
                    updateTaskList(tutorId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void editTask(String tutorId) {
        int selectedIndex = jList1.getSelectedIndex(); // Get the selected task index
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task to edit.");
            return;
        }

        String newDescription = txtTaskInput.getText(); // Get the new task description from the input field
        if (newDescription == null || newDescription.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Task description cannot be empty.");
            return;
        }

        ArrayList<Task> tasks = tutorTasks.get(tutorId);
        if (tasks == null || selectedIndex >= tasks.size()) {
            System.out.println("Tasks list is null or index out of bounds: " + tasks);
            JOptionPane.showMessageDialog(this, "Invalid task selection.");
            return;
        }

        Task task = tasks.get(selectedIndex); // Get the selected task
        System.out.println("Editing task: " + task.getDescription());

        task.setDescription(newDescription); // Update the task description

        // Update the database
        try {
            String query = String.format(
                    "UPDATE tasks SET description = '%s' WHERE task_id = '%s'",
                    newDescription, task.getTaskId()
            );
            MySQL2.executeIUD(query); // Execute the update query
            updateTaskList(tutorId);  // Refresh the task list
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating task: " + e.getMessage());
        }
    }

    // Helper function to update the list of tasks displayed
    private void updateTaskList(String tutorId) {
        try {
            ArrayList<Task> tasks = getTasksFromDatabase(tutorId);
            if (tasks != null) {
                tutorTasks.put(tutorId, tasks); // Ensure tutorTasks is updated
                System.out.println("Tasks for tutor " + tutorId + ": " + tasks);
                String[] taskDescriptions = new String[tasks.size()];
                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    taskDescriptions[i] = (task.isCompleted() ? "[Completed] " : "[Incomplete] ") + task.getDescription();
                }
                jList1.setListData(taskDescriptions);
                reset();// Update the JList with task descriptions
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Helper function to get tasks from the database

    private ArrayList<Task> getTasksFromDatabase(String tutorId) {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            String query = String.format("SELECT * FROM tasks WHERE tutor_id = '%s'", tutorId);
            ResultSet rs = MySQL2.executeSearch(query);
            while (rs.next()) {
                String taskId = rs.getString("task_id");
                String description = rs.getString("description");
                boolean isCompleted = rs.getInt("is_completed") == 1; // Convert 0/1 to boolean
                tasks.add(new Task(taskId, description, isCompleted));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    // Function to generate a new unique task ID
    private String generateTaskId() {
        return "task_" + System.currentTimeMillis(); // Example: task_1630918232871
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtTaskInput = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnAddTask = new javax.swing.JButton();
        btnEditTask = new javax.swing.JButton();
        btnDeleteTask = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        chkComplete = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 28)); // NOI18N
        jLabel1.setText("Todo List");

        btnAddTask.setText("Add Task");
        btnAddTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTaskActionPerformed(evt);
            }
        });

        btnEditTask.setText("Edit Task");
        btnEditTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditTaskActionPerformed(evt);
            }
        });

        btnDeleteTask.setText("Delete Task");
        btnDeleteTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteTaskActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jList1);

        chkComplete.setText("Mark as complete");
        chkComplete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCompleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(468, 468, 468)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(btnEditTask, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnDeleteTask, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(chkComplete, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(txtTaskInput, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnAddTask, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTaskInput, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddTask, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditTask, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteTask, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkComplete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTaskActionPerformed
        String tutorId = "1"; // Example tutor ID (replace with actual tutor ID)
        deleteTask(tutorId);
    }//GEN-LAST:event_btnDeleteTaskActionPerformed

    private void btnAddTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTaskActionPerformed
        String tutorId = "1"; // Example tutor ID (replace with actual tutor ID)
        addTask(tutorId);
    }//GEN-LAST:event_btnAddTaskActionPerformed

    private void btnEditTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditTaskActionPerformed
        String tutorId = "1"; // Example tutor ID (replace with actual tutor ID)
        editTask(tutorId);
    }//GEN-LAST:event_btnEditTaskActionPerformed

    private void chkCompleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCompleteActionPerformed
        String tutorId = "1"; // Example tutor ID (replace with actual tutor ID)
        markTaskComplete(tutorId);
    }//GEN-LAST:event_chkCompleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddTask;
    private javax.swing.JButton btnDeleteTask;
    private javax.swing.JButton btnEditTask;
    private javax.swing.JButton chkComplete;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtTaskInput;
    // End of variables declaration//GEN-END:variables

    private void reset() {
        txtTaskInput.setText("");
    }
}
