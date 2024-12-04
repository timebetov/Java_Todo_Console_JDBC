# Todo List Console Application

This is a Java-based console application for managing a todo list. The project showcases object-oriented programming principles, clean code structure, and database integration using SQLite and JDBC.

## Features
- **Add Task**: Create a new task with a title, description, and optional deadline.
- **Get Task By ID**: Retrieve a specific task by its ID.
- **List All Tasks**: Display all tasks in the database.
- **Update Task**: Modify an existing task.
- **Delete Task By ID**: Remove a specific task.
- **Delete All Tasks**: Clear all tasks from the database.

## Technologies Used
- **Java**: Core programming language.
- **JDBC**: For database connectivity.
- **SQLite**: Lightweight database for storing tasks.
- **Lombok**: Reduces boilerplate code with annotations.
- **JUNIT**: A widely-used testing framework for Java, used to write and run repeatable tests.
- **Mockito**: A mocking framework for unit tests in Java, enabling to simulate and test the behavior of dependencies (e.g., database or external services) without needing to connect to them directly.

## Prerequisites
- Java 11 or later
- Maven (for managing dependencies)

## Setup Instructions

1. **Clone the Repository**:
   ```sh
   git clone https://github.com/timebetov/Java_Todo_Console_JDBC.git
   cd Java_Todo_Console_JDBC
   ```

2. **Run the Application**:
   - Compile and run the application using your IDE or Maven:
     ```sh
     mvn compile exec:java
     ```

3. **Database Schema**:
   The database is initialized automatically during runtime if the `todoapp.db` file does not exist. The schema is as follows:
   ```sql
   CREATE TABLE IF NOT EXISTS tasks (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       title TEXT NOT NULL,
       description TEXT,
       status BOOLEAN NOT NULL DEFAULT 0,
       due_date TIMESTAMP
   );
   ```

## How to Use
1. **Add Task**: Enter a title, description, and optional deadline.
2. **List Tasks**: View all tasks with their details.
3. **Update Task**: Select a task by ID and modify its details.
4. **Delete Task**: Remove a task by ID or delete all tasks.

## Project Structure
```
src/main/java
├── dev/timebetov/todo
│   ├── db          # Database configuration and initialization
│   ├── models      # Task model
│   ├── repositories # Task repository
│   ├── services    # Task service logic
│   └── utils       # Utility functions (e.g., input handling, validation)

src/test/java
└── dev/timebetov/todo/services # Task service logic test cases
```

## Testing

This application has been thoroughly tested using JUnit all CRUD operations, ensuring that the application behaves as expected:

---

- **Unit Tests**: Focus on individual methods in the service and repository layers using a mocking framework like Mockito.
- **Integration Tests**: Test the flow between the application and the SQLite database by verifying task creation, updates, and deletion in an isolated environment.

---

Feel free to reach out if you have any questions or feedback!

