import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private int id;
    private String description;
    private String status; // todo, in-progress, done
    private String createdAt;
    private String updatedAt;

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = "todo";
        this.createdAt = getCurrentTime();
        this.updatedAt = this.createdAt;
    }

    public int getId() {
        return id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
        updateTimestamp();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        updateTimestamp();
    }

    public String getCreatedAt() {
        return createdAt;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public String toString() {
        return "[" + id + "] " + description + " - " + status +
                " - Created: " + createdAt + " | Updated: " + updatedAt;
    }
    private void updateTimestamp(){
        this.updatedAt = getCurrentTime();

    }

}