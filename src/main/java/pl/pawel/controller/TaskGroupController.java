package pl.pawel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.pawel.model.Task;
import pl.pawel.model.projection.GroupReadModel;
import pl.pawel.model.projection.GroupWriteModel;
import pl.pawel.repository.TaskGroupRepository;
import pl.pawel.service.TaskGroupService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class TaskGroupController {


    private final TaskGroupService taskGroupService;
    private final TaskGroupRepository taskGroupRepository;

    public TaskGroupController(TaskGroupService taskGroupService, TaskGroupRepository taskGroupRepository) {
        this.taskGroupService = taskGroupService;
        this.taskGroupRepository = taskGroupRepository;
    }

    @GetMapping
    public ResponseEntity<List<GroupReadModel>> getAllTaskGroups() {
        final List<GroupReadModel> taskGroups = taskGroupService.getAll();

        return ResponseEntity.ok(taskGroups);
    }


    @PostMapping
    public ResponseEntity<GroupReadModel> createTaskGroup(@RequestBody @Valid GroupWriteModel writeModel) {
        GroupReadModel saved = taskGroupService.createGroup(writeModel);

        if (saved == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.created(URI.create("/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<Task>> getAllTasksByGroupId(@PathVariable Long id) {
        List<Task> tasks = taskGroupService.getAllTasksByGroupId(id);
        return ResponseEntity.ok(tasks);
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable Long id) {
        taskGroupService.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

}
