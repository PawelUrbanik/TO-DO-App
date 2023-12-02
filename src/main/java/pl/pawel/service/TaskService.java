package pl.pawel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.pawel.model.Task;
import pl.pawel.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TaskService {

    private final Logger logger = LoggerFactory.getLogger(TaskService.class);
    final private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Async
    public CompletableFuture<List<Task>> findAllAsync(){
        logger.info("Get all tasks async");
        return CompletableFuture.supplyAsync(taskRepository::findAll);
    }

    public List<Task> findTasksForToday() {
        LocalDateTime today = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        final List<Task> tasks = taskRepository.findAllByDoneFalseAndDeadlineNullOrDeadlineIsBefore(today);
        return tasks;
    }
}
