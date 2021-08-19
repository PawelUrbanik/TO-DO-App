package pl.pawel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import pl.pawel.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Pobiera taski według przekazanego parametru
     * @param done true lub false
     * @return Lista tasków
     */
    @RestResource(path = "done", rel = "done")
    List<Task> findByDone(@Param("state") boolean done);
}
