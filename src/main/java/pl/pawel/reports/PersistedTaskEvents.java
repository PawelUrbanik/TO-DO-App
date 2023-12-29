package pl.pawel.reports;

import pl.pawel.event.TaskEvent;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "task_events")
public class PersistedTaskEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    LocalDateTime occurrence;
    long taskId;
    String name;

    public PersistedTaskEvents() {
    }

    PersistedTaskEvents(TaskEvent event) {
        this.taskId = event.getTaskId();
        this.name = event.getClass().getSimpleName();
        this.occurrence = LocalDateTime.ofInstant(event.getOccurrence(), ZoneId.systemDefault());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(LocalDateTime occurrence) {
        this.occurrence = occurrence;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}