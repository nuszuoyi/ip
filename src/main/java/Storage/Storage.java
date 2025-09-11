package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.AmadeusException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

/**
 * Handles saving and loading tasks from the local file system.
 */
public class Storage {

    /** File path where tasks are saved and loaded from */
    private final String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath Path to the file for storing tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the given list of tasks to the file.
     *
     * @param tasks List of tasks to be saved.
     */
    public void saveTasks(ArrayList<Task> tasks) {
        File dir = new File(filePath).getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                String line = "";
                if (task instanceof ToDo) {
                    line = String.format("T | %d | %s", task.isDone() ? 1 : 0, task.getDescription());
                } else if (task instanceof Deadline) {
                    Deadline d = (Deadline) task;
                    line = String.format("D | %d | %s | %s",
                            d.isDone() ? 1 : 0, d.getDescription(), d.getByRaw());
                } else if (task instanceof Event) {
                    Event e = (Event) task;
                    line = String.format("E | %d | %s | %s | %s",
                            e.isDone() ? 1 : 0, e.getDescription(), e.getFromRaw(), e.getToRaw());
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the file.
     *
     * @return List of tasks loaded from the file.
     * @throws AmadeusException If an I/O error occurs during loading.
     */
    public ArrayList<Task> loadTasks() throws AmadeusException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(" \\| ");
                    String type = parts[0];
                    boolean isDone = "1".equals(parts[1]);

                    if ("T".equals(type)) {
                        ToDo t = new ToDo(parts[2]);
                        if (isDone) {
                            t.markAsDone();
                        }
                        tasks.add(t);
                    } else if ("D".equals(type)) {
                        Deadline d = new Deadline(parts[2], parts[3]);
                        if (isDone) {
                            d.markAsDone();
                        }
                        tasks.add(d);
                    } else if ("E".equals(type)) {
                        Event e = new Event(parts[2], parts[3], parts[4]);
                        if (isDone) {
                            e.markAsDone();
                        }
                        tasks.add(e);
                    }
                } catch (Exception ex) {
                    // Skip corrupted line
                }
            }
        } catch (IOException e) {
            throw new AmadeusException("Error loading tasks from file: " + e.getMessage());
        }

        return tasks;
    }
}
