package pl.pawel.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task extends BaseAuditableEntity{

    public Task() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Opis nie może byc pusty")
    private String description;
    private boolean done;
    private LocalDateTime deadline;


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

    public void updateFrom(final Task toUpdate) {
        setDone(toUpdate.isDone());
        setDescription(toUpdate.getDescription());
        setDeadline(toUpdate.getDeadline());
    }
}
