package dev.timebetov.todo;

import dev.timebetov.todo.db.DatabaseInitializer;
import dev.timebetov.todo.models.Task;
import dev.timebetov.todo.repositories.TaskRepository;
import dev.timebetov.todo.services.TaskService;
import dev.timebetov.todo.utils.Utils;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Main {

    enum Options { SHOW, GET, ADD, UPDATE, DELETE, CLEAN, EXIT }

    public static void main(String[] args) {

        // Initializing Database
        DatabaseInitializer.initilizeDatabase();

        // Initializing Repository
        TaskService taskService = new TaskService(new TaskRepository());

        // Entry point
        startApp(taskService);
    }

    private static void startApp(TaskService taskService) {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to my todo app made with Java and <3");
            while (true) {
                menu();
                String inputOption = Utils.getInput(scanner, ">>>\t", false);

                Options option = Options.valueOf(inputOption.toUpperCase());
                switch (option) {
                    case SHOW:
                        printTasks(taskService);
                        break;
                    case GET:
                        getTask(scanner, taskService);
                        break;
                    case ADD:
                        addTask(scanner, taskService);
                        break;
                    case UPDATE:
                        updateTask(scanner, taskService);
                        break;
                    case DELETE:
                        deleteTask(scanner, taskService);
                        break;
                    case CLEAN:
                        cleanList(scanner, taskService);
                        break;
                    case EXIT:
                        System.out.println("Bye"); return;
                    default: break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void menu() {

        String welcome = """
                Please choose the option:
                (SHOW) -> Show all tasks.
                (GET) -> Get task by ID.
                (ADD) -> Add new task.
                (UPDATE) -> Change the task by ID.
                (DELETE) -> Delete task by ID.
                (CLEAN) -> Clean my list.
                (EXIT) -> Close the application
                """;
        System.out.print(welcome);
    }

    private static void printTasks(TaskService taskService) {

        List<Task> tasks = null;
        try {
            tasks = taskService.getTasks();
        } catch (SQLException e) {
            Utils.showError(e.getMessage());
            return;
        }

        Utils.printHeaders();
        for (Task task : tasks) {
            System.out.printf(Utils.format, task.getId(), task.getTitle(), task.getDescription(), task.getDueTime());
            Utils.printBorder(false);
        }
    }

    private static void printTask(Task task) {

        Utils.printHeaders();
        System.out.printf(Utils.format,
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueTime().toLocalDateTime().toString());
        Utils.printBorder(false);
    }

    private static void getTask(Scanner scanner, TaskService taskService) {

        int id = Utils.getIntegerFromInput(scanner);
        if (id == -1) {
            return;
        }

        Task task = null;
        try {
            task = taskService.getById(id);
        } catch (SQLException e) {
            Utils.showError(e.getMessage());
            return;
        }

        printTask(task);
    }

    private static void addTask(Scanner scanner, TaskService taskService) {

        // Getting the title and description of task from user
        String title = Utils.getInput(scanner, "Task title: ", false);
        String description = Utils.getInput(scanner, "Task description: ", false);
        Timestamp deadline = Utils.getValidTimestamp(scanner, true);

        Task addedTask = null;

        try {
            addedTask = taskService.addTask(title, description, deadline);
        } catch (SQLException e) {
            Utils.showError(e.getMessage());
            return;
        }

        // In case of success
        Utils.printBorder(true);
        System.out.printf("\t\t\tTask %s was added successfully!%n", addedTask.getTitle());
        Utils.printBorder(true);
        printTask(addedTask);
    }

    private static void updateTask(Scanner scanner, TaskService taskService) {

        int id = Utils.getIntegerFromInput(scanner);
        String oldTitle;

        try {
            Task task = taskService.getById(id);
            oldTitle = task.getTitle();

            String title = Utils.getInput(scanner, "Task title ("+ task.getTitle() +"): ", true);
            String description = Utils.getInput(scanner, "Task description ("+task.getDescription()+"): ", true);
            Timestamp deadline = Utils.getValidTimestamp(scanner, false);

            if (title != null) {
                task.setTitle(title);
            }
            if (description != null) {
                task.setDescription(description);
            }
            if (deadline != null) {
                task.setDueTime(deadline);
            }

            taskService.updateTask(task);
        } catch (SQLException e) {
            Utils.showError(e.getMessage());
            return;
        }

        Utils.printBorder(true);
        System.out.printf("Task %s updated successfully!%n", oldTitle);
        Utils.printBorder(true);
    }

    private static void deleteTask(Scanner scanner, TaskService taskService) {

        int id = Utils.getIntegerFromInput(scanner);
        Task deletedTask = null;

        try {
            deletedTask = taskService.deleteTask(id);
        } catch (SQLException e) {
            Utils.showError(e.getMessage());
            return;
        }

        Utils.printBorder(true);
        System.out.println("\t\t\tDELETED TASK:");
        printTask(deletedTask);
    }

    private static void cleanList(Scanner scanner, TaskService taskService) {

        String input = Utils.getInput(scanner, "Are you sure to clear list? (yes/nope): ", false);

        if (input.equalsIgnoreCase("yes")) {
            try {
                taskService.deleteTasks();
                Utils.printBorder(true);
                System.out.println("List cleaned successfully!");
                Utils.printBorder(true);
            } catch (SQLException e) {
                Utils.showError(e.getMessage());
            }
        }
    }
}
