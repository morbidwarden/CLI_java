import java.io.*;
import java.util.*;
import java.nio.file.*;

public class TaskManager {
    private static final String FILE_NAME = "tasks.json";

    // Save task list to file
    public static void saveTasks(List<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write("[\n");
            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                writer.write("  {\n");
                writer.write("    \"id\": " + t.getId() + ",\n");
                writer.write("    \"description\": \"" + t.getDescription().replace("\"", "\\\"") + "\",\n");
                writer.write("    \"status\": \"" + t.getStatus() + "\",\n");
                writer.write("    \"createdAt\": \"" + t.getCreatedAt() + "\",\n");
                writer.write("    \"updatedAt\": \"" + t.getUpdatedAt() + "\"\n");
                writer.write("  }" + (i < tasks.size() - 1 ? "," : "") + "\n");
            }
            writer.write("]");
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    // Load tasks from file
    public static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            if (!Files.exists(Paths.get(FILE_NAME))) {
                return tasks; // return empty if file doesn't exist
            }

            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
            String json = String.join("", lines);

            // Naive JSON parsing — assuming fixed format
            String[] items = json.split("\\{");
            int idCounter = 1;

            for (String item : items) {
                if (item.contains("description")) {
                    int id = Integer.parseInt(item.split("\"id\": ")[1].split(",")[0]);
                    String desc = item.split("\"description\": \"")[1].split("\",")[0];
                    String status = item.split("\"status\": \"")[1].split("\",")[0];
                    String createdAt = item.split("\"createdAt\": \"")[1].split("\",")[0];
                    String updatedAt = item.split("\"updatedAt\": \"")[1].split("\"")[0];

                    Task t = new Task(id, desc);
                    t.setStatus(status);
                    // manually setting timestamps (not done in constructor)
                    FieldSetter.setField(t, "createdAt", createdAt);
                    FieldSetter.setField(t, "updatedAt", updatedAt);

                    tasks.add(t);
                    idCounter++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }
}


