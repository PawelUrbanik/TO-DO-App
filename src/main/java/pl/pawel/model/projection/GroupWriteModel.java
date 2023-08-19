package pl.pawel.model.projection;

import pl.pawel.model.Task;
import pl.pawel.model.TaskGroups;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {

    private String description;
    private Set<GroupTaskWriteModel> tasks;

    public TaskGroups toGroup(){
        var group = new TaskGroups();
        group.setDescription(this.description);
        group.setTasks(
                tasks.stream()
                        .map(source -> source.toTask(group))
                        .collect(Collectors.toSet())
        );
        return group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<GroupTaskWriteModel> getTasks() {
        return tasks;
    }

    public void setTasks(Set<GroupTaskWriteModel> tasks) {
        this.tasks = tasks;
    }
}
