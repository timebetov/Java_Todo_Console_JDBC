package dev.timebetov.todo.repositories;

import dev.timebetov.todo.db.DatabaseConfig;
import dev.timebetov.todo.models.Task;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private final DataSource dataSource;

    public TaskRepository() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    public int addTask(Task task) throws SQLException {

        String sql = "INSERT INTO tasks (title, description, status, due_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setBoolean(3, task.isStatus());
            pstmt.setTimestamp(4, task.getDueTime());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet gKeys = pstmt.getGeneratedKeys()) {
                    if (gKeys.next()) {
                        return gKeys.getInt(1);
                    }
                }
            }
            // No ID was generated
            return -1;
        }
    }

    public Task getTask(int id) throws SQLException {

        Task task = null;

        String sql = "SELECT * FROM tasks WHERE id=?";

        try (Connection conn = dataSource.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    task = new Task();
                    task.setId(rs.getInt("id"));
                    task.setTitle(rs.getString("title"));
                    task.setDescription(rs.getString("description"));
                    task.setStatus(rs.getBoolean("status"));
                    task.setDueTime(rs.getTimestamp("due_date"));
                }
            }
        }

        return task;
    }

    public List<Task> getAllTasks() throws SQLException {

        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBoolean("status"),
                        rs.getTimestamp("due_date")
                ));
            }
        }

        return tasks;
    }

    public boolean updateTask(Task task) throws SQLException {

        String sql = "UPDATE tasks SET title=?, description=?, status=?, due_date=? WHERE id=?";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setBoolean(3, task.isStatus());
            pstmt.setTimestamp(4, task.getDueTime());

            pstmt.setInt(5, task.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean deleteTask(int id) throws SQLException {

        String sql = "DELETE FROM tasks WHERE id=?";

        try (Connection conn = dataSource.getConnection();
            PreparedStatement delStmt = conn.prepareStatement(sql)) {

            delStmt.setInt(1, id);
            int rowsAffected = delStmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean deleteTasks() throws SQLException {

        String sql = "DELETE FROM tasks";
        try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
