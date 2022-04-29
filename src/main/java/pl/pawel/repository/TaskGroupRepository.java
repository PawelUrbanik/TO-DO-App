package pl.pawel.repository;

import pl.pawel.model.TaskGroups;

import java.util.List;
import java.util.Optional;

interface TaskGroupRepository {

    List<TaskGroups> findAll();

    Optional<TaskGroups> findById(Integer id);

    TaskGroups save(TaskGroups taskGroups);

    boolean existsByDoneIsFalseAndProject_Id(Integer projectId);
}
