package pl.pawel.reports;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pawel.model.Task;
import pl.pawel.repository.TaskRepository;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final TaskRepository taskRepository;
    private final PersistedTaskEventsRepository eventsRepository;

    public ReportController(TaskRepository taskRepository, PersistedTaskEventsRepository persistedTaskEventsRepository) {
        this.taskRepository = taskRepository;
        this.eventsRepository = persistedTaskEventsRepository;
    }

    @GetMapping("/count/{id}")
    public ResponseEntity<TaskWithChangesCount> readTaskWithCount(@PathVariable long id) {
        return taskRepository.findById(id)
                .map(task -> new TaskWithChangesCount(task, eventsRepository.findByTaskId(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private static class TaskWithChangesCount {
        public String description;
        public boolean done;
        public int changesCount;
        TaskWithChangesCount(final Task task, final List<PersistedTaskEvents> events) {
            description = task.getDescription();
            done = task.isDone();
            changesCount = events.size();
        }
    }
}
