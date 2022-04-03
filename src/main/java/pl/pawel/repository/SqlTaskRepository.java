package pl.pawel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawel.model.Task;

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Long> {

    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);
}
