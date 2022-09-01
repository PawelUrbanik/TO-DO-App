package pl.pawel.service;

import org.springframework.stereotype.Service;
import pl.pawel.TaskConfigurationProperties;
import pl.pawel.model.Project;
import pl.pawel.model.Task;
import pl.pawel.model.TaskGroups;
import pl.pawel.model.projection.GroupReadModel;
import pl.pawel.repository.ProjectRepository;
import pl.pawel.repository.TaskGroupRepository;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    ProjectRepository projectRepository;
    TaskGroupRepository taskGroupRepository;
    TaskConfigurationProperties taskConfigurationProperties;


    public ProjectService(ProjectRepository projectRepository, TaskGroupRepository taskGroupRepository, TaskConfigurationProperties taskConfigurationProperties) {
        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.taskConfigurationProperties = taskConfigurationProperties;
    }

    public Project createPoject(final Project projectToSave) {
        return projectRepository.save(projectToSave);
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public GroupReadModel createGroup(int projectId, LocalDateTime deadline) {
        if (!taskConfigurationProperties.getTemplte().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone task from project is allowed");
        }

        TaskGroups result = projectRepository.findById(projectId)
                .map(project -> {
                    TaskGroups taskGroup = new TaskGroups();
                    taskGroup.setDescription(project.getDescription());
                    taskGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectSteps -> new Task(
                                            projectSteps.getDescription(),
                                            deadline.plusDays(projectSteps.getDaysToDeadline())
                                    )).collect(Collectors.toSet())
                    );
                    taskGroup.setProject(project);
                    return taskGroupRepository.save(taskGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
         return new GroupReadModel(result);
    }
}
