package AmadeusK;
public class Ui {
    public void printMessage(String message) {
        String horizontalLine = "    ____________________________________________________________";
        String[] lines = message.split("\n");
        System.out.println(horizontalLine);
        for (String line : lines) {
            System.out.println("     " + line);
        }
        System.out.println(horizontalLine);
        System.out.println();
    }
    public void showLoadingError() {
        System.out.println("Oops! Something went wrong while loading your tasks.");
    }
}
