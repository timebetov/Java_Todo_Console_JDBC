package dev.timebetov.todo.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initilizeDatabase() {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS tasks (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    description TEXT,
                    status BOOLEAN NOT NULL DEFAULT 0,
                    due_date TIMESTAMP
                )
                """;

        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {

            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
}
