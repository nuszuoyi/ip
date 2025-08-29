import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;



public class AmadeusK {
    private static final String DATA_PATH = "." + File.separator + "data" + File.separator + "AmadeusK.txt";

    public static void printMessage(String message){
            String horizontalLine= "    ____________________________________________________________";
            String[] lines = message.split("\n");  
            
            System.out.println(horizontalLine);
            for (String line : lines) {
                System.out.println("     " + line);  
            }
            System.out.println(horizontalLine);
            System.out.println();
        }
    

    public static void saveTasks(ArrayList<Task> tasks) {
        try {
            File dir = new File("." + File.separator + "data");
            if (!dir.exists()) dir.mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_PATH));
            for (Task task : tasks) {
                String line = "";
                if (task instanceof ToDo) {
                    line = String.format("T | %d | %s", task.isDone ? 1 : 0, task.getDescription());
                } else if (task instanceof Deadline) {
                    Deadline d = (Deadline) task;
                    line = String.format("D | %d | %s | %s", d.isDone ? 1 : 0, d.getDescription(), d.getByRaw());
                } else if (task instanceof Event) {
                    Event e = (Event) task;
                    line = String.format("E | %d | %s | %s | %s", e.isDone ? 1 : 0, e.getDescription(), e.from, e.to);
                }
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(DATA_PATH);
        if (!file.exists()) return tasks;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(DATA_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(" \\| ");
                    String type = parts[0];
                    boolean isDone = "1".equals(parts[1]);
                    if ("T".equals(type)) {
                        ToDo t = new ToDo(parts[2]);
                        if (isDone) t.markAsDone();
                        tasks.add(t);
                    } else if ("D".equals(type)) {
                        Deadline d = new Deadline(parts[2], parts[3]);
                        if (isDone) d.markAsDone();
                        tasks.add(d);
                    } else if ("E".equals(type)) {
                        Event e = new Event(parts[2], parts[3], parts[4]);
                        if (isDone) e.markAsDone();
                        tasks.add(e);
                    }
                } catch (Exception ex) {
                    // skip corrupted line
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);  
        ArrayList<Task> tasks = loadTasks();
        
        printMessage("Hello! I'm AmadeusK\nWhat can I do for you?");
        String input = sc.nextLine();  
        
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                StringBuilder output = new StringBuilder().append("Here are the tasks in your list:\n");
                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    output.append(i+1 + ".").append(task.toString()).append("\n");
                }
                
                printMessage(output.toString());
                input = sc.nextLine();
            } 
            else if (input.startsWith("mark")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task task = tasks.get(index);
                        task.markAsDone();
                        printMessage("Nice! I've marked this task as done:\n  [X] " + task.getDescription());
                        saveTasks(tasks);
                    } else {
                        printMessage("Sorry, that task number does not exist.");
                    }
                } catch (Exception e) {
                    printMessage("Please enter a valid task number to mark.");
                }
                input = sc.nextLine();
            }
            else if (input.startsWith("unmark")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task task = tasks.get(index);
                        task.markAsUndone();
                        printMessage("OK, I've marked this task as not done yet:\n  [ ] " + task.getDescription());
                        saveTasks(tasks);
                    } else {
                        printMessage("Sorry, that task number does not exist.");
                    }
                } catch (Exception e) {
                    printMessage("Please enter a valid task number to unmark.");
                }
                input = sc.nextLine();
            }
            else if (input.startsWith("todo")) {
                String description = input.substring(5);
                Task newTask = new ToDo(description);
                tasks.add(newTask);
                printMessage("Got it. I've added this task:\n  " + newTask + "\nNow you have " + tasks.size() + " tasks in the list.");
                saveTasks(tasks);
                input = sc.nextLine();
            }
            else if (input.startsWith("deadline")) {
                String[] parts = input.substring(9).split(" /by ");
                if (parts.length == 2) {
                    Task newTask = new Deadline(parts[0], parts[1]);
                    tasks.add(newTask);
                    printMessage("Got it. I've added this task:\n  " + newTask + "\nNow you have " + tasks.size() + " tasks in the list.");
                    saveTasks(tasks);
                } else {
                    printMessage("Please use the format: deadline <description> /by <yyyy-mm-dd>");
                }
                input = sc.nextLine();
            }
            else if (input.startsWith("event")) {
                String[] parts = input.substring(6).split(" /from ");
                if (parts.length == 2) {
                    String[] timeParts = parts[1].split(" /to ");
                    if (timeParts.length == 2) {
                        Task newTask = new Event(parts[0], timeParts[0], timeParts[1]);
                        tasks.add(newTask);
                        printMessage("Got it. I've added this task:\n  " + newTask + "\nNow you have " + tasks.size() + " tasks in the list.");
                        saveTasks(tasks);
                    } else {
                        printMessage("Please use the format: event <description> /from <start> /to <end>");
                    }
                } else {
                    printMessage("Please use the format: event <description> /from <start> /to <end>");
                }
                input = sc.nextLine();
            }
            else if (input.startsWith("delete")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task removed = tasks.remove(index);
                        printMessage("Noted. I've removed this task:\n  " + removed + "\nNow you have " + tasks.size() + " tasks in the list.");
                        saveTasks(tasks);
                    } else {
                        printMessage("Sorry, that task number does not exist.");
                    }
                } catch (Exception e) {
                    printMessage("Please enter a valid task number to delete.");
                }
                input = sc.nextLine();
            }
            else {
                printMessage("Sorry, I didn't understand that command. Please try again!");
                input = sc.nextLine(); 
            }
        }
        printMessage("Bye. Hope to see you again soon!");
        sc.close(); 
    }
}


