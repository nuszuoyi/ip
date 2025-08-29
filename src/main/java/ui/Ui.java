package ui;

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
}

