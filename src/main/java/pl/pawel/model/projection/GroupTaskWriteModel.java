package pl.pawel.model.projection;

import org.springframework.format.annotation.DateTimeFormat;
import pl.pawel.model.Task;
import pl.pawel.model.TaskGroups;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

public class GroupTaskWriteModel {
    @NotBlank(message = "Opis Taska nie może byc pusty")
    private String description;
    @NotNull(message = "Deadline taska nie może być pusty")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;

    private List<Task> tasks = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Task toTask(final TaskGroups group){
        return new Task(description, deadline, group);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
