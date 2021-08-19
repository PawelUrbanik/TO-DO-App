package pl.pawel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import pl.pawel.model.Note;

@RepositoryRestResource
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Override
    @RestResource(exported = false) //Metoda niedostępna na zewnątrz
    void deleteById(Long aLong);

    @Override
    @RestResource(exported = false)
    void delete(Note note);
}
