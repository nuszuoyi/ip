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
        
        printMessage("Hello! I'm AmadeusK\n What can I do for you?");
        String input = sc.nextLine();  
        
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                StringBuilder output = new StringBuilder().append("Here are the tasks in your list:\n");
                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    output.append(i + 1).append(".");
                    output.append("[" + task.getStatusIcon() + "] ");
                    output.append(task.getDescription()).append("\n");
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
                        printMessage("OOPS!!! The task number is invalid");
                    }
                } catch (Exception e) {
                    printMessage("OOPS!!! The task number is invalid");
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
                        printMessage("OOPS!!! The task number is invalid");
                    }
                } catch (Exception e) {
                    printMessage("OOPS!!! The task number is invalid");
                }
                input = sc.nextLine();
            }
            else {
                Task newTask = new Task(input);
                tasks.add(newTask);
                printMessage("added: " + input);
                input = sc.nextLine(); 
            }
        }
        printMessage("Bye. Hope to see you again soon!");
        sc.close(); 
    }
}

