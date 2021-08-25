package pl.pawel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pawel.model.Task;
import pl.pawel.repository.TaskRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    public static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository TaskRepository) {
        this.taskRepository = TaskRepository;
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

    @PutMapping("/tasks/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody @Valid Task task)
    {
        if (!taskRepository.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }

        if (task.getId()!=0 && !id.equals(task.getId()))
        {
            return ResponseEntity.badRequest().build();
        }

        task.setId(id);
        LOGGER.warn("Update obj");
        taskRepository.save(task);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id)
    {

        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()){
            return ResponseEntity.ok().body(task.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> createTask(@RequestBody @Valid Task taskToSave)
    {
        Task saved = taskRepository.save(taskToSave);

        if (saved == null) return ResponseEntity.badRequest().build();
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

}
