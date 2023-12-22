package pl.pawel.model.projection;

import pl.pawel.model.Task;
import pl.pawel.model.TaskGroups;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupReadModel {

    private long id;

    private String description;
    /**
     * The deadline of latest task
     */
    private LocalDateTime deadline;
    private List<GroupTaskReadModel> tasks;

    public GroupReadModel(TaskGroups source) {
        this.description = source.getDescription();
        this.id = source.getId();
        source.getTasks().stream()
                .map(Task::getDeadline)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .ifPresent(lastDeadline -> deadline=lastDeadline);
        tasks = source.getTasks().stream().map(GroupTaskReadModel::new).collect(Collectors.toList());
    }

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

    public List<GroupTaskReadModel> getTasks() {
        return tasks;
    }

    public void setTasks(List<GroupTaskReadModel> tasks) {
        this.tasks = tasks;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
