package dev.timebetov.todo.services;

import dev.timebetov.todo.models.Task;
import dev.timebetov.todo.repositories.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addTaskTest() throws SQLException {

        Timestamp deadline = Timestamp.valueOf(LocalDateTime.now().plusDays(2));

        Task task = new Task("Title", "Description", deadline);
        Mockito.when(taskRepository.addTask(task)).thenReturn(true);

        Task result = taskService.addTask("Title", "Description", deadline);

        Assertions.assertEquals("Title", result.getTitle());
        Mockito.verify(taskRepository, Mockito.times(1)).addTask(task);
    }

    @Test
    void getTaskById() throws SQLException {

        Timestamp deadline = Timestamp.valueOf(LocalDateTime.now().plusDays(2));

        Task task = new Task(1, "Title", "Description", false, deadline);
        Mockito.when(taskRepository.getTask(1)).thenReturn(task);

        Task result = taskService.getById(1);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getId());

        Mockito.verify(taskRepository, Mockito.times(1)).getTask(1);
    }

    @Test
    void updateTaskById() throws SQLException {

        Timestamp deadline = Timestamp.valueOf(LocalDateTime.now().plusDays(3));

        Task task = new Task(1, "Updated Title", "Updated Description", false, deadline);
        Mockito.when(taskRepository.updateTask(task)).thenReturn(true);
        taskService.updateTask(task);

        Mockito.verify(taskRepository, Mockito.times(1)).updateTask(task);
    }

    @Test
    void deleteTaskById() throws SQLException {

        Timestamp deadline = Timestamp.valueOf(LocalDateTime.now().plusDays(2));

        Task task = new Task(1, "Delete title", "Delete description", false, deadline);
        Mockito.when(taskRepository.getTask(1)).thenReturn(task);
        Mockito.when(taskRepository.deleteTask(1)).thenReturn(true);

        Task result = taskService.deleteTask(1);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getId());
        Mockito.verify(taskRepository, Mockito.times(1)).deleteTask(1);
    }
}
