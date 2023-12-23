package pl.pawel.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pawel.model.Task;
import pl.pawel.model.TaskGroups;
import pl.pawel.model.projection.GroupReadModel;
import pl.pawel.model.projection.GroupTaskWriteModel;
import pl.pawel.model.projection.GroupWriteModel;
import pl.pawel.repository.TaskGroupRepository;
import pl.pawel.service.TaskGroupService;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import java.net.BindException;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/groups")
public class TaskGroupController {


    private final TaskGroupService taskGroupService;
    private final TaskGroupRepository taskGroupRepository;

    public TaskGroupController(TaskGroupService taskGroupService, TaskGroupRepository taskGroupRepository) {
        this.taskGroupService = taskGroupService;
        this.taskGroupRepository = taskGroupRepository;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String allGroups(Model model) {
        GroupTaskWriteModel groupTaskWriteModel = new GroupTaskWriteModel();
        model.addAttribute("group", groupTaskWriteModel);
        return "groups";
    }

    @PostMapping(params = "addTask", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addTaskToGroup(@ModelAttribute("group") GroupTaskWriteModel current) {
        current.getTasks().add(new Task());
        return "groups";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addGroup(
            @ModelAttribute("group") @Valid GroupWriteModel writeModel,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "groups";
        }

        taskGroupService.createGroup(writeModel);
        model.addAttribute("group", new GroupWriteModel());
        model.addAttribute("groups", getGroups());
        model.addAttribute("message", "Dodano grupę");

        return "groups";
    }

    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GroupReadModel>> getAllTaskGroups() {
        final List<GroupReadModel> taskGroups = taskGroupService.getAll();

        return ResponseEntity.ok(taskGroups);
    }


    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupReadModel> createTaskGroup(@RequestBody @Valid GroupWriteModel writeModel) {
        GroupReadModel saved = taskGroupService.createGroup(writeModel);

        if (saved == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.created(URI.create("/" + saved.getId())).body(saved);
    }

    @ResponseBody
    @GetMapping(value = "/{id}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Task>> getAllTasksByGroupId(@PathVariable Long id) {
        List<Task> tasks = taskGroupService.getAllTasksByGroupId(id);
        return ResponseEntity.ok(tasks);
    }

    @ResponseBody
    @Transactional
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> toggleGroup(@PathVariable Long id) {
        taskGroupService.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ModelAttribute("groups")
    List<GroupReadModel> getGroups() {
        return taskGroupService.getAll();
    }
}
