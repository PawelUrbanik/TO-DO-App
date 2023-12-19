package pl.pawel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.pawel.model.Task;

import java.time.LocalDateTime;
import java.util.List;

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Long> {

    @Override
    @Query(nativeQuery = true, value = "select count(*) >0 from tasks where id=?1")
    boolean existsById(@Param("id") Long id);

    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Long groupId);

    @Override
    List<Task> findAllByGroup_Id(Long groupId);

    @Override
    @Query("select t from Task t where t.done = false and (t.deadline is null or t.deadline < :deadline)")
    List<Task> findTasksByDoneIsFalseAndDeadlineIsNullorDeadlineBefore(@Param("deadline") LocalDateTime dateTime);
}
