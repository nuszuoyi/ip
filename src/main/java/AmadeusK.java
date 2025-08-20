import java.util.Scanner;
import java.util.ArrayList;

public class AmadeusK {
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
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);  
        ArrayList<Task> tasks = new ArrayList<>();
        
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
                input = sc.nextLine();
            }
            else if (input.startsWith("deadline")) {
                String[] parts = input.substring(9).split(" /by ");
                if (parts.length == 2) {
                    Task newTask = new Deadline(parts[0], parts[1]);
                    tasks.add(newTask);
                    printMessage("Got it. I've added this task:\n  " + newTask + "\nNow you have " + tasks.size() + " tasks in the list.");
                } else {
                    printMessage("Please use the format: deadline <description> /by <time>");
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


