package pl.pawel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawel.TaskConfigurationProperties;
import pl.pawel.model.Project;
import pl.pawel.model.projection.GroupReadModel;
import pl.pawel.model.projection.GroupTaskWriteModel;
import pl.pawel.model.projection.GroupWriteModel;
import pl.pawel.model.projection.ProjectWriteModel;
import pl.pawel.repository.ProjectRepository;
import pl.pawel.repository.TaskGroupRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    ProjectRepository projectRepository;
    TaskGroupRepository taskGroupRepository;
    TaskConfigurationProperties taskConfigurationProperties;
    private TaskGroupService taskGroupService;


    public ProjectService(ProjectRepository projectRepository, TaskGroupRepository taskGroupRepository, TaskGroupService taskGroupService, TaskConfigurationProperties taskConfigurationProperties) {
        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.taskConfigurationProperties = taskConfigurationProperties;
        this.taskGroupService = taskGroupService;
    }

    public Project createProject(final ProjectWriteModel projectToSave) {
        return projectRepository.save(projectToSave.toProject());
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Transactional
    public GroupReadModel createGroup(int projectId, LocalDateTime deadline) {
        if (!taskConfigurationProperties.getTemplte().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone task from project is allowed");
        }

        GroupReadModel result = projectRepository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectSteps -> {
                                        var task = new GroupTaskWriteModel();
                                        task.setDescription(projectSteps.getDescription());
                                        task.setDeadline(deadline.plusDays(projectSteps.getDaysToDeadline()));
                                        return task;
                                    }).collect(Collectors.toSet())
                    );
                    return taskGroupService.createGroup(targetGroup, project);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
        return result;
    }
}
