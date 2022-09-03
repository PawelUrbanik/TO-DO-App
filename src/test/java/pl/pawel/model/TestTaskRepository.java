package pl.pawel.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.pawel.repository.TaskRepository;

import java.util.*;

public class TestTaskRepository implements TaskRepository {

    private Map<Integer, Task> tasks = Collections.emptyMap();



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
        return tasks.put(tasks.size()+1, task);
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
    public boolean existsByDoneIsFalseAndGroup_Id(Integer groupId) {
        return false;
    }
}
