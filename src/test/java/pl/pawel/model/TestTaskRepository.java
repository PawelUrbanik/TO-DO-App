package pl.pawel.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.pawel.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.*;

public class TestTaskRepository implements TaskRepository {

    private Map<Long, Task> tasks = new HashMap<>();



    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Page<Task> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public Task save(Task task) {
        final long key = tasks.size() + 1;
        try {
            var field = Task.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(task, key);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        tasks.put(key, task);
        return tasks.get(key);

    }

    @Override
    public List<Task> findByDone(boolean done) {
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return tasks.containsKey(id);
    }

    @Override
    public boolean existsByDoneIsFalseAndGroup_Id(Long groupId) {
        return false;
    }

    @Override
    public List<Task> findAllByGroup_Id(Long groupId) {
        return null;
    }

    @Override
    public List<Task> findAllByDoneFalseAndDeadlineNullOrDeadlineIsBefore(LocalDateTime dateTime) {
        return null;
    }

}
