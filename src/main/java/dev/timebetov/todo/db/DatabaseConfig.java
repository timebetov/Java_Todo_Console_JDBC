package dev.timebetov.todo.db;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

public class DatabaseConfig {

    private static SQLiteDataSource dataSource;

    static {
        try {
            dataSource = new SQLiteDataSource();
            dataSource.setUrl("jdbc:sqlite:todoapp.db");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize SQLiteDataSource: " + e.getMessage());
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
