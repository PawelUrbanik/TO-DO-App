package pl.pawel.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<TaskGroups> taskGroups;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<ProjectSteps> steps;

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TaskGroups> getTaskGroups() {
        return taskGroups;
    }

    public void setTaskGroups(Set<TaskGroups> taskGroups) {
        this.taskGroups = taskGroups;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id && Objects.equals(description, project.description) && Objects.equals(taskGroups, project.taskGroups) && Objects.equals(steps, project.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, taskGroups, steps);
    }
}
