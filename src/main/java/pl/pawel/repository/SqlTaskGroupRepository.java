package pl.pawel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.pawel.model.TaskGroups;

import java.util.List;

@Repository
public interface SqlTaskGroupRepository  extends TaskGroupRepository, JpaRepository<TaskGroups, Long> {

    //Zapytanie poprzez HQL
    //Skrócony SELECT, zamiast nazwy tabeli nazwa encji/Klasy
    @Override
    @Query(nativeQuery = false, value = "from TaskGroups g join fetch g.tasks")
    List<TaskGroups> findAll();

}
