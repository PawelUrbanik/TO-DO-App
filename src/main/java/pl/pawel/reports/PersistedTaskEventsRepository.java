package pl.pawel.reports;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersistedTaskEventsRepository extends JpaRepository<PersistedTaskEvents, Integer> {
    List<PersistedTaskEvents> findByTaskId(long taskId);
}
