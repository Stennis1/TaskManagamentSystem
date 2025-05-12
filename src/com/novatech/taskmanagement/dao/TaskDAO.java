package com.novatech.taskmanagement.dao;

import com.novatech.taskmanagement.model.Task;
import com.novatech.taskmanagement.utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public void addTask(Task task) throws Exception {
        String sql = "INSERT INTO task (title, description, dueDate, status) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setDate(3, Date.valueOf(task.getDueDate()));
            stmt.setString(4, task.getStatus());
            stmt.executeUpdate();
        }
    }

    public List<Task> getAllTasks() throws Exception {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task ORDER BY dueDate";

        try (Connection connection = DBConnection.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setDueDate(rs.getDate("dueDate").toLocalDate());
                task.setStatus(rs.getString("status"));
                tasks.add(task);
            }
        }
        return tasks;
    }

    public Task getTaskByID(int id) throws Exception {
        String sql = "SELECT * FROM task WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("dueDate").toLocalDate(),
                        rs.getString("status")
                    );
                }
            }
        }
        return null;
    }

    public void updateTask(Task task) throws Exception {
        String sql = "UPDATE Task SET title = ?, description = ?, dueDuate = ?, status = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setDate(3, Date.valueOf(task.getDueDate()));
            stmt.setString(4, task.getStatus());
            stmt.setInt(5, task.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteTask(int id) throws Exception {
        String sql = "DELETE FROM Task WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
