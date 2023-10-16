package pl.pawel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.pawel.model.Task;
import pl.pawel.repository.TaskRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    public static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository TaskRepository) {
        this.taskRepository = TaskRepository;
    }

    @GetMapping(params = {"!sort", "!page", "!size"})
    public ResponseEntity<List<Task>> readAllTasks(){
        LOGGER.warn("REQUES GET ALL");
        return ResponseEntity.ok(taskRepository.findAll());
    }

    @GetMapping
    public ResponseEntity<List<Task>> readAllTasks(Pageable pageable) {
        LOGGER.warn("REQUES GET ALL WITH PARAMETER");
        return ResponseEntity.ok(taskRepository.findAll(pageable).getContent());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody @Valid Task toUpdate)
    {
        if (!taskRepository.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }

        if (toUpdate.getId()!=0 && !id.equals(toUpdate.getId()))
        {
            return ResponseEntity.badRequest().build();
        }

        taskRepository.findById(id).ifPresent(task -> {
            task.updateFrom(toUpdate);
            LOGGER.warn("Update obj");
            taskRepository.save(toUpdate);
        });

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id)
    {

        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()){
            return ResponseEntity.ok().body(task.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody @Valid Task taskToSave)
    {
        Task saved = taskRepository.save(taskToSave);

        if (saved == null) return ResponseEntity.badRequest().build();
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable Long id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.findById(id).ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("search/")
    ResponseEntity<List<Task>> findByDone(@RequestParam(defaultValue = "true", name = "dones") boolean done){
        return ResponseEntity.ok(
                taskRepository.findByDone(done)
        );
    }
}
