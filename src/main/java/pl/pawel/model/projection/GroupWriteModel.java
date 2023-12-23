package pl.pawel.model.projection;

import pl.pawel.model.Project;
import pl.pawel.model.TaskGroups;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class GroupWriteModel {

    @NotBlank(message = "Opis Grupy nie może byc pusty")
    private String description;
    private List<GroupTaskWriteModel> tasks = new ArrayList<>();

    public TaskGroups toGroup(Project project){
        var group = new TaskGroups();
        group.setDescription(this.description);
        group.setTasks(
                tasks.stream()
                        .map(source -> source.toTask(group))
                        .collect(Collectors.toSet())
        );
        group.setProject(project);
        return group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GroupTaskWriteModel> getTasks() {
        return tasks;
    }

    public void setTasks(List<GroupTaskWriteModel> tasks) {
        this.tasks = tasks;
    }
}
