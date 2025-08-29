package AmadeusK;
import java.util.Scanner;

import Exception.AmadeusKException;
import Storage.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import task.TaskList;
import task.ToDo;

import java.io.File;

public class AmadeusK {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public AmadeusK(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (AmadeusKException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        ui.printMessage("Hello! I'm AmadeusK\nWhat can I do for you?");
        String input = sc.nextLine();

        while (!input.equals("bye")) {
            if (input.equals("list")) {
                StringBuilder output = new StringBuilder().append("Here are the tasks in your list:\n");
                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    output.append(i + 1).append(".").append(task.toString()).append("\n");
                }
                ui.printMessage(output.toString());
            }
            else if (input.startsWith("mark")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task task = tasks.get(index);
                        task.markAsDone();
                        ui.printMessage("Nice! I've marked this task as done:\n  [X] " + task.getDescription());
                        storage.saveTasks(tasks.getTasks());
                    } else {
                        ui.printMessage("Sorry, that task number does not exist.");
                    }
                } catch (Exception e) {
                    ui.printMessage("Please enter a valid task number to mark.");
                }
            }
            else if (input.startsWith("unmark")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task task = tasks.get(index);
                        task.markAsUndone();
                        ui.printMessage("OK, I've marked this task as not done yet:\n  [ ] " + task.getDescription());
                        storage.saveTasks(tasks.getTasks());
                    } else {
                        ui.printMessage("Sorry, that task number does not exist.");
                    }
                } catch (Exception e) {
                    ui.printMessage("Please enter a valid task number to unmark.");
                }
            }
            else if (input.startsWith("todo")) {
                String description = input.substring(5);
                Task newTask = new ToDo(description);
                tasks.add(newTask);
                ui.printMessage("Got it. I've added this task:\n  " + newTask
                        + "\nNow you have " + tasks.size() + " tasks in the list.");
                storage.saveTasks(tasks.getTasks());
            }
            else if (input.startsWith("deadline")) {
                String[] parts = input.substring(9).split(" /by ");
                if (parts.length == 2) {
                    Task newTask = new Deadline(parts[0], parts[1]);
                    tasks.add(newTask);
                    ui.printMessage("Got it. I've added this task:\n  " + newTask
                            + "\nNow you have " + tasks.size() + " tasks in the list.");
                    storage.saveTasks(tasks.getTasks());
                } else {
                    ui.printMessage("Please use the format: deadline <description> /by <yyyy-mm-dd>");
                }
            }
            else if (input.startsWith("event")) {
                String[] parts = input.substring(6).split(" /from ");
                if (parts.length == 2) {
                    String[] timeParts = parts[1].split(" /to ");
                    if (timeParts.length == 2) {
                        Task newTask = new Event(parts[0], timeParts[0], timeParts[1]);
                        tasks.add(newTask);
                        ui.printMessage("Got it. I've added this task:\n  " + newTask
                                + "\nNow you have " + tasks.size() + " tasks in the list.");
                        storage.saveTasks(tasks.getTasks());
                    } else {
                        ui.printMessage("Please use the format: event <description> /from <start> /to <end>");
                    }
                } else {
                    ui.printMessage("Please use the format: event <description> /from <start> /to <end>");
                }
            }
            else if (input.startsWith("delete")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task removed = tasks.remove(index);
                        ui.printMessage("Noted. I've removed this task:\n  " + removed
                                + "\nNow you have " + tasks.size() + " tasks in the list.");
                        storage.saveTasks(tasks.getTasks());
                    } else {
                        ui.printMessage("Sorry, that task number does not exist.");
                    }
                } catch (Exception e) {
                    ui.printMessage("Please enter a valid task number to delete.");
                }
            }
            else {
                ui.printMessage("Sorry, I didn't understand that command. Please try again!");
            }

            input = sc.nextLine();
        }

        ui.printMessage("Bye. Hope to see you again soon!");
        sc.close();
    }

    public static void main(String[] args) {
        final String filePath = "." + File.separator + "data" + File.separator + "AmadeusK.txt";
        new AmadeusK(filePath).run();
    }
}
