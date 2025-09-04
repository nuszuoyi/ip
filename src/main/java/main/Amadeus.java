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
     * Runs the main loop of the chatbot, reading user input and handling commands.
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
     * Main entry point of the application.
     *
     * @param args Command line arguments (ignored).
     */
    public static void main(String[] args) {
        final String filePath = "." + File.separator + "data" + File.separator + "Amadeus.txt";
        new Amadeus(filePath).run();
    }
}
