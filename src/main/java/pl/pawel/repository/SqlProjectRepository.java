package pl.pawel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.pawel.model.Project;

import java.util.List;

@Repository
public interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {


    /**
     * Wyszukuje projekty i kroki dla projektów. tylko te które mają kroki
     * @return
     */
    @Override
    @Query(value = "select distinct p from Project p join fetch p.steps")
    List<Project> findAll();
}
