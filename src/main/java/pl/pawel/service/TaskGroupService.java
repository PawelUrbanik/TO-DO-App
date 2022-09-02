package pl.pawel.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.pawel.model.TaskGroups;
import pl.pawel.model.projection.GroupReadModel;
import pl.pawel.model.projection.GroupWriteModel;
import pl.pawel.repository.TaskGroupRepository;
import pl.pawel.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestScope
public class TaskGroupService {

    private TaskGroupRepository taskGroupRepository;
    private TaskRepository taskRepository;

    public TaskGroupService(TaskGroupRepository repository, TaskRepository taskRepository) {
        this.taskGroupRepository = repository;
        this.taskRepository = taskRepository;
    }


    public GroupReadModel createGroup(GroupWriteModel writeModel)
    {
        TaskGroups result = taskGroupRepository.save(writeModel.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> getAll(){
        return taskGroupRepository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toogleGroup(int grupId)
    {
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(grupId))
        {
            throw new IllegalStateException("Grupa ma niezakończone taski. Zakończ taski.");
        }

        TaskGroups result= taskGroupRepository.findById(grupId).orElseThrow(()-> new IllegalArgumentException("Nie znaleziono taska z takim id"));
        result.setDone(!result.isDone());
        taskGroupRepository.save(result);
    }


}
