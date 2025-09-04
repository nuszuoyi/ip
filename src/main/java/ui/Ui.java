package ui;

import java.util.ArrayList;

import tasks.Task;

/**
 * Handles user interface interactions such as printing messages and errors.
 */
public class Ui {

    /**
     * Prints a message to the console with a horizontal border for formatting.
     *
     * @param message Message to be printed.
     */
    public void printMessage(String message) {
        String horizontalLine = "    ____________________________________________________________";
        // Split message into lines for printing
        String[] lines = message.split("\n");

        System.out.println(horizontalLine);
        for (String line : lines) {
            System.out.println("     " + line);
        }
        System.out.println(horizontalLine);
        System.out.println();
    }

    /**
     * Shows a loading error message when tasks cannot be loaded.
     */
    public void showLoadingError() {
        System.out.println("Oops! Something went wrong while loading your tasks.");
    }

    /**
     * Prints tasks that match a search keyword.
     *
     * @param tasks List of matching tasks to display.
     */
    public void showMatchingTasks(ArrayList<Task> tasks) {
        System.out.println("    ____________________________________________________________");
        if (tasks.isEmpty()) {
            System.out.println("     No matching tasks found.");
        } else {
            System.out.println("     Here are the matching tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("     " + (i + 1) + "." + tasks.get(i));
            }
        }
        System.out.println("    ____________________________________________________________");
    }

}
