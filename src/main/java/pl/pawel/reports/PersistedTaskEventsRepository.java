package pl.pawel.reports;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PersistedTaskEventsRepository extends JpaRepository<PersistedTaskEvents, Integer> {
    List<PersistedTaskEvents> findByTaskId(long taskId);
    List<PersistedTaskEvents> findByTaskIdAndOccurrenceBefore(long taskId, LocalDateTime occurrence);
}
