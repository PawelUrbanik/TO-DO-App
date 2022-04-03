package pl.pawel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawel.model.TaskGroups;

@Repository
public interface SqlTaskGroupRepository  extends TaskGroupRepository, JpaRepository<TaskGroups, Long> {
}
