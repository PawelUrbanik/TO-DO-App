package pl.pawel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import pl.pawel.model.Note;
import pl.pawel.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    List<Task> findAll();

    Page<Task> findAll(Pageable pageable);

    Optional<Task> findById(Long id);

    Task save(Task task);

    /**
     * Pobiera taski według przekazanego parametru
     * @param done true lub false
     * @return Lista tasków
     */
    @RestResource(path = "done", rel = "done")
    List<Task> findByDone(@Param("state") boolean done);

    boolean existsById(Long id);
}
