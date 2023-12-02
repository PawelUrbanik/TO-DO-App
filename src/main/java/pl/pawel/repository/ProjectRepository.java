package pl.pawel.repository;

import pl.pawel.model.Project;
import pl.pawel.model.projection.ProjectWriteModel;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    List<Project> findAll();

    Optional<Project> findById(Integer id);

    Project save(ProjectWriteModel project);

}
