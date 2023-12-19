package pl.pawel.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    public Task() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Opis Taska nie może byc pusty")
    private String description;
    private boolean done;
    private LocalDateTime deadline;
    @Embedded
    private Audit audit = new Audit();

    @ManyToOne
    @JoinColumn(name = "task_group_id")
    private TaskGroups group;

    public Task(String description, LocalDateTime deadline) {
        this(description, deadline, null);
    }

    public Task(String description, LocalDateTime deadline, TaskGroups group) {
        this.description=description;
        this.deadline = deadline;
        if (group != null) {
            this.setGroup(group);
        }
    }
    void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    TaskGroups getGroup() {
        return group;
    }

    void setGroup(TaskGroups group) {
        this.group = group;
    }

    public void updateFrom(final Task toUpdate) {
        setDone(toUpdate.isDone());
        setDescription(toUpdate.getDescription());
        setDeadline(toUpdate.getDeadline());
        setGroup(toUpdate.getGroup());
    }
}
