package dev.timebetov.todo.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Utils {

    public static final String format = "| %-5s | %-30s | %-40s | %-25s |%n";

    public static void printHeaders() {

        printBorder(false);
        System.out.printf(format, "ID", "Title", "Description", "Deadline");
        printBorder(false);
    }
    public static void printBorder(boolean line) {

        if (!line) {
            System.out.printf("%1$s%2$s%1$s%3$s%1$s%4$s%1$s%5$s%1$s%n",
                    "+",
                    "-".repeat(7),
                    "-".repeat(32),
                    "-".repeat(42),
                    "-".repeat(27));
        } else {
            System.out.println("-".repeat(109));
        }
    }
    public static void showError(String message) {
        System.out.println("-".repeat(70));
        System.out.printf("ERROR: %s!%n", message);
        System.out.println("-".repeat(70));
    }
    public static String getInput(Scanner scanner, String prompt, boolean empty) {

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (empty) {
                if (input.isEmpty()) {
                    return null;
                }
            }
            if (input.length() > 2) {
                return input;
            }
            showError("Input cannot be empty. Please try again");
        }
    }
    public static Timestamp getValidTimestamp(Scanner scanner, boolean update) {

        String input = getInput(scanner, "DEADLINE or leave it empty to set default value: (Enter date and time in format yyyy-MM-dd HH:mm:ss) ", true);

        // Formatter
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Timestamp dueTo = null;

        while (true) {
            if (input == null) {
                if (update) {
                    dueTo = Timestamp.valueOf(LocalDateTime.now().plusDays(7));
                    break;
                }
                break;
            }

            try {
                // Parsing input to LocalDateTime
                LocalDateTime ldt = LocalDateTime.parse(input, dtf);

                // Converting to Timestamp
                dueTo = Timestamp.valueOf(ldt);
                break;
            } catch (Exception e) {
                Utils.showError("Invalid input. Please use the correct format: yyyy:MM:dd HH:mm:ss");
                input = getInput(scanner, "Try again: ", true);
            }
        }

        return dueTo;
    }
    public static int getIntegerFromInput(Scanner scanner) {

        while (true) {
            System.out.print("Enter the task ID: (or type 'back') ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("back")) {
                return -1;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                showError("Please provide valid ID number: (or type 'back') ");
            }
        }
    }
}
