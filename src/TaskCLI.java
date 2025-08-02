import java.util.List;
import java.util.ArrayList;

public class TaskCLI {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No command provided. Usage: java TaskCLI <command> <args>");
            return;
        }

        String command = args[0];

        switch (command) {
            case "add":
                if (args.length < 2) {
                    System.out.println("Usage: java TaskCLI add <description>");
                    return;
                }
                String description = args[1];
                addTask(description);
                break;
            case "list":
                String filter = args.length > 1 ? args[1] : "all";
                listTasks(filter);
                break;
            case "mark-done":
                if (args.length < 2) {
                    System.out.println("Usage: java TaskCLI mark-done <id>");
                    return;
                }
                try {
                    int id = Integer.parseInt(args[1]);
                    markStatus(id, "done");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ID. Must be a number.");
                }
                break;
            case "mark-in-progress":
                if (args.length < 2) {
                    System.out.println("Usage: java TaskCLI mark-in-progress <id>");
                    return;
                }
                try {
                    int id = Integer.parseInt(args[1]);
                    markStatus(id, "in-progress");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ID. Must be a number.");
                }
                break;

            case "update":
                if (args.length < 3) {
                    System.out.println("Usage: java TaskCLI update <id> <new description>");
                    return;
                }

                try {
                    int idToUpdate = Integer.parseInt(args[1]);
                    String newDesc = args[2];
                    updateTask(idToUpdate, newDesc);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid task ID. It should be a number.");
                }
                break;

            default:
                System.out.println("Unknown command: " + command);
                break;
        }
    }
    private static void addTask(String description) {
        List<Task> tasks = TaskManager.loadTasks();

        // Find max ID and increment
        int nextId = 1;
        for (Task t : tasks) {
            if (t.getId() >= nextId) {
                nextId = t.getId() + 1;
            }
        }

        Task newTask = new Task(nextId, description);
        tasks.add(newTask);
        TaskManager.saveTasks(tasks);
        System.out.println("Task added successfully (ID: " + newTask.getId() + ")");
    }
    private static void listTasks(String filter){
        List<Task> tasks = TaskManager.loadTasks();
        boolean found = false;
        for( Task t : tasks){
            if(filter.equals("all") || t.getStatus().equalsIgnoreCase(filter)){
                System.out.println(t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No tasks found for filter: " + filter);
        }
    }
    private static void updateTask(int id, String updatedDescription){
        List<Task> tasks = TaskManager.loadTasks();
        boolean found = false;

        for (Task t : tasks) {
            if (t.getId() == id) {
                t.setDescription(updatedDescription);
                found = true;
                break;
            }
        }
        if (found) {
            TaskManager.saveTasks(tasks);
            System.out.println("Task updated successfully.");
        } else {
            System.out.println("Task with ID " + id + " not found.");
        }
    }
    private static void markStatus(int id,String status){
        List<Task> tasks = TaskManager.loadTasks();
        boolean found = false;
        for (Task t : tasks){
            if(t.getId()==id){
                t.setStatus(status);
                found = true;
            }
        }
        if(found){
            TaskManager.saveTasks(tasks);
            System.out.println("Task"+ id + "marked as "+ status+ ".");
        }else{
            System.out.println("Task with id "+ id +" not found." );
        }

    }
}
