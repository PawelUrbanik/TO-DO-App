package pl.pawel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pawel.model.Task;
import pl.pawel.model.projection.GroupReadModel;
import pl.pawel.model.projection.GroupWriteModel;
import pl.pawel.service.TaskGroupService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class TaskGroupController {


    private final TaskGroupService taskGroupService;

    public TaskGroupController(TaskGroupService taskGroupService) {
        this.taskGroupService = taskGroupService;
    }

    @GetMapping
    public ResponseEntity<List<GroupReadModel>> getAllTaskGroups(){
        final List<GroupReadModel> taskGroups = taskGroupService.getAll();

        return ResponseEntity.ok(taskGroups);
    }


    @PostMapping
    public ResponseEntity<GroupReadModel> createTaskGroup(@RequestBody @Valid GroupWriteModel writeModel) {
        GroupReadModel saved = taskGroupService.createGroup(writeModel);

        if (saved == null) return ResponseEntity.badRequest().build();
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<Task>> getAllTasksByGroupId(@PathVariable Long id) {
        List<Task> tasks = taskGroupService.getAllTasksByGroupId(id);
        return ResponseEntity.ok(tasks);
    }
}
