import java.util.ArrayList;
import java.io.*;

public class Storage  {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void saveTasks(ArrayList<Task> tasks) {
        try {
            File dir = new File(filePath).getParentFile();
            if (!dir.exists()) dir.mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (Task task : tasks) {
                String line = "";
                if (task instanceof ToDo) {
                    line = String.format("T | %d | %s", task.isDone ? 1 : 0, task.getDescription());
                } else if (task instanceof Deadline) {
                    Deadline d = (Deadline) task;
                    line = String.format("D | %d | %s | %s", d.isDone ? 1 : 0, d.getDescription(), d.getByRaw());
                } else if (task instanceof Event) {
                    Event e = (Event) task;
                    line = String.format("E | %d | %s | %s | %s", e.isDone ? 1 : 0, e.getDescription(), e.getFromRaw(), e.getToRaw());
                }
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public ArrayList<Task> loadTasks() throws AmadeusKException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return tasks;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
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
            throw new AmadeusKException("Error loading tasks from file: " + e.getMessage());
        }
        return tasks;
    }
}
