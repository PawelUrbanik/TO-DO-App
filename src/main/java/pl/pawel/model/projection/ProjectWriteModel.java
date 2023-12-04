package pl.pawel.model.projection;

import pl.pawel.model.Project;
import pl.pawel.model.ProjectSteps;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ProjectWriteModel {

    @NotBlank( message = "Project description must not be empty")
    private String description;

    @Valid
    private List<ProjectSteps> steps = new ArrayList<>();

    public ProjectWriteModel() {
        steps.add(new ProjectSteps());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProjectSteps> getSteps() {
        return steps;
    }

    public void setSteps(List<ProjectSteps> steps) {
        this.steps = steps;
    }

    public Project toProject() {
        Project project = new Project();
        project.setDescription(getDescription());
        steps.forEach(step -> step.setProject(project));
        project.setSteps(new HashSet<>(steps));
        return project;
    }
}