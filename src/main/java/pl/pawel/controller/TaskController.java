package pl.pawel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pawel.model.Task;
import pl.pawel.repository.TaskRepository;

import java.util.List;

@RestController
public class TaskController {

    public static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
    public ResponseEntity<List<Task>> readAllTasks(){
        LOGGER.warn("REQUES GET ALL");
        return ResponseEntity.ok(taskRepository.findAll());
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> readAllTasks(Pageable pageable) {
        LOGGER.warn("REQUES GET ALL WITH PARAMETER");
        return ResponseEntity.ok(taskRepository.findAll(pageable).getContent());
    }

}
