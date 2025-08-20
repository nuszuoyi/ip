import java.util.Scanner;  

public class AmadeusK {
     public static void printMessage(String message){
            String horizontalLine= "    ____________________________________________________________";
            String[] lines = message.split("\n");  
            
            System.out.println(horizontalLine);
            for (String line : lines) {
                System.out.println("     " + line.trim());  
            }
            System.out.println(horizontalLine);
            System.out.println();
        }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);  
        String recordedString = "";
        int count = 0;
        printMessage("Hello! I'm AmadeusK\n What can I do for you?");
        String input = sc.nextLine();  
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                
                printMessage(recordedString);
                input = sc.nextLine();
                
            } 
            else{

                printMessage("added: "+input);
                count++;
                recordedString += Integer.toString(count) + ". " + input + "\n";
                input = sc.nextLine(); 
            }
        }
        printMessage("Bye. Hope to see you again soon!");
        sc.close(); 
    }
}
