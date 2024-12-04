package dev.timebetov.todo.services;

import dev.timebetov.todo.models.Task;
import dev.timebetov.todo.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
public class TaskService {

    TaskRepository taskRepo;

    // Add Task
    public Task addTask(String title, String description, Timestamp deadline) throws SQLException {

        Task userTask = new Task(title, description, deadline);

        int id = taskRepo.addTask(userTask);
        if (id >= 0) {
            userTask.setId(id);
            return userTask;
        }

        throw new SQLException("Failed to add the task");
    }

    // Get Task By Id
    public Task getById(int id) throws SQLException {

        Task task = taskRepo.getTask(id);
        if (task == null) {
            throw new SQLException("Task with ID: " + id + " not found");
        }

        return task;
    }

    // Get All Tasks
    public List<Task> getTasks() throws SQLException {

        List<Task> tasks = taskRepo.getAllTasks();
        if (tasks.isEmpty()) {
            throw new SQLException("List is empty");
        }

        return tasks;
    }

    // Update Task
    public void updateTask(Task task) throws SQLException {
        boolean isUpdated = taskRepo.updateTask(task);
        if (!isUpdated) {
            throw new SQLException("Could not update the task: " + task.getTitle());
        }
    }

    // Delete Task by ID
    public Task deleteTask(int id) throws SQLException {

        Task deletedTask = taskRepo.getTask(id);
        boolean isDeleted = taskRepo.deleteTask(id);

        if (isDeleted) {
            return deletedTask;
        } else {
            throw new SQLException("Task with given ID: " + id + " not found");
        }
    }

    // Delete tasks
    public void deleteTasks() throws SQLException {

        boolean isCleaned = taskRepo.deleteTasks();
        if (!isCleaned) {
            throw new SQLException("List is already empty");
        }
    }

}
