package main;

import java.io.File;
import java.util.Scanner;

import command.CommandHandler;
import exceptions.AmadeusException;
import parser.Parser;
import storage.Storage;
import tasks.TaskList;
import ui.Ui;

/**
 * Represents the Amadeus chatbot application.
 * Handles loading tasks, executing user commands, and interacting with the UI.
 */
public class Amadeus {

    /** Storage for persisting tasks */
    private final Storage storage;

    /** Task list managed by this bot */
    private TaskList tasks;

    /** User interface for interacting with the user */
    private final Ui ui;

    /** Command handler for processing user inputs */
    private final CommandHandler handler;

    /**
     * Constructs an Amadeus bot with the given file path for task storage.
     *
     * @param filePath Path to the file where tasks are saved/loaded.
     */
    public Amadeus(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (AmadeusException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
        handler = new CommandHandler(tasks, storage, ui);
    }

    /**
     * Default constructor for GUI usage.
     * Uses default file path for storage.
     */
    public Amadeus() {
        this("." + File.separator + "data" + File.separator + "Amadeus.txt");
    }

    /**
     * Gets a response from Amadeus based on user input.
     * This method is used by the GUI to interact with the bot.
     *
     * @param input The user's input string
     * @return The response from Amadeus
     */
    public String getResponse(String input) {
        try {
            // Handle special case for "bye" command
            if (input.trim().equalsIgnoreCase("bye")) {
                return "Bye. Hope to see you again soon!";
            }
            
            // Use a custom UI that captures the response as a string
            StringCapturingUi stringUi = new StringCapturingUi();
            CommandHandler guiHandler = new CommandHandler(tasks, storage, stringUi);
            
            // Process the command
            guiHandler.handleCommand(Parser.parse(input));
            
            // Return the captured response
            return stringUi.getCapturedOutput();
        } catch (Exception e) {
            return "Oops! Something went wrong: " + e.getMessage();
        }
    }

    /**
     * Gets the initial greeting message from Amadeus.
     *
     * @return The greeting message
     */
    public String getGreeting() {
        return "Hello! I'm Amadeus\nWhat can I do for you?";
    }

    /**
     * Runs the main loop of the chatbot, reading user input and handling commands.
     * This method is used for CLI mode.
     */
    public void run() {
        Scanner sc = new Scanner(System.in);
        ui.printMessage("Hello! I'm Amadeus\nWhat can I do for you?");
        String input = sc.nextLine();

        while (!input.equals("bye")) {
            handler.handleCommand(Parser.parse(input));
            input = sc.nextLine();
        }

        ui.printMessage("Bye. Hope to see you again soon!");
        sc.close();
    }

    /**
     * Main entry point of the application for CLI mode.
     *
     * @param args Command line arguments (ignored).
     */
    public static void main(String[] args) {
        final String filePath = "." + File.separator + "data" + File.separator + "Amadeus.txt";
        new Amadeus(filePath).run();
    }

    /**
     * Inner class to capture UI output as a string for GUI usage.
     */
    private static class StringCapturingUi extends Ui {
        private StringBuilder capturedOutput = new StringBuilder();

        @Override
        public void printMessage(String message) {
            if (capturedOutput.length() > 0) {
                capturedOutput.append("\n");
            }
            capturedOutput.append(message);
        }

        @Override
        public void showLoadingError() {
            printMessage("Error loading tasks from file.");
        }

        /**
         * Gets the captured output and resets the buffer.
         *
         * @return The captured output string
         */
        public String getCapturedOutput() {
            String output = capturedOutput.toString();
            capturedOutput = new StringBuilder();
            return output;
        }
    }
}